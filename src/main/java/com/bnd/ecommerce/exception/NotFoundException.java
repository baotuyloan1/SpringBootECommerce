package com.bnd.ecommerce.exception;

public class NotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message){
        super("Category not found.");
    }
}
