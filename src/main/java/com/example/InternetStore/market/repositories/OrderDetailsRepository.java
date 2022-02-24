package com.example.InternetStore.market.repositories;

import com.example.InternetStore.market.models.OrderDetails;
import com.example.InternetStore.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByClient(User user_id);
}
