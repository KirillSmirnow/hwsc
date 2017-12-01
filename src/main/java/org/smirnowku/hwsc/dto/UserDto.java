package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

public class UserDto {

    private String username;
    private String password;
    private String name;

    public String getUsername() {
        if (PropertyValidator.isEmpty(username))
            throw new IllegalArgumentException("Username cannot be empty");
        return username;
    }

    public String getPassword() {
        if (PropertyValidator.isEmpty(password))
            throw new IllegalArgumentException("Password cannot be empty");
        return password;
    }

    public String getName() {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
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
