package org.farm.market;

import org.farm.farm.Animal;
import org.farm.farm.Crop;
import org.farm.farm.Product;
import org.farm.farm.Farm;

import org.farm.farm.EdibleCropType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Market {
    private final LinkedList<Product> products;  // Use LinkedList instead of array
    private int coins;  // Market's coins

    private int turnCounter;
    private static final int ROTATE_INTERVAL = 5; // Rotate every 5 turns


    public void incrementTurnMarket() {
        turnCounter++;
        if (turnCounter >= ROTATE_INTERVAL) {
            rotateMarket();
            turnCounter = 0; // Reset counter after rotation
        }
    }
    private void rotateMarket() {
        // Clear and add new products to the market
        products.clear();
        addDefaultProducts(); // Add default or new products
        System.out.println("Market rotated.");
    }

    public Market() {
        this.products = new LinkedList<>();  // Initialize LinkedList
        this.coins = 10000000;

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
        //    products.add(product);

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
                    System.out.println(i + ": " + products.get(i).getName() + " " + products.get(i).getPrice());
                }
            } else {
                // Print without index
                for (Product product : products) {
                    System.out.println(product.getName());
                }
            }
        }
    }


    private void addDefaultProducts() {
        Random random = new Random();

        // Clear the market before adding new products (shop rotation)
        clearMarket();
        System.out.println("Market has been cleared for shop rotation.");

        // Check if there are fewer than 5 items on the market
        int numProductsToAdd = 5 - products.size();  // Add enough products to reach 5 items
        if (numProductsToAdd > 0) {
            for (int i = 0; i < numProductsToAdd; i++) {
                addRandomProduct(random);
            }
            System.out.println("Default randomized products added to the market.");
        } else {
            System.out.println("Market already has 5 products, no new items added.");
        }
    }

    private void clearMarket() {
        products.clear(); // This will remove all existing products from the market
    }

    private void addRandomProduct(Random random) {
        if (random.nextBoolean()) {
            products.add(createRandomAnimal(random));
        } else {
            products.add(createRandomCrop(random));
        }
    }

    private Animal createRandomAnimal(Random random) {
        String[] animalNames = {"Cow", "Chicken"};
        String[] animalProducts = {"Milk", "Egg"};
        EdibleCropType[] animalFood = {EdibleCropType.CORN, EdibleCropType.WHEAT};
        int[] baseMaxAges = {20, 10};
        int[] ageRanges = {5, 2};

        int animalIndex = random.nextInt(animalNames.length);

        String name = animalNames[animalIndex];
        int price = animalIndex == 0 ? 300 + random.nextInt(201) : 50 + random.nextInt(51);
        int survivalTime = 3 + random.nextInt(3);
        int maxAge = baseMaxAges[animalIndex] + random.nextInt(ageRanges[animalIndex] * 2 + 1) - ageRanges[animalIndex];
        int productPrice = animalIndex == 0 ? 150 + random.nextInt(51) : 20 + random.nextInt(11);

        Product producedProduct = new Product(animalProducts[animalIndex], productPrice);
        return new Animal(name, price, survivalTime, producedProduct, animalFood[animalIndex], 0, maxAge, ageRanges[animalIndex]);
    }

    private Crop createRandomCrop(Random random) {
        String[] cropNames = {"Corn", "Wheat", "Potato"};
        EdibleCropType[] cropTypes = {EdibleCropType.CORN, EdibleCropType.WHEAT, EdibleCropType.NONE};
        boolean[] cropImmunity = {false, false, true};

        int cropIndex = random.nextInt(cropNames.length);

        String name = cropNames[cropIndex];
        int price;
        int growthTime;
        int yield;
        boolean isImmune = cropImmunity[cropIndex];

        switch (cropTypes[cropIndex]) {
            case CORN:
                price = 50 + random.nextInt(51);
                growthTime = 5 + random.nextInt(4);
                yield = 6 + random.nextInt(5);
                break;
            case WHEAT:
                price = 40 + random.nextInt(31);
                growthTime = 5 + random.nextInt(3);
                yield = 8 + random.nextInt(4);
                break;
            case NONE:
            default:
                price = 20 + random.nextInt(21);
                growthTime = 6 + random.nextInt(3);
                yield = 6 + random.nextInt(3);
                break;
        }

        return new Crop(name, price, growthTime, yield, cropTypes[cropIndex], isImmune);
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
