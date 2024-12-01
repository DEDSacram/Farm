package org.farm.farm;

import java.util.LinkedList;

public class Storage {

    private final LinkedList<Product> inventory; // Use LinkedList instead of an array
//    public static final int MAX_CAPACITY = 10; // Limit storage to 10 products

    public Storage() {
        inventory = new LinkedList<>();
    }

    public boolean addProduct(Product product) {
        for (Product existingProduct : inventory) {
            if (areProductsEquivalent(existingProduct, product)) {
                // Combine quantities if products are equivalent
                existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                return true;
            }
        }

        // Additional checks for animals
        if (product instanceof Animal) {
            Animal animal = (Animal) product;

            // Prevent adding animals with limited lifetime into storage
            if (animal.getAge() > 0 || animal.getSurvivalTime() < animal.getMaxSurvivalTime()) {
                System.out.println("Cannot add animals with active lifetimes or hunger statuses to storage.");
                return false;
            }
        }

        // Add the product if no equivalent product exists and it's not a restricted animal
        inventory.add(product);
        return true;
    }

    // Helper method to check if two products are equivalent
    private boolean areProductsEquivalent(Product existing, Product newProduct) {
        if (existing.getClass() != newProduct.getClass()) {
            return false; // Products must belong to the same subclass
        }
        if (!existing.getName().equals(newProduct.getName()) ||
                existing.getPrice() != newProduct.getPrice()) {
            return false; // Name and price must match
        }

        // Check subclass-specific properties
        if (existing instanceof Crop && newProduct instanceof Crop) {
            Crop existingCrop = (Crop) existing;
            Crop newCrop = (Crop) newProduct;
            return existingCrop.getGrowthTime() == newCrop.getGrowthTime() &&
                    existingCrop.getYield() == newCrop.getYield() &&
                    existingCrop.getCropType() == newCrop.getCropType() &&
                    existingCrop.isImmune() == newCrop.isImmune();
        }

        if (existing instanceof Animal && newProduct instanceof Animal) {
            Animal existingAnimal = (Animal) existing;
            Animal newAnimal = (Animal) newProduct;

            // Include lifetime-specific checks for animals
            return existingAnimal.getAge() == newAnimal.getAge() &&
                    existingAnimal.getMaxAge() == newAnimal.getMaxAge() &&
                    existingAnimal.getProducedProduct().equals(newAnimal.getProducedProduct()) &&
                    existingAnimal.getRequiredFood() == newAnimal.getRequiredFood();
        }

        // For generic Product, no additional checks
        return true;
    }




    public boolean removeProduct(int index) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index!");
            return false;
        }

        Product product = inventory.get(index);
        if (product.getQuantity() == 0) {  // Check if quantity is zero
            inventory.remove(index);  // Remove product from storage if quantity is zero
            System.out.println("Product removed from storage: " + product.getName());
            return true;
        } else {
            System.out.println("Product still has quantity, cannot remove yet.");
            return false;  // Do not remove if the product still has quantity
        }
    }


    // Get product by index
    public Product getProduct(int index) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index!");
            return null;
        }
        return inventory.get(index); // Retrieves the product at the specified index
    }

    // In Storage class
    public void printStorageWithIndex(boolean isIndexingEnabled) {
        if (inventory.isEmpty()) {
            System.out.println("Storage is empty.");
        } else {
            System.out.println("Storage contents:");
            if (isIndexingEnabled) {
                // Print with index
                for (int i = 0; i < inventory.size(); i++) {
                    Product product = inventory.get(i);
                    String productDetails = i + ": " + product.getName() + " - Price: " + product.getPrice() + " - Quantity: " + product.getQuantity();

                    // If the product is an Animal, print additional details
                    if (product instanceof Animal) {
                        Animal animal = (Animal) product;
                        productDetails += " - Age: " + animal.getAge() + "/" + animal.getMaxAge() + " - Survival Time: " + animal.getSurvivalTime() + " - Produces: " + animal.getProducedProduct().getName();
                    }

                    // If the product is a Crop, print additional details
                    else if (product instanceof Crop) {
                        Crop crop = (Crop) product;
                        productDetails += " - Growth Time: " + crop.getGrowthTime() + " - Yield: " + crop.getYield();
                    }

                    System.out.println(productDetails);
                }
            } else {
                // Print without index
                for (Product product : inventory) {
                    String productDetails = product.getName() + " - Price: " + product.getPrice() + " - Quantity: " + product.getQuantity();

                    // If the product is an Animal, print additional details
                    if (product instanceof Animal) {
                        Animal animal = (Animal) product;
                        productDetails += " - Age: " + animal.getAge() + "/" + animal.getMaxAge() + " - Survival Time: " + animal.getSurvivalTime() + " - Produces: " + animal.getProducedProduct().getName();
                    }

                    // If the product is a Crop, print additional details
                    else if (product instanceof Crop) {
                        Crop crop = (Crop) product;
                        productDetails += " - Growth Time: " + crop.getGrowthTime() + " - Yield: " + crop.getYield();
                    }

                    System.out.println(productDetails);
                }
            }
        }
    }


    // Get the current number of products in the inventory
    public int size() {
        return inventory.size();
    }

    public LinkedList<Product> getInventory() {
        return inventory;
    }
}
