package org.farm.farm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        //this.coins = 100;
        this.coins = 1000; // test

    }

    // Increment the age of each animal in the farm
    public void incrementAnimalAge() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null) {
                animals[i].increaseAge();
                if (!animals[i].isAlive()) {
                    System.out.println(animals[i].getName() + " has died of old age!");
                    animals[i] = null; // Remove the dead animal from the farm
                }
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


    // Collect products from animals and add them to storage
    public void collectAnimalProducts() {
        for (Animal animal : animals) {
            if (animal != null) {
                Product product = animal.getProducedProduct();
                if (storage.addProduct(product)) {
                    System.out.println("Collected " + product.getName() + " from " + animal.getName());
                } else {
                    System.out.println("Storage is full! Cannot collect " + product.getName() + " from " + animal.getName());
                    break; // Stop if storage is full
                }
            }
        }
    }



    // Trigger a catastrophe (affects farm)
    public void triggerCatastrophe() {
        Catastrophe.applyCatastrophe(this);
    }

    public void feedAnimalsByPriority() {
        // Sort animals by age and hunger (young and hungry animals prioritized)
        List<Animal> sortedAnimals = Arrays.stream(animals)
                .filter(Objects::nonNull)  // Remove null animals
                .sorted(Comparator.comparingInt(Animal::getAge)  // Sort by age (young first)
                        .thenComparingInt(Animal::getSurvivalTime)) // Then by hunger (lower survival time means hungrier)
                .toList();

        // Feed the animals in the order of priority
        for (Animal animal : sortedAnimals) {
            int cropIndex = findSuitableCropForAnimal(animal);

            if (cropIndex == -1) {
                System.out.println("No suitable food available for " + animal.getName());
                break; // Exit if no suitable food is found
            }

            // Check if the animal's survival time is 1
            if (animal.getSurvivalTime() == 1) {
                // Feed the animal and remove the crop from storage
                feedAnimalFromStorage(animal, cropIndex);
            } else {
                System.out.println("Animal " + animal.getName() + " does not need food at this time.");
            }
        }

    }

    // Method to find a suitable crop for the animal
    private int findSuitableCropForAnimal(Animal animal) {
        for (int i = 0; i < storage.size(); i++) {
            Product product = storage.getProduct(i);
            if (product instanceof Crop) {
                Crop crop = (Crop) product;
                if (crop.getCropType().equals(animal.getRequiredFood())) {  // Check if the crop matches the animal's preference
                    return i;  // Return the index of the suitable crop
                }
            }
        }
        return -1;  // Return -1 if no suitable food is found
    }

    // can be manual
    private void feedAnimalFromStorage(Animal animal, int cropIndex) {
        if (cropIndex < 0 || cropIndex >= storage.size()) {
            System.out.println("Invalid crop index.");
            return;
        }

        Product product = storage.getProduct(cropIndex);  // Get the product from storage
        if (product instanceof Crop) {  // Check if the product is a crop
            Crop crop = (Crop) product;

            // Feed the animal and reset its survival time to 5
            if (animal.feed(crop.getCropType())) {
                System.out.println("Fed " + animal.getName() + " with " + crop.getName());

                // Decrease the quantity of the product
                crop.decreaseQuantity();
                // reset time needed to feed
                animal.resetSurvivalTime();

                // Check if the quantity is zero, then remove the product
                if (crop.getQuantity() == 0) {
                    storage.removeProduct(cropIndex);  // Remove product if quantity is zero
                    System.out.println(crop.getName() + " has been removed from storage due to zero quantity.");
                }
            } else {
                System.out.println(animal.getName() + " cannot eat " + crop.getName());
            }
        } else {
            System.out.println("Selected product is not a valid crop for feeding.");
        }
    }







    // Method to expand the crop array by a specified amount
    public void expandCrops(int expandBy) {
        Crop[] newFields = new Crop[fields.length + expandBy];
        System.arraycopy(fields, 0, newFields, 0, fields.length);  // Copy existing crops to the new array
        fields = newFields;  // Update the reference to the new expanded array

        // Update maxCrops to reflect the new capacity
        maxCrops += expandBy;

        System.out.println("Crop space expanded! New crop capacity: " + maxCrops);
    }

    // Method to expand the animal array by a specified amount
    public void expandAnimals(int expandBy) {
        Animal[] newAnimals = new Animal[animals.length + expandBy];
        System.arraycopy(animals, 0, newAnimals, 0, animals.length);  // Copy existing animals to the new array
        animals = newAnimals;  // Update the reference to the new expanded array

        // Update maxAnimals to reflect the new capacity
        maxAnimals += expandBy;

        System.out.println("Animal space expanded! New animal capacity: " + maxAnimals);
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
