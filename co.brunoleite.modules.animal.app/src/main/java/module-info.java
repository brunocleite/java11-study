module co.brunoleite.modules.animal.app {
    requires co.brunoleite.modules.animal.factory;
    requires co.brunoleite.modules.animal.concretefactory; //Removing this line and also Intellij dependency on this module would still compile but no concrete providers would be found

    uses co.brunoleite.modules.animal.factory.AnimalFactoryProvider;
}