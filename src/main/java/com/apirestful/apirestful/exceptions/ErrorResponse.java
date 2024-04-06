package com.apirestful.apirestful.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter // metodos getter com lombok
@Setter // metodos setter com lombok
@RequiredArgsConstructor // construtor para as variaveis com final usando lombok
@JsonInclude(JsonInclude.Include.NON_NULL) // so vai incluir se o valor nao for nullo
public class ErrorResponse {

    // definindo atributos
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    @Getter // metodos getter com lombok
    @Setter // metodos setter com lombok
    @RequiredArgsConstructor // construtor para as variaveis com final usando lombok
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    void addValidationError(String field, String message) {

        if (Objects.isNull(errors)) {

            this.errors = new ArrayList<>();

        }

        this.errors.add(new ValidationError(field, message));

    }

}
