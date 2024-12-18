package org.farm.game;

import java.util.LinkedList;
import java.util.Scanner;
import org.farm.farm.Farm;
import org.farm.market.Market;
import org.farm.farm.Product;
import org.farm.farm.Crop;
import org.farm.farm.Animal;

public class Game {
    private final Farm farm;
    private final Market market;
    private int turn;
    private boolean isIndexingEnabled;
    Scanner scanner = new Scanner(System.in);

    public Game() {
        this.farm = new Farm();
        this.market = new Market();
        this.turn = 0;
        this.isIndexingEnabled = true;  // Default is non-indexed display
    }

    public void toggleIndexing() {
        this.isIndexingEnabled = !this.isIndexingEnabled;
        System.out.println("Indexing is now " + (isIndexingEnabled ? "enabled." : "disabled."));
    }

    public void start() {
        while (true) {
            System.out.println("\n===== FARM GAME =====");

            System.out.println("1. Harvest Crops");
            System.out.println("2. Check Market");
            System.out.println("3. Buy Product");
            System.out.println("4. Sell Product");
            System.out.println("5. Add Crop from Storage");
            System.out.println("6. Add Animal from Storage");
            System.out.println("7. Next Turn");
            System.out.println("8. Check Farm");
            System.out.println("9. Check Storage");
            System.out.println("10. Expand Crop Space");
            System.out.println("11. Expand Animal Space");
            System.out.println("12. Exit");
            System.out.print("Enter command: ");

            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "1":
                    farm.harvest();
                    break;
                case "2":
                    market.printMarketWithIndex(isIndexingEnabled);
                    break;
                case "3":
                    buyProduct();
                    break;
                case "4":
                    sellProduct();
                    break;
                case "5":
                    addCropFromStorage(isIndexingEnabled);  // Pass indexing flag
                    break;
                case "6":
                    addAnimalFromStorage(isIndexingEnabled);  // Pass indexing flag
                    break;
                case "7":
                    nextTurn();
                    break;
                case "8":
                    farm.printFarmStatus();
                    break;
                case "9":
                    farm.getStorage().printStorageWithIndex(isIndexingEnabled);  // Pass indexing flag
                    break;
                case "10":
                    expandCropSpace();  // Expand crop space option
                    break;
                case "11":
                    expandAnimalSpace();  // Expand animal space option
                    break;
                case "12":
                    System.out.println("Exiting the game...");
                    return;  // Exit the game
                default:
                    System.out.println("Invalid command! Please try again.");
            }
        }
    }

    private void buyProduct() {
        int buyProductIndex = -1;
        boolean validInput = false;
        market.printMarketWithIndex(isIndexingEnabled); // Pass indexing flag
        while (!validInput) {
            System.out.println("Enter index:");
            try {
                buyProductIndex = Integer.parseInt(scanner.nextLine());
                market.buyProductByIndex(buyProductIndex, farm);
                validInput = true;  // Input is valid, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for the product index.");
            }
        }
    }

    private void sellProduct() {
        int sellProductIndex = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter index:");
            try {
                sellProductIndex = Integer.parseInt(scanner.nextLine());
                Product product = farm.getStorage().getProduct(sellProductIndex);

                if (product != null) {
                    int maxQuantity = product.getQuantity();
                    System.out.println("Enter quantity to sell (1 to " + maxQuantity + "):");

                    int quantityToSell = Integer.parseInt(scanner.nextLine());

                    if (quantityToSell > 0 && quantityToSell <= maxQuantity) {
                        // Sell the product in a loop to handle the quantity
                        for (int i = 0; i < quantityToSell; i++) {
                            market.sellProductByIndex(sellProductIndex, farm);
                        }
                        validInput = true; // Exit the loop after successful sale
                    } else {
                        System.out.println("Invalid quantity! Please enter a number between 1 and " + maxQuantity + ".");
                    }
                } else {
                    System.out.println("No product found at the given index.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }


    private void expandCropSpace() {
        int costPerExpansion = 50;  // Cost to expand crop space by 5
        System.out.println("Enter how many crop spaces you want to expand by:");
        int expandBy = Integer.parseInt(scanner.nextLine());
        int totalCost = expandBy * costPerExpansion;

        if (farm.getCoins() >= totalCost) {
            farm.decreaseCoins(totalCost);
            farm.expandCrops(expandBy);
            System.out.println("Crop space expanded by " + expandBy + " for " + totalCost + " coins.");
        } else {
            System.out.println("Not enough coins! You need " + totalCost + " coins to expand the crop space.");
        }
    }

    private void expandAnimalSpace() {
        int costPerExpansion = 50;  // Cost to expand animal space by 5
        System.out.println("Enter how many animal spaces you want to expand by:");
        int expandBy = Integer.parseInt(scanner.nextLine());
        int totalCost = expandBy * costPerExpansion;

        if (farm.getCoins() >= totalCost) {
            farm.decreaseCoins(totalCost);
            farm.expandAnimals(expandBy);
            System.out.println("Animal space expanded by " + expandBy + " for " + totalCost + " coins.");
        } else {
            System.out.println("Not enough coins! You need " + totalCost + " coins to expand the animal space.");
        }
    }


    private void nextTurn() {
        // Catastrophe in the day can happen if so you cant have the crops or animals harvested
        farm.triggerCatastrophe();

        // Aging / Growth
        farm.incrementAnimalAge();
        farm.HungerorKill();


        farm.decreaseCropGrowthTime();

        // Feed Animals from Storage (prioritize young and hungry ones)
        farm.feedAnimalsByPriority();

        // Harvest crops
        farm.harvest();

        // Collect farm animal products
        farm.collectAnimalProducts();

        // Increment turn in the market
        market.incrementTurnMarket();

        // Increment turn number
        turn++;
        System.out.println("Turn " + turn + " complete.");
    }


    private void addCropFromStorage(boolean isIndexingEnabled) {
        System.out.println("\n=== Choose Crop to Add from Storage ===");

        LinkedList<Product> allProducts = farm.getStorage().getInventory();  // Get all products from storage

        // Print crops with indexes
        int index = 0;
        for (Product product : allProducts) {
            if (product instanceof Crop) {
                System.out.println(index + ". " + product);  // Print the crop with its index
                index++;
            }
        }

        // Input validation for crop index
        int cropIndex = -1;
        while (cropIndex < 0 || cropIndex >= allProducts.size()) {
            System.out.print("Enter the index of the crop to add: ");
            try {
                cropIndex = Integer.parseInt(scanner.nextLine());
                if (cropIndex < 0 || cropIndex >= allProducts.size()) {
                    System.out.println("Invalid index. Please select a valid crop index.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for the crop index.");
            }
        }

        // Proceed if the input is valid
        Product selectedCrop = allProducts.get(cropIndex);
        if (selectedCrop instanceof Crop) {
            boolean added = farm.addCrop((Crop) selectedCrop);
            if (added) {
                selectedCrop.decreaseQuantity();
                if (selectedCrop.getQuantity() == 0) {
                    farm.getStorage().removeProduct(cropIndex);  // Remove from storage if quantity is 0
                }
                System.out.println("Crop added to the farm: " + selectedCrop.getName());
            } else {
                System.out.println("Failed to add crop to the farm.");
            }
        } else {
            System.out.println("Invalid selection. Please choose a valid crop.");
        }
    }


    private void addAnimalFromStorage(boolean isIndexingEnabled) {
        System.out.println("\n=== Choose Animal to Add from Storage ===");

        LinkedList<Product> allProducts = farm.getStorage().getInventory();  // Get all products from storage

        // Print animals with indexes (hide max age)
        int index = 0;
        for (Product product : allProducts) {
            if (product instanceof Animal) {
                Animal animal = (Animal) product;
                System.out.println(index + ". " + animal.getName() + " - Price: " + animal.getPrice() + " - Quantity: " + animal.getQuantity() +
                        " - Survival Time: " + animal.getSurvivalTime() + " - Age: " + animal.getAge() + "/" + animal.getMaxAge() +
                        " - Produces: " + animal.getProducedProduct().getName() + " - Eats: " + animal.getRequiredFood());
                index++;
            }
        }

        // Input validation for animal index
        int animalIndex = -1;
        while (animalIndex < 0 || animalIndex >= allProducts.size()) {
            System.out.print("Enter the index of the animal to add: ");
            try {
                animalIndex = Integer.parseInt(scanner.nextLine());
                if (animalIndex < 0 || animalIndex >= allProducts.size()) {
                    System.out.println("Invalid index. Please select a valid animal index.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for the animal index.");
            }
        }

        // Proceed if the input is valid
        Product selectedAnimal = allProducts.get(animalIndex);
        if (selectedAnimal instanceof Animal) {
            Animal animal = (Animal) selectedAnimal;
            System.out.println("You selected: " + animal.getName() + " - Current Age: " + animal.getAge());  // Show current age after selection

            boolean added = farm.addAnimal(animal);
            if (added) {
                selectedAnimal.decreaseQuantity();
                if (selectedAnimal.getQuantity() == 0) {
                    farm.getStorage().removeProduct(animalIndex);  // Remove from storage if quantity is 0
                }
                System.out.println("Animal added to the farm: " + selectedAnimal.getName());
            } else {
                System.out.println("Failed to add animal to the farm.");
            }
        } else {
            System.out.println("Invalid selection. Please choose a valid animal.");
        }
    }


}

