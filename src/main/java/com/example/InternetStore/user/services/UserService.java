package com.example.InternetStore.user.services;

import com.example.InternetStore.email.services.MailSender;
import com.example.InternetStore.user.models.Role;
import com.example.InternetStore.user.models.User;
import com.example.InternetStore.user.repositories.UserRepostirory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepostirory userRepostirory;

    private final PasswordEncoder passwordEncoder;

    private final MailSender mailSender;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepostirory.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepostirory.findByUsername(user.getUsername());
        User userEmailFromDb = userRepostirory.findByEmail(user.getEmail());

        //проверка, что такого username нету уже, если есть выводим уже есть
        if (userFromDb != null ){
            return false;
        }
        //проверка, что такого email нету уже, если есть выводим уже есть
        if (userEmailFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepostirory.save(user);

        sendMessage(user, 1);

        return true;
    }

    //отправка сообщения пользователю
    private void sendMessage(User user, int typeMessage) {
        if (!StringUtils.isEmpty(user.getEmail())){

            String message;
            //типы сообщений
            //первый тип, если новый пользователь
            //второй для восстановления mail
            if (1 == typeMessage) {
                message = String.format(
                        "Привет, %s! \n" +
                                "Добро пожаловать!" +
                                "пожалуйста зайдите сюда, чтоб подтвердить аккаунт: http://localhost:8080/activate/%s",
                        user.getUsername(),
                        user.getActivationCode()
                );
                mailSender.send(user.getEmail(), "Активация email", message );
            } else {
                message = String.format(
                        "Привет, %s! \n" +
                                "пожалуйста зайдите сюда, чтоб восстановить пароль: http://localhost:8080/user/recovery/%s",
                        user.getUsername(),
                        user.getRecovery_code()
                );
                mailSender.send(user.getEmail(), "Восстановление пароля", message );
            }

        }
    }


    public boolean activateUser(String code) {
        User user = userRepostirory.findByActivationCode(code);

        if (user == null){
            return false;
        }

        user.setActivationCode(null);

        userRepostirory.save(user);

        return true;
    }

    //вывод всех пользователей
    public Iterable<User> findAll() {
        return userRepostirory.findAll();
    }

    //сохранение пользователя
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepostirory.save(user);
    }

    //обновление профиля пользователя
    public void updateProfile(User user, String password, String email) {

        String userEmail = user.getEmail();
        //проверка, что маил изменился
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));

        if(isEmailChanged){
            user.setEmail(email);

            //проверяем, если пользователь поменял email, то его надо снова подтвердить
            if(!StringUtils.isEmpty(email)){
                //генерируем код
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        //проверяем что новый пароль
        if (!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepostirory.save(user);

        if (isEmailChanged){
            sendMessage(user, 1);
        }

    }

    //восстановление пароля
    public void recoveryAccount(User user, String email){
        //находим профиль по email
        user = userRepostirory.findByEmail(email);
        //генерируем уникальную ссылку для восстановления пароля
        user.setRecovery_code(UUID.randomUUID().toString());
        //сохраняем ссылку в БД
        userRepostirory.save(user);
        //отправляем сообщение пользователю
        sendMessage(user, 2);
    }

    //Восстановление пароля, проверка кода
    public boolean recoveryUser(String code) {
        //проверка пользователя по коду
        User user = userRepostirory.findByRecoveryCode(code);
        //если кода такого нету, то выводим false
        if (user == null){
            return false;
        }
        //инча true
        return true;
    }

    //Восстановление пароля, проверка кода
    public boolean setPasswordUser(String code, String password) {
        //проверка пользователя по коду
        User user = userRepostirory.findByRecoveryCode(code);
        //если кода такого нету, то выводим false
        if (user == null){
            return false;
        }
        //обнуляем ссылку
        user.setRecovery_code(null);
        //сохраняем новый зашифрованный пароль в БД
        user.setPassword(passwordEncoder.encode(password));
        //сохраняем пользователя
        userRepostirory.save(user);

        return true;
    }


}
