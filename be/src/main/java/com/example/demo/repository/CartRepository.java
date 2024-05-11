package com.example.demo.repository;

import com.example.demo.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {
    boolean existsByCartFoodIdAndCartUserId(int foodId,int userId);
    CartEntity findByCartFoodIdAndCartUserId(int foodId,int userId);
    List<CartEntity> findByCartUserId(int userId);
}
