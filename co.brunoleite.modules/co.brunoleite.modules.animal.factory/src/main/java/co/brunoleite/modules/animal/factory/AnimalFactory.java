package co.brunoleite.modules.animal.factory;

import co.brunoleite.modules.animal.Animal;
import co.brunoleite.modules.animal.Cat;
import co.brunoleite.modules.animal.Dog;

public interface AnimalFactory {

    Cat createCat();
    Dog createDog();
    <A extends Animal> A createCustom(Class<A> customAnimalClass);

}
