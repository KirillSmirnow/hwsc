package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
