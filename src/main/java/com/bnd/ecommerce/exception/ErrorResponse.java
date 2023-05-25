package com.bnd.ecommerce.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError {

    private HttpStatus httpStatus;
    private String message;
    private List<String> error;
}
