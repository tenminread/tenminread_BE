package com.tenminread.repository;

import com.tenminread.domain.home.HomeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeStateRepository extends JpaRepository<HomeState, Integer> { }
