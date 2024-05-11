package com.example.demo.service.imp;

import com.example.demo.entity.CartEntity;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public boolean addToCart(int foodId, int userId) {
        boolean isSuccess = false;
        try{
            if(cartRepository.existsByCartFoodIdAndCartUserId(foodId, userId)){
                CartEntity cartEntity = cartRepository.findByCartFoodIdAndCartUserId(foodId, userId);
                int count = cartEntity.getCartFoodQuantity();
                count += 1;
                cartEntity.setCartFoodQuantity(count);
                cartRepository.save(cartEntity);
            }else{
                CartEntity cartEntity = new CartEntity();
                cartEntity.setCartUserId(userId);
                cartEntity.setCartFoodId(foodId);
                cartEntity.setCartFoodQuantity(1);
                cartRepository.save(cartEntity);
            }
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean removeFromCart(int foodId, int userId) {
        boolean isSuccess = false;
        try{
            if(cartRepository.existsByCartFoodIdAndCartUserId(foodId, userId)){
                CartEntity cartEntity = cartRepository.findByCartFoodIdAndCartUserId(foodId, userId);
                if(cartEntity.getCartFoodQuantity() > 1){
                    int count = cartEntity.getCartFoodQuantity();
                    count -= 1;
                    cartEntity.setCartFoodQuantity(count);
                    cartRepository.save(cartEntity);
                }else{
                    cartRepository.delete(cartEntity);
                }
                isSuccess = true;
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public Map<String, Integer> getCart(int userId) {
        List<CartEntity> list = cartRepository.findByCartUserId(userId);
        Map<String, Integer> cart = new HashMap<>();
        for (CartEntity item : list) {
            cart.put(Integer.toString(item.getCartFoodId()),item.getCartFoodQuantity());
        }
        return cart;
    }

    @Override
    public boolean deleteCart(int userId) {
        boolean isSuccess = false;
        try {
            List<CartEntity> list = cartRepository.findByCartUserId(userId);
            for (CartEntity item : list) {
                cartRepository.deleteById(item.getCartId());
            }
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }
}
