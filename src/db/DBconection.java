package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db"; // corrected casing
    private static final String USER = "root";
    private static final String PASSWORD = ""; // empty if you're using default XAMPP settings

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
