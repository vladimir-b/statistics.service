package com.n26.exercise.resource;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by Vladimir on 12/4/2016.
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler({
            IllegalArgumentException.class,
    })
    @ResponseStatus(BAD_REQUEST)
    public Object handleException(Exception e, HttpServletRequest request) {
       return ImmutableMap.of("message", e.getMessage());
    }
}
