package org.zafu.spring_new.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zafu.spring_new.entity.Permission;

public interface PermissionRepo extends JpaRepository<Permission, String> {}
