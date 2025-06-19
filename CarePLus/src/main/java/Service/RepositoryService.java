package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;

public class RepositoryService {
    private final String url      = "jdbc:mysql://localhost:3306/careplus";
    private final String username = "root";
    private final String password = "postgres";


    private static RepositoryService instance;

    private RepositoryService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not load MySQL driver", e);
        }
    }

    public static RepositoryService getInstance() {
        if (instance == null) {
            synchronized (RepositoryService.class) {
                if (instance == null) {
                    instance = new RepositoryService();
                }
            }
        }
        return instance;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}