package com.example.demo.entity;

import jakarta.persistence.*;

@Entity(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "food_desc")
    private String foodDesc;

    @Column(name = "food_price")
    private float foodPrice;

    @Column(name = "food_image")
    private String foodImage;

    @Column(name = "food_category")
    private String foodCategory;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }
}
