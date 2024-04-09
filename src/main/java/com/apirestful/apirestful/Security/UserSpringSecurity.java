package com.apirestful.apirestful.Security;

import com.apirestful.apirestful.models.enums.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor // Gera automaticamente um construtor com todos os atributos da classe.
@Getter // Gera automaticamente métodos getters para todos os atributos da classe.

public class UserSpringSecurity implements UserDetails { // Implementa a interface UserDetails do Spring Security.

    private Long id; // ID do usuário.
    private String username; // Nome de usuário do usuário.
    private String password; // Senha do usuário.
    private Collection<? extends GrantedAuthority> authorities; // Autoridades (perfis) do usuário.

    public UserSpringSecurity(Long id, String username, String password, Set<ProfileEnum> profileEnums) {
        this.id = id;
        this.username = username;
        this.password = password;

        // Mapeia os perfis do usuário para uma coleção de GrantedAuthority e armazena no atributo authorities.
        this.authorities = profileEnums.stream()
                .map(x -> new SimpleGrantedAuthority(x.getDescription()))
                .collect(Collectors.toList());
    }

    // Implementações dos métodos da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Retorna as autoridades (perfis) do usuário.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Retorna se a conta do usuário não está expirada.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Retorna se a conta do usuário não está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Retorna se as credenciais do usuário não estão expiradas.
    }

    @Override
    public boolean isEnabled() {
        return true; // Retorna se o usuário está habilitado.
    }

    public boolean hasProfile(ProfileEnum profileEnum) {
        // Verifica se as autoridades (perfis) do usuário contêm a autoridade associada ao perfil especificado.
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    }

}
