package com.epam.esm.exception;

import com.epam.esm.security.exception.InvalidJwtAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
        return new ResponseEntity<Object>(
                new APIError(e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(RuntimeException e) {
        return new ResponseEntity<Object>(
                new APIError(e.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({OAuth2AuthenticationProcessingException.class})
    public ResponseEntity<Object> handleOAuth2AuthenticationProcessingException(RuntimeException e) {
        return new ResponseEntity<Object>(
                new APIError(e.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(RuntimeException ex) {
        return new ResponseEntity<Object>(
                new APIError(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
        /*return handleExceptionInternal(ex, new APIError(ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);*/
    }

    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public ResponseEntity<Object> handleInvalidJwtAuthenticationException(RuntimeException e) {
        return new ResponseEntity<Object>(
                new APIError(e.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(RuntimeException e) {
        return new ResponseEntity<Object>(
                new APIError(e.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        APIError apiError =
                new APIError(errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        APIError apiError =
                new APIError(errors);
        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        APIError apiError =
                new APIError(error);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new APIError(ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
