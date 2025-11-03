package com.tenminread.repository;

import com.tenminread.domain.common.id.FavoriteId;
import com.tenminread.domain.favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
}
