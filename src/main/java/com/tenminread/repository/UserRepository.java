package com.tenminread.repository; // (BookRepository가 있는 패키지)

import com.tenminread.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
