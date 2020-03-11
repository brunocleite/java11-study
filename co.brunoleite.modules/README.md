# Java 11 Study - Modules

[JEP 261: Module system](http://openjdk.java.net/jeps/261)

For understanding the new Java module system that was introduced on Java 9 I've built an application that uses all the features.
This applications is comprised of 4 modules:

1. `co.brunoleite.modules.animal` -> declares base and child model classes, Animal, Cat and Dog
2. `co.brunoleite.modules.animal.factory` -> declares interfaces for Animal factories (using abstract factory pattern)
3. `co.brunoleite.modules.animal.concretefactory` -> declares implementations for the Animal factories
4. `co.brunoleite.modules.animal.app` -> the application that uses the other 3 modules 

## Certification topic checklist: Understanding Modules

### 1. Describe the Modular JDK

TBD

### 2. Declare modules and enable access between modules

Module declaration is available on all 4 modules through the `module-info.java`

The `exports` directive is used on `co.brunoleite.modules.animal` and `co.brunoleite.modules.animal.factory` modules.

The `requires` directive is used on `co.brunoleite.modules.animal.app` and `co.brunoleite.module.animal.concretefactory`

The `requires transitive` directive is used on `co.brunoleite.modules.animal.factory` so that the concrete factory can also be able to access Animal classes.

The `provides..with` directive is used on `co.brunoleite.modules.animal.concretefactory` to provide implementations for AnimalFactory

The `uses` directive is used on `co.brunoleite.modules.animal.app` to indicate the use of a service

### 3. Describe how a modular project is compiled and run

All commands are executed from the modules root `/java11-study/co.brunoleite.modules`

#### Compile not dependent module

Compile module `co.brunoleite.modules.animals` which is a module that does not depend on any other:

```bash
javac11 -d co.brunoleite.modules.animal/target/classes \
    co.brunoleite.modules.animal/src/main/java/module-info.java \
    co.brunoleite.modules.animal/src/main/java/co/brunoleite/modules/animal/*.java
```

#### Compile module dependent on another

Compile module `co.brunoleite.modules.animal.factory` which is a module that depends on `co.brunoleite.modules.animal`

This example shows the use of `module-path` which points to a directory tree of compiled `.class` files for the dependent modules.

```bash
javac11 -d co.brunoleite.modules.animal.factory/target/classes \
    --module-path co.brunoleite.modules.animal/target/classes \
    co.brunoleite.modules.animal.factory/src/main/java/module-info.java \
    co.brunoleite.modules.animal.factory/src/main/java/co/brunoleite/modules/animal/factory/*.java
```

#### Multi-module compilation

To compile all the modules together.

This example shows the use of `module-source-path` which compiles various dependent modules together. 

```bash
javac11 -d out \
    --module-source-path "./*/src/main/java" \
    "co.brunoleite.modules.animal.app/src/main/java/co/brunoleite/modules/animal/app/AnimalApp.java"

java11 -p out -m co.brunoleite.modules.animal.app/co.brunoleite.modules.animal.app.AnimalApp
```
