package org.zafu.spring_new.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zafu.spring_new.entity.InvalidatedToken;

public interface InvalidatedTokenRepo extends JpaRepository<InvalidatedToken, String> {}
