package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {

    private String username;
    private String password;
    private String name;

    public UserDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public UserDto(long id, Date created, Date updated, String username, String name) {
        super(id, created, updated);
        this.username = username;
        this.name = name;
    }
}
