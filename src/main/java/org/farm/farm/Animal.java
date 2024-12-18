package org.farm.farm;

import java.util.Random;

public class Animal extends Product {
    private int survivalTime;  // Turns remaining until the animal must be fed
    private final int maxSurvivalTime;
    private int age;           // Animal's age in turns
    private final int maxAge;        // Animal's maximum lifespan
    private final Product producedProduct;   // The product the animal generates (e.g., milk, eggs)
    private final EdibleCropType requiredFood; // The type of crop the animal eats

    public Animal(String name, int price, int survivalTime, Product producedProduct, EdibleCropType requiredFood, int age, int baseMaxAge, int ageRange) {
        super(name, price);  // Use Product constructor
        this.survivalTime = survivalTime;
        this.maxSurvivalTime = survivalTime;
        this.producedProduct = producedProduct;
        this.requiredFood = requiredFood;
        this.age = age;

        // Assign maxAge based on baseMaxAge ± ageRange
        Random random = new Random();
        this.maxAge = baseMaxAge + random.nextInt(ageRange * 2 + 1) - ageRange; // Random between baseMaxAge - ageRange and baseMaxAge + ageRange
    }
    public void decreaseSurvivalTime(){
        this.survivalTime--;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void resetSurvivalTime() {
        this.survivalTime = maxSurvivalTime;
    }

    public Product getProducedProduct() {
        return producedProduct;
    }

    public EdibleCropType getRequiredFood() {
        return requiredFood;
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void increaseAge() {
        age++;
    }

    // Check if the animal is alive based on its age
    public boolean isAlive() {
        return age < maxAge;
    }

    // Feed the animal with a crop
    public boolean feed(EdibleCropType food) {
        if (this.requiredFood == food) {
            this.survivalTime = this.maxSurvivalTime; // Reset survival time
            System.out.println(this.getName() + " has been fed with " + food);
            return true;
        } else {
            System.out.println(this.getName() + " cannot eat " + food);
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Survival Time: " + survivalTime + ", Age: " + age + "/" + maxAge + ", Produces: " + producedProduct.getName()
                + ", Eats: " + requiredFood;
    }

    public int getMaxSurvivalTime() {
        return maxSurvivalTime;
    }
}
