package org.farm.farm;



public class Crop extends Product {
    private int growthTime;  // Turns required for growth
    private int yield;       // Quantity produced

    public Crop(String name,int price, int growthTime, int yield) {
        super(name,price);
        this.growthTime = growthTime;
        this.yield = yield;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void decrementGrowthTime() {
        if (growthTime > 0) growthTime--;
    }

    public int getYield() {
        return yield;
    }

    @Override
    public String toString() {
        return super.toString() + ", Growth Time: " + growthTime + ", Yield: " + yield;
    }

}
