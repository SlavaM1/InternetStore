package com.example.InternetStore.utils.path;

public interface CartPath {
    String CART = "/cart";

    String CART_ITEM_ADD = "/{id}&&{price}";

    String CART_REMOVE_ITEM = "/remove/{id}";

    String  CART_INCREASE_ITEM = "/increase/{id}";

    String  CART_REDUCE_ITEM = "/reduce/{id}";

    String CART_ORDER = "/order";

    String DESTROY_SESSION_CART = "/invalidate/session";

    String PROMO_CODE_APPLY = "/promo";
}
