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

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    private String password;
    private String name;

    public User() {
    }

    public User(String username, String password, String name) {
        if (PropertyValidator.isEmpty(username))
            throw new IllegalArgumentException("Username cannot be empty");
        this.username = username;
        setPassword(password);
        setName(name);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
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
}
