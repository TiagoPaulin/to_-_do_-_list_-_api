package com.apirestful.apirestful.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor // Anotação lombok para gerar um construtor com todos os campos da classe
@Getter // Anotação lombok para gerar automaticamente os métodos getters para os campos privados da classe
public enum ProfileEnum { // Declaração da enumeração ProfileEnum

    ADMIN(1, "ROLE_ADMIN"), // Declaração do valor ADMIN com código 1 e descrição "ROLE_ADMIN"
    USER(2, "ROLE_USER"); // Declaração do valor USER com código 2 e descrição "ROLE_USER"

    // Atributos
    private Integer code; // Declaração do campo code que armazena o código da enumeração
    private String description; // Declaração do campo description que armazena a descrição da enumeração

    // Método
    public static ProfileEnum toEnum (Integer code) { // Declaração do método estático toEnum para converter um código em uma instância de ProfileEnum

        if (Objects.isNull(code)) { // Verifica se o código é nulo
            return null; // Retorna null se o código for nulo
        }

        for (ProfileEnum x : ProfileEnum.values()) { // Itera sobre os valores da enumeração ProfileEnum
            if (code.equals(x.getCode())) { // Verifica se o código passado como argumento é igual ao código do elemento atual
                return x; // Retorna o elemento atual se os códigos forem iguais
            }
        }

        // Se o código não corresponder a nenhum valor da enumeração, lança uma exceção
        throw new IllegalArgumentException("Invalid code: " + code);

    }
}
