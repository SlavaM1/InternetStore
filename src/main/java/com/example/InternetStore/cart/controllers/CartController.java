package com.example.InternetStore.cart.controllers;


import com.example.InternetStore.cart.services.CartService;
import com.example.InternetStore.utils.path.CartPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping(CartPath.CART)
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //добавляем товар в сессию
    @PostMapping(CartPath.CART_ITEM_ADD)
    public String cartItemAdd(@PathVariable(value = "id") long id,
                              @PathVariable(value = "price") double price,
                              HttpServletRequest request) {

        cartService.addProductToSession(id,price,request);
        return "redirect:/market";
    }

    //выводим всю корзину
    @GetMapping
    public String showCart(Model model, HttpServletRequest request) {
       return cartService.showCartService(model, request);
    }

    //очищаем всю корзину
    @PostMapping(CartPath.DESTROY_SESSION_CART)
    public String destroySessionCart(HttpServletRequest request) {
        //обнуляем всю корзину сессию
        cartService.destroySessionCartService(request);
        return "redirect:/cart";
    }

    //удалить конкретный товар из сессии
    @PostMapping(CartPath.CART_REMOVE_ITEM)
    public String cartRemoveItem(@PathVariable(value = "id") long id, HttpServletRequest request) {
        cartService.cartRemoveItemService(id, request);
        return "redirect:/cart";
    }

    //увеличить количество товара
    @PostMapping(CartPath.CART_INCREASE_ITEM)
    public String cartIncreaseItem(@PathVariable(value = "id") long id, HttpServletRequest request) {
        cartService.cartIncreaseItemService(id,request);
        return "redirect:/cart";
    }

    //уменьшение количества товара
    @PostMapping(CartPath.CART_REDUCE_ITEM)
    public String cartReduceItem(@PathVariable(value = "id") long id, HttpServletRequest request) {
        cartService.cartReduceItemService(id,request);
        return "redirect:/cart";
    }

    //страница оформления заказа
    @GetMapping(CartPath.CART_ORDER)
    public String cartOrder(Model model) {
        model.addAttribute("title", "Страница оформления заказа");
        return "carts/cart-order";
    }

    //применение промокода
    @PostMapping(CartPath.PROMO_CODE_APPLY)
    public String cartPromoCodeApply(@RequestParam("promo-code") String promoCode, HttpServletRequest request, Map<String, Object> model){

            model.put("message", cartService.cartPromoCodeApplyService(promoCode, request));
            return "carts/cart-main";
    }

}
