package com.example.InternetStore.market.models;


import com.example.InternetStore.user.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_order;

    private String client_data, order_details, status_order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User client;

    private Double final_price;
    private Date data_order;

    public String getClientName() {
        return client != null ? client.getUsername() : "<none>";
    }

    public OrderDetails(Date data_order, String client_data,
                        String order_details, Double final_price,
                        String status_order, User user){
        this.client = user;
        this.data_order = data_order;
        this.client_data = client_data;
        this.order_details = order_details;
        this.final_price = final_price;
        this.status_order = status_order;
    }
}
