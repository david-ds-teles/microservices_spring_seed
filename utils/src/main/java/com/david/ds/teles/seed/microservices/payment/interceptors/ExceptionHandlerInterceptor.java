package com.david.ds.teles.seed.microservices.payment.interceptors;

import com.david.ds.teles.seed.microservices.payment.exceptions.MyExceptionError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerInterceptor extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("default handleConstraintViolation error handler", ex);

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String temp = violation.getMessage();
            errors.add(field + ": " + temp);
        }

        ErrorResponse rsp = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "invalid request", errors);
        return ResponseEntity.badRequest().body(rsp);
    }

    @ExceptionHandler(MyExceptionError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> myExceptionHandler(MyExceptionError ex) {
        log.info("default throwable error handler");

        log.error(ex.getMessage());

        ErrorResponse rsp = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(rsp);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleThrowableException(Throwable ex) {
        log.error("default throwable error handler", ex);

        ErrorResponse rsp = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error");
        return ResponseEntity.internalServerError().body(rsp);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("default handleHttpMessageNotReadable handler", ex);

        ErrorResponse rsp = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "body not provided");

        return ResponseEntity.badRequest().body(rsp);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("default handleHttpRequestMethodNotSupported handler", ex);

        ErrorResponse rsp = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "method not supported");

        return ResponseEntity.badRequest().body(rsp);
    }

    @Override
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("default exception error handler", ex);

        ErrorResponse rsp = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error");
        return ResponseEntity.internalServerError().body(rsp);
    }

    @JsonInclude(Include.NON_NULL)
    public static record ErrorResponse(int status, String message, List<String> errors) {

        public ErrorResponse(int status, String message) {
            this(status, message, null);
        }

    }
}
