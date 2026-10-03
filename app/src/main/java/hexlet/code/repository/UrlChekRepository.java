package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static hexlet.code.repository.BaseRepository.dataSource;

public class UrlChekRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id," +
                " status_code," +
                " h1," +
                " title," +
                " description," +
                " created_at)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setLong(1, urlCheck.getUrlId());
            stmt.setInt(2, urlCheck.getStatusCode());
            stmt.setString(3, urlCheck.getH1());
            stmt.setString(4, urlCheck.getTitle());
            stmt.setString(5, urlCheck.getDescription());
            stmt.setTimestamp(6, urlCheck.getCreatedAt());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            }
        }
    }

    public static List<UrlCheck> getEntities(Long urlId) throws SQLException{
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);

            ResultSet resultSet = stmt.executeQuery();
            List<UrlCheck> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long urlIdInDataBase = resultSet.getLong("url_id");
                Integer statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                UrlCheck urlCheck = new UrlCheck(urlIdInDataBase, statusCode, h1, title, description, createdAt);
                urlCheck.setId(id);
                result.add(urlCheck);
            }
            return result;
        }
    }

    public static Optional<UrlCheck> findLast(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long urlIdInDataBase = resultSet.getLong("url_id");
                Integer statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                UrlCheck urlCheck = new UrlCheck(urlIdInDataBase, statusCode, h1, title, description, createdAt);
                urlCheck.setId(id);
                return Optional.of(urlCheck);
            }
            return Optional.empty();
        }
    }

    public static Map<Long, UrlCheck> getAllLastChecks() throws SQLException {
        var sql = "SELECT * FROM (\n" +
                "    SELECT *, ROW_NUMBER() OVER (PARTITION BY url_id ORDER BY created_at DESC) AS rn\n" +
                "    FROM url_checks\n" +
                ") AS ranked\n" +
                "WHERE rn = 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            Map<Long, UrlCheck> lastChecks = new HashMap<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long urlIdInDataBase = resultSet.getLong("url_id");
                Integer statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                UrlCheck urlCheck = new UrlCheck(urlIdInDataBase, statusCode, h1, title, description, createdAt);
                urlCheck.setId(id);

                lastChecks.put(urlIdInDataBase, urlCheck);
            }
            return lastChecks;
        }


    }
}
