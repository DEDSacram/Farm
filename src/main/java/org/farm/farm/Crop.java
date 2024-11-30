package org.farm.farm;

public class Crop extends Product {
    private int growthTime;
    private int yield;  // The yield of the crop when harvested
    private boolean isImmune;
    private EdibleCropType cropType;  // The type of crop (for feeding animals)

    public Crop(String name, int price, int growthTime, int yield, EdibleCropType cropType) {
        this(name, price, growthTime, yield, cropType, false);
    }

    public Crop(String name, int price, int growthTime, int yield, EdibleCropType cropType, boolean isImmune) {
        super(name, price);  // Call the constructor of Product
        this.growthTime = growthTime;
        this.yield = yield;
        this.cropType = cropType;
        this.isImmune = isImmune;
    }

    public boolean isImmune() {
        return isImmune;
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

    public EdibleCropType getCropType() {
        return cropType;
    }

    @Override
    public String toString() {
        return super.toString() + " - Growth Time: " + growthTime + " - Yield: " + yield + " - Crop Type: " + cropType;
    }

    // Set quantity to yield when harvested
    public void harvest() {
        setQuantity(yield);  // Set quantity to the crop's yield
    }
}
