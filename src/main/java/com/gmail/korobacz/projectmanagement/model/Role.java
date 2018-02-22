package com.gmail.korobacz.projectmanagement.model;

import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity {

    private String name;

    public Role() {
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
