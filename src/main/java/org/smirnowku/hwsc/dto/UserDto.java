package org.smirnowku.hwsc.dto;

import java.util.Date;

public class UserDto extends BaseDto {

    private String username;
    private String password;
    private String name;

    public UserDto() {
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String password() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
