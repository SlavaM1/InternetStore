package com.example.InternetStore.cart.services;

import com.example.InternetStore.market.models.Market;
import com.example.InternetStore.market.repositories.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final GetSessionService getSessionService;
    private final MarketRepository marketRepository;


    public Double SumCart(HashMap<Long, List<Double>> notes) {

        //подсчет итоговой суммы корзины
        double valueCart = 0.0;
        List<Double> values = new ArrayList<Double>();
        //пробгаем по массиву и считаем итог
        for (Map.Entry<Long, List<Double>> setMap : notes.entrySet()) {
            values = setMap.getValue();
            valueCart += values.stream()
                    .mapToDouble(a -> a)
                    .reduce(1, (a, b) -> a * b);
        }
        return valueCart;
    }


    //добавляем товар в сессию
    public void addProductToSession(long id, double price, HttpServletRequest request) {
        //получить сессию корзины
        HashMap<Long, List<Double>> CartSession = getSessionService.getSessionCart(request);

        //для корзины
        ArrayList<Double> list = new ArrayList<Double>();

        //проверка, есть ли в сесси что-то или нет
        if (CartSession == null) {
            CartSession = new HashMap<>();
            // if CartSession object is not present in session, set CartSession in the request session
            request.getSession().setAttribute("CART_SESSION", CartSession);
        }

        //проверка, если нет такого товара, то добавляем новый, иначе увеличиваем количество
        if (!CartSession.containsKey(id)) {
            list.add(0, 1.0);
            list.add(1, price);
            CartSession.put(id, list);
        } else {
            cartIncreaseItemService(id, request);
        }

        request.getSession().setAttribute("CART_SESSION", CartSession);
    }

    public HashMap<Long, List<Double>> showCartItemService(HttpServletRequest request){
        HashMap<Long, List<Double>> CartSession = getSessionService.getSessionCart(request);

        //если нету в сессии ничего, то не выводить
        if (CartSession == null) {
            CartSession = new HashMap<>();
            // if CartSession object is not present in session, set CartSession in the request session
            request.getSession().setAttribute("CART_SESSION", CartSession);
        }
        return CartSession;
    }

    //выводим всю корзину
    public String showCartService(Model model, HttpServletRequest request) {
        //получить сессию корзины
        HashMap<Long, List<Double>> CartSession = showCartItemService(request);

        //вывод корзины
        Set<Long> keys = CartSession.keySet();
        Iterable<Market> outputCart = marketRepository.findAllById(keys);
        model.addAttribute("cart", outputCart);

        //подсчет итоговой суммы корзины
        double valueCart = SumCart(CartSession);

        //проверяем, если сумма есть, значит товар есть, выводим текущую корзину
        //иначе выводим ниче и просим добавить что нибудь в корзину
        if (valueCart != 0) {
            model.addAttribute("cartValueSum", valueCart);
            model.addAttribute("CartSession", CartSession != null ? CartSession : new ArrayList<>());
            return "carts/cart-main";
        } else {
            model.addAttribute("title", "Страница корзины");
            return "carts/cart-empty";
        }
    }


    //обнуляем всю корзину сессию
    public void destroySessionCartService(HttpServletRequest request) {
        request.getSession().setAttribute("CART_SESSION", null);
    }


    //удалить конкретный товар из сессии
    public void cartRemoveItemService(long id, HttpServletRequest request) {
        //получить сессию корзины
        HashMap<Long, List<Double>> removeItem = getSessionService.getSessionCart(request);

        //удаляем товар из сессии по id
        removeItem.remove(id);
        //кладем обратно в сессию
        request.getSession().setAttribute("CART_SESSION", removeItem);
    }

    //увеличить количество товара
    public void cartIncreaseItemService(long id, HttpServletRequest request) {
        //получить сессию корзины
        HashMap<Long, List<Double>> increaseItem = getSessionService.getSessionCart(request);
        ArrayList<Double> list = new ArrayList<Double>();

        list.add(0, increaseItem.get(id).get(0) + 1.0);
        list.add(1, increaseItem.get(id).get(1));

        increaseItem.put(id, list);
        request.getSession().setAttribute("CART_SESSION", increaseItem);
    }

    //уменьшение количества товара
    public void cartReduceItemService(long id, HttpServletRequest request) {
        //получить сессию корзины
        HashMap<Long, List<Double>> reduceItem = getSessionService.getSessionCart(request);
        //уменьшаем количество
        ArrayList<Double> list = new ArrayList<Double>();

        list.add(0, reduceItem.get(id).get(0) - 1.0);
        list.add(1, reduceItem.get(id).get(1));
        reduceItem.put(id, list);

        //если количество товаров стало равным 0, то удаляем товар из коризны
        if (reduceItem.get(id).get(0) == 0) {
            cartRemoveItemService(id, request);
        }
        //кладем обратно в сессию
        request.getSession().setAttribute("CART_SESSION", reduceItem);
    }
}
