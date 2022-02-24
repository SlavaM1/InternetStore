package com.example.InternetStore.cart.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class GetSessionService {

    //получаем сессию корзины из БД
    public HashMap<Long, List<Double>> getSessionCart(HttpServletRequest request){
        HashMap<Long, List<Double>> CartSession = (HashMap<Long, List<Double>>) request.getSession().getAttribute("CART_SESSION");
        return CartSession;
    }
}
