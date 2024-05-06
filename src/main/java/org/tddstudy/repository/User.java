package org.tddstudy.repository;

import lombok.*;


public class User {
    private String id;
    private String pw;
    private String name;
    private String phone;

    public User() {}
    public User(String id , String pw , String name , String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone= phone;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}

