package com.example.InternetStore.email.services;

import com.example.InternetStore.cart.services.CartService;
import com.example.InternetStore.market.models.Market;
import com.example.InternetStore.market.models.OrderDetails;
import com.example.InternetStore.market.repositories.MarketRepository;
import com.example.InternetStore.market.repositories.OrderDetailsRepository;
import com.example.InternetStore.user.models.User;
import com.example.InternetStore.user.repositories.UserRepostirory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SimpleEmailExampleService {
    @Value("${spring.mail.admin}")
    private String mailAdmin;

    private final OrderDetailsRepository orderDetailsRepository;
    @Resource
    TemplateEngine templateEngine;
    private final UserRepostirory userRepostirory;
    private final JavaMailSender emailSender;
    private final MarketRepository marketRepository;
    private final CartService cartService;

    //Отправка email пользователю о заказе и сохранения в БД заказа
    public void sendHtmlEmailService(String fio, String email, String phone, String message_cart, HttpServletRequest request) throws MessagingException {

        //берем данные из сесси о текущей состоянии корзины
        HashMap<Long, List<Double>> CartSession = cartService.showCartItemService(request);

        Set<Long> keys = CartSession.keySet();

        Iterable<Market> note1 = marketRepository.findAllById(keys);

        double valueCart = cartService.SumCart(CartSession);


        Context context = new Context();

        context.setVariable("valueCart", valueCart);
        context.setVariable("cart", note1);
        context.setVariable("fio", fio);
        context.setVariable("email", email);
        context.setVariable("phone", phone);
        context.setVariable("message_cart", message_cart);
        context.setVariable("CartSession", CartSession!=null? CartSession:new ArrayList<>());


        String emailContent = templateEngine.process("emailTemplate", context);


        MimeMessage message = emailSender.createMimeMessage();
        //сообщение для пользователя
        MimeMessage messageUser = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        //сообщение для пользоваетеля
        MimeMessageHelper helperUser = new MimeMessageHelper(messageUser, multipart, "utf-8");

        //текущая дата заказа
        Date date = new Date();

        //String htmlMsg = templateEngine.process("emailTemplate", context);
        message.setContent(emailContent, "text/html");
        messageUser.setContent(emailContent, "text/html");



        //пользователю
        helperUser.setTo(email);
        helperUser.setSubject("Покупка в интернет магазине");


        String value = "";
        String status = "в обработке";
        String clientDetails = fio + " ; " + email + " ; " + phone + " ; " + message_cart;
        for (Map.Entry entry: CartSession.entrySet()) {

            value += entry + ";";

        }

        //ищем пользователя по email. если найден, то заказ присвоится ему
        //иначе будет иметь значение id = null
        User user = userRepostirory.findByEmail(email);

        OrderDetails orderDetails = new OrderDetails(date, clientDetails, value, valueCart,status,user);
        orderDetailsRepository.save(orderDetails);

        helper.setTo(mailAdmin);
        helper.setSubject("Покупка в магазине №" + orderDetails.getId_order());

        this.emailSender.send(message);
        //пользователю
        this.emailSender.send(messageUser);
        request.getSession().setAttribute("CART_SESSION", null);
    }
}
