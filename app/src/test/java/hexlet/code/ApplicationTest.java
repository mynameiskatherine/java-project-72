package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    static Javalin app;
    static MockWebServer testServer;
    static String testUrl;

    @BeforeAll
    static void setUpAndStartServer() throws IOException {
        testServer = new MockWebServer();
        testServer.enqueue(new MockResponse().setBody(getFixture("testBody.html")));
        testServer.enqueue(new MockResponse().setStatus("200"));
        testServer.start();
        testUrl = testServer.url("/").toString();
    }

    private static String getFixture(String fileName) throws IOException {
        Path path = Paths.get("src/test/resources/fixtures/" + fileName).toAbsolutePath().normalize();
        return new String(Files.readAllBytes(path));
    }

    @BeforeEach
    void setUp() throws IOException {
        app = App.getApp();
    }

    @AfterAll
    static void stopServer() throws IOException {
        testServer.shutdown();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Page Analyzer");
        });
    }

    @Test
    void testSitesPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Sites");
        });
    }

    @Test
    void testAddingSitePage() {
        JavalinTest.test(app, (server, client) -> {
            var url = testUrl.replaceAll("/$", "");
            var requestBody = "url=" + url;
            var response = client.post("/urls", requestBody);
            var saved = UrlRepository.search(url);
            assertThat(response.code()).isEqualTo(200);
            assertThat(saved).isPresent();
        });
    }

    @Test
    void testSitePage() {
        var url = new Url("https://www.example.com", LocalDateTime.now());
        UrlRepository.save(url);
        var id = url.getId();
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + id);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testSiteNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testSiteChecks() {
        JavalinTest.test(app, (server, client) -> {
            var url = testUrl.replaceAll("/$", "");
            var requestBody = "url=" + url;
            assertThat(client.post("/urls", requestBody).code()).isEqualTo(200);
            assertThat(UrlRepository.search(url)).isNotNull();
            Url savedUrl = UrlRepository.search(url).get();
            var response = client.post("/urls/" + savedUrl.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
            List<UrlCheck> listOfChecks = UrlCheckRepository.getEntitiesByUrlId(savedUrl.getId());
            for (UrlCheck check : listOfChecks) {
                assertThat(check.getDescription()).isEqualTo("From description");
                assertThat(check.getH1()).isEqualTo("");
                assertThat(check.getTitle()).isEqualTo("From title tag");
            }
        });
    }
}
