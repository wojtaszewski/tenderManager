package com.gmail.korobacz.projectmanagement.DTO;

import com.gmail.korobacz.projectmanagement.model.Role;

import java.util.Collection;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Collection<Role> roles;

    public UserDTO(String firstName, String lastName, String email, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
