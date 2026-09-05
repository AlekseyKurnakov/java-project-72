package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class App {

    private static final Path TEMPLATES_PATH =
            Path.of("src", "main", "resources", "templates");

    private static final Path STATIC_PATH =
            Path.of("src", "main", "resources", "static");

    private static final Path JTE_CLASSES_PATH =
            Path.of("jte-classes");

    private static String getMode() {
        return System.getenv()
                .getOrDefault("APP_ENV", "production");
    }

    private static boolean isDevelopment() {
        return getMode().equals("development")
                && Files.isDirectory(TEMPLATES_PATH);
    }

    private static TemplateEngine createTemplateEngine() {
        if (isDevelopment()) {
            var codeResolver = new DirectoryCodeResolver(TEMPLATES_PATH);
            return TemplateEngine.create(codeResolver, JTE_CLASSES_PATH, ContentType.Html);
        }
        var classLoader = App.class.getClassLoader();
        var codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static HikariDataSource getDatabase() throws Exception{

        String databaseUrl = System.getenv()
                .getOrDefault(
                        "JDBC_DATABASE_URL",
                        "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;"
                );

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        InputStream url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        String sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        return dataSource;
    }

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();

            config.fileRenderer(new JavalinJte(createTemplateEngine()));

            if (isDevelopment()) {
                config.staticFiles.add(STATIC_PATH.toString(), Location.EXTERNAL);
            } else {
                config.staticFiles.add("/static", Location.CLASSPATH);
            }

            config.routes.get("/", ctx -> {
                ctx.render("articles/index.jte");
            });
        });

        return app;
    }

    public static void main(String[] args) throws Exception {
        BaseRepository.dataSource = getDatabase();

        Javalin app = getApp();

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "8080")
        );

        app.start("0.0.0.0", port);
    }
}
