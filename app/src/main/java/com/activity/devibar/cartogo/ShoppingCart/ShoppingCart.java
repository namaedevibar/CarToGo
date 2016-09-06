package com.activity.devibar.cartogo.ShoppingCart;

/**
 * Created by namai on 9/6/2016.
 */
public class ShoppingCart {

    private String item;
    private int quantity;
    private double price;

    public ShoppingCart(String item, int quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
