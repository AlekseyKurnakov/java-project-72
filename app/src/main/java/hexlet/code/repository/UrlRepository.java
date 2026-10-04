package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            }
        }
    }

    public static Optional<Url> findByUrl(String urlName) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name ILIKE ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, urlName);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Instant createdAtInDataBase = resultSet.getTimestamp("created_at").toInstant();
                Long id = resultSet.getLong("id");

                Url url = new Url(name);
                url.setCreatedAt(createdAtInDataBase);
                url.setId(id);

                return Optional.of(url);
            }

            return Optional.empty();
        }
    }

    public static Optional<Url> find(Long id) throws SQLException{
        String sql = "SELECT * FROM urls WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Instant createdAtInDataBase = resultSet.getTimestamp("created_at").toInstant();
                Long idFromDatabase = resultSet.getLong("id");

                Url url = new Url(name);
                url.setId(idFromDatabase);
                url.setCreatedAt(createdAtInDataBase);

                return Optional.of(url);
            }

            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException{
        var sql = "SELECT * FROM urls ORDER BY created_at DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            List<Url> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Instant createdAtInDataBase = resultSet.getTimestamp("created_at").toInstant();
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAtInDataBase);
                result.add(url);
            }
            return result;
        }
    }


}
