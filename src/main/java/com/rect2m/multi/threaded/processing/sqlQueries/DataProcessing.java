package com.rect2m.multi.threaded.processing.sqlQueries;

import java.sql.*;
import java.util.Date;

public class DataProcessing {
    private static final String DB_URL = "jdbc:h2:./data/employer";

    public static void startDataProcessing() {
        // Здійснюємо обробку даних по черзі
        double averageSalary = calculateAverageSalary(new Date(2023, 0, 1), new Date(2023, 11, 31));
        System.out.println("Середня ЗП за певний період: " + averageSalary);

        int experienceYears = 5;
        int workersWithExperience = countWorkersWithExperience(experienceYears);
        System.out.println("Кількість працівників з досвідом більше " + experienceYears + " років: " + workersWithExperience);

        findMinMaxSalary();

        String educationLevel = "Master of Law";
        int workersWithEducation = countWorkersWithEducation(educationLevel);
        System.out.println("Кількість працівників з освітою " + educationLevel + ": " + workersWithEducation);

        countWorkersByGender();

        int totalHoursWorked = calculateTotalHoursWorked(new Date(2023, 0, 1), new Date(2023, 11, 31));
        System.out.println("Загальна кількість годин, які відпрацювали всі робітники: " + totalHoursWorked);

        double averageAge = calculateAverageAge(new Date(2023, 0, 1), new Date(2023, 11, 31));
        System.out.println("Середня вікова група робітників: " + averageAge);
    }

    public static double calculateAverageSalary(Date startDate, Date endDate) {
        double averageSalary = 0.0;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT AVG(salary) AS average_salary FROM workers";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                averageSalary = rs.getDouble("average_salary");
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
        return averageSalary;
    }

    public static int countWorkersWithExperience(int yearsOfExperience) {
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) AS worker_count FROM workers WHERE experience > ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, yearsOfExperience);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("worker_count");
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
        return count;
    }

    public static void findMinMaxSalary() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT MAX(salary) AS max_salary, MIN(salary) AS min_salary FROM workers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                double maxSalary = rs.getDouble("max_salary");
                double minSalary = rs.getDouble("min_salary");
                System.out.println("Найвища зарплата: " + maxSalary);
                System.out.println("Найнижча зарплата: " + minSalary);
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
    }

    public static int countWorkersWithEducation(String educationLevel) {
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) AS worker_count FROM workers WHERE education = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, educationLevel);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("worker_count");
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
        return count;
    }

    public static void countWorkersByGender() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT gender, COUNT(*) AS gender_count FROM workers GROUP BY gender";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String gender = rs.getString("gender");
                int count = rs.getInt("gender_count");
                System.out.println("Гендер: " + gender + ", Кількість: " + count);
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
    }


    public static int calculateTotalHoursWorked(Date startDate, Date endDate) {
        int totalHours = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT SUM(hours_worked) AS total_hours FROM workers WHERE hours_worked BETWEEN ? AND ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 0);  // Мінімальна кількість годин
                pstmt.setInt(2, 40); // Максимальна кількість годин (припустимо, що робочий тиждень 40 годин)
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    totalHours = rs.getInt("total_hours");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
        return totalHours;
    }


    public static double calculateAverageAge(Date startDate, Date endDate) {
        double averageAge = 0.0;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT AVG(age) AS average_age FROM workers";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                averageAge = rs.getDouble("average_age");
            }
        } catch (SQLException e) {
            System.err.println("Помилка при виконанні запиту: " + e.getMessage());
        }
        return averageAge;
    }

}
