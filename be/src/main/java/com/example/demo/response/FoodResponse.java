package com.example.demo.response;

import java.util.List;

public class FoodResponse {
    private boolean success;
    private String message;
    private List<ListFoodResponse> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListFoodResponse> getData() {
        return data;
    }

    public void setData(List<ListFoodResponse> data) {
        this.data = data;
    }
}
