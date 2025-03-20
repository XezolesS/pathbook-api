package com.pathbook.pathbook_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public Login login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        return new Login(email, password);
    }
}
