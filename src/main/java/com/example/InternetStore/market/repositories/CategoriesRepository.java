package com.example.InternetStore.market.repositories;


import com.example.InternetStore.market.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Categories findById(long id);
}
