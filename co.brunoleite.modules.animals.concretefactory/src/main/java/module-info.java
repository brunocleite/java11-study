import co.brunoleite.modules.animals.factory.AnimalFactoryProvider;

module co.brunoleite.modules.animals.concretefactory {
    requires co.brunoleite.modules.animals.factory;

    provides AnimalFactoryProvider with
            co.brunoleite.modules.animals.concretefactory.FerociousAnimalFactoryProvider,
            co.brunoleite.modules.animals.concretefactory.CuteAnimalFactoryProvider;
}