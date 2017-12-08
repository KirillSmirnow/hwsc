package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.UserDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hwsc_user")
public class User extends BaseEntity {

    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MAX_NAME_LENGTH = 30;

    @Column(nullable = false, unique = true, updatable = false, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    public User() {
    }

    public User(String username, String password, String name) {
        validateUsername(username);
        this.username = username;
        setPassword(password);
        setName(name);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public UserDto toDto() {
        return new UserDto(getId(), getCreated(), getUpdated(), username, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private void validateUsername(String username) {
        if (PropertyValidator.isEmpty(username)) throw new IllegalArgumentException("Username cannot be empty");
        if (username.length() > MAX_USERNAME_LENGTH)
            throw new IllegalArgumentException(String.format("Username is too long (max length is %d)", MAX_USERNAME_LENGTH),
                    String.format("Username is too long (max length is %d, current length is %d)", MAX_USERNAME_LENGTH, username.length()));
    }

    private void validateName(String name) {
        if (PropertyValidator.isEmpty(name)) throw new IllegalArgumentException("Name cannot be empty");
        if (name.length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException(String.format("Name is too long (max length is %d)", MAX_NAME_LENGTH),
                    String.format("Name is too long (max length is %d, current length is %d)", MAX_NAME_LENGTH, name.length()));
    }
}
