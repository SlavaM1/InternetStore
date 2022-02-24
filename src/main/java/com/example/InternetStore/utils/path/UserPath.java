package com.example.InternetStore.utils.path;

public interface UserPath {
    String USER = "/user";

    String PROFILE = "/profile";

    String RECOVERY = "recovery";

    String RECOVERY_USER = "/recovery/{code}";

    String ORDERS = "/orders";

    String REGISTRATION = "/registration";

    String ACTIVATION_CODE = "/activate/{code}";
}
