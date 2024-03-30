package hexlet.code.util;

import hexlet.code.App;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Utils {
    public static String getFixture(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/fixtures/" + fileName).toAbsolutePath().normalize();
        return new String(Files.readAllBytes(path));
    }

    public static String readFileFromResources(String fileName) throws IOException {
        URL url = App.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        String sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));
        return sql;
    }

}
