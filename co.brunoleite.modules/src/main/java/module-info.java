module co.brunoleite.modules {
    requires co.brunoleite.modules.animals.factory;
    requires co.brunoleite.modules.animals.concretefactory; //Removing this line and also Intellij dependency on this module would still compile but no concrete providers would be found

    uses co.brunoleite.modules.animals.factory.AnimalFactoryProvider;
}