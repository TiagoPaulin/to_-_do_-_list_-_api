package com.apirestful.apirestful.models;

import com.apirestful.apirestful.models.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

// Criando a classe do usuário
@Entity // Define a classe como uma entidade no banco de dados
@Table(name = "table_user") // Define o nome da tabela no banco de dados
@AllArgsConstructor // Implementa um construtor com todos os atributos com lombok
@NoArgsConstructor // Implementa um construtor vazio com lombok
@Getter // Gera automaticamente os métodos getters com lombok
@Setter // Gera automaticamente os métodos setters com lombok
@EqualsAndHashCode // Gera automaticamente os métodos equals e hashCode com lombok
public class User {

    // Definindo interfaces
    public interface CreateUser{} // Interface para criar um usuário
    public interface UpdateUser{} // Interface para atualizar um usuário

    // Definindo o ID do usuário
    @Id
    @Column(name = "user_id", unique = true) // Define as propriedades da coluna na tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente com autoincremento
    private Long id;

    // Definindo atributos do usuário
    @Column(name = "user_name", length = 100, nullable = false, unique = true) // Define as propriedades da coluna na tabela
    @NotNull(groups = CreateUser.class) // Não permite valor null ao criar o usuário
    @NotEmpty(groups = CreateUser.class) // Não permite string vazia (" ") ao criar o usuário
    @Size(groups = CreateUser.class, min = 3, max = 100) // Define o tamanho mínimo e máximo que o nome deve ter
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Quando retornar o JSON na API, não vai retornar a senha para o front
    @Column(name = "user_password", length = 60, nullable = false) // Define as propriedades da coluna na tabela
    @NotNull(groups = {CreateUser.class, UpdateUser.class})  // Não permite valor null ao criar o usuário e alterar a senha
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})  // Não permite string vazia (" ") ao criar o usuário e alterar a senha
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) // Define o tamanho mínimo e máximo que a senha deve ter ao criar o usuário e alterar a senha
    private String password;

    @OneToMany(mappedBy = "user") // Define a cardinalidade - um usuário pode ter várias tasks
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Quando retornar o JSON na API, não vai retornar as tasks do usuário para o front
    private List<Task> tasks = new ArrayList<>(); // Lista de tarefas associadas ao usuário

    @ElementCollection(fetch = FetchType.EAGER) // Define uma coleção de elementos que será carregada imediatamente
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Quando retornar o JSON na API, não vai retornar os perfis do usuário para o front
    @CollectionTable(name = "table_profile") // Define o nome da tabela para armazenar os perfis do usuário
    @Column(name = "user_profile", nullable = false) // Define as propriedades da coluna na tabela
    private Set<Integer> profiles = new HashSet<>(); // Conjunto de perfis do usuário

    public Set<ProfileEnum> getProfiles() { // Declaração do método getProfiles
        return this.profiles.stream() // Inicia uma stream com os valores dos perfis
                .map(x -> ProfileEnum.toEnum(x)) // Mapeia cada valor de perfil para uma instância de ProfileEnum usando o método estático toEnum
                .collect(Collectors.toSet()); // Coleta os resultados em um conjunto e retorna
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode()); // Adiciona o código do perfil ao conjunto de perfis do usuário.
    }

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
