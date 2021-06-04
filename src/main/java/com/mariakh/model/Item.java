package com.mariakh.model;

public class Item {

    private String name;
    private long price;
    private double quantity;
    private long sum;

    public Item() {
    }

    public Item(String name, int price, float quantity, int sum) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
