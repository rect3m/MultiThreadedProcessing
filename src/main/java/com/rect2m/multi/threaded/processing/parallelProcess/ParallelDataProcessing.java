package com.rect2m.multi.threaded.processing.parallelProcess;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelDataProcessing {
    private static final String DB_URL = "jdbc:h2:./data/employer";
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static void processData() {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM workers");
            rs.next();
            int totalWorkers = rs.getInt("total");
            int batchSize = totalWorkers / NUM_THREADS;

            for (int i = 0; i < NUM_THREADS; i++) {
                int offset = i * batchSize;
                int limit = (i + 1) * batchSize;
                int threadNum = i; // Оголошуємо фінальну змінну
                executor.submit(() -> processBatch(offset, limit, threadNum));
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Чекаємо завершення всіх потоків
            }
            System.out.println("Обробка завершена.");
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
    }

    private static void processBatch(int offset, int limit, int threadNum) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM workers LIMIT ? OFFSET ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit - offset);
            pstmt.setInt(2, offset);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            System.out.println("Потік № " + threadNum);
            while (rs.next() && count < 5) {  // Змінено на 5
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String education = rs.getString("education");
                int experience = rs.getInt("experience");
                double salary = rs.getDouble("salary");
                int hours_worked = rs.getInt("hours_worked");

                // Обробка робітника - приклад виведення перших 5 робітників кожного потоку
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender +
                        ", Education: " + education + ", Experience: " + experience + ", Salary: " + salary + ", Hours worked: " + hours_worked);

                count++;
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні пакетної обробки: " + e.getMessage());
        }
    }
}
