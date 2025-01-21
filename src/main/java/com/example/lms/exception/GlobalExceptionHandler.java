package com.example.lms.exception;

import com.example.lms.playload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,WebRequest webRequest) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException
            (BadRequestException exception, WebRequest webRequest) {

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException exception, WebRequest webRequest){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation Failed",
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );

        exception.getBindingResult().getFieldErrors()
                .forEach(
                        error ->{
                            errorResponse.addValidationError(
                                    error.getField() + ": " + error.getDefaultMessage()
                            );
                        }
                );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

    }

    //Handle generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
