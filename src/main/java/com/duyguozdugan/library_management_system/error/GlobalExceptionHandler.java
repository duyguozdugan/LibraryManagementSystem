package com.duyguozdugan.library_management_system.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }


        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGenericException(Exception ex) {
            return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
        }



    }


