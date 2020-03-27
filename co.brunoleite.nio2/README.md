# Java 11 Study - Java File I/O (NIO2)
 

## Certification topic: Java File I/O (NIO.2) (1Z0-816 and 1Z0-817)


### 1. Use Path interface to operate on file and directory paths

Path interface is used to represent files and directories on the system.

To create a `Path` use the method `Path.of(String first, String... others)`

`Path.of("/usr/lib")` is the same as `Path.of("/usr","lib")`

Examples can be found on `co.brunoleite.nio2.LearningPaths` class.

The more complicated parts are `resolve` and `relativize`

#### Resolve

Resolve gets the Path A and appends Path B

If Path B is absolute path then it will return Path B.

#### Relativize

Relativize gives a relative path to navigate from Path A to Path B.

i.e. if you use `cd` on the result of relativize it will take you from Path A to Path B


### 2. Use Files class to check, delete, copy or move a file or directory

[Oracle Files tutorial](https://docs.oracle.com/javase/tutorial/essential/io/file.html)

Examples can be found on `co.brunoleite.nio2.LearningFiles` class.

### 3. Use Stream API with Files

As objective above, examples can be found on `co.brunoleite.nio2.LearningFiles` class.

