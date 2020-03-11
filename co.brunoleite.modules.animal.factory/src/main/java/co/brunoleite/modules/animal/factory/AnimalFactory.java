package co.brunoleite.modules.animal.factory;

import co.brunoleite.modules.animal.Cat;
import co.brunoleite.modules.animal.Dog;

public interface AnimalFactory {

    Cat createCat();
    Dog createDog();

}
