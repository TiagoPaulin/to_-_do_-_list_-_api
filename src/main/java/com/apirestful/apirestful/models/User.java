package com.apirestful.apirestful.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// criando a classe do usuario
@Entity // definindo como entidade no banco de dados
@Table(name = "table_user") // definindo nome da tbabela no banco
@AllArgsConstructor // implementando o construtor com os atributos com lombok
@NoArgsConstructor // implementando o construtor vazio com lombok
@Getter // metods getter com lombok
@Setter // metodos setters com lombok
@EqualsAndHashCode // metodos equals e hascode com lombok
public class User {

    // definindo interfaces
    public interface CreateUser{}
    public interface UpdateUser{}

    // definindo id do usuario
    @Id
    @Column(name = "user_id", unique = true)   // definindo propriedades da coluna na tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement do id
    private Long id;

    // definindo atributos do usuario
    @Column(name = "user_name", length = 100, nullable = false, unique = true) // definindo propriedades da coluna na tabela
    @NotNull(groups = CreateUser.class) // nao permite valor null ao criar o usuario
    @NotEmpty(groups = CreateUser.class) // nao permite string vazia (" ") ao criar o usuario
    @Size(groups = CreateUser.class, min = 3, max = 100) // tamanho minimo e maximo que o nome deve ter
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // quando retornar o json na api não vai retornar a senha para o front
    @Column(name = "user_password", length = 60, nullable = false) // definindo propriedades da coluna na tabela
    @NotNull(groups = {CreateUser.class, UpdateUser.class})  // nao permite valor null ao criar o usuario e alterar a senha
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})  // nao permite string vazia (" ") ao criar o usuario e alterar a senha
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) // tamanho minimo e maximo que a senha deve ter ao criar o usuario e alterar a senha
    private String password;
    @OneToMany(mappedBy = "user") // definindo cardinalidade, um usuário pode ter várias tasks
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // quando retornar o json na api não vai retornar as tasks do usuário para o front
    private List<Task> tasks = new ArrayList<>();

    // implementação do construtor e getters e setters da maneira convencional

//    // construtor
//    public User(){}
//
//    // metodos get
//    public Long getId() {
//        return id;
//    }
//    public String getUsername() {
//        return username;
//    }
//    public String getPassword() {
//        return password;
//    }
//    @JsonIgnore // pra nao retornar todas as tasks do usuário quaqndo procurar por ele
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    // metodos set
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//    // hascode e equals
//    @Override
//    public boolean equals(Object object) {
//
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//
//        User other = (User) object;
//
//        if (this.id == null)
//            if (other.id != null)
//                return false;
//            else if (!this.id.equals(other.id))
//                return false;
//
//        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username)
//                && Objects.equals(this.password, other.password);
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
