package org.farm.farm;

public class Animal extends Product {
    private int survivalTime;  // Turns remaining until the animal must be fed
    private int age;           // Animal's age in turns
    private Product producedProduct;   // The product the animal generates (e.g., milk, eggs)
    private EdibleCropType requiredFood; // The type of crop the animal eats

    public Animal(String name, int price, int survivalTime, Product producedProduct, EdibleCropType requiredFood, int age) {
        super(name, price);  // Use Product constructor
        this.survivalTime = survivalTime;
        this.producedProduct = producedProduct;
        this.requiredFood = requiredFood;
        this.age = age;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void resetSurvivalTime(int time) {
        this.survivalTime = time;
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

    public void increaseAge() {
        age++;
    }

    // Feed the animal with a crop
    public boolean feed(EdibleCropType food, int newSurvivalTime) {
        if (this.requiredFood == food) {
            this.survivalTime = newSurvivalTime; // Reset survival time
            System.out.println(this.getName() + " has been fed with " + food);
            return true;
        } else {
            System.out.println(this.getName() + " cannot eat " + food);
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Survival Time: " + survivalTime + ", Age: " + age + ", Produces: " + producedProduct.getName()
                + ", Eats: " + requiredFood;
    }
}
