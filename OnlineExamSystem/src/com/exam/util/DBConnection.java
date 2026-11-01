package com.exam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

private static final String DB_URL =
        "jdbc:mysql://localhost:3306/exam_system?useSSL=false&serverTimezone=UTC";

private static final String DB_USER = "root";

private static final String DB_PASSWORD = "Kanu@955";

// Load MySQL JDBC Driver
static {
    try {
        Class.forName(DB_DRIVER);
        System.out.println("MySQL JDBC Driver loaded successfully.");
    } catch (ClassNotFoundException e) {
        System.err.println("MySQL JDBC Driver not found!");
        e.printStackTrace();
    }
}

// Establish database connection
public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            DB_URL,
            DB_USER,
            DB_PASSWORD
    );
}

// Close database connection
public static void closeConnection(Connection conn) {
    if (conn != null) {
        try {
            conn.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing database connection.");
            e.printStackTrace();
        }
    }
}

// Test database connection
public static void main(String[] args) {
    try (Connection conn = getConnection()) {
        if (conn != null && !conn.isClosed()) {
            System.out.println("Database connected successfully!");
            System.out.println("Connected to: " +
                    conn.getCatalog());
        }
    } catch (SQLException e) {
        System.err.println("Database connection failed!");
        e.printStackTrace();
    }
}


}
