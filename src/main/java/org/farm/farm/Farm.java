package org.farm.farm;



import java.util.ArrayList;
import java.util.List;

public class Farm {
    // If a crop is harvested it can be replanted, but with a lower yield
    private List<Crop> fields;
    private List<Animal> animals;
    private Storage storage;
    private int coins;

    private int maxCrops = 10;  // Initial limit for crops
    private int maxAnimals = 5; // Initial limit for animals

    public Farm() {
        this.fields = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.storage = new Storage();
        this.coins = 100;
    }

    public void incrementAnimalAge() {
        for (Animal animal : animals) {
            animal.increaseAge();
        }
    }

    public void harvest() {
        fields.removeIf(crop -> {
            if (crop.getGrowthTime() == 0) {
                storage.addProduct(crop, crop.getYield());
                return true;
            }
            return false;
        });
    }

    public void triggerCatastrophe() {
        Catastrophe.applyCatastrophe(this);
    }

    public List<Crop> getFields() {
        return fields;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public Storage getStorage() {
        return storage;
    }

    public int getCoins() {
        return coins;
    }

    // Method to decrease coins
    public void decreaseCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
        } else {
            System.out.println("Not enough coins to complete the transaction.");
        }
    }

    // Method to increase coins
    public void increaseCoins(int amount) {
        coins += amount;
    }

    public void printFarmStatus() {
        System.out.println("\nFarm Status:");
        System.out.println("Coins: " + coins);
        System.out.println("Crops:" + fields.size() + "/" + maxCrops);
        for (Crop crop : fields) {
            System.out.println("  " + crop.getName() + " - Growth: " + crop.getGrowthTime() + ", Yield: " + crop.getYield());
        }
        System.out.println("Animals:" + animals.size() + "/" + maxAnimals);
        for (Animal animal : animals) {
            System.out.println("  " + animal.getName() + " - Age: " + animal.getAge());
        }
    }
}
