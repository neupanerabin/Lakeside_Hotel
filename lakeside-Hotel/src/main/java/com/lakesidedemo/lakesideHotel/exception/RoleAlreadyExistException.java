package com.lakesidedemo.lakesideHotel.exception;


/*
 * @author : rabin
 */

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
