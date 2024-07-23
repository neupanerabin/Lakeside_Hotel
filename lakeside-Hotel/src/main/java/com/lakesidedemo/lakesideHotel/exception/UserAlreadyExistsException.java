package com.lakesidedemo.lakesideHotel.exception;


/*
 * @author : rabin
 */

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
