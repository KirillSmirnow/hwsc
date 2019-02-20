package hwsc.service;

import hwsc.dto.UserDto;
import hwsc.model.User;

public interface UserService {

    void signUp(UserDto dto);

    void edit(String username, UserDto dto);

    UserDto get(String username);

    User getEntity(String username);
}
