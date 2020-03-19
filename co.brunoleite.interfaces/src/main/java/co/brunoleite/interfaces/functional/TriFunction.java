package co.brunoleite.interfaces.functional;

@FunctionalInterface
public interface TriFunction<T,U,V,R> {

    R apply(T t, U u, V v);

    default void whatType(){ // Not abstract, does not count for Functional Interface SAM
        print("This is a TriFunction");
    }


    private void print(String s){ // Not abstract
        System.out.println(s);
    }

    static void staticMethod(){
        System.out.println("Cool static method"); // Not abstract
    }

    private static void privateStaticMethod(){
        System.out.println("Cool private static method"); // Not abstract
    }

    boolean equals(Object o); // Abstract, but as it is public method from java.lang.Object does not count

    //Object clone(); // not ignored, as it's not a public (but protected) method from java.lang.Object

}
