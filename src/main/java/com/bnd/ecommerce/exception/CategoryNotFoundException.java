package com.bnd.ecommerce.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(){
        super("Category not found.");
    }
}
