package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    default boolean exists(String username) {
        return findByUsername(username) != null;
    }
}
