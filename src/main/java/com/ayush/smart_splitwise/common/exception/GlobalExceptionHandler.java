package com.ayush.smart_splitwise.common.exception;

import com.ayush.smart_splitwise.common.exception.custom.EmailAlreadyExistsException;
import com.ayush.smart_splitwise.common.exception.custom.PasswordMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handle(PasswordMismatchException ex, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST,request));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(ex.getMessage(),HttpStatus.CONFLICT,request));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex, HttpServletRequest request) {

        String message="Request body is missing";

        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(message,HttpStatus.BAD_REQUEST,request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(message, HttpStatus.BAD_REQUEST,request));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handle(BadCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse("Invalid Email or Password",HttpStatus.UNAUTHORIZED,request));
    }

    private ErrorResponse buildErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return errorResponse;
    }
}
