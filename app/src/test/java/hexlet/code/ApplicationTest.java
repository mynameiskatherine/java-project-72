package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    static Javalin app;
    @BeforeEach
    void setUp() throws IOException {
        app = App.getApp();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testSitesPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testAddingSitePages() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https%3A%2F%2Fwww.example.ru";
            var response = client.post("/urls", requestBody);
            var saved = UrlRepository.search("https://www.example.com");
            assertThat(response.code()).isEqualTo(200);
            assertThat(saved.isPresent());
        });
    }

    @Test
    void testSitePage() {
        var url = new Url("https://google.com", LocalDateTime.now());
        UrlRepository.save(url);
        var id = url.getId();
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + id);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testSiteNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
