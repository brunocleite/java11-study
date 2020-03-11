package co.brunoleite.modules.animal.concretefactory;

import co.brunoleite.modules.animal.factory.AnimalFactory;
import co.brunoleite.modules.animal.factory.AnimalFactoryProvider;

public class CuteAnimalFactoryProvider implements AnimalFactoryProvider {
    @Override
    public AnimalFactory get() {
        return new CuteAnimalFactory();
    }
}
