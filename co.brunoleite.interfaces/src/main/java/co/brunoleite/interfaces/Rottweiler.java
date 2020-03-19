package co.brunoleite.interfaces;

public class Rottweiler implements Canis, AggressiveDog {

    @Override
    public void bite() {
        Canis.super.bite();
        AggressiveDog.super.bite();
        System.out.println("Rottweiler bite");
    }
}
