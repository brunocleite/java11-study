package co.brunoleite.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class LearningPaths {

    private static Logger log = Logger.getLogger(LearningPaths.class.getName());

    public static void main(String[] args) throws IOException {
        String dir = "src/main/resources";
        Path dirPath = Path.of(dir);

        String fileName = "text-file.txt";
        //Resolve just goes deeper into the path
        Path path = dirPath.resolve(fileName);

        log.info("The filename is " + path.getFileName());
        log.info("The file system is " + path.getFileSystem());

        for (Path p : path) {
            log.info("Path part: " + p);
        }
        for (int i = 0; i < path.getNameCount(); i++) {
            log.info("The name for index " + i + " is " + path.getName(i));
        }

        log.info("Comparing " + path.toString() + " to " + Path.of(dir) + " results " + path.compareTo(Path.of(dir)));

        log.info(path.toString() + " ends with /text-file ? " + path.endsWith("/text-file"));
        log.info(path.toString() + " ends with /text-file.txt ? " + path.endsWith("/text-file.txt"));
        log.info(path.toString() + " ends with text-file.txt ? " + path.endsWith("text-file.txt"));
        log.info(path.toString() + " ends with resources/text-file.txt ? " + path.endsWith("resources/text-file.txt"));

        log.info("The parent for " + path + " is " + path.getParent());
        log.info("The parent for " + path.getParent() + " is " + path.getParent().getParent());
        log.info("The parent for " + path.getParent().getParent() + " is " + path.getParent().getParent().getParent());
        log.info("The parent for " + path.getParent().getParent().getParent() + " is " + path.getParent().getParent().getParent().getParent());

        log.info("The parent for /usr is " + Path.of("/usr").getParent());

        log.info("The root for " + path + " is " + path.getRoot());

        log.info("Is the path " + path + " absolute? " + path.isAbsolute());

        //Relativizing means: if I'm on the first path and want to go into the second, what should I pass to 'cd' command
        relativize(path, path);
        relativize(path, Path.of("src/main"));
        relativize("/usr", "/usr/lib");
        relativize("/usr/lib", "/usr");
        try {
            relativize("/usr/lib", "lib");
        } catch (IllegalArgumentException e) {
        }
        relativize("usr/lib", "usr");
        relativize(path.toString(), "other-file.txt");

        resolve("/usr/lib", "/usr/bin");
        resolve("/usr/lib", "logs");
        resolve("usr/lib", "logs");

        log.info("Absolute path for " + path + " is " + path.toAbsolutePath());
        Path invalidAbsolutePath = Path.of("invalid/path");
        log.info("Absolute path for " + invalidAbsolutePath + " is " + invalidAbsolutePath.toAbsolutePath());

        //resolveSibling is same as (getParent() == null) ? other : getParent().resolve(other)
        log.info("Resolving sibling for " + path + " is " + path.resolveSibling("another-file.txt"));

        Path e1 = Path.of("/usr/lib");
        Path e2 = Path.of("/usr", "lib");
        log.info("Paths " + e1 + " and " + e2 + " are equals? " + e1.equals(e2));


        //Finding files
        Stream<Path> found = Files.find(Path.of("./"), 100, (p, a) -> p.getFileName().toString().matches("Learning.*\\.java"));
        found.forEach(System.out::println);

        Stream<Path> foundWalk = Files.walk(Path.of("./"), 100);
        foundWalk.filter(p -> {
            try {
                return Files.isHidden(p);
            } catch (IOException e) {
                return false;
            }
        }).forEach(System.out::println);

    }

    public static void resolve(String path, String toPath) {
        resolve(Path.of(path), Path.of(toPath));
    }

    public static void resolve(Path path, Path toPath) {
        log.info("Resolving " + path + " to " + toPath + " is " + path.resolve(toPath));
    }

    public static void relativize(String path, String toPath) {
        relativize(Path.of(path), Path.of(toPath));
    }

    public static void relativize(Path path, Path toPath) {
        log.info("Relativizing " + path + " to " + toPath + " is " + path.relativize(toPath));
    }
}
