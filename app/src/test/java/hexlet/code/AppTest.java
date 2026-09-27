package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.stream.Collectors;

import hexlet.code.repository.UrlChekRepository;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;

import java.io.IOException;

public class AppTest {

    private static Javalin app;
    private static final String testUrl = "https://example.com";

    private static MockWebServer mockServer;

    @BeforeAll
    static void startMockServer() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void stopMockServer() throws IOException {
        mockServer.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");

        HikariDataSource testDataSource = new HikariDataSource(config);

        InputStream url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        String sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        try (Connection connection = testDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = testDataSource;

        app = App.getApp();
    }
    @AfterEach
    void closeDatabase() {
        BaseRepository.dataSource.close();
    }

    @Test
    void testCreateUrlSuccess() throws SQLException {

        JavalinTest.test(app, (server, client) -> {

            var response = client.post(
                    NamedRoutes.urlsPath(),
                    "url=" + testUrl
            );

            assertThat(response.code())
                    .isEqualTo(302);

            Url urlInDataBase = UrlRepository.findByUrl(testUrl)
                    .orElseThrow();

            assertThat(response.headers().get("Location"))
                    .containsExactly(NamedRoutes.urlPath(urlInDataBase.getId()));

            assertThat(urlInDataBase.getName())
                    .isEqualTo(testUrl);

            var pageResponse = client.get(
                    NamedRoutes.urlPath(urlInDataBase.getId())
            );

            assertThat(pageResponse.code())
                    .isEqualTo(200);

            assertThat(pageResponse.body().string())
                    .contains(testUrl)
                    .contains("Страница успешно добавлена");

        });

    }

    @Test
    void testCreateUrlDuplicate() throws SQLException {

        JavalinTest.test(app, (server, client) -> {

            Url newUrl = new Url(testUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(newUrl);

            var responseAlreadyExists = client.post(
                    NamedRoutes.urlsPath(),
                    "url=" + testUrl
            );

            Url urlInDataBase = UrlRepository.findByUrl(testUrl)
                    .orElseThrow();

            assertThat(responseAlreadyExists.code())
                    .isEqualTo(302);

            assertThat(responseAlreadyExists.headers().get("Location"))
                    .containsExactly(NamedRoutes.urlPath(urlInDataBase.getId()));

            assertThat(UrlRepository.getEntities()).hasSize(1);

            var pageResponseAlreadyExists = client.get(
                    NamedRoutes.urlPath(urlInDataBase.getId())
            );

            assertThat(pageResponseAlreadyExists.code())
                    .isEqualTo(200);

            assertThat(pageResponseAlreadyExists.body().string())
                    .contains("Страница уже существует");
        });
    }

    @Test
    void testCreateUrlInvalid() throws SQLException {

        JavalinTest.test(app, (server, client) -> {

            var responseIncorrectUrl = client.post(
                    NamedRoutes.urlsPath(),
                    "url=" + "response" + "@incorrectUrl.com"
            );

            assertThat(responseIncorrectUrl.code())
                    .isEqualTo(422);

            assertThat(responseIncorrectUrl.body().string())
                    .contains("Некорректный URL");
        });
    }

    @Test
    void testHome() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());

            assertThat(response.code())
                    .isEqualTo(200);

            assertThat(response.body().string())
                    .contains("Анализатор страниц");
        });
    }

    @Test
    void testShow() throws SQLException {
        JavalinTest.test(app, (server, client) -> {

            Url newUrl = new Url(testUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(newUrl);

            var pageResponseShowUrl = client.get(
                    NamedRoutes.urlPath(newUrl.getId())
            );

            assertThat(pageResponseShowUrl.code())
                    .isEqualTo(200);

            assertThat(pageResponseShowUrl.body().string())
                    .contains(testUrl)
                    .contains("data-test=\"url\"")
                    .contains(String.valueOf(newUrl.getId()));

        });
    }

    @Test
    void testIndex() throws SQLException {
        JavalinTest.test(app, (server, client) -> {

            Url firstUrl = new Url("https://first.com", new Timestamp(System.currentTimeMillis()));
            Url secondUrl = new Url("https://second.com", new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(firstUrl);
            UrlRepository.save(secondUrl);

            var response = client.get(NamedRoutes.urlsPath());

            assertThat(response.code())
                    .isEqualTo(200);

            String body = response.body().string();

            assertThat(body)
                    .contains("data-test=\"urls\"")
                    .contains("https://first.com")
                    .contains("https://second.com");
        });
    }

    @Test
    void testCreateCheckSuccess() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            String html = "<html><head><title>Test title</title>"
                    + "<meta name=\"description\" content=\"Test description\">"
                    + "</head><body><h1>Test h1</h1></body></html>";

            mockServer.enqueue(new MockResponse.Builder()
                    .code(200)
                    .body(html)
                    .build());

            String mockUrl = mockServer.url("/").toString();
            Url url = new Url(mockUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(url);

            var response = client.post(NamedRoutes.urlChecksPath(url.getId()), "");

            assertThat(response.code())
                    .isEqualTo(302);

            var pageResponse = client.get(NamedRoutes.urlPath(url.getId()));

            assertThat(pageResponse.body().string())
                    .contains("Страница успешно проверена")
                    .contains("Test title")
                    .contains("Test h1")
                    .contains("Test description");

            assertThat(UrlChekRepository.getEntities(url.getId()))
                    .hasSize(1);
        });
    }

    @Test
    void testCreateCheckError() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            mockServer.enqueue(new MockResponse.Builder()
                    .code(500)
                    .build());

            String mockUrl = mockServer.url("/").toString();
            Url url = new Url(mockUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(url);

            var response = client.post(NamedRoutes.urlChecksPath(url.getId()), "");

            assertThat(response.code())
                    .isEqualTo(302);

            var pageResponse = client.get(NamedRoutes.urlPath(url.getId()));

            assertThat(pageResponse.body().string())
                    .contains("Произошла ошибка при проверке");

            assertThat(UrlChekRepository.getEntities(url.getId()))
                    .isEmpty();
        });
    }

}
