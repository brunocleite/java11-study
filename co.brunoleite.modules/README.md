# Java 11 Study - Modules

[JEP 261: Module system](http://openjdk.java.net/jeps/261)

For understanding the new Java module system that was introduced on Java 9 an application was built that uses all the features.
This applications is comprised of 5 modules:

1. `co.brunoleite.modules.animal` -> declares base and child model classes, Animal, Cat and Dog
2. `co.brunoleite.modules.animal.factory` -> declares interfaces for Animal factories (using abstract factory pattern)
3. `co.brunoleite.modules.animal.concretefactory` -> declares implementations for the Animal factories
4. `co.brunoleite.modules.animal.app` -> the application that uses the other 3 modules
5. `co.brunoleite.modules.animal.extended` -> some other animals declarations, used for 'requires static' example

## Certification topic: Understanding Modules (1Z0-815 and 1Z0-817)

### 1. Describe the Modular JDK

#### JDK modules
Beginning on Java 9 the JDK has been splitted up into modules. This enables the developer to use a lightweight version of the JDK with just the needed modules.
The Java API modules are also referred as `platform modules` and their names begin with `java.*`
The `java.base` module is the main module and is available to all applications even if it is not declared on `module-info.java` file.

#### jlink tool

`jlink` is a tool to create a custom Java runtime image that contains only the modules that are required for a given application

Before using `jlink`, first compile all the modules. 

```bash
javac11 -d out --module-source-path "./*/src/main/java" $(find -name "*.java")
```

##### Linking files in an exploded directory

Exploded directory is a a directory that contains `.class` files
```bash
jlink --launcher AnimalApp=co.brunoleite.modules.animal.app/co.brunoleite.modules.animal.app.AnimalApp --module-path /usr/lib/jvm/jdk-11.0.6/jmods:out --add-modules co.brunoleite.modules.animal.app --output mod

./mod/bin/AnimalApp
```
- `--launcher` option specifies the application entry point
- `--module-path` specifies where `jlink` will find the modules needed by the application. Both the JDK modules and the compiled classes `out` directory are specified here.
- `--add-modules` specifies which module to add, here we are including just the main module `co.brunoleite.modules.animal.app` because it will search for the dependencies and include those as well
- `--output` tells the output directory

##### Creating JAR files for modules
```bash
mkdir lib
jar11 --create --file lib/co.brunoleite.modules.animal.jar -C out/co.brunoleite.modules.animal .
jar11 --create --file lib/co.brunoleite.modules.animal.app.jar --main-class co.brunoleite.modules.animal.app.AnimalApp -C out/co.brunoleite.modules.animal.app .
jar11 --create --file lib/co.brunoleite.modules.animal.concretefactory.jar -C out/co.brunoleite.modules.animal.concretefactory .
jar11 --create --file lib/co.brunoleite.modules.animal.factory.jar -C out/co.brunoleite.modules.animal.factory .
jar11 --create --file lib/co.brunoleite.modules.animal.extended.jar -C out/co.brunoleite.modules.animal.extended .
```
- `--create` tells `jar` tool to create a jar file
- `--file` specified the output file name
- `-C` specifies the directory with files to include

Now we can use `jlink` as above, but with two changes:
1. Specify the jar `lib` folder instead of exploded `out` folder on `--module-path`
2. On `--launcher` we can specify just the module name as we already created the `co.brunoleite.modules.animal.app.jar` with the main class information.
```bash
jlink --launcher AnimalApp=co.brunoleite.modules.animal.app --module-path /usr/lib/jvm/jdk-11.0.6/jmods:lib --add-modules co.brunoleite.modules.animal.app --output mod
```

##### Resolving dependencies through `jdeps`

When using the `jlink` you always have to specify the dependencies so that the tool can assemble the Java Runtime Environment

To list the modules that a `jar` needs:
```bash
jdeps11 --module-path co.brunoleite.modules/lib --list-deps co.brunoleite.modules/lib/co.brunoleite.modules.animal.app.jar
```


 

### 2. Declare modules and enable access between modules

Module declaration is available on all 4 modules through the `module-info.java`

The `exports` directive is used on `co.brunoleite.modules.animal` and `co.brunoleite.modules.animal.factory` modules.

The `exports..to` directive is used on `co.brunoleite.modules.animal`. Please have in mind that even if `co.brunoleite.modules.animal.factory` declares transitive dependency on `co.brunoleite.modules.animal` it wouldn't be available for `co.brunoleite.modules.animal.app` if it wasn't in the `to..` clause.  

The `requires` directive is used on `co.brunoleite.modules.animal.app` and `co.brunoleite.module.animal.concretefactory`

The `requires transitive` directive is used on `co.brunoleite.modules.animal.factory` so that the concrete factory can also be able to access Animal classes.

The `provides..with` directive is used on `co.brunoleite.modules.animal.concretefactory` to provide implementations for AnimalFactory

The `uses` directive is used on `co.brunoleite.modules.animal.app` to indicate the use of a service

The `opens` directive is used on `co.brunoleite.modules.animal.app` to open the `Bird` class to runtime access by `co.brunoleite.modules.animal.concretefactory` method `createCustom(Class<A extends Animal> animal)`. It could be used `open` on the whole module declaration also.

The `opens..to` directive is the same as `opens` but for a specific module

The `requires static` is used on `co.brunoleite.modules.animal.app` to declare a compile-time dependency. The required module does not make to the module graph.
 

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
    $(find -name "*.java")

java11 -p out -m co.brunoleite.modules.animal.app/co.brunoleite.modules.animal.app.AnimalApp
```



## Certification topic: Services in a modular application (1Z0-816 and 1Z0-817)

### 1. Describe the components of Services including directives

The specific services directives are `provides..with` and `uses`. Those uses are described on [section 2](#2-declare-modules-and-enable-access-between-modules) above.

### 2. Design a service type, load the services using ServiceLoader, check for dependencies of the services including consumer module and provider module

In this example:

The service module is `co.brunoleite.modules.animal.factory`

The service provider module is `co.brunoleite.modules.animal.concretefactory`

The consumer module is `co.brunoleite.modules.animal.app`. It uses the ServiceLoader to load the provider.
