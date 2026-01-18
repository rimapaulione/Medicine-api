package org.example.medicine_api.exception;

public class DrugNotFoundException extends RuntimeException {
    public DrugNotFoundException(String message) {
        super(message);
    }
}