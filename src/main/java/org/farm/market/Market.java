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


    // balancing prices yet to do, right now farming is much better than owning an animal
    private void addDefaultProducts() {
        Random random = new Random();

        // Clear the market before adding new products (shop rotation)
        products.clear(); // This will remove all existing products from the market
        System.out.println("Market has been cleared for shop rotation.");

        // Arrays or lists of possible product types
        String[] cropNames = {"Corn", "Wheat", "Potato"};
        EdibleCropType[] cropTypes = {EdibleCropType.CORN, EdibleCropType.WHEAT, EdibleCropType.NONE};
        boolean[] cropImmunity = {false, false, true};  // Potato is always immune

        // Animal templates
        String[] animalNames = {"Cow", "Chicken"};
        String[] animalProducts = {"Milk", "Egg"};
        EdibleCropType[] animalFood = {EdibleCropType.CORN, EdibleCropType.WHEAT};
        int[] baseMaxAges = {20, 10};  // Cow's max age range [15, 25], Chicken's [8, 12]
        int[] ageRanges = {5, 2};  // Cow's age range 5, Chicken's age range 2

        // Check if there are fewer than 5 items on the market
        int numProductsToAdd = 5 - products.size();  // Add enough products to reach 5 items
        if (numProductsToAdd > 0) {
            for (int i = 0; i < numProductsToAdd; i++) {
                // Randomly select either a Crop or Animal type
                boolean isAnimal = random.nextBoolean();

                if (isAnimal) {
                    // Randomly pick an animal
                    int animalIndex = random.nextInt(animalNames.length);
                    int price = 0;

                    // Set price based on animal type
                    switch (animalNames[animalIndex]) {
                        case "Cow":
                            price = 300 + random.nextInt(201);  // Cow: Price between 300 and 500
                            break;
                        case "Chicken":
                            price = 50 + random.nextInt(51);   // Chicken: Price between 50 and 100
                            break;
                    }

                    int survivalTime = 3 + random.nextInt(3);  // Random survival time between 3 and 5
                    int maxAge = baseMaxAges[animalIndex] + random.nextInt(ageRanges[animalIndex] * 2 + 1) - ageRanges[animalIndex]; // Random maxAge within range

                    // Determine the price for the animal product (Milk for Cow, Egg for Chicken)
                    int productPrice = 0;
                    switch (animalProducts[animalIndex]) {
                        case "Milk":
                            productPrice = 150 + random.nextInt(51);  // Milk price between 150 and 200
                            break;
                        case "Egg":
                            productPrice = 20 + random.nextInt(11);  // Egg price between 20 and 30
                            break;
                    }

                    // Create the Product with the correct price for milk or egg
                    Product producedProduct = new Product(animalProducts[animalIndex], productPrice);

                    // Create the Animal
                    Animal animal = new Animal(animalNames[animalIndex],
                            price,
                            survivalTime,
                            producedProduct,  // The product (Milk or Egg)
                            animalFood[animalIndex],
                            0,
                            maxAge,
                            ageRanges[animalIndex]);
                    products.add(animal);
                }
                else {
                    // Randomly pick a crop
                    int cropIndex = random.nextInt(cropNames.length);
                    int price = 0;
                    int growthTime = 0;
                    int yield = 0;

                    // Set price, growthTime, and yield based on crop type
                    switch (cropTypes[cropIndex]) {
                        case CORN:
                            price = 50 + random.nextInt(51);  // Corn: Price between 50 and 100
                            growthTime = 5 + random.nextInt(4);  // Random growth time between 5 and 8
                            yield = 6 + random.nextInt(5);      // Lower yield, random between 6 and 10
                            break;
                        case WHEAT:
                            price = 40 + random.nextInt(31);  // Wheat: Price between 40 and 70
                            growthTime = 5 + random.nextInt(3);  // Random growth time between 5 and 7
                            yield = 8 + random.nextInt(4);      // Moderate yield between 8 and 11
                            break;
                        case NONE:
                            price = 20 + random.nextInt(21);  // Potato: Price between 20 and 40
                            growthTime = 6 + random.nextInt(3);  // Potato: Random growth time between 6 and 8
                            yield = 6 + random.nextInt(3);      // Potato: Lower yield between 6 and 8
                            break;
                    }

                    boolean isImmune = cropImmunity[cropIndex];  // Use the predefined immunity
                    Crop crop = new Crop(cropNames[cropIndex],
                            price,
                            growthTime,
                            yield,
                            cropTypes[cropIndex],
                            isImmune);
                    products.add(crop);
                }
            }

            System.out.println("Default randomized products added to the market.");
        } else {
            System.out.println("Market already has 5 products, no new items added.");
        }
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
