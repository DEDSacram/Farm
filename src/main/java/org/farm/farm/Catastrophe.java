package org.farm.farm;



import java.util.List;
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

    private static void affectCrops(List<Crop> crops) {
        for (Crop crop : crops) {
            if (RANDOM.nextInt(100) < 50) { // 50% chance to reduce growth
                crop.decrementGrowthTime();
                System.out.println("Catastrophe reduced crop growth!");
            }
        }
    }

    private static void affectAnimals(List<Animal> animals) {
        animals.removeIf(animal -> {
            if (animal.getAge() > 5 && RANDOM.nextInt(100) < 50) {  // 50% chance to kill older animals
                System.out.println("A catastrophe killed an animal aged " + animal.getAge());
                return true;
            }
            return false;
        });
    }
}

