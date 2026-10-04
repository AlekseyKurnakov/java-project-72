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
import java.util.stream.Collectors;

import hexlet.code.repository.UrlChekRepository;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;

import java.io.IOException;
import io.javalin.http.HttpStatus;

public class AppTest {

    private static Javalin app;
    private static final String testUrl = "https://example.com";
    private static MockWebServer mockServer;

    @BeforeAll
    static void setUpClass() throws SQLException, IOException {
        mockServer = new MockWebServer();
        mockServer.start();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");

        HikariDataSource testDataSource = new HikariDataSource(config);

        InputStream schemaStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
        String sql = new BufferedReader(new InputStreamReader(schemaStream))
                .lines().collect(Collectors.joining("\n"));

        try (Connection connection = testDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = testDataSource;
    }

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = BaseRepository.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM url_checks");
            statement.execute("DELETE FROM urls");
        }
        app = App.getApp();
    }

    @AfterAll
    static void tearDownClass() throws IOException {
        mockServer.close();
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
                    .isEqualTo(HttpStatus.FOUND.getCode());

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
                    .isEqualTo(HttpStatus.OK.getCode());

            assertThat(pageResponse.body().string())
                    .contains(testUrl)
                    .contains("Страница успешно добавлена");

        });

    }

    @Test
    void testCreateUrlDuplicate() throws SQLException {

        JavalinTest.test(app, (server, client) -> {

            Url newUrl = new Url(testUrl);
            UrlRepository.save(newUrl);

            var responseAlreadyExists = client.post(
                    NamedRoutes.urlsPath(),
                    "url=" + testUrl
            );

            Url urlInDataBase = UrlRepository.findByUrl(testUrl)
                    .orElseThrow();

            assertThat(responseAlreadyExists.code())
                    .isEqualTo(HttpStatus.FOUND.getCode());

            assertThat(responseAlreadyExists.headers().get("Location"))
                    .containsExactly(NamedRoutes.urlPath(urlInDataBase.getId()));

            assertThat(UrlRepository.getEntities()).hasSize(1);

            var pageResponseAlreadyExists = client.get(
                    NamedRoutes.urlPath(urlInDataBase.getId())
            );

            assertThat(pageResponseAlreadyExists.code())
                    .isEqualTo(HttpStatus.OK.getCode());

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
                    .isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT.getCode());

            assertThat(responseIncorrectUrl.body().string())
                    .contains("Некорректный URL");
        });
    }

    @Test
    void testHome() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());

            assertThat(response.code())
                    .isEqualTo(HttpStatus.OK.getCode());

            assertThat(response.body().string())
                    .contains("Анализатор страниц");
        });
    }

    @Test
    void testShow() throws SQLException {
        JavalinTest.test(app, (server, client) -> {

            Url newUrl = new Url(testUrl);
            UrlRepository.save(newUrl);

            var pageResponseShowUrl = client.get(
                    NamedRoutes.urlPath(newUrl.getId())
            );

            assertThat(pageResponseShowUrl.code())
                    .isEqualTo(HttpStatus.OK.getCode());

            assertThat(pageResponseShowUrl.body().string())
                    .contains(testUrl)
                    .contains("data-test=\"url\"")
                    .contains(String.valueOf(newUrl.getId()));

        });
    }

    @Test
    void testIndex() throws SQLException {
        JavalinTest.test(app, (server, client) -> {

            Url firstUrl = new Url("https://first.com");
            Url secondUrl = new Url("https://second.com");
            UrlRepository.save(firstUrl);
            UrlRepository.save(secondUrl);

            var response = client.get(NamedRoutes.urlsPath());

            assertThat(response.code())
                    .isEqualTo(HttpStatus.OK.getCode());

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
                    .code(HttpStatus.OK.getCode())
                    .body(html)
                    .build());

            String mockUrl = mockServer.url("/").toString();
            Url url = new Url(mockUrl);
            UrlRepository.save(url);

            var response = client.post(NamedRoutes.urlChecksPath(url.getId()), "");

            assertThat(response.code())
                    .isEqualTo(HttpStatus.FOUND.getCode());

            var pageResponse = client.get(NamedRoutes.urlPath(url.getId()));

            assertThat(pageResponse.body().string())
                    .contains("Страница успешно проверена")
                    .contains("Test title")
                    .contains("Test h1")
                    .contains("Test description");

            var checks = UrlChekRepository.getEntities(url.getId());
            assertThat(checks).hasSize(1);

            var savedCheck = checks.get(0);
            assertThat(savedCheck.getStatusCode()).isEqualTo(HttpStatus.OK.getCode());
            assertThat(savedCheck.getTitle()).isEqualTo("Test title");
            assertThat(savedCheck.getH1()).isEqualTo("Test h1");
            assertThat(savedCheck.getDescription()).isEqualTo("Test description");
        });
    }

    @Test
    void testCreateCheckError() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            mockServer.enqueue(new MockResponse.Builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.getCode())
                    .build());

            String mockUrl = mockServer.url("/").toString();
            Url url = new Url(mockUrl);
            UrlRepository.save(url);

            var response = client.post(NamedRoutes.urlChecksPath(url.getId()), "");

            assertThat(response.code())
                    .isEqualTo(HttpStatus.FOUND.getCode());

            var pageResponse = client.get(NamedRoutes.urlPath(url.getId()));

            assertThat(pageResponse.body().string())
                    .contains("Произошла ошибка при проверке");

            assertThat(UrlChekRepository.getEntities(url.getId()))
                    .isEmpty();
        });
    }

}
