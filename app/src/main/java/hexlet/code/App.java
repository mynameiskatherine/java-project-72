package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;
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

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
    public static String readFileFromResources(String fileName) throws IOException {
        URL url = App.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        String sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));
        return sql;
    }

    private static void setDataSource() throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        HikariDataSource dataSource;
        String dbUrl = System.getenv("JDBC_DB_URL");
        if (dbUrl != null) {
            hikariConfig.setJdbcUrl(dbUrl);
            hikariConfig.setUsername(System.getenv().getOrDefault("JDBC_DB_USERNAME", ""));
            hikariConfig.setPassword(System.getenv().getOrDefault("JDBC_DB_PASSWORD", ""));
            hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
            dataSource = new HikariDataSource(hikariConfig);
        } else {
            hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
            dataSource = new HikariDataSource(hikariConfig);
            var sql = readFileFromResources("schema.sql");

            log.info(sql);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute(sql);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        BaseRepository.dataSource = dataSource;
    }

    public static Javalin getApp() throws IOException {
        setDataSource();

        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);

        return app;
    }
}
