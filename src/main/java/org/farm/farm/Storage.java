package org.farm.farm;

import java.util.LinkedList;

public class Storage {

    private final LinkedList<Product> inventory; // Use LinkedList instead of an array
    public static final int MAX_CAPACITY = 10; // Limit storage to 10 products

    public Storage() {
        inventory = new LinkedList<>();
    }

    // Add product to the inventory
    public boolean addProduct(Product product) {
        if (inventory.size() >= MAX_CAPACITY) {
            System.out.println("Inventory is full!");
            return false;
        }
        inventory.add(product); // Adds product to the end of the list
        return true;
    }

    public boolean removeProduct(int index) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index!");
            return false;
        }

        Product product = inventory.get(index);
        if (product.getQuantity() == 0) {  // Check if quantity is zero
            inventory.remove(index);  // Remove product from storage if quantity is zero
            System.out.println("Product removed from storage: " + product.getName());
            return true;
        } else {
            System.out.println("Product still has quantity, cannot remove yet.");
            return false;  // Do not remove if the product still has quantity
        }
    }


    // Get product by index
    public Product getProduct(int index) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index!");
            return null;
        }
        return inventory.get(index); // Retrieves the product at the specified index
    }

    // In Storage class
    public void printStorageWithIndex(boolean isIndexingEnabled) {
        if (inventory.isEmpty()) {
            System.out.println("Storage is empty.");
        } else {
            System.out.println("Storage contents:");
            if (isIndexingEnabled) {
                // Print with index
                for (int i = 0; i < inventory.size(); i++) {
                    System.out.println(i + ": " + inventory.get(i).getName() + " - Quantity: 1");
                }
            } else {
                // Print without index
                for (Product product : inventory) {
                    System.out.println(product.getName() + " - Quantity: " + product.getQuantity());
                }
            }
        }
    }

    // Get the current number of products in the inventory
    public int size() {
        return inventory.size();
    }

    // Check if the inventory is full
    public boolean isFull() {
        return inventory.size() >= MAX_CAPACITY;
    }

    public LinkedList<Product> getInventory() {
        return inventory;
    }
}
