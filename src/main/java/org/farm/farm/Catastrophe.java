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
            if (crops[i] != null && RANDOM.nextInt(100) < 50) {  // 50% chance to reduce growth
                if(crops[i].isImmune()){
                    continue;
                }
                crops[i].decrementGrowthTime();
                System.out.println("Catastrophe reduced crop growth!");
            }
        }
    }

    private static void affectAnimals(Animal[] animals) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null && animals[i].getAge() > 5 && RANDOM.nextInt(100) < 50) {  // 50% chance to kill older animals
                System.out.println("A catastrophe killed an animal aged " + animals[i].getAge());
                animals[i] = null;  // Remove the animal by setting it to null
            }
        }
    }
}
