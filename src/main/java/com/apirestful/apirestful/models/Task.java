package com.apirestful.apirestful.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

// criando a classe da task
@Entity // definindo como entidade no banco de dados
@Table(name = "table_task") // definindo nome da tbabela no banco
@AllArgsConstructor // implementando o construtor com os atributos com lombok
@NoArgsConstructor // implementando o construtor vazio com lombok
@Getter // metods getter com lombok
@Setter // metodos setters com lombok
@EqualsAndHashCode // metodos equals e hascode com lombok
public class Task {

    // definindo id da task
    @Id
    @Column(name = "task_id", unique = true)   // definindo propriedades da coluna na tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement do id
    private Long id;

    // definindo atributos
    @ManyToOne // definindo cardinalidade, muitas tarefas são de um usuário
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // referencia o id do usuário na tabela de tasks
    private User user;
    @Column(name = "task_description", length = 255, nullable = false) // definindo propriedades da coluna na tabela
    @NotNull // não permite valor nulo na descrição da tarefa
    @NotEmpty // não permite String vazia na descrição da tarefa
    @Size(min = 1, max = 255) // tamanho minimo e maximo que a descrição deve ter
    private String description;

    // implementação do construtor e getters e setters da maneira convencional
//
//    // construtor
//    public Task(){}
//
//    // metodos get
//    public Long getId() {
//        return id;
//    }
//    public User getUser() {
//        return user;
//    }
//    public String getDescription() {
//        return description;
//    }
//
//    // metodos set
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public void setUser(User user) {
//        this.user = user;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }

//    // hascode e equals
//    @Override
//    public boolean equals(Object object) {
//
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//
//        Task other = (Task) object;
//
//        if (this.id == null)
//            if (other.id != null)
//                return false;
//            else if (!this.id.equals(other.id))
//                return false;
//
//        return Objects.equals(this.id, other.id) && Objects.equals(this.user, other.user)
//                && Objects.equals(this.description, other.description);
//
//    }
//    @Override
//    public int hashCode() {
//
//        final int prime = 31;
//        int result =1;
//        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
//
//        return result;
//    }
}
