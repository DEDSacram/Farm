package org.farm.game;

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
        this.isIndexingEnabled = false;  // Default is non-indexed display
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
            System.out.println("10. Toggle Indexing (On/Off)");
            System.out.println("11. Exit");
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
                    System.out.println("Buy product {name, quantity} for now index");
                    String buyProductIndex = scanner.nextLine().toLowerCase();
                    market.buyProductByIndex(Integer.parseInt(buyProductIndex), farm);
                    break;
                case "4":
                    System.out.println("Sell product {name, quantity} for now index");
                    String sellProductIndex = scanner.nextLine().toLowerCase();
                    if (farm.getStorage().getProduct(Integer.parseInt(sellProductIndex)) != null) {
                        market.sellProductByIndex(Integer.parseInt(sellProductIndex), farm);
                    }
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
                    toggleIndexing();  // Toggle indexing on or off
                    break;
                case "11":
                    System.out.println("Exiting the game...");
                    return;  // Exit the game
                default:
                    System.out.println("Invalid command! Please try again.");
            }
        }
    }


    private void nextTurn() {

        farm.incrementAnimalAge();
        farm.decreaseCropGrowthTime();
        farm.triggerCatastrophe();
        farm.harvest();
        farm.collectAnimalProducts();
        turn++;
        System.out.println("Turn " + turn + " complete.");
    }

    private void addCropFromStorage(boolean isIndexingEnabled) {
        System.out.println("\n=== Choose Crop to Add from Storage ===");
        farm.getStorage().printStorageWithIndex(isIndexingEnabled);  // Pass indexing flag

        System.out.print("Enter the index of the crop to add: ");
        int cropIndex = Integer.parseInt(scanner.nextLine());

        Product product = farm.getStorage().getProduct(cropIndex);
        if (product instanceof Crop) {
            boolean added = farm.addCrop((Crop) product);  // Add crop to farm if valid
            if (added) {
                product.decreaseQuantity();  // Decrease the quantity of the product in storage
                if (product.getQuantity() == 0) {
                    farm.getStorage().removeProduct(cropIndex);  // Remove product from storage if quantity is 0
                }
                System.out.println("Crop added to the farm: " + product.getName());
            } else {
                System.out.println("Failed to add crop to the farm.");
            }
        } else {
            System.out.println("Selected product is not a crop.");
        }
    }


    private void addAnimalFromStorage(boolean isIndexingEnabled) {
        System.out.println("\n=== Choose Animal to Add from Storage ===");
        farm.getStorage().printStorageWithIndex(isIndexingEnabled);  // Pass indexing flag

        System.out.print("Enter the index of the animal to add: ");
        int animalIndex = Integer.parseInt(scanner.nextLine());

        Product product = farm.getStorage().getProduct(animalIndex);
        if (product instanceof Animal) {
            boolean added = farm.addAnimal((Animal) product);  // Add animal to farm if valid
            if (added) {
                product.decreaseQuantity();  // Decrease the quantity of the product in storage
                if (product.getQuantity() == 0) {
                    farm.getStorage().removeProduct(animalIndex);  // Remove product from storage if quantity is 0
                }
                System.out.println("Animal added to the farm: " + product.getName());
            } else {
                System.out.println("Failed to add animal to the farm.");
            }
        } else {
            System.out.println("Selected product is not an animal.");
        }
    }

}
