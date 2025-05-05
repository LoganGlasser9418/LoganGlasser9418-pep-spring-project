package com.example.exception;

//this code retrieved from week 9 day 4 video: Using Spring Web
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
