package com.maiia.pro.exception;

public class AppointmentConflictException extends RuntimeException{
    public AppointmentConflictException(String errorMessage) {super(errorMessage);}
}
