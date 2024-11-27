package org.farm.market;

import org.farm.farm.Animal;
import org.farm.farm.Crop;
import org.farm.farm.Product;
import org.farm.farm.Farm;

import java.util.ArrayList;
import java.util.List;

// Prices are changed by percentages

public class Market {
    private List<Product> products;
    private int coins;  // Market's coins

    public Market() {
        this.products = new ArrayList<>();
        this.coins = 1000;  // Market starts with 1000 coins

        addDefaultProducts();
    }

    // Buy product method - Farm spends coins, Market earns coins
    public boolean buyProduct(Product product, Farm farm) {
        if (farm.getCoins() >= product.getPrice()) {

            farm.decreaseCoins(product.getPrice()); // Deduct coins from the farm
            coins += product.getPrice(); // Market earns the coins
            farm.getStorage().addProduct(product,1);
            products.remove(product);
            System.out.println("Farm bought " + product.getName() + " for " + product.getPrice() + " coins.");
            return true;
        } else {
            System.out.println("Not enough coins to buy " + product.getName());
            return false;
        }
    }


    // Sell product method (Farm sells product to market, Farm earns money)
    public boolean sellProduct(Product product, Farm farm) {
        // Check if the farm has the product in storage
        if (farm.getStorage().getProducts().containsKey(product)) {
            // Remove product from farm storage
            farm.getStorage().removeProduct(product, 1);
            // Add product to market
            products.add(product);
            // Increase the farm's coins (market pays for the product)
            farm.increaseCoins(product.getPrice());
            coins -= product.getPrice(); // Market loses the money
            System.out.println("Farm sold " + product.getName() + " to market for " + product.getPrice() + " coins.");
            return true;
        } else {
            System.out.println("Farm does not have " + product.getName() + " to sell.");
            return false;
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

    // Sell product method - Farm earns coins, Market loses coins
//    public void sellProduct(Product product, Farm farm) {
//        if (products.contains(product)) {
//            products.remove(product);
//            farm.increaseCoins(product.getPrice()); // Farm earns the coins
//            coins -= product.getPrice(); // Market loses the coins
//            System.out.println("Farm sold " + product.getName() + " for " + product.getPrice() + " coins.");
//        } else {
//            System.out.println(product.getName() + " not found in the market.");
//        }
//    }

    // Get available products in the market
    public List<Product> getProducts() {
        return products;
    }

    // Get the market's coin balance
    public int getCoins() {
        return coins;
    }
}
