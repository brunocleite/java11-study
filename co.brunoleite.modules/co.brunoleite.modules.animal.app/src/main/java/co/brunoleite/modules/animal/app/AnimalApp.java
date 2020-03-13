package co.brunoleite.modules.animal.app;

import co.brunoleite.modules.animal.Animal;
import co.brunoleite.modules.animal.extended.Frog;
import co.brunoleite.modules.animal.factory.AnimalFactoryProvider;

import java.util.ServiceLoader;

public class AnimalApp {

    public static void main(String[] args) {
        ServiceLoader<AnimalFactoryProvider> loader = ServiceLoader.load(AnimalFactoryProvider.class);

        for (AnimalFactoryProvider provider : loader) {
            System.out.println("Found AnimalFactoryProvider: " + provider.get().getClass());
            printAnimal(provider.get().createCat());
            printAnimal(provider.get().createDog());
            printAnimal(provider.get().createCustom(Bird.class)); //Bird is on this module and opened so `concretefactory` module can access it
            try {
                printAnimal(provider.get().createCustom(Frog.class)); //Frog is on `extended` module which is `requires static` so it is not available at runtime
            }catch(NoClassDefFoundError e){
                System.out.println("The frog run away");
            }
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
