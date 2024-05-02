package com.apirestful.apirestful.controllers;

import com.apirestful.apirestful.models.User;
import com.apirestful.apirestful.models.dto.UserCreateDTO;
import com.apirestful.apirestful.models.dto.UserUpdateDTO;
import com.apirestful.apirestful.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController // definir como camada de controle
@RequestMapping("/user") //  define o inicio da requisição de todos os métodos dessa classe
@Validated
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/{id}") // metodo que lidara com uma requisição do usuário
    public ResponseEntity<User> findById(@PathVariable Long id){ // retorna uma entidade de resposta do tipo usuario

        User user = us.findById(id);

        return ResponseEntity.ok().body(user);

    }

    @PostMapping // requisição post  do usuário
    public ResponseEntity<Void> create(@Valid @RequestBody UserCreateDTO obj){

        User user = us.fromDTO(obj);

        User newUser = us.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}") // requisiçãopu do usuário para fazer o update no banco
    public ResponseEntity<Void> update(@Valid @RequestBody UserUpdateDTO obj, @PathVariable Long id){

        obj.setId(id);

        User user = us.fromDTO(obj);

        us.update(user);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}") // requisição  delete do usuário
    public ResponseEntity<Void> delete(@PathVariable Long id){

        us.delete(id);

        return ResponseEntity.noContent().build();

    }

}
