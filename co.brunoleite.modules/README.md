# Java 11 Study - Modules

For understanding the new Java module system that was introduced on Java 9 I've built an application that uses all the features.
This applications is comprised of 4 modules:

1. `co.brunoleite.modules` -> the application that the other 3 modules
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

To compile the project manually, run (from /study root):

```bash
javac -d out --module-source-path \ 
    co.brunoleite.modules/src/main/java: \
    co.brunoleite.modules.animals/src/main/java: \
    co.brunoleite.modules.animals.factory/src/main/java: \
    co.brunoleite.modules.animals.concretefactory/src/main/java: \
    
```