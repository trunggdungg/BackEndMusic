package com.example.musicbackend.model.respone;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("User with id " + id + " does not exist");
    }
}
