package co.brunoleite.modules.animals.concretefactory;

import co.brunoleite.animal.Cat;
import co.brunoleite.animal.CuteCat;
import co.brunoleite.animal.CuteDog;
import co.brunoleite.animal.Dog;
import co.brunoleite.modules.animals.factory.AnimalFactory;

public class CuteAnimalFactory implements AnimalFactory {

    @Override
    public Cat createCat() {
        return new CuteCat();
    }

    @Override
    public Dog createDog() {
        return new CuteDog();
    }
}
