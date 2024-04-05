package com.apirestful.apirestful.controllers;

import com.apirestful.apirestful.models.Task;
import com.apirestful.apirestful.models.User;
import com.apirestful.apirestful.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {

        Task task = this.taskService.findById(id);

        return  ResponseEntity.ok(task);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId (@PathVariable Long userId) {

        List<Task> tasks = taskService.findAllByUserId(userId);

        return ResponseEntity.ok().body(tasks);

    }

    @PostMapping // requisição post  da task
    @Validated // valida a criaação da task
    public ResponseEntity<Void> create(@Valid @RequestBody Task task){

        taskService.create(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}") // requisiçãopu da task para fazer o update no banco
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task task, @PathVariable Long id){

        task.setId(id);
        taskService.update(task);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}") // requisição  delete da task
    public ResponseEntity<Void> delete(@PathVariable Long id){

        taskService.delete(id);

        return ResponseEntity.noContent().build();

    }

}
