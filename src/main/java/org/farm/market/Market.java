package org.farm.market;

import org.farm.farm.Animal;
import org.farm.farm.Crop;
import org.farm.farm.Product;
import org.farm.farm.Farm;

import java.util.LinkedList;
import java.util.List;

public class Market {
    private final LinkedList<Product> products;  // Use LinkedList instead of array
    private int coins;  // Market's coins

    public Market() {
        this.products = new LinkedList<>();  // Initialize LinkedList
        this.coins = 1000;  // Market starts with 1000 coins

        addDefaultProducts();
    }

    // Buy product method - Farm spends coins, Market earns coins
    public boolean buyProductByIndex(int index, Farm farm) {
        if (index < 0 || index >= products.size()) {
            System.out.println("Invalid product index.");
            return false;
        }
        Product product = products.get(index); // Get the product by index
        if (farm.getCoins() >= product.getPrice()) {

            farm.decreaseCoins(product.getPrice()); // Deduct coins from the farm
            coins += product.getPrice(); // Market earns the coins
            product.setQuantity(1); // Ensure product is added with quantity of 1 to storage
            farm.getStorage().addProduct(product); // Add product to farm storage
            products.remove(index); // Remove product from the market
            System.out.println("Farm bought " + product.getName() + " for " + product.getPrice() + " coins.");
            return true;
        } else {
            System.out.println("Not enough coins to buy " + product.getName());
            return false;
        }
    }


    // Sell product method (Farm sells product to market, Farm earns money)
    public boolean sellProductByIndex(int index, Farm farm) {
        if (index < 0 || index >= farm.getStorage().size()) {
            System.out.println("Invalid product index in farm storage.");
            return false;
        }
        Product product = farm.getStorage().getProduct(index);  // Get product from farm storage
        if (product != null) {
            // Decrease the quantity of the product
            product.decreaseQuantity();

            // If quantity is 0, remove from storage
            if (product.getQuantity() == 0) {
                farm.getStorage().removeProduct(index);
            }

            // Add product to market (market now has it for sale)
            products.add(product);

            // Increase the farm's coins (market pays for the product)
            farm.increaseCoins(product.getPrice());
            coins -= product.getPrice(); // Market loses the money
            System.out.println("Farm sold " + product.getName() + " to market for " + product.getPrice() + " coins.");
            return true;
        } else {
            System.out.println("Farm does not have that product.");
            return false;
        }
    }


    // In Market class
    public void printMarketWithIndex(boolean isIndexingEnabled) {
        if (products.isEmpty()) {
            System.out.println("Market is empty.");
        } else {
            System.out.println("Market products:");
            if (isIndexingEnabled) {
                // Print with index
                for (int i = 0; i < products.size(); i++) {
                    System.out.println(i + ": " + products.get(i).getName());
                }
            } else {
                // Print without index
                for (Product product : products) {
                    System.out.println(product.getName());
                }
            }
        }
    }



    // Add default products to the market
    private void addDefaultProducts() {
        // Adding some crops
        products.add(new Crop("Corn", 30, 4, 8)); // Corn - Price: 30, Growth Time: 4, Yield: 8
        products.add(new Crop("Wheat", 50, 5, 10)); // Wheat - Price: 50, Growth Time: 5, Yield: 10

        // Adding some animals
        // Adding some animals with produced products (e.g., Milk for Cow)
        products.add(new Animal("Cow", 300, 2, new Product("Milk", 100), 2)); // Cow - Price: 300, Survival Time: 2, Milk Product, Age: 2
        products.add(new Animal("Chicken", 100, 1, new Product("Egg", 20), 1)); // Chicken - Price: 100, Survival Time: 1, Egg Product, Age: 1
    }

    // Get available products in the market
    public List<Product> getProducts() {
        return products;
    }

    // Get the market's coin balance
    public int getCoins() {
        return coins;
    }
}
