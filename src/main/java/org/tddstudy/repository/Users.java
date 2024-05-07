package org.tddstudy.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {
    private List<User> users;

    public Users(List<User> users) {
        //필요시 여기에 유효성 검증 추가
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void setUsers() {}

    public List<User> getUsers() {
        return users;
    }
    public User findById(String id) {
        Optional<User> foundUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return foundUser.orElse(null);
    }

    public boolean hasPasswordDollarWord(String id , String pw) {
        Optional<User> foundUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if ( foundUser.isEmpty() ) return false;

        return foundUser.get().getPw().contains(pw);
    }

    public boolean isValid() {
        return false;
    }

    public void setPhone(String id , String phone) {
        Optional<User> foundUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        foundUser.ifPresent(user -> user.setName(phone));
    }

    public void setName(String id , String name) {
        Optional<User> foundUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        foundUser.ifPresent(user -> user.setName(name));
    }






}
