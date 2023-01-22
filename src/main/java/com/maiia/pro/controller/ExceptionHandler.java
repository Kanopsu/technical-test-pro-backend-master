package com.maiia.pro.controller;

import com.maiia.pro.exception.AppointmentConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<Object> handleAppointmentConflictException(AppointmentConflictException appointmentConflictException,
                                                                     WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Conflict between two appointment");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
