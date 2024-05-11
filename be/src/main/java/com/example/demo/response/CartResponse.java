package com.example.demo.response;

public class CartResponse {
    private boolean success;
    private String message;
    private Object cartData;

    public Object getCartData() {
        return cartData;
    }

    public void setCartData(Object cartData) {
        this.cartData = cartData;
    }

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
}
