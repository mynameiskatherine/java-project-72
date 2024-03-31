package hexlet.code.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    public static String readFileFromResources(String fileName) throws IOException {
        InputStream stream = Utils.class.getClassLoader().getResourceAsStream("fixtures/" + fileName);
        StringBuilder stringBuilder = new StringBuilder();
        if (stream != null) {
            try (InputStreamReader reader = new InputStreamReader(stream);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                while (bufferedReader.ready()) {
                    stringBuilder.append(bufferedReader.readLine());
                }
            }
        } else {
            throw new IllegalArgumentException("no data in stream");
        }
        return stringBuilder.toString();
    }

}
