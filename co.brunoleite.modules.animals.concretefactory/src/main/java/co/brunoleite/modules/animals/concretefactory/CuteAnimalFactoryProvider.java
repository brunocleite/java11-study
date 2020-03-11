package co.brunoleite.modules.animals.concretefactory;

import co.brunoleite.modules.animals.factory.AnimalFactory;
import co.brunoleite.modules.animals.factory.AnimalFactoryProvider;

public class CuteAnimalFactoryProvider implements AnimalFactoryProvider {
    @Override
    public AnimalFactory get() {
        return new CuteAnimalFactory();
    }
}
