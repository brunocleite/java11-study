# Java 11 Study - Modules

[JEP 261: Module system](http://openjdk.java.net/jeps/261)

For understanding the new Java module system that was introduced on Java 9 I've built an application that uses all the features.
This applications is comprised of 4 modules:

1. `co.brunoleite.modules` -> the application that uses the other 3 modules
2. `co.brunoleite.modules.animals` -> declares base and child model classes, Animal, Cat and Dog
3. `co.brunoleite.modules.animals.factory` -> declares interfaces for Animal factories (using abstract factory pattern)
4. `co.brunoleite.modules.animals.concretefactory` -> declares implementations for the Animal factories 

## Certification topic checklist: Understanding Modules

### 1. Describe the Modular JDK

### 2. Declare modules and enable access between modules

Module declaration is available on all 4 modules through the `module-info.java`

The `exports` directive is used on `co.brunoleite.modules.animals` and `co.brunoleite.modules.animals.factory` modules.

The `requires` directive is used on `co.brunoleite.modules` and `co.brunoleite.modules.animals.concretefactory`

The `requires transitive` directive is used on `co.brunoleite.modules.animals.factory` so that the concrete factory can also be able to access Animal classes.

The `provides..with` directive is used on `co.brunoleite.modules.animals.factory.concretefactory` to provide implementations for AnimalFactory

The `uses` directive is used on `co.brunoleite.modules`

### 3. Describe how a modular project is compiled and run

All commands are executed from the project root `/java11-study`

#### Compile not dependent module

Compile module `co.brunoleite.modules.animals` which is a module that does not depend on any other:

```bash
javac -d co.brunoleite.modules.animals/target/classes \
    co.brunoleite.modules.animals/src/main/java/module-info.java \
    co.brunoleite.modules.animals/src/main/java/co/brunoleite/animal/*.java
```

#### Compile module dependent on another

Compile module `co.brunoleite.modules.animals.factory` which is a module that depends on `co.brunoleite.modules.animals`

This example shows the use of `module-path` which points to a directory tree of compiled `.class` files for the dependent modules.

```bash
javac -d co.brunoleite.modules.animals.factory/target/classes \
    --module-path co.brunoleite.modules.animals/target/classes \
    co.brunoleite.modules.animals.factory/src/main/java/module-info.java \
    co.brunoleite.modules.animals.factory/src/main/java/co/brunoleite/modules/animals/factory/*.java
```

#### Multi-module compilation

To compile all the modules together.

This example shows the use of `module-source-path` which compiles various dependent modules together. 
The order of specifying `module-source-path` matters, it must have the non-dependent first.


```bash
javac11 -d out \
    --module-source-path co.brunoleite.modules.animals/src/main/java \
    --module-source-path co.brunoleite.modules.animals.factory/src/main/java \
    --module-source-path co.brunoleite.modules.animals.concretefactory/src/main/java \
    --module-source-path co.brunoleite.modules/src/main/java \
    co.brunoleite.modules/src/main/java/co/brunoleite/modules/AnimalApp.java    
```