package co.brunoleite.modules.animals.factory;

import co.brunoleite.animal.Cat;
import co.brunoleite.animal.Dog;

public interface AnimalFactory {

    Cat createCat();
    Dog createDog();

}
