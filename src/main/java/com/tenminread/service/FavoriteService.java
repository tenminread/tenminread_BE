package com.tenminread.service;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.common.id.FavoriteId;
import com.tenminread.domain.favorite.Favorite;
import com.tenminread.repository.BookRepository;
import com.tenminread.repository.FavoriteRepository;
import com.tenminread.repository.UserRepository;
import com.tenminread.domain.user.User;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.BookDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (Main DB 트랜잭션 사용, CUD가 있으므로 readOnly=false)
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;

  /**
   * 좋아요 상태를 토글합니다. (추가 또는 삭제)
   */
  public BookDtos.FavoriteToggleResponse toggleFavorite(Integer userId, Integer bookId) {
    FavoriteId favoriteId = new FavoriteId(userId, bookId);
    Optional<Favorite> existingFavorite = favoriteRepository.findById(favoriteId);

    if (existingFavorite.isPresent()) {
      // 3-1. 이미 존재하면: 좋아요 취소 (삭제)
      favoriteRepository.delete(existingFavorite.get());
      return new BookDtos.FavoriteToggleResponse(bookId, userId, false);
    } else {
      // 3-2. 존재하지 않으면: 좋아요 (생성)
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
      return new BookDtos.FavoriteToggleResponse(bookId, userId, true);
    }
  }
}
