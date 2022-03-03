package com.example.InternetStore.cart.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class PromoCode {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String promoCode;

    //начало и конца действия купона
    private Date firstPromoCode;
    private Date lastPromoCode;

    //размер скидки
    private int discount;

}
