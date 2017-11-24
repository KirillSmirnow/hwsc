package org.smirnowku.hwsc.server.model;

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
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
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
}
