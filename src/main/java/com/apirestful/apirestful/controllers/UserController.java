package com.apirestful.apirestful.controllers;

import com.apirestful.apirestful.models.User;
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
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/{id}") // metodo que lidara com uma requisição do usuário
    public ResponseEntity<User> findById(@PathVariable Long id){ // retorna uma entidade de resposta do tipo usuario

        User user = us.findById(id);

        return ResponseEntity.ok().body(user);

    }

    @PostMapping // requisição post  do usuário
    @Validated(User.CreateUser.class) // valida a criaação do usuário baseada na interface criada no modelo
    public ResponseEntity<Void> create(@Valid @RequestBody User user){

        us.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}") // requisiçãopu do usuário para fazer o update no banco
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id){

        user.setId(id);
        us.update(user);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}") // requisição  delete do usuário
    public ResponseEntity<Void> delete(@PathVariable Long id){

        us.delete(id);

        return ResponseEntity.noContent().build();

    }

}
