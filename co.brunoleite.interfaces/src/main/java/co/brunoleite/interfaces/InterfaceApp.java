package co.brunoleite.interfaces;

import co.brunoleite.interfaces.functional.TriFunction;

import java.util.function.Predicate;

public class InterfaceApp {

    public static void main(String[] args) {
        System.out.println("Animal kingdom: " + Animal.Constants.KINGDOM);

        Rottweiler rottweiler = new Rottweiler();
        rottweiler.bite();

        AggressiveDog.talk();

        String message = "Various cool animals: ";
        TriFunction<String, String, String, String> t = (d1, d2, d3) ->  message + d1 + "," + d2 + "," + d3;
        System.out.println(t.apply("Rottweiler", "German Shepherd", "Street dog"));

        //Two below are same thing
        Predicate<String> p = pp->pp.isEmpty();
        Predicate<String> p2 = String::isEmpty;
    }

}
