package com.example.InternetStore.market.repositories;

import com.example.InternetStore.market.models.Categories;
import com.example.InternetStore.market.models.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    Page<Market> findAll(Pageable pageable);
    List<Market> findByCategories(Categories categories);
//    Page<Market> findByTag(String tag, Pageable pageable);
}
