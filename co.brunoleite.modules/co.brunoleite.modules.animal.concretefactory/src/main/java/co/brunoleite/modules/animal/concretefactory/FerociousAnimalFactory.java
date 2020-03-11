package co.brunoleite.modules.animal.concretefactory;

import co.brunoleite.modules.animal.Cat;
import co.brunoleite.modules.animal.Dog;
import co.brunoleite.modules.animal.FerociousCat;
import co.brunoleite.modules.animal.FerociousDog;
import co.brunoleite.modules.animal.factory.AnimalFactory;

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
