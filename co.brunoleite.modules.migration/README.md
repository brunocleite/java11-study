# Java 11 Study - Modules Migration

There are four types of modules in the new module system:

- `System Modules` – These are the modules listed when we run the list-modules command above. They include the Java SE and JDK modules.
- `Application Modules (Named Modules)` – These modules are what we usually want to build when we decide to use Modules. They are named and defined in the compiled module-info.class file included in the assembled JAR.
- `Automatic Modules` – We can include unofficial modules by adding existing JAR files to the module path. The name of the module will be derived from the name of the JAR. Automatic modules will have full read access to every other module loaded by the path.
- `Unnamed Module` – When a class or JAR is loaded onto the classpath, but not the module path, it's automatically added to the unnamed module. It's a catch-all module to maintain backward compatibility with previously-written Java code.

Modules have been introduced by two major reasons:
- `Reliable configuration` - to replace the brittle, error-prone class-path mechanism with a means for program components to declare explicit dependences upon one another, along with
- `Strong encapsulation` - to allow a component to declare which of its public types are accessible to other components, and which are not.

Here will be covered how to use an `Unnamed Module` and make it an `Automatic Module` for use with a modular application.
- `co.brunoleite.modules.migration.flower` directory does not have the `module-info.java` so it is not a module
- `co.brunoleite.modules.migration.flower.app` directory does not have the `module-info.java` so it is not a module

Unnamed modules
- Code in an `unnamed module` is shadowed by named modules. That means if an `unnamed module` has a class `com.foo.Bar` and a `named module` `exports com.foo` then the `unnamed module` Bar class will not be visible.
- `Automatic modules` can access all classes of all modules plus the `unnamed module`
- `Named modules` can't access classes of `unnamed module`


## Certification topic: Migration to a Modular Application (1Z0-816 and 1Z0-817)

First, delete the `module-info.java` file from both `co.brunoleite.modules.migration.flower` and `co.brunoleite.modules.migration.flower.app` to start with two fresh pre-module projects.

Let's first compile and run the classes the pre-module way.

```bash
mkdir -p co.brunoleite.modules.migration.flower/target/classes
javac -d co.brunoleite.modules.migration.flower/target/classes co.brunoleite.modules.migration.flower/src/main/java/co/brunoleite/modules/migration/flower/Rose.java
mkdir nomodule
jar cvf nomodule/co.brunoleite.modules.migration.flower.jar -C co.brunoleite.modules.migration.flower/target/classes .
```
```bash
mkdir -p co.brunoleite.modules.migration.flower.app/target/classes
javac -d co.brunoleite.modules.migration.flower.app/target/classes -cp nomodule/co.brunoleite.modules.migration.flower.jar co.brunoleite.modules.migration.flower.app/src/main/java/co/brunoleite/modules/migration/flower/app/FlowerApp.java
jar cvf nomodule/co.brunoleite.modules.migration.flower.app.jar -C co.brunoleite.modules.migration.flower.app/target/classes .
```
```bash
java -cp "nomodule/*" co.brunoleite.modules.migration.flower.app.FlowerApp
```

### 1. Migrate the application developed using a Java version prior to SE 9 to SE 11 including top-down and bottom-up migration, splitting a Java SE 8 application into modules for migration 

For migrating an application two questions must be made:

- What does the module require?
- What does the module export?

`jdeps` answers the first question. The public API design answers the second.

#### Using top-down migration (a.k.a Application migration)

For top-down migration we have to modularize (write the `module-info.java` file) our project and make our dependencies and the dependencies of our dependencies automatic modules.

Start by removing `module-info.java` if you have gone through the migration already
```bash
rm -rf */src/main/java/module-info.java
```

Let's use `jdeps` in our `co.brunoleite.modules.migration.flower` to see what it requires
```bash
jdeps11 --module-path nomodule/co.brunoleite.modules.migration.flower.jar -s nomodule/co.brunoleite.modules.migration.flower.app.jar 
co.brunoleite.modules.migration.flower -> java.base
co.brunoleite.modules.migration.flower.app.jar -> co.brunoleite.modules.migration.flower
co.brunoleite.modules.migration.flower.app.jar -> java.base
co.brunoleite.modules.migration.flower.app.jar -> java.logging
```

Write the `module-info.java` using `jdeps`
```bash
jdeps11 --generate-module-info . --module-path nomodule/ nomodule/co.brunoleite.modules.migration.flower.app.jar
mv co.brunoleite.modules.migration.flower.app/module-info.java co.brunoleite.modules.migration.flower.app/src/main/java/
```

Recompile
```bash
javac11 -d co.brunoleite.modules.migration.flower.app/target/classes \
    -p nomodule/co.brunoleite.modules.migration.flower.jar \
    co.brunoleite.modules.migration.flower.app/src/main/java/module-info.java \
    co.brunoleite.modules.migration.flower.app/src/main/java/co/brunoleite/modules/migration/flower/app/FlowerApp.java
```

Package
```bash
jar11 cvf module/co.brunoleite.modules.migration.flower.app.jar -e co.brunoleite.modules.migration.flower.app.FlowerApp -C co.brunoleite.modules.migration.flower.app/target/classes/ .
```

Run
```bash
java11 -p module/co.brunoleite.modules.migration.flower.app.jar:nomodule/co.brunoleite.modules.migration.flower.jar -m co.brunoleite.modules.migration.flower.app
```

#### Using bottom-up migration (a.k.a Library migration)

For bottom-up migration we start modularizing from the bottom-most library (only depends on modules) and keep going up. No automatic modules here.

Start by removing `module-info.java` if you have gone through the migration already
```bash
rm -rf */src/main/java/module-info.java
```

First, let's use `jdeps` in our `co.brunoleite.modules.migration.flower` to see that it requires `java.base`
```bash
jdeps11 co.brunoleite.modules.migration.flower

co.brunoleite.modules.migration.flower -> java.base
   co.brunoleite.modules.migration.flower             -> java.lang                                          java.base
```

Then let's use `jdeps` to generate the `module-info.java`
```bash
jdeps11 --generate-module-info . nomodule/co.brunoleite.modules.migration.flower.jar
mv co.brunoleite.modules.migration.flower/module-info.java co.brunoleite.modules.migration.flower/src/main/java/
```

Recompile `co.brunoleite.modules.migration.flower` with module information
```bash
javac11 -d co.brunoleite.modules.migration.flower/target/classes \
    co.brunoleite.modules.migration.flower/src/main/java/module-info.java \
    co.brunoleite.modules.migration.flower/src/main/java/co/brunoleite/modules/migration/flower/Rose.java
mkdir module
jar cvf module/co.brunoleite.modules.migration.flower.jar -C co.brunoleite.modules.migration.flower/target/classes .
```

Then run the `co.brunoleite.modules.migration.flower.app` as `unnammed module`
```bash
java11 --add-modules co.brunoleite.modules.migration.flower -p module/co.brunoleite.modules.migration.flower.jar -cp nomodule/co.brunoleite.modules.migration.flower.app.jar co.brunoleite.modules.migration.flower.app.FlowerApp
```
It was needed to use `--add-modules` to add the module `co.brunoleite.modules.migration.flower` to the default root set. Otherwise it wouldn't be seen by `unnamed module`.


### 2. Use jdeps to determine dependencies and identify way to address the cyclic dependencies

Detailed explanation on this topic can be found on http://java.boot.by/ocpjd11-upgrade-guide/ch06s02.html
