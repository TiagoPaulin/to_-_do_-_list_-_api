package com.apirestful.apirestful.services;

import com.apirestful.apirestful.Security.UserSpringSecurity;
import com.apirestful.apirestful.models.Task;
import com.apirestful.apirestful.models.User;
import com.apirestful.apirestful.models.enums.ProfileEnum;
import com.apirestful.apirestful.repositories.TaskRepository;
import com.apirestful.apirestful.services.exceptions.AuthorizationException;
import com.apirestful.apirestful.services.exceptions.DataBindingViolationException;
import com.apirestful.apirestful.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service    // definindo como camda de serviço
public class TaskService {

    // definindo comunicação com o repository
    @Autowired
    private TaskRepository tr;

    // definindo comunicação com o user service
    @Autowired
    private UserService us;

    // metodo para encontrar pelo id
//    public Task findById(Long id){
//
//        Optional<Task> task = tr.findById(id);
//
//        return task.orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada"));
//
//    }

    public Task findById(Long id) {
        Task task = this.tr.findById(id).orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada"));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();

        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
        && !userHasTask(userSpringSecurity, task)) throw new AuthorizationException("Acesso negado");

        return task;
    }

    // metodo para retornar todas as tasks de um usuário
    public List<Task> findAllByUser() {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();

        if (Objects.isNull(userSpringSecurity)) throw new AuthorizationException("Acesso negado");


        List<Task> tasks = this.tr.findByUser_Id(userSpringSecurity.getId());

        return tasks;

    }

    // metodo para criar uma nova task
    @Transactional
    public Task create(Task task){

        UserSpringSecurity userSpringSecurity = UserService.authenticated();

        if (Objects.isNull(userSpringSecurity)) throw new AuthorizationException("Acesso negado");


        User user = us.findById(userSpringSecurity.getId()); // verifica se a task esta associada a um usuario

        task.setId(null); // garante que o id seja gerado no medel, caso o usuári consiga de alguma forma criar o user com id
        task.setUser(user);

        task = tr.save(task);

        return task;

    }

    // metodo de update da tarefa
    @Transactional
    public Task update(Task task) {

        Task newTask = findById(task.getId());  // e feito dessa forma pq no spring quando vc insere no banco um objeto com o mesmo id, ele sobrescreve o anterior, fazendo assim o update
        newTask.setDescription(task.getDescription());

        return tr.save(newTask); // então ao salvar a nova task no banco com o mesmo id da anterior ele vai sobrescrever

    }

    public void delete(Long id) {

        findById(id);

        try {

            tr.deleteById(id);

        } catch (Exception e) {

            throw new DataBindingViolationException("Nao foi possivel deletar a task");

        }

    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }

}
