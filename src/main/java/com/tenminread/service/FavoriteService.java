package com.tenminread.service; // (BookService가 있는 패키지)

import com.tenminread.domain.book.Book;
import com.tenminread.domain.common.id.FavoriteId;
import com.tenminread.domain.favorite.Favorite;
import com.tenminread.repository.BookRepository;
import com.tenminread.repository.FavoriteRepository;
import com.tenminread.repository.UserRepository; // ⬅️ 방금 만든 Repository
import com.tenminread.domain.user.User;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.FavoriteToggleResponse; // (이전에 정의한 DTO)
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;

  /**
   * 좋아요 상태를 토글합니다.
   */
  @Transactional
  public FavoriteToggleResponse toggleFavorite(Integer userId, Integer bookId) {

    // 1. 복합키 생성
    FavoriteId favoriteId = new FavoriteId(userId, bookId);

    // 2. 기존 좋아요 내역 조회
    Optional<Favorite> existingFavorite = favoriteRepository.findById(favoriteId);

    if (existingFavorite.isPresent()) {
      // 3-1. 이미 존재하면: 좋아요 취소 (삭제)
      favoriteRepository.delete(existingFavorite.get());
      return new FavoriteToggleResponse(bookId, userId, false);

    } else {
      // 3-2. 존재하지 않으면: 좋아요 (생성)
      // Favorite 엔티티를 만들려면 User와 Book 객체가 필요합니다.
      User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
      Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

      Favorite newFavorite = Favorite.builder()
        .id(favoriteId)
        .user(user)
        .book(book)
        .build();

      favoriteRepository.save(newFavorite);
      return new FavoriteToggleResponse(bookId, userId, true);
    }
  }
}
