package co.brunoleite.animal.modules.app;

import co.brunoleite.modules.animal.Animal;
import co.brunoleite.modules.animal.factory.AnimalFactoryProvider;

import java.util.ServiceLoader;

public class AnimalApp {

    public static void main(String[] args) {
        ServiceLoader<AnimalFactoryProvider> loader = ServiceLoader.load(AnimalFactoryProvider.class);

        for (AnimalFactoryProvider provider : loader) {
            System.out.println("Found AnimalFactoryProvider: " + provider.get().getClass());
            printAnimal(provider.get().createCat());
            printAnimal(provider.get().createDog());
        }

    }

    private static void printAnimal(Animal animal) {
        if (animal.getFerocity() >= 6) {
            System.out.println("Wow, this is a ferocious " + animal.getSpecie());
        } else {
            System.out.println("What a cute " + animal.getSpecie());
        }
    }
}
