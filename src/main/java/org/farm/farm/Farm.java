package org.farm.farm;

public class Farm {
    private Crop[] fields;  // Array for fields (crops)
    private Animal[] animals;  // Array for animals
    private Storage storage;
    private int coins;

    private int maxCrops = 10;  // Initial limit for crops
    private int maxAnimals = 5; // Initial limit for animals

    public Farm() {
        this.fields = new Crop[maxCrops];  // Initialize the fields array with a fixed size
        this.animals = new Animal[maxAnimals];  // Initialize the animals array with a fixed size
        this.storage = new Storage();
        this.coins = 100;
    }

    // Increment the age of each animal in the farm
    public void incrementAnimalAge() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null) {
                animals[i].increaseAge();
            }
        }
    }
    public void decreaseCropGrowthTime() {
        for (Crop crop : fields) {
            if (crop != null) {  // Check if the crop is not null
                crop.decrementGrowthTime();  // Decrease growth time for each non-null crop
            }
        }
    }


    // Harvest crops and add to storage, removes harvested crops
    public void harvest() {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] != null && fields[i].getGrowthTime() == 0) {
                // Add the harvested crop to storage
                Crop harvestedCrop = fields[i];
                harvestedCrop.harvest();  // Set quantity to yield

                // Add product to storage and remove the harvested crop from the field
                storage.addProduct(harvestedCrop);
                fields[i] = null;  // Crop is harvested and space is cleared
            }
        }
    }


    // Trigger a catastrophe (affects farm)
    public void triggerCatastrophe() {
        Catastrophe.applyCatastrophe(this);
    }

    // Method to add a crop to the fields if there is space
    public boolean addCrop(Crop crop) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null) {  // Find an empty space
                fields[i] = crop;
                return true;
            }
        }
        System.out.println("No space left for more crops.");
        return false;
    }

    // Method to add an animal to the farm if there is space
    public boolean addAnimal(Animal animal) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] == null) {  // Find an empty space
                animals[i] = animal;
                return true;
            }
        }
        System.out.println("No space left for more animals.");
        return false;
    }

    public Crop[] getFields() {
        return fields;
    }

    public Animal[] getAnimals() {
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

    // Print the status of the farm
    public void printFarmStatus() {
        System.out.println("\nFarm Status:");
        System.out.println("Coins: " + coins);
        System.out.println("Crops: " + countNonNull(fields) + "/" + maxCrops);
        for (Crop crop : fields) {
            if (crop != null) {
                System.out.println("  " + crop.getName() + " - Growth: " + crop.getGrowthTime() + ", Yield: " + crop.getYield());
            }
        }
        System.out.println("Animals: " + countNonNull(animals) + "/" + maxAnimals);
        for (Animal animal : animals) {
            if (animal != null) {
                System.out.println("  " + animal.getName() + " - Age: " + animal.getAge());
            }
        }
    }

    // Utility method to count non-null elements in an array
    private int countNonNull(Object[] array) {
        int count = 0;
        for (Object obj : array) {
            if (obj != null) {
                count++;
            }
        }
        return count;
    }
}
