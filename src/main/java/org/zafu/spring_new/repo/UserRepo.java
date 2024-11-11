package org.zafu.spring_new.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zafu.spring_new.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
