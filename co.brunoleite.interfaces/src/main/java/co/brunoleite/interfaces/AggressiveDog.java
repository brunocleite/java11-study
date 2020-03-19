package co.brunoleite.interfaces;

public interface AggressiveDog {

    default void bite(){
        localPrint("Dog bite");
    }

    static void talk(){ //Is always 'public' and can't be shadowed by implementations
        print("Aggressive dog says: Roaf...");
    }

    private void localPrint(String s){
        print(s);
    }

    private static void print(String s){
        System.out.println(s);
    }

}
