package com.apirestful.apirestful.repositories;

import com.apirestful.apirestful.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // definindo como repositório
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
