package com.rect2m.multi.threaded.processing.faker;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataGenerator {
    private static final String DB_URL = "jdbc:h2:./data/employer";
    private static final int NUM_WORKERS = 1000000;

    public static void generateData() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO workers (name, age, gender, education, experience, salary, hours_worked) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Faker faker = new Faker();
                for (int i = 0; i < NUM_WORKERS; i++) {
                    pstmt.setString(1, faker.name().fullName());
                    pstmt.setInt(2, faker.number().numberBetween(18, 65));
                    pstmt.setString(3, faker.options().option("Male", "Female"));
                    pstmt.setString(4, faker.educator().course());
                    pstmt.setInt(5, faker.number().numberBetween(0, 30));
                    pstmt.setDouble(6, faker.number().randomDouble(2, 1000, 10000));
                    pstmt.setInt(7, faker.number().numberBetween(0, 2000));  // Генеруємо випадкову кількість годин
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
                System.out.println("Дані згенеровано та вставлено успішно.");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Помилка при вставці даних: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Помилка при підключенні до бази даних: " + e.getMessage());
        }
    }
}
