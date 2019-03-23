package hwsc.dto;

import hwsc.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class UserDto extends BaseDto {

    private String username;
    private String password;
    private String name;

    public UserDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    private UserDto(long id, LocalDateTime created, LocalDateTime updated,
                    String username, String password, String name) {
        super(id, created, updated);
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getCreated(), user.getUpdated(),
                user.getUsername(), user.getPassword(), user.getName());
    }
}
