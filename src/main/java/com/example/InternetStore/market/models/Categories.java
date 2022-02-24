package com.example.InternetStore.market.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameCategory;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "categories_market"
            , joinColumns = @JoinColumn(name = "categories_id")
            , inverseJoinColumns = @JoinColumn(name = "market_id")
    )
    private List<Market> markets;

    public void addMarketToCategories(Market market){
        if (markets==null){
            markets = new ArrayList<>();
        }
        markets.add(market);
    }
}
