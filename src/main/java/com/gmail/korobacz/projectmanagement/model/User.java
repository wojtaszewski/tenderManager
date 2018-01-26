package com.gmail.korobacz.projectmanagement.model;

public class User {

    private long id;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

