package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.dto.UserDto;

public interface UserService {

    void signUp(UserDto dto);

    void edit(String username, UserDto dto);

    UserDto get(String username);

    User getEntity(String username);
}
