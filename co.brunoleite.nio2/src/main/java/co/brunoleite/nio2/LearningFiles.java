package co.brunoleite.nio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class LearningFiles {

    private Logger log = Logger.getLogger(LearningFiles.class.getName());

    public static void main(String[] args) throws IOException {
        //Delete if exists, create file
        Path file1 = Path.of("file1.txt");
        if (Files.exists(file1)) {
            Files.delete(file1);
        }
        final FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"));
        Files.createFile(file1, fileAttribute);

        //Write string to file
        Files.writeString(file1, "Hello world");

        //Move file
        Path file1Moved = Path.of("file1-moved.txt");
        final Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions(file1);
        Files.move(file1, file1Moved, StandardCopyOption.REPLACE_EXISTING);
        Files.setPosixFilePermissions(file1Moved, posixFilePermissions);

        assert !Files.exists(file1);
        assert Files.exists(file1Moved);

        //Read string from file
        assert Files.readString(file1Moved).equals("Hello world");

        //Copy file
        Path file1Copy = Path.of("file1-copy.txt");
        Files.copy(file1Moved, file1Copy, StandardCopyOption.REPLACE_EXISTING);

        //Temporary file
        Path tempFile = Files.createTempFile("tempfile-", ".tmp");
        System.out.println("Temporary file created: " + tempFile);
        Files.writeString(tempFile, "Am I alive?", StandardOpenOption.DELETE_ON_CLOSE);

        //Temporary dir
        Path tempDir = Files.createTempDirectory("tempdir-");
        System.out.println("Temporary dir created: " + tempDir);
        tempDir.toFile().deleteOnExit();

        Files.writeString(tempDir.resolve("child.txt"), "child file", StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE);

        //Reading small files
        byte[] readAllBytes = Files.readAllBytes(file1Copy);
        assert new String(readAllBytes).equals("Hello world");

        List<String> lines = Files.readAllLines(file1Copy, StandardCharsets.UTF_8);
        assert lines.size() == 1;
        assert lines.get(0).equals("Hello world");

        //Reading through a stream
        try (InputStream inputStream = Files.newInputStream(file1Copy)) {
            assert inputStream.read() == 'H';
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(file1Copy)) {
            assert bufferedReader.readLine().equals("Hello world");
        }

        //Directory stream
        final DirectoryStream<Path> paths = Files.newDirectoryStream(Path.of("/tmp"), "temp*");
        for(Path p: paths){
            assert p.toAbsolutePath().equals(tempFile);
        }





    }
}
