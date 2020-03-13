package co.brunoleite.modules.animal.concretefactory;

import co.brunoleite.modules.animal.Animal;
import co.brunoleite.modules.animal.factory.AnimalFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractAnimalFactory implements AnimalFactory {

    @Override
    public <A extends Animal> A createCustom(Class<A> customAnimalClass){
        try {
            return customAnimalClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Could not instantiate animal of class "+ customAnimalClass.getCanonicalName());
            e.printStackTrace();
        }
        return null;
    }


}
