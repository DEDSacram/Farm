package org.farm.farm;

public class Crop extends Product {
    private int growthTime;
    private int yield;  // The yield of the crop when harvested

    public Crop(String name, int price, int growthTime, int yield) {
        super(name, price);  // Call the constructor of Product
        this.growthTime = growthTime;
        this.yield = yield;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void decrementGrowthTime() {
        if (growthTime > 0) {
            growthTime--;  // Decrease growth time
        }
    }

    public int getYield() {
        return yield;
    }

    @Override
    public String toString() {
        return super.toString() + " - Growth Time: " + growthTime + " - Yield: " + yield;
    }

    // Set quantity to yield when harvested
    public void harvest() {
        setQuantity(yield);  // Set quantity to the crop's yield
    }
}
