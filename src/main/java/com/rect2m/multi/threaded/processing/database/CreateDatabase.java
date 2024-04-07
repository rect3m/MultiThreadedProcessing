package com.rect2m.multi.threaded.processing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    private static final String DB_URL = "jdbc:h2:./data/employer";

    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
            // Створюємо таблицю "workers" з необхідними полями
            String sql = "CREATE TABLE IF NOT EXISTS workers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "age INT," +
                    "gender VARCHAR(10)," +
                    "education VARCHAR(255)," +
                    "experience INT," +
                    "salary DOUBLE," +
                    "hours_worked INT DEFAULT 0" +  // Додано нову колонку для кількості годин
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Таблиця створена успішно.");
        } catch (SQLException e) {
            System.err.println("Помилка при створенні таблиці: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
