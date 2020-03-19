package co.brunoleite.interfaces;

public interface Animal {

    //abstract method
    String makeSound(); //This will always be 'public' and 'abstract'

    //nested interface
    interface Constants { //This is always 'static'

        //constant variable
        String KINGDOM = "Animalia"; //This will always be 'public', 'static' and 'final'

    }

}
