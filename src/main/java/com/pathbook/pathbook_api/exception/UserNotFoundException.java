package com.pathbook.pathbook_api.exception;

public class UserNotFoundException extends RecordNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
