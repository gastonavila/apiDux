package com.example.apidux.Services;


import org.springframework.stereotype.Service;

@Service
public class UserService {


    private static final String TEST_USER = "test";
    private static final String TEST_PASSWORD = "12345";

    public boolean validateUser(String username, String password) {
        return TEST_USER.equals(username) && TEST_PASSWORD.equals(password);
    }
}
