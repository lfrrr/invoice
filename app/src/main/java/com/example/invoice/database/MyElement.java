package com.example.invoice.database;

public class MyElement {
    public float price;
    public int id;
    public MyElement(){
    }
    public MyElement(float price, int id) {
        this.price = price;
        this.id = id;
    }
    public float getPrice() {
        return this.price;
    }
    public int getId() {
        return this.id;
    }
    public String toString() {
        return "MyElement{" +
                "price='" + price + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
