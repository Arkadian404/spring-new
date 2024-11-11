package org.zafu.spring_new.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zafu.spring_new.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String> {}
