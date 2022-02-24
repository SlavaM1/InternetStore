package com.example.InternetStore.user.repositories;

import com.example.InternetStore.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepostirory extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByActivationCode(String code);

    User findByRecoveryCode(String code);
}
