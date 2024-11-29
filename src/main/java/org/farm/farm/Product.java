
package org.farm.farm;

public class Product {
    private int price;
    private String name;
    private int quantity;  // Quantity property (starts at 1)

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;  // Default quantity starts at 1
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;  // Getter for quantity
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;  // Decrease quantity by 1
        }
    }

    @Override
    public String toString() {
        return name + " - Price: " + price + " - Quantity: " + quantity;
    }
}

