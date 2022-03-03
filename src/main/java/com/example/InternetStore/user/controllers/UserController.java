package com.example.InternetStore.user.controllers;

import com.example.InternetStore.user.models.User;
import com.example.InternetStore.user.services.UserService;
import com.example.InternetStore.utils.path.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping(UserPath.USER)
public class UserController {
    private final UserService userService;

    //редактирование пользователя
    @GetMapping(UserPath.PROFILE)
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "users/profile";
    }

    @PostMapping(UserPath.PROFILE)
    public String updateProfile(@AuthenticationPrincipal User user, @RequestParam String password, @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    //восстановление пароля
    @GetMapping(UserPath.RECOVERY)
    public String recoveryProfile() {
        return "recovery";
    }

    //тут принять данные, пока на ссылке заглушка
    @PostMapping(UserPath.RECOVERY)
    public String recoveryProfilePost(User user, @RequestParam String email) {
        userService.recoveryAccount(user, email);
        return "redirect:/login";
    }

    //подтвержденяи пользователя
    @GetMapping(UserPath.RECOVERY_USER)
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.recoveryUser(code);

        if (isActivated) {
            model.addAttribute("code", code);
            return "recovery-password";
        } else {
            model.addAttribute("message", "Что-то пошло не так, возможно ваша ссылка неверна");
            return "login";
        }

    }

    //изменение пароля
    @PostMapping(UserPath.RECOVERY_USER)
    public String setNewPassword(Model model, @PathVariable String code, @RequestParam String password) {
        boolean isActivated = userService.setPasswordUser(code, password);

        if (isActivated) {
            model.addAttribute("message", "Пароль успешно изменен");

        } else {
            model.addAttribute("message", "Что-то пошло не так");

        }
        return "login";
    }


}
