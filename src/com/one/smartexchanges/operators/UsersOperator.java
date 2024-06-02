package com.one.smartexchanges.operators;

import java.util.List;
import java.util.ArrayList;
import com.one.smartexchanges.models.User;

public class UsersOperator {
    private static List<User> users;

    public UsersOperator() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean createUser(String username, String password) {
        boolean userCreated = false;
        boolean validated = usernameAvailable(username);
        if (validated) {
            var user = new User(username, password);
            users.add(user);
            userCreated = true;
        }else {
            userCreated = false;
        }
        return userCreated;
    }

    public boolean usernameAvailable(String username) {
        boolean validated = true;
        for (User item : users) {
            if (item.getUsername().equals(username)) {
                validated = false;
                break;
            }
        }
        return validated;
    }

    public User singIn(String username, String password) {
        User userActive = null;
        for (User item : users) {
            if (item.getUsername().equals(username)) {
                if (item.getPassword().equals(password)){
                    userActive = item;
                }
            }
        }
        return userActive;
    }

    public void logOut(User userActive){
        userActive = null;
    }
}