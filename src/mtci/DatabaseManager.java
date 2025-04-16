package mtci;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DatabaseManager {

    // Updated: Can connect to any database by full path
    private static Connection connectToDatabase(String fullPath) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + fullPath);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    public static void createNewPlayer(String playerName) {
        try (Connection conn = connectToDatabase("./database/players.db")) {
            if (conn != null) {
                String query = "INSERT INTO players (name, ge01Score, ge02Score, ge03Score, ge04Score, ge05Score, ge06Score, ge07Score, ge08Score, ge09Score, ge10Score, ge11Score) VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, playerName);
                    pstmt.executeUpdate();
                    System.out.println("New player " + playerName + " created with default scores.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating new player: " + e.getMessage());
        }
    }

    public static boolean playerExists(String playerName) {
        try (Connection conn = connectToDatabase("./database/players.db")) {
            if (conn != null) {
                String query = "SELECT name FROM players WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, playerName);
                    ResultSet rs = pstmt.executeQuery();
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if player exists: " + e.getMessage());
        }
        return false;
    }

    // ✅ FIXED: Load questions directly from the subjectCode database (like ge10.db)
    public static List<Question> getQuestions(String subjectCode) {
        List<Question> questions = new ArrayList<>();
        String dbPath = subjectCode; // Subject-specific database

        try (Connection conn = connectToDatabase(dbPath)) {
            if (conn != null) {
                String query = "SELECT questions, choice_a, choice_b, choice_c, choice_d, answer FROM questions";
                try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        String questionText = rs.getString("questions");
                        String correctAnswer = rs.getString("answer");
                        List<String> choices = new ArrayList<>();
                        choices.add(rs.getString("choice_a"));
                        choices.add(rs.getString("choice_b"));
                        choices.add(rs.getString("choice_c"));
                        choices.add(rs.getString("choice_d"));

                        questions.add(new Question(questionText, choices, correctAnswer));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading questions from " + dbPath + ": " + e.getMessage());
        }
        return questions;
    }

    public static void updatePlayerScore(String playerName, String subject, int score) {
        try (Connection conn = connectToDatabase("./database/players.db")) {
            if (conn != null) {
                String column = subject + "Score";
                String query = "UPDATE players SET " + column + " = ? WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, score);
                    pstmt.setString(2, playerName);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating player score: " + e.getMessage());
        }
    }

    public static ArrayList<String> getAllPlayers() {
        ArrayList<String> players = new ArrayList<>();
        String query = "SELECT name FROM players";
        try (Connection conn = connectToDatabase("./database/players.db"); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                players.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching player list: " + e.getMessage());
        }
        return players;
    }

    public static List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>();
        String query = "SELECT DISTINCT subjectCode FROM questions.db"; // If needed for future use
        // Currently not used because subjects are determined by files like ge10.db
        return subjects;
    }

    public static JPanel createLeaderboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String query = "SELECT name, total_score FROM players ORDER BY total_score DESC LIMIT 10";

        try (Connection conn = connectToDatabase("./database/players.db"); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("name");
                double score = rs.getDouble("total_score");
                JLabel label = new JLabel(name + ": " + String.format("%.1f", score));
                panel.add(label);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return panel;
    }

    public static Connection getConnection(String dbUrl) throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    List<Question> getRandomQuestions(String subject, int count) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    boolean addPlayer(String playerName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
