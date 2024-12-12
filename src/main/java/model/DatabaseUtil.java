package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Ganti dengan URL database MySQL Anda untuk XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/taskova?autoReconnect=true&useSSL=false"; // Ganti nama_database dengan nama database Anda
    private static final String USER = "root";  // Username default untuk XAMPP MySQL
    private static final String PASSWORD = "";  // Password default untuk XAMPP MySQL adalah kosong
    private static Connection connection;

    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DatabaseUtil.class) {
                if (connection == null || connection.isClosed()) {
                    // Ganti URL, user, dan password dengan konfigurasi database Anda
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                }
            }
        }
        return connection;
    }

    // Method untuk menutup koneksi
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
