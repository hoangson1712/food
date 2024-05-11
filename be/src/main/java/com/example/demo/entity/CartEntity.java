package com.example.demo.entity;

import jakarta.persistence.*;

@Entity(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @Column(name = "cart_user_id")
    private int cartUserId;

    @Column(name = "cart_food_id")
    private int cartFoodId;

    @Column(name = "cart_food_quantity")
    private int cartFoodQuantity;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(int cartUserId) {
        this.cartUserId = cartUserId;
    }

    public int getCartFoodId() {
        return cartFoodId;
    }

    public void setCartFoodId(int cartFoodId) {
        this.cartFoodId = cartFoodId;
    }

    public int getCartFoodQuantity() {
        return cartFoodQuantity;
    }

    public void setCartFoodQuantity(int cartFoodQuantity) {
        this.cartFoodQuantity = cartFoodQuantity;
    }
}
