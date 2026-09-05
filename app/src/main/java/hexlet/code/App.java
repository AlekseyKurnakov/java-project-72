package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class App {

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
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        });

        return app;
    }

    public static void main(String[] args) throws Exception {
        BaseRepository.dataSource = getDatabase();
        Javalin app = getApp();
        app.start("0.0.0.0", 8080);
    }
}
