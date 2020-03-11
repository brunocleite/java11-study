import co.brunoleite.modules.animal.concretefactory.CuteAnimalFactoryProvider;
import co.brunoleite.modules.animal.concretefactory.FerociousAnimalFactoryProvider;
import co.brunoleite.modules.animal.factory.AnimalFactoryProvider;

module co.brunoleite.modules.animal.concretefactory {
    requires co.brunoleite.modules.animal.factory;

    provides AnimalFactoryProvider with
            FerociousAnimalFactoryProvider,
            CuteAnimalFactoryProvider;
}