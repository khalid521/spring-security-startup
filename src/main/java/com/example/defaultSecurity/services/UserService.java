package com.example.defaultSecurity.services;

import java.util.List;

import com.example.defaultSecurity.models.User;

public interface UserService {


    public User addUser(User user);
    public void initUsers();

    public List<User> getAllUsers();
    
}
