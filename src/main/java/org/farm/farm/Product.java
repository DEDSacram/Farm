package org.farm.farm;

// may be abstract
public class Product {
    private int price;
    private String name;

    public Product(String name,int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return name + " - Price: " + price;
    }
}