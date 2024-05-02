package com.apirestful.apirestful.repositories;

import com.apirestful.apirestful.models.Task;
import com.apirestful.apirestful.models.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // definidno como repositório
public interface TaskRepository extends JpaRepository<Task, Long> {

    // select pronto (tem que usar esse formato de nome
    List<TaskProjection> findByUser_Id(Long id);

    // select utilizando query Jpql (não importa o nome do metodo)
//    @Query(value = "SELECT t FROM Task t WHERE t.user.id = : user_id") // query seleciona todas as tasks baseadas no parametro passado no metodo que e o id do usuario
//    List<Task> findByUserId(@Param ("user_id") Long user_id);

    // select com query sql puro
//    @Query(value = "SELECT * FROM table_task t WHERE t.user_id = : user_id", nativeQuery = true)
//    List<Task> findByUserId(@Param ("user_id") Long user_id);

}
