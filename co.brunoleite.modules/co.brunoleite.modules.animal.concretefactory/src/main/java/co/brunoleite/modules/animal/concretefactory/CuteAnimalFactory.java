package co.brunoleite.modules.animal.concretefactory;

import co.brunoleite.modules.animal.Cat;
import co.brunoleite.modules.animal.CuteCat;
import co.brunoleite.modules.animal.CuteDog;
import co.brunoleite.modules.animal.Dog;
import co.brunoleite.modules.animal.factory.AnimalFactory;

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
