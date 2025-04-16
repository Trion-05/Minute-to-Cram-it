package mtci;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class Array {

    private final DatabaseManager dbManager = new DatabaseManager();
    private String subjectCode;

    // Player methods
    public ArrayList<String> loadPlayersFromSQL() throws SQLException {
        // Assuming DatabaseManager's getAllPlayers returns a list of Player objects
        return DatabaseManager.getAllPlayers();
    }

    public boolean addNewPlayer(String playerName) throws SQLException {
        return dbManager.addPlayer(playerName);
    }

    public void updatePlayerScore(String playerName, String subject, double score) throws SQLException {
        DatabaseManager.updatePlayerScore(playerName, subject, (int) score);  // Use dbManager here
    }

    // Question methods
    public List<Question> getQuestions(String subject, int count) throws SQLException {
        List<Question> randomQuestions = new ArrayList<>();
        String sql = "SELECT questions, choice_a, choice_b, choice_c, choice_d, answer "
                + "FROM questions WHERE subject = ? ORDER BY RANDOM() LIMIT ?";

        try (Connection conn = DatabaseManager.getConnection("./database/" + subjectCode + ".db"); // Ensure getConnection is defined in DatabaseManager
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for subject and limit (number of questions)
            stmt.setString(1, subject);
            stmt.setInt(2, count);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String questionText = rs.getString("questions");
                    List<String> choices = new ArrayList<>();
                    choices.add(rs.getString("choice_a"));
                    choices.add(rs.getString("choice_b"));
                    choices.add(rs.getString("choice_c"));
                    choices.add(rs.getString("choice_d"));
                    String correctAnswer = rs.getString("answer");

                    // Create a Question object and add it to the list
                    randomQuestions.add(new Question(questionText, choices, correctAnswer));
                }
            }
        }
        return randomQuestions;
    }

    public List<String> getAllSubjects() {
        return DatabaseManager.getAllSubjects();
    }

    // UI components
    public JButton createPlayerButton() throws SQLException {
        ArrayList<String> players = loadPlayersFromSQL();
        JButton button = new JButton();

        if (players.isEmpty()) {
            button.setText("No Players");
        } else {
            StringBuilder sb = new StringBuilder("<html>Players:<br>");
            for (String player : players) {
                sb.append(player).append("<br>");
            }
            sb.append("</html>");
            button.setText(sb.toString());
        }
        return button;
    }

    public JPanel createLeaderboard() throws SQLException {
        return DatabaseManager.createLeaderboardPanel();
    }

    // Utility method
    public boolean isValidSubject(String subject) {
        return DatabaseManager.getAllSubjects().contains(subject);
    }
}
