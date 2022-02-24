package com.example.InternetStore.user.controllers;


import com.example.InternetStore.market.models.OrderDetails;
import com.example.InternetStore.market.repositories.OrderDetailsRepository;
import com.example.InternetStore.user.models.User;
import com.example.InternetStore.utils.path.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(UserPath.USER)
public class OrderDetailsUserController {
    private final OrderDetailsRepository orderDetailsRepository;

    @GetMapping(UserPath.ORDERS)
    public String showMyOrders(@AuthenticationPrincipal User user, Model model) {
        Iterable<OrderDetails> orderDetails = orderDetailsRepository.findByClient(user);
        model.addAttribute("orderDetails", orderDetails);
        return "order/my-orders";
    }
}
