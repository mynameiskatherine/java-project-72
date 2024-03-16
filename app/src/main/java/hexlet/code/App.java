package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.stream.Collectors;

@Slf4j
public final class App {
    public static void main(String[] args) throws IOException {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static String getDbUrl() {
        String dbUrl = System.getenv().getOrDefault("JDBC_DB_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        return dbUrl;
    }

    public static Javalin getApp() throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDbUrl());

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        URL url = App.class.getClassLoader().getResource("schema.sql");
        File file = new File(url.getFile());
        String sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));
        log.info(sql);

        try (var connection = dataSource.getConnection(); var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.get("/", ctx -> ctx.result("Hello, World"));
        return app;
    }
}
