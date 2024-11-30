package org.farm.farm;

import java.util.Random;

public class Catastrophe {
    private static final Random RANDOM = new Random();

    public static void applyCatastrophe(Farm farm) {
        int type = RANDOM.nextInt(2);  // 0 = crops, 1 = animals
        if (type == 0) {
            affectCrops(farm.getFields());
        } else {
            affectAnimals(farm.getAnimals());
        }
    }

    private static void affectCrops(Crop[] crops) {
        for (int i = 0; i < crops.length; i++) {
            if (crops[i] != null) {

                // 5% chance to destroy the crop
                if (RANDOM.nextInt(100) < 5) {  // 5% chance of destruction
                    if (crops[i].isImmune()) {
                        continue;
                    }
                    crops[i] = null;  // Crop is destroyed by setting it to null
                    System.out.println("Catastrophe destroyed a crop!");
                    continue;  // Skip to the next crop since this one is destroyed
                }

                // 10% chance to reduce the crop growth (if not destroyed)
                if (RANDOM.nextInt(100) < 10) {  // 10% chance to reduce growth time
                    if (crops[i].isImmune()) {
                        continue;
                    }
                    // Generate a random value between 1.2 and 2.0 for growth increase
                    double randomFactor = 1.1 + (RANDOM.nextDouble() * 0.6);  // RANDOM.nextDouble() between 0.0 and 1.0
                    int growthIncrease = (int) (crops[i].getGrowthTime() * randomFactor);
                    crops[i].setGrowthTime(growthIncrease);

                    System.out.println("Catastrophe reduced crop growth!");
                }
            }
        }
    }


    private static void affectAnimals(Animal[] animals) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null && Math.floor((animals[i].getAge() / (double)animals[i].getMaxAge()) * 10) > 5 && RANDOM.nextInt(100) < 30) {
                System.out.println("A catastrophe killed an animal aged " + animals[i].getAge());
                animals[i] = null;  // Remove the animal by setting it to null
            }

        }
    }
}
