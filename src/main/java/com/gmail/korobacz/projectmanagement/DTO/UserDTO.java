package com.gmail.korobacz.projectmanagement.dto;

import java.util.List;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleDTO> roles;

    public UserDTO(){
    }

    public UserDTO(String firstName, String lastName, String email, String password, List<RoleDTO> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public List<RoleDTO> getRoles() {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public static class UserDTOBuilder{
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private List<RoleDTO> roles;

        public UserDTOBuilder firstName(String firstName){
            this.firstName=firstName;
            return this;
        }

        public UserDTOBuilder lastName(String lastName){
            this.lastName=lastName;
            return this;
        }

        public UserDTOBuilder email(String email){
            this.email=email;
            return this;
        }

        public UserDTOBuilder password(String password){
            this.password=password;
            return this;
        }

        public UserDTOBuilder roles(List<RoleDTO> roles){
            this.roles=roles;
            return this;
        }

        public UserDTO build(){
            return new UserDTO(firstName, lastName, email, password,  roles);
        }

    }
}
