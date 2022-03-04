package com.example.InternetStore.cart.repositories;


import com.example.InternetStore.cart.models.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    PromoCode findById(long id);
    PromoCode findByPromoCode(String promoCode);
}
