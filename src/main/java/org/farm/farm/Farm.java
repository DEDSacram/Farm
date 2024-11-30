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

//    public void feedAnimal(int animalIndex, EdibleCropType crop) {
//        if (animalIndex < 0 || animalIndex >= animals.length || animals[animalIndex] == null) {
//            System.out.println("Invalid animal index.");
//            return;
//        }
//
//        Animal animal = animals[animalIndex];
//        int newSurvivalTime = 5; // Reset survival time to 5 turns (or a value of your choice)
//        if (!animal.feed(crop, newSurvivalTime)) {
//            System.out.println("Failed to feed " + animal.getName() + ". Check the food type.");
//        }
//    }

    public void feedAnimalFromStorage(int animalIndex, int cropIndex) {
        if (animalIndex < 0 || animalIndex >= animals.length || animals[animalIndex] == null) {
            System.out.println("Invalid animal index.");
            return;
        }

        if (cropIndex < 0 || cropIndex >= storage.size()) {
            System.out.println("Invalid crop index.");
            return;
        }

        Animal animal = animals[animalIndex];
        Crop crop = (Crop) storage.getProduct(cropIndex);  // Cast to Crop since it's a product

        if (animal.feed(crop.getCropType(), 5)) { // Reset survival time to 5
            System.out.println("Fed " + animal.getName() + " with " + crop.getName());
            storage.removeProduct(cropIndex); // Remove the crop from storage after feeding
        } else {
            System.out.println(animal.getName() + " cannot eat " + crop.getName());
        }
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
