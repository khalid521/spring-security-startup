package com.example.defaultSecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController
{
    @RequestMapping(value = {"/api/user"}, method = RequestMethod.GET)
    public ResponseEntity<?> testUser()
    {
        return ResponseEntity.ok("Hello Spring Demo!");
    }
}
