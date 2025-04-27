package com.casado.sb3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DepartmentCannotBeUpdatedException extends RuntimeException {

    public DepartmentCannotBeUpdatedException(String message) {
        super(message);
    }
}