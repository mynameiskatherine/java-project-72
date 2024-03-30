package hexlet.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static String getFixture(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/fixtures/" + fileName).toAbsolutePath().normalize();
        return new String(Files.readAllBytes(path));
    }
}
