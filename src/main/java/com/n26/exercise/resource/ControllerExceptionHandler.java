package com.n26.exercise.resource;

// import org.springframework.beans.TypeMismatchException;
//import org.springframework.validation.BindException;
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
           //BindException.class,
            IllegalArgumentException.class,
           // InvalidParamException.class,
          //  MissingServletRequestParameterException.class,
          //  ServletRequestBindingException.class,
      //      TypeMismatchException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public /*SimpleResponseError*/ Object handleException(Exception e, HttpServletRequest request) {
        return null;
    }
}
