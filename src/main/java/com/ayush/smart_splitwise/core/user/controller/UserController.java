package com.ayush.smart_splitwise.core.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String check(){
        return "Session Maintain";
    }
}
