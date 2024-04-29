package com.apirestful.apirestful.services;

import com.apirestful.apirestful.Security.UserSpringSecurity;
import com.apirestful.apirestful.models.User;
import com.apirestful.apirestful.models.enums.ProfileEnum;
import com.apirestful.apirestful.repositories.UserRepository;
import com.apirestful.apirestful.services.exceptions.AuthorizationException;
import com.apirestful.apirestful.services.exceptions.DataBindingViolationException;
import com.apirestful.apirestful.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service // definindo como camada de serviço
public class UserService {

    // definindo comunicação com oo repositories
    @Autowired //notação para instanciar esses atributos
    private UserRepository ur;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // metodo para encontrar o usuário pelo id
    public User findById(Long id){

        UserSpringSecurity userSpringSecurity = authenticated();

        if (!Objects.nonNull(userSpringSecurity)
                && !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = ur.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado")); // se o optional conter um eser retorna o user, se nao lança uma exceção

    }

//    public User findById (Long id) {
//
//        UserSpringSecurity userSpringSecurity = authenticated();
//        if (!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
//        && !id.equals(userSpringSecurity.getId()))
//            throw new AuthorizationException("acesso negado");
//
//        Optional<User> user = ur.findById(id);
//
//        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado")); // se o optional conter um eser retorna o user, se nao lança uma exceção
//    }

    // metodo para criar o novo usuário
    @Transactional // usar para realizar insert e update no banco de dados, garante atomicidade
    public User create(User user){

        user.setId(null); // garante que o id seja gerado no medel, caso o usuári consiga de alguma forma criar o user com id

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // criptografa a senha do usuario
        user.setProfiles(
                Stream.of(
                        ProfileEnum.USER.getCode())
                        .collect(Collectors.toSet()));

        user = ur.save(user);

        return user;

    }

//    método para alterar os dados de um usuario
    @Transactional
    public User update(User user){

        User newUser = findById(user.getId()); // verifica se o usuário existe
        newUser.setPassword(user.getPassword());

        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // criptografa a senha dousuario


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

    public static UserSpringSecurity authenticated() {

        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

    }

}
