package com.example.InternetStore.cart.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PromoCodeDto {
    private String promoCode;

    //начало и конца действия купона
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date firstPromoCode;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastPromoCode;

    //размер скидки
    private int discount;
}
