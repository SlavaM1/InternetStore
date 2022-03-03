package com.example.InternetStore.admin.controllers;

import com.example.InternetStore.admin.services.OrderDetailsService;
import com.example.InternetStore.utils.path.AdminPath;
import com.example.InternetStore.utils.path.OrderDetailsPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(AdminPath.ADMIN)
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    //вывод всез заказов из БД
    @GetMapping(OrderDetailsPath.ORDER_DETAILS)
    public String OrderDetailsMain(Model model) {
        model.addAttribute("orderDetails", orderDetailsService.findAll());
        return "order/order-main";
    }

    //подробный вывод товаров из БД
    @GetMapping(OrderDetailsPath.ORDER_DETAILS_ID)
    private String OrderDetails(@PathVariable(value = "id") long id, Model model) {
       return orderDetailsService.OrderDetailsService(id,model);
    }

}
