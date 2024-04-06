package com.apirestful.apirestful.services.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(value = HttpStatus.NOT_FOUND) // retorna erro 404 de objeto não encontrado
public class ObjectNotFoundException extends EntityNotFoundException {

    public ObjectNotFoundException (String message) {
        super(message);
    }
}
