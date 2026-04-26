package ru.rostislav.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.rostislav.exception.NullUserException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullUserException.class)
    public String handleUserException() {
        return "redirect:/login";
    }

}
