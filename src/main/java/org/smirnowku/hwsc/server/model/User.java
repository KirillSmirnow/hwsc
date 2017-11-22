package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hwsc_user")
public class User extends BaseEntity {

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
