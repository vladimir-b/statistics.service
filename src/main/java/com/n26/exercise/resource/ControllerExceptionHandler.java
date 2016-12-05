package com.n26.exercise.resource;

// import org.springframework.beans.TypeMismatchException;
//import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by Vladimir on 12/4/2016.
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler({
            IllegalArgumentException.class,
    })
    @ResponseStatus(NO_CONTENT)
    public void handleException(Exception e, HttpServletRequest request) {}
}
