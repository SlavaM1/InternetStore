package com.example.InternetStore.market.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "market")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name,full_text;
    private float price;
    private String filename;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "categories_market"
            , joinColumns = @JoinColumn(name = "market_id")
            , inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    private List<Categories> categories;



    public Market(String name, String full_text, float price) {
        this.name = name;
        this.full_text = full_text;
        this.price = price;
    }

    public void addCategoriesToMarket(Categories category){
        if (categories==null){
            categories = new ArrayList<>();
        }
        categories.add(category);
    }

}
