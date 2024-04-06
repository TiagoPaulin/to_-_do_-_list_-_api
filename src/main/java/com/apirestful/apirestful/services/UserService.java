package com.apirestful.apirestful.services;

import com.apirestful.apirestful.models.User;
import com.apirestful.apirestful.repositories.TaskRepository;
import com.apirestful.apirestful.repositories.UserRepository;
import com.apirestful.apirestful.services.exceptions.DataBindingViolationException;
import com.apirestful.apirestful.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service // definindo como camada de serviço
public class UserService {

    // definindo comunicação com oo repositories
    @Autowired //notação para instanciar esses atributos
    private UserRepository ur;

    // metodo para encontrar o usuário pelo id
    public User findById(Long id){

        Optional<User> user = ur.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado")); // se o optional conter um eser retorna o user, se nao lança uma exceção

    }

    // metodo para criar o novo usuário
    @Transactional // usar para realizar insert e update no banco de dados, garante atomicidade
    public User create(User user){

        user.setId(null); // garante que o id seja gerado no medel, caso o usuári consiga de alguma forma criar o user com id

        user = ur.save(user);

        return user;

    }

//    método para alterar os dados de um usuario
    @Transactional
    public User update(User user){

        User newUser = findById(user.getId()); // verifica se o usuário existe
        newUser.setPassword(user.getPassword());

        return ur.save(newUser);

    }

    // método para deletar o usuário
    public void delete(Long id){

        findById(id);

        try {

            ur.deleteById(id);

        } catch (Exception e){

            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");

        }

    }

}
