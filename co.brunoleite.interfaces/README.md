# Java 11 Study - Interfaces
 
Java 9 introduced private methods and private static method in interfaces. In Java 9 (and hence Java 11 too) an interface can have seven different things:

- constant variables (since Java 1.0)

- abstract methods (since Java 1.0)

- nested types (since Java 2.0)

- default methods (since Java 8.0)

- static methods (since Java 8.0)

- private methods (since Java 9.0)

- private static methods (since Java 9.0) 

## Certification topic: Java Interfaces (1Z0-816 and 1Z0-817)


### 1. Create and use methods in interfaces


## Constant variable
`co.brunoleite.interfaces.Animal` shows a declaration of a constant variable

## Abstract method
`co.brunoleite.interfaces.Animal` shows a declaration of an abstract method

## Nested interface
`co.brunoleite.interfaces.Animal` shows a declaration of a nested interface

`Map.Entry` is an example of a nested interface.

## Default methods

The classes `Rottweiler` and interfaces `AgressiveDog` and `Canis` show the use of default methods, including implementation of two interfaces with same default method name.

## Static methods

The interface `AgressiveDog` shows an example of a `static` method.

## Private methods

Are used as helper methods for `default` methods inside interface.
The interface `AgressiveDog` shows an example of that.

## Private static methods

Are used as helper methods for `default` or `static` methods.
Again, the interface `AgressiveDog` shows an example of that.

### 2. Define and write functional interfaces

Functional interfaces provide target types for lambda expressions and method references. Each functional interface has a single abstract method (SAM), called the functional method for that functional interface, to which the lambda expression's parameter and return types are matched or adapted. 

 Functional interfaces can provide a target type in multiple contexts, such as assignment context, method invocation, or cast context:

```bash
// Assignment context
Predicate<String> p = String::isEmpty;					
```

```bash
// Method invocation context
stream.filter(e -> e.getSize() > 10)...
```

```bash
// Cast context
stream.map((ToIntFunction) e -> e.getSize())...
```

`java.util.function` provides a default JDK set of functional interfaces. BiFunction is one of them.

`TriFunction` shows an example of a Functional Interface with 3 parameters. 