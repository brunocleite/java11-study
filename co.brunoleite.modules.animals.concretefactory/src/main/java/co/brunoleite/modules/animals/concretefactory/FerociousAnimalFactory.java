package co.brunoleite.modules.animals.concretefactory;

import co.brunoleite.animal.Cat;
import co.brunoleite.animal.Dog;
import co.brunoleite.animal.FerociousCat;
import co.brunoleite.animal.FerociousDog;
import co.brunoleite.modules.animals.factory.AnimalFactory;

public class FerociousAnimalFactory implements AnimalFactory {
    @Override
    public Cat createCat() {
        return new FerociousCat();
    }

    @Override
    public Dog createDog() {
        return new FerociousDog();
    }
}
