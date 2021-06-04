package com.mariakh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Order {

    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("orderNumber")
    private long number;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("items")
    private List<Item> items;

    public Order() {
    }

    public Order(String dateTime, long number, String customerName) {
        this.dateTime = dateTime;
        this.number = number;
        this.customerName = customerName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
