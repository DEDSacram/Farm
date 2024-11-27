package org.farm.farm;


public class Animal extends Product {
    private int survivalTime;  // Turns remaining until the animal must be fed
    private int age;           // Animal's age in turns
    private Product producedProduct;   // The product the animal generates (e.g., milk, eggs)

    public Animal(String name, int price, int survivalTime, Product producedProduct, int age) {
        super(name,price);  // Use Product constructor
        this.survivalTime = survivalTime;
        this.producedProduct = producedProduct;
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

    public int getAge() {
        return age;
    }

    public void increaseAge() {
        age++;
    }
    @Override
    public String toString() {
        return super.toString() + ", Survival Time: " + survivalTime + ", Age: " + age + ", Produces: " + producedProduct.getName();
    }
}

