package com.sumit.sumit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class handler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Unhandled Exception !! ");
        problemDetail.setDetail("Unhandled Exception message from controller advice  ");
       // problemDetail.setStatus((int) ERROR_CODES.UNHANDLED_EXCEPTION);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

    }
}
