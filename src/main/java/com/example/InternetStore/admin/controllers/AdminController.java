package com.example.InternetStore.admin.controllers;


import com.example.InternetStore.user.models.Role;
import com.example.InternetStore.user.models.User;
import com.example.InternetStore.user.repositories.UserRepostirory;
import com.example.InternetStore.user.services.UserService;
import com.example.InternetStore.utils.path.AdminPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(AdminPath.ADMIN)
@RequiredArgsConstructor
public class AdminController {
    private final UserRepostirory userRepostirory;
    private final UserService userService;


    @GetMapping
    public String adminMain(){
        return "admin/admin-main";
    }

    @GetMapping(AdminPath.USER_LIST)
    public String userList(Model model){
        model.addAttribute("users", userRepostirory.findAll());
        return "admin/userList";
    }

    @GetMapping(AdminPath.USER_EDIT)
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "admin/userEdit";
    }

    @PostMapping(AdminPath.USER_EDIT_POST)
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String,String> form, @RequestParam("userId") User user){
        userService.saveUser(user, username, form);
        return "redirect:/admin/user-list";
    }
}
