package com.example.InternetStore.user.controllers;

import com.example.InternetStore.user.models.User;
import com.example.InternetStore.user.services.UserService;
import com.example.InternetStore.utils.path.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    public final UserService userService;

    //форма регистрации пользователя
    @GetMapping(UserPath.REGISTRATION)
    public String registration() {
        return "users/registration";
    }

    //добавление пользователя
    @PostMapping(UserPath.REGISTRATION)
    public String addUser(User user, Map<String, Object> model) {

        if (!userService.addUser(user)) {
            model.put("message", "Такой пользователь или email уже существует");
            return "users/registration";
        }
        return "redirect:/login";
    }

    //подтвержденяи пользователя
    @GetMapping(UserPath.ACTIVATION_CODE)
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "Ваш аккаунт успешно подтвержден");
        } else {
            model.addAttribute("message", "Не найден код активации");
        }
        return "login";
    }
}
