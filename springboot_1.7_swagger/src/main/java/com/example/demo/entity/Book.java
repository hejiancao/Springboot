package com.example.demo.entity;

import java.io.Serializable;

/**
 * @author created by shaos on 2019/6/19
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 5875889497306343123L;
    
    private long id;
    private String name;
    private double price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
