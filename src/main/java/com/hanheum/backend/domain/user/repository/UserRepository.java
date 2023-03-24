package com.hanheum.backend.domain.user.repository;

import com.hanheum.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
