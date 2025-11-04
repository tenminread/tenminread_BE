package com.tenminread.service;

import com.tenminread.dto.HomeDtos.*;
import com.tenminread.domain.book.Book;
import com.tenminread.domain.home.HomeState;
import com.tenminread.repository.BookRepository;
import com.tenminread.repository.UserInterestRepository;
import com.tenminread.repository.HomeStateRepository;
import com.tenminread.repository.ReadingProgressRepository;
import com.tenminread.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HomeService {

  private static final ZoneId KST = ZoneId.of("Asia/Seoul");
  private static final String IDLE = "IDLE";
  private static final String READING = "READING";

  // 카테고리 맵(문학1, 역사2, 철학3, 사회4, 자연5, 기술6)
  public static final int CAT_LITERATURE = 1;
  public static final int CAT_HISTORY = 2;
  public static final int CAT_PHILOSOPHY = 3;
  public static final int CAT_SOCIAL = 4;
  public static final int CAT_NATURAL = 5;
  public static final int CAT_TECH = 6;

  private final HomeStateRepository homeStateRepo;
  private final BookRepository bookRepo;
  private final SummaryRepository summaryRepo;
  private final ReadingProgressRepository progressRepo;
  private final UserInterestRepository userInterestRepo;

  /** 베타 데이 전용: 값이 있으면 무조건 해당 bookid 고정, 공백/미설정이면 완전 무시 */
  @Value("${home.beta.fixed-book-id:}")
  private String betaFixedBookId;

  @Transactional
  public HomeTodayResponse getToday(Integer userId) {
    LocalDate todayKst = LocalDate.now(KST);

    // HomeState 없으면 생성
    HomeState state = homeStateRepo.findById(userId)
      .orElseGet(() -> homeStateRepo.save(HomeState.builder()
        .userId(userId)
        .anchorDate(todayKst) // 최초 진입이면 오늘 날짜가 기준
        .status(IDLE)
        .build()));

    // 1. 완독 확인 후 상태 전이
    // READING + 모든 seq가 100% 완료면 IDLE로 전환 + current 비움 + last 갱신 + anchorDate 오늘로 재설정
    if (Objects.equals(state.getStatus(), READING) && isFinished(userId, state.getCurrentBookid())) {
      state.setStatus(IDLE);
      state.setLastBookid(state.getCurrentBookid());
      state.setCurrentBookid(null);
      state.setAnchorDate(todayKst);
      homeStateRepo.save(state);
    }

    // 2. 오늘의 책 결정
    Integer fixedId = parseNullableInt(betaFixedBookId);
    if (fixedId != null) {
      // 베타 플래그가 있으면 무조건 고정 (없으면 즉시 무시)
      ensureBookExists(fixedId);
      if (!Objects.equals(state.getCurrentBookid(), fixedId)) {
        state.setCurrentBookid(fixedId);
        state.setAnchorDate(todayKst);
        homeStateRepo.save(state);
      }
    } else {
      // 정상 로직: IDLE이며 current_bookid가 없거나(미선정) anchor_date != 오늘이면 새로 뽑기
      if (Objects.equals(state.getStatus(), IDLE)
        && (state.getCurrentBookid() == null || !todayKst.equals(state.getAnchorDate()))) {

        List<Integer> interested = safeInterests(userId);
        List<Book> pool = interested.isEmpty()
          ? List.of() // 관심이 없으면 풀 비움(현재 스펙상 온보딩에서 최소 1개)
          : bookRepo.findAllByCategoryIds(interested);

        // 전일(today-1) 선정 제외
        Set<Integer> excludes = new HashSet<>();
        if (state.getLastBookid() != null) excludes.add(state.getLastBookid());

        Integer nextId = pickOneDeterministic(pool, userId, todayKst, "today", excludes);
        if (nextId != null) {
          state.setCurrentBookid(nextId);
          state.setAnchorDate(todayKst);
          homeStateRepo.save(state);
        }
      }
    }

    // 3. 진행률 계산 및 status 갱신
    TodayProgress progress = computeProgress(userId, state.getCurrentBookid());
    String calc = calcStatus(progress.progressPercent());
    if (!calc.equals(state.getStatus())) {
      // 완독인 경우는 위에서 처리 완료, 여기선 0%→IDLE, (0,100)→READING 갱신만
      state.setStatus(calc);
      homeStateRepo.save(state);
    }

    // 4. 카드 변환
    BookCard todayCard = null;
    if (state.getCurrentBookid() != null) {
      bookRepo.findById(state.getCurrentBookid())
        .ifPresent(b -> todayCard = new BookCard(b.getBookid(), b.getTitle(), b.getAuthor(),
          b.getCategory().getCategoryid()));
    }

    return new HomeTodayResponse(todayCard, progress, state.getStatus());
  }

  // ---------- 내부 유틸 ----------

  private boolean isFinished(Integer userId, Integer bookid) {
    if (bookid == null) return false;
    int total = summaryRepo.findAllByBookidOrderBySeq(bookid).size();
    if (total <= 0) return false;
    long done = progressRepo.findByUserIdAndBookidOrderBySeqAsc(userId, bookid).stream()
      .filter(p -> p.getProgress() != null && p.getProgress() >= 100.0)
      .count();
    return done >= total;
  }

  private TodayProgress computeProgress(Integer userId, Integer bookid) {
    if (bookid == null) return new TodayProgress(null, 0.0, 0, 0);
    int total = Math.max(1, summaryRepo.findAllByBookidOrderBySeq(bookid).size());
    long done = progressRepo.findByUserIdAndBookidOrderBySeqAsc(userId, bookid).stream()
      .filter(p -> p.getProgress() != null && p.getProgress() >= 100.0)
      .count();
    double pct = (done * 100.0) / total;
    return new TodayProgress(bookid, pct, (int) done, total);
  }

  private static String calcStatus(double pct) {
    if (pct <= 0.0) return IDLE;
    if (pct >= 100.0) return IDLE; // 완독은 별도로 처리
    return READING;
  }

  private List<Integer> safeInterests(Integer userId) {
    List<Integer> ids = userInterestRepo.findCategoryIdsByUser(userId);
    return ids == null ? List.of() : ids;
  }

  private Integer pickOneDeterministic(List<Book> pool, Integer userId, LocalDate day, String salt, Set<Integer> excludes) {
    if (pool == null || pool.isEmpty()) return null;
    // 제외 처리
    List<Book> candidates = pool.stream()
      .filter(b -> excludes == null || !excludes.contains(b.getBookid()))
      .toList();
    if (candidates.isEmpty()) return null;

    // 시드 기반 결정(유저+날짜+salt)
    long seed = seededLong(userId, day.toString(), salt);
    Random r = new Random(seed);
    Book pick = candidates.get(r.nextInt(candidates.size()));
    return pick.getBookid();
  }

  private static long seededLong(Integer userId, String... salts) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(("u:" + userId + "|" + String.join("|", salts)).getBytes());
      byte[] bytes = md.digest();
      long l = 0L;
      for (int i = 0; i < 8; i++) l = (l << 8) | (bytes[i] & 0xff);
      return l;
    } catch (Exception e) {
      return Objects.hash(userId, Arrays.hashCode(salts));
    }
  }

  private void ensureBookExists(Integer bookid) {
    if (!bookRepo.existsById(bookid)) {
      throw new IllegalStateException("home.beta.fixed-book-id=" + bookid + " 도서가 Book 테이블에 없습니다.");
    }
  }

  private static Integer parseNullableInt(String v) {
    if (v == null) return null;
    String t = v.trim();
    if (t.isEmpty()) return null;
    try { return Integer.parseInt(t); } catch (NumberFormatException e) { return null; }
  }
}
