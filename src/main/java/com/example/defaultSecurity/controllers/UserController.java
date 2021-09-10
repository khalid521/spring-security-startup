package com.example.defaultSecurity.controllers;

import java.util.List;

import com.example.defaultSecurity.models.User;
import com.example.defaultSecurity.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController
{


    @Autowired
    UserService userService;

    @RequestMapping(value = {"/api/user"}, method = RequestMethod.GET)
    public ResponseEntity<?> testUser()
    {
        return ResponseEntity.ok("Hello Private User!");
    }

    @RequestMapping(value = {"/public"}, method = RequestMethod.GET)
    public ResponseEntity<?> publicUser()
    {
        return ResponseEntity.ok("Hello Public User!");
    }
    
    @RequestMapping(value = {"/init"}, method = RequestMethod.GET)
    public ResponseEntity<?> init()
    {
        userService.initUsers();
        return ResponseEntity.ok("Users Init");
    }

    @RequestMapping(value = {"/all"}, method = RequestMethod.GET)
    public ResponseEntity<List<?>> getAll()
    {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
