package org.example.service.Impl.Exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String message){
        super(message);
    }
}
