package com.example.InternetStore.email.controllers;

import com.example.InternetStore.email.services.SimpleEmailExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class SimpleEmailExampleController {
    private final SimpleEmailExampleService simpleEmailExampleService;

    //Отправка email пользователю о заказе и сохранения в БД заказа
    @PostMapping("/sendSimpleEmail")
    public String sendHtmlEmail(@RequestParam String fio,
                                @RequestParam String email, @RequestParam String phone,
                                @RequestParam String message_cart, HttpServletRequest request) throws MessagingException {

        simpleEmailExampleService.sendHtmlEmailService(fio,email,phone,message_cart,request);
        return "redirect:/cart";
    }



}