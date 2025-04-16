package mtci;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;

public class Main {

    private int currentQuestionIndex = 0;
    private int score = 0;
    private List<Question> questions;
    private String playerName;
    private String subjectCode;
    private List<String> answerFeedback = new ArrayList<>();
    private final String backgroundImagePath;

    //==========MAIN METHOD==========
    public static void main(String[] args) {
        new Main().showMenu();
    }

    //==========BG IMAGE CONSTRUCTROR==========//  
    public Main() {
        this.backgroundImagePath = "/mtci/bg.png";
    }

    //==========MAIN MENU==========
    public void showMenu() {
        JFrame frame = new JFrame("MINUTE TO CRAM IT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //=====BACKGROUND=====
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource(backgroundImagePath));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override

                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                // =====MENU TITLE & SUBTITLE=====
                JLabel title = new JLabel("MINUTE TO CRAM IT", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 50));
                title.setForeground(new Color(6, 240, 2));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel subtitle = new JLabel("<html><div style='text-align: center;'>"
                        + "The Last Braincell Standing"
                        + "</div></html>", SwingConstants.CENTER);

                subtitle.setFont(new Font("Gameplay", Font.PLAIN, 20));
                subtitle.setForeground(new Color(255, 255, 255));
                subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel titlePanel = new JPanel();
                titlePanel.setOpaque(false);
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                titlePanel.add(title);
                titlePanel.add(Box.createRigidArea(new Dimension(0, 1))); // space
                titlePanel.add(subtitle);
                titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

                //=====MENU BUTTON DESIGN=====
                RoundedButton playBtn = createMenuButton("Play", new Color(9, 168, 3), new Color(0, 0, 0));
                RoundedButton leaderboardBtn = createMenuButton("Leader Board", new Color(207, 166, 2), new Color(0, 0, 0));
                RoundedButton aboutUsBtn = createMenuButton("About Us", new Color(13, 55, 209), new Color(0, 0, 0));
                RoundedButton exitBtn = createMenuButton("Exit", new Color(171, 3, 3), new Color(0, 0, 0));

                //=====BUTTON FUNCTIONS=====
                playBtn.addActionListener(e1 -> {
                    frame.dispose();
                    playGame(); // Directly calling playGame method without loading screen
                });

                leaderboardBtn.addActionListener(e1 -> {
                    frame.dispose();
                    showLeaderBoard();
                });

                aboutUsBtn.addActionListener(e1 -> {
                    frame.dispose();
                    showAboutUs();
                });

                exitBtn.addActionListener((ActionEvent e1) -> {
                    System.exit(0);
                });

                //=====MENU BUTTON POSITION====
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
                buttonPanel.add(playBtn);
                buttonPanel.add(leaderboardBtn);
                buttonPanel.add(aboutUsBtn);
                buttonPanel.add(exitBtn);

                //=====MENU LAYOUT=====
                JPanel menuPanel = new JPanel();
                menuPanel.setOpaque(false);
                menuPanel.setLayout(new BorderLayout());
                menuPanel.add(titlePanel, BorderLayout.NORTH);
                menuPanel.add(buttonPanel, BorderLayout.CENTER);
                background.add(menuPanel);
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        //=====ESC KEY FUNCTION=====
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    //=====ROUNDED CORNERS=====
    private RoundedButton createMenuButton(String text, Color backgroundColor, Color borderColor) {
        RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setFont(new Font("Gameplay", Font.BOLD, 18));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setCustomBorderColor(borderColor);
        button.setFocusPainted(false);
        return button;
    }

    //=====PLAY GAME METHOD=====
    public void playGame() {
        JFrame frame = new JFrame("Select Player");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //===BACKGROUND IMAGE===
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource(backgroundImagePath));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };

                //=====LAYOUT & DESIGN=====
                background.setLayout(new GridBagLayout());
                JLabel title = new JLabel("Select Player", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 30));
                title.setForeground(Color.WHITE);

                //=====BUTTON DESIGN=====
                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 18));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setCustomBorderColor(Color.BLACK);
                backBtn.setForeground(Color.WHITE);
                backBtn.addActionListener((ActionEvent ae) -> {
                    frame.dispose();
                    showMenu();
                });

                //=====BUTTON FUNCTION=====
                RoundedButton newPlayerBtn = createMenuButton("New Player", new Color(9, 168, 3), Color.BLACK);
                newPlayerBtn.addActionListener(ae -> {
                    frame.dispose();
                    newPlayerScreen();
                });

                RoundedButton existingPlayerBtn = createMenuButton("Existing Player", new Color(255, 138, 0), Color.BLACK);
                existingPlayerBtn.addActionListener(ae -> {
                    frame.dispose();
                    existingPlayerScreen();
                });

                JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                buttonPanel.setOpaque(false);
                buttonPanel.add(newPlayerBtn);
                buttonPanel.add(existingPlayerBtn);
                buttonPanel.add(backBtn);

                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setOpaque(false);
                contentPanel.add(title, BorderLayout.NORTH);
                contentPanel.add(buttonPanel, BorderLayout.SOUTH);

                background.add(contentPanel);
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

    //=====NEW PALYER===
    private void newPlayerScreen() {
        JFrame frame = new JFrame("New Player");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource(backgroundImagePath));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                JLabel title = new JLabel("Enter Player Name", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 30));
                title.setForeground(Color.WHITE);

                JTextField nameField = new JTextField(20);
                nameField.setFont(new Font("Gameplay", Font.PLAIN, 22));
                nameField.setHorizontalAlignment(SwingConstants.CENTER);

                RoundedButton createBtn = createMenuButton("Create", new Color(9, 168, 3), Color.BLACK);

                createBtn.addActionListener((ActionEvent ae) -> {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        if (!DatabaseManager.playerExists(name)) {
                            DatabaseManager.createNewPlayer(name);
                            frame.dispose();
                            selectSubjectScreen(name);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Player already exists.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a name.");
                    }
                });

                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 18));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setCustomBorderColor(Color.BLACK);
                backBtn.setForeground(Color.WHITE);
                backBtn.addActionListener(ae -> {
                    frame.dispose();
                    playGame();
                });

                JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                inputPanel.setOpaque(false);
                inputPanel.add(nameField);
                inputPanel.add(createBtn);
                inputPanel.add(backBtn);

                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setOpaque(false);
                contentPanel.add(title, BorderLayout.NORTH);
                contentPanel.add(inputPanel, BorderLayout.SOUTH);

                background.add(contentPanel);
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

//===SELECT EXISTING PLAYER===
    private void existingPlayerScreen() {
        JFrame frame = new JFrame("Existing Players");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {

                ImageIcon bgIcon = new ImageIcon(getClass().getResource("/mtci/bg.png"));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                JLabel title = new JLabel("Select Existing Player", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 30));
                title.setForeground(Color.WHITE);

                JPanel boxPanel = new JPanel(new BorderLayout());
                boxPanel.setOpaque(false);
                boxPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

                JPanel transparentBox = new JPanel(new BorderLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(new Color(0, 0, 0, 150));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                        g2d.dispose();
                    }
                };
                transparentBox.setOpaque(false);
                transparentBox.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
                transparentBox.setPreferredSize(new Dimension(600, 500));

                JPanel playersPanel = new JPanel(new GridLayout(0, 1, 10, 10));
                playersPanel.setOpaque(false);
                //=====PLAYERS LIST=====
                ArrayList<String> players = DatabaseManager.getAllPlayers();
                if (players.isEmpty()) {
                    System.err.println("No players found in the database.");
                }
                for (String name : players) {
                    //===DYNAMIC BUTTONS===
                    RoundedButton btn = createMenuButton(name, new Color(80, 155, 255), Color.BLACK);
                    btn.addActionListener(ae -> {
                        frame.dispose();
                        selectSubjectScreen(name);
                    });
                    playersPanel.add(btn);
                }

                // Wrap the playersPanel in a JScrollPane to handle overflow if too many players
                JScrollPane scrollPane = new JScrollPane(playersPanel);
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setBorder(null);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                transparentBox.add(title, BorderLayout.NORTH);
                transparentBox.add(scrollPane, BorderLayout.CENTER);

                //===BACK BUTTON===
                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 18));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setCustomBorderColor(Color.BLACK);
                backBtn.setForeground(Color.WHITE);
                backBtn.addActionListener(ae -> {
                    frame.dispose();
                    playGame();
                });

                JPanel backPanel = new JPanel();
                backPanel.setOpaque(false);
                backPanel.add(backBtn);

                boxPanel.add(transparentBox, BorderLayout.CENTER);
                boxPanel.add(backPanel, BorderLayout.SOUTH);

                // box panel to the background label
                background.add(boxPanel);

                // Set the frame content and display
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

    private void selectSubjectScreen(String playerName) {
        JFrame frame = new JFrame("Select Subject");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource(backgroundImagePath));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                JLabel title = new JLabel("Select Subject", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 24));
                title.setForeground(Color.WHITE);
                title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

                JPanel subjectButtonPanel = new JPanel(new GridBagLayout());
                subjectButtonPanel.setOpaque(false);

                // Get the list of subjects from the database
                Map<String, String> subjectMap = getSubjectsFromDatabase();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;

                int row = 0;
                for (Map.Entry<String, String> entry : subjectMap.entrySet()) {
                    String subjectCode = entry.getKey();
                    String subjectName = entry.getValue();
                    String label = subjectCode + ": " + subjectName;

                    // BUTTONS F0R SUBJRVTS
                    RoundedButton btn = createMenuButton(label, new Color(255, 138, 0), Color.BLACK);
                    btn.setFont(new Font("Gameplay", Font.PLAIN, 16));
                    btn.setPreferredSize(new Dimension(500, 40));

                    btn.addActionListener(ae -> {
                        frame.dispose();
                        loadSubjectDatabaseAndStart(playerName, subjectCode);
                    });

                    gbc.gridy = row++;
                    subjectButtonPanel.add(btn, gbc);
                }

                // Back button 
                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 14));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setCustomBorderColor(Color.BLACK);
                backBtn.setForeground(Color.WHITE);
                backBtn.setPreferredSize(new Dimension(100, 30));

                backBtn.addActionListener(ae -> {
                    frame.dispose();
                    playGame();
                });

                gbc.gridy = row;
                subjectButtonPanel.add(backBtn, gbc);

                JScrollPane scrollPane = new JScrollPane(subjectButtonPanel);
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setBorder(null);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                JPanel transparentBox;
                transparentBox = new JPanel(new BorderLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(new Color(0, 0, 0, 200));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                        g2d.dispose();
                    }
                };
                transparentBox.setOpaque(false);
                transparentBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                transparentBox.setPreferredSize(new Dimension(600, 600));
                transparentBox.add(title, BorderLayout.NORTH);
                transparentBox.add(scrollPane, BorderLayout.CENTER);

                background.add(transparentBox);
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

// ===GETTER METHOD FOR TYHE SUBEJCTS===
    private Map<String, String> getSubjectsFromDatabase() {
        Map<String, String> subjects = new LinkedHashMap<>();
        String url = "jdbc:sqlite:./database/questions.db";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT subjectCode, subjectName FROM questions ORDER BY subjectCode ASC")) {

            while (rs.next()) {
                subjects.put(rs.getString("subjectCode"), rs.getString("subjectName"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading subjects: " + e.getMessage());
        }

        return subjects;
    }

// ===LOAD THE SELECTED SUBJHECT===
    private void loadSubjectDatabaseAndStart(String playerName, String subjectCode) {
        String dbPath = "./database/" + subjectCode + ".db";
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            JOptionPane.showMessageDialog(null, "Database for " + subjectCode + " not found!");
            return;
        }

        startGame(playerName, subjectCode, dbPath);
    }

// ===START GAME===
    private void startGame(String playerName, String subjectCode, String dbPath) {
        this.playerName = playerName;
        this.subjectCode = subjectCode;
        this.questions = DatabaseManager.getQuestions(dbPath);

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions found for this subject.");
            return;
        }
        answerFeedback = new ArrayList<>();
        Collections.shuffle(this.questions); // Shuffle questions randomly

        JFrame frame = new JFrame("Quiz Game");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Custom background panel that scales image
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = new ImageIcon(getClass().getResource(backgroundImagePath)).getImage();
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // Stretch to fit
            }
        };
        background.setLayout(new GridBagLayout());
        frame.setContentPane(background);

// GridBagConstraints setup
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

// === Info Panel: Player Name, Subject, Score ===
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        infoPanel.setOpaque(false);

        JLabel playerLabel = new JLabel("Player: " + playerName, SwingConstants.CENTER);
        JLabel subjectLabel = new JLabel("Subject: " + subjectCode.toUpperCase(), SwingConstants.CENTER);
        JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER); // Will update later

        Font infoFont = new Font("Gameplay", Font.BOLD, 20);
        playerLabel.setFont(infoFont);
        subjectLabel.setFont(infoFont);
        scoreLabel.setFont(infoFont);

        playerLabel.setForeground(Color.WHITE);
        subjectLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);

        infoPanel.add(playerLabel);
        infoPanel.add(subjectLabel);
        infoPanel.add(scoreLabel);

        background.add(infoPanel, gbc);

// === Timer Label ===
        gbc.gridy++;
        JLabel timerLabel = new JLabel("Time: 60s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Gameplay", Font.BOLD, 26));
        timerLabel.setForeground(Color.WHITE);
        background.add(timerLabel, gbc);

// === Question Box ===
        gbc.gridy++;
        gbc.insets = new Insets(40, 15, 15, 15); // Push a bit down for spacing

        JPanel questionBox = new JPanel();
        questionBox.setBackground(new Color(0, 0, 0, 180)); // Semi-transparent black
        questionBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        questionBox.setLayout(new GridBagLayout());
        questionBox.setPreferredSize(new Dimension(1000, 1000)); // Size of the box

// === Wrapped HTML Label ===
        JLabel questionLabel = new JLabel();
        questionLabel.setFont(new Font("Gameplay", Font.BOLD, 24));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

// Let text wrap by using HTML + max width CSS
        questionLabel.setText("<html><div style='text-align: center; width:700px;'>" + "" + "</div></html>");

        questionBox.add(questionLabel);
        background.add(questionBox, gbc);

// === Choice Buttons Panel ===
        gbc.gridy++;
        gbc.insets = new Insets(25, 15, 15, 15);

        JPanel choicePanel = new JPanel(new GridLayout(2, 2, 15, 15));
        choicePanel.setOpaque(false);
        List<JButton> choiceButtons = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Gameplay", Font.PLAIN, 20));
            btn.setBackground(new Color(255, 138, 0));
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            choiceButtons.add(btn);
            choicePanel.add(btn);
        }

        background.add(choicePanel, gbc);

        frame.setVisible(true);

        // ===== Game State Setup =====
        score = 0;
        currentQuestionIndex = 0;
        answerFeedback = new ArrayList<>();

        // ===== Timer Start =====
        GameTimer gameTimer = new GameTimer(60, timerLabel, () -> endGame(frame));
        gameTimer.start();

        // ===== Load First Question =====
        updateChoices(questionLabel, choiceButtons);

        // ===== Button Listeners =====
        for (JButton btn : choiceButtons) {
            btn.addActionListener((ActionEvent e) -> {
                Question q = questions.get(currentQuestionIndex);
                String selected = btn.getText();
                boolean correct = selected.equals(q.getCorrectAnswer());

                btn.setBackground(correct ? Color.GREEN : Color.RED);
                btn.setOpaque(true);
                btn.setEnabled(false);

                answerFeedback.add((correct ? "✔️ " : "❌ ")
                        + q.getQuestionText()
                        + " | Selected: " + selected
                        + " | Answer: " + q.getCorrectAnswer());

                if (correct) {
                    score++;
                    scoreLabel.setText("Score: " + score); // update score display
                }

                // Delay then show next question or end
                new javax.swing.Timer(500, ev -> {
                    ((javax.swing.Timer) ev.getSource()).stop();
                    currentQuestionIndex++;

                    if (currentQuestionIndex < questions.size()) {
                        resetButtonColors(choiceButtons);
                        updateChoices(questionLabel, choiceButtons);
                    } else {
                        gameTimer.stop();
                        endGame(frame);
                    }
                }).start();
            });
        }
    }

    private void updateChoices(JLabel questionLabel, List<JButton> choiceButtons) {
        Question q = questions.get(currentQuestionIndex);

        // Wrap the question in HTML to allow line breaks and max width
        String wrappedQuestion = "<html><div style='text-align: center; width:700px;'>"
                + q.getQuestionText()
                + "</div></html>";
        questionLabel.setText(wrappedQuestion);

        List<String> choices = q.getChoices();
        for (int i = 0; i < choiceButtons.size(); i++) {
            JButton btn = choiceButtons.get(i);
            btn.setText(choices.get(i));
            btn.setEnabled(true);
            btn.setBackground(new Color(255, 138, 0)); // Orange-ish color
        }
    }

    private void resetButtonColors(List<JButton> choiceButtons) {
        for (JButton btn : choiceButtons) {
            btn.setBackground(null);
            btn.setEnabled(true);
        }
    }

    private void endGame(JFrame frame) {
        DatabaseManager.updatePlayerScore(playerName, subjectCode, score);
        frame.dispose();

        JFrame resultFrame = new JFrame("Game Over");
        resultFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom background panel that scales image
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = new ImageIcon(getClass().getResource(backgroundImagePath)).getImage();
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // Stretch to fit
            }
        };
        background.setLayout(new GridBagLayout());
        resultFrame.setContentPane(background);

        JLabel resultLabel = new JLabel("Final Score: " + score, SwingConstants.CENTER);
        resultLabel.setFont(new Font("Gameplay", Font.BOLD, 32));
        resultLabel.setForeground(Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setOpaque(false);
        panel.add(resultLabel);

        // Buttons with different colors
        RoundedButton reviewBtn = new RoundedButton("Review Answers");
        reviewBtn.setBackground(new Color(13, 55, 209)); // Blue
        reviewBtn.addActionListener(e -> reviewAnswersScreen());

        RoundedButton playAgainBtn = new RoundedButton("Play Again");
        playAgainBtn.setBackground(new Color(9, 168, 3)); // Green
        playAgainBtn.addActionListener(e -> {
            resultFrame.dispose();
            startGame(playerName, subjectCode, "./database/" + subjectCode + ".db");
        });

        RoundedButton selectAnotherBtn = new RoundedButton("Select Another Subject");
        selectAnotherBtn.setBackground(new Color(255, 138, 0)); // Yellow/Orange
        selectAnotherBtn.addActionListener(e -> {
            resultFrame.dispose();
            selectSubjectScreen(playerName);
        });

        RoundedButton menuBtn = new RoundedButton("Main Menu");
        menuBtn.setBackground(new Color(171, 3, 3)); // Red
        menuBtn.addActionListener(e -> {
            resultFrame.dispose();
            showMenu();
        });

        // Apply consistent font and foreground
        for (JButton b : List.of(reviewBtn, playAgainBtn, selectAnotherBtn, menuBtn)) {
            b.setFont(new Font("Gameplay", Font.PLAIN, 20));
            b.setForeground(Color.WHITE);
            panel.add(b);
        }

        background.add(panel);
        resultFrame.setVisible(true);
    }

//===OKAY NA DITO NA PART===
    private void reviewAnswersScreen() {
        JFrame reviewFrame = new JFrame("Answer Review");
        reviewFrame.setSize(900, 600);
        reviewFrame.setLocationRelativeTo(null);
        reviewFrame.getContentPane().setBackground(Color.BLACK);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.BLACK);

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String feedback = answerFeedback.get(i);
            String selected = extractSelectedAnswer(feedback);

            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            questionPanel.setBackground(new Color(30, 30, 30));

            JLabel questionLabel = new JLabel("<html><b>Q: " + q.getQuestionText() + "</b></html>");
            questionLabel.setForeground(Color.WHITE);
            questionPanel.add(questionLabel);

            for (String choice : q.getChoices()) {
                JLabel choiceLabel = new JLabel("• " + choice);
                choiceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                if (choice.equals(q.getCorrectAnswer())) {
                    choiceLabel.setForeground(Color.GREEN); // Correct answer
                } else if (choice.equals(selected)) {
                    choiceLabel.setForeground(Color.RED); // Wrong selection
                } else {
                    choiceLabel.setForeground(Color.LIGHT_GRAY); // Neutral
                }

                questionPanel.add(choiceLabel);
            }

            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(questionPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        reviewFrame.add(scrollPane);
        reviewFrame.setVisible(true);
    }

// Helper method to extract the selected answer from answerFeedback entry
    private String extractSelectedAnswer(String feedback) {
        int selectedStart = feedback.indexOf("Selected: ");
        int answerStart = feedback.indexOf(" | Answer:");

        if (selectedStart == -1 || answerStart == -1) {
            return "";
        }

        return feedback.substring(selectedStart + 10, answerStart).trim();
    }

//==========================================
//LEADERBBOARD OKAY NATO DITO AS WELL HAHHAA
//=========================================
    public void showLeaderBoard() {
        JFrame frame = new JFrame("Leaderboard");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximized, but keeps taskbar visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {

                ImageIcon bgIcon = new ImageIcon(getClass().getResource("/mtci/bg.png"));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                //  one-row, one-column section for the title "Leaderboard"
                JPanel titlePanel = new JPanel(new GridLayout(1, 1));
                titlePanel.setOpaque(false);
                JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 30));
                title.setForeground(Color.WHITE);
                title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                titlePanel.add(title);  // Add title to the titlePanel

                // container for leaderboard and scores (outside the transparent table)
                JPanel leaderboardContainer = new JPanel();
                leaderboardContainer.setLayout(new BorderLayout());
                leaderboardContainer.setOpaque(false);
                leaderboardContainer.setPreferredSize(new Dimension(1000, 600));

                // Transparent grid structure for leaderboard scores 
                JPanel leaderboardTable = new JPanel();
                leaderboardTable.setLayout(new GridLayout(0, 12)); // 12 columns (1 for name, 11 for scores)
                leaderboardTable.setOpaque(false);  // Transparent background
                leaderboardTable.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

                // column headers for the leaderboard table
                leaderboardTable.add(createHeaderLabel("Player"));
                for (int i = 1; i <= 11; i++) {
                    leaderboardTable.add(createHeaderLabel("GE" + String.format("%02d", i)));
                }

                // leaderboard data
                try {
                    String sql = "SELECT name, ge01Score, ge02Score, ge03Score, ge04Score, ge05Score, ge06Score, ge07Score, ge08Score, ge09Score, ge10Score, ge11Score, total_score FROM players ORDER BY total_score DESC LIMIT 10";
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:./database/players.db"); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

                        // Display player names and scores for each subject
                        while (rs.next()) {
                            String playerName = rs.getString("name");
                            leaderboardTable.add(createDataLabel(playerName));
                            for (int i = 1; i <= 11; i++) {
                                int score = rs.getInt("ge" + String.format("%02d", i) + "Score");
                                leaderboardTable.add(createDataLabel(String.valueOf(score)));
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error fetching leaderboard content: " + e.getMessage());
                    }
                } catch (Exception e) {
                }

                // Dark transparent background for the leaderboard box
                JPanel transparentBox = new JPanel(new BorderLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(new Color(0, 0, 0, 200));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                        g2d.dispose();
                    }
                };
                transparentBox.setOpaque(false);
                transparentBox.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
                transparentBox.add(leaderboardTable, BorderLayout.CENTER);

                //add title and leaderboard to the main panel contianer
                JPanel mainContainer = new JPanel();
                mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
                mainContainer.setOpaque(false);

                //title leaderboard
                mainContainer.add(titlePanel);
                mainContainer.add(Box.createVerticalStrut(20));
                mainContainer.add(transparentBox);

                // Back Button 
                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 18));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setCustomBorderColor(Color.BLACK);
                backBtn.setForeground(Color.WHITE);
                backBtn.addActionListener(ae -> {
                    frame.dispose();
                    showMenu();  // Assuming showMenu() takes the user back to the main menu
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
                buttonPanel.add(Box.createHorizontalGlue());
                buttonPanel.add(backBtn);
                buttonPanel.add(Box.createHorizontalGlue());

                mainContainer.add(Box.createVerticalStrut(20));  // Add space between leaderboard and back button
                mainContainer.add(buttonPanel); // Add the back button panel

                // Add the main container panel to the background
                background.add(mainContainer);

                // Set the frame content and display it
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

// Helper method to create header labels for column titles
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Gameplay", Font.BOLD, 14));
        label.setForeground(Color.GREEN);  // Subject headers in green
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return label;
    }

// Helper method to create data labels for player names and scores
    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Gameplay", Font.PLAIN, 14));
        label.setForeground(text.equals("Player") ? Color.WHITE : Color.YELLOW);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return label;
    }
//=============================================================
//About us, Mission Vision
//=============================================================

    public void showAboutUs() {
        JFrame frame = new JFrame("About Us");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set the background image and layout
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource(backgroundImagePath));
                Image bgImage = bgIcon.getImage();
                Image scaledBgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

                JLabel background = new JLabel(new ImageIcon(scaledBgImage)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(scaledBgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                background.setLayout(new GridBagLayout());

                // Game title for About Us
                JLabel title = new JLabel("About Us", SwingConstants.CENTER);
                title.setFont(new Font("Gameplay", Font.BOLD, 30));
                title.setForeground(Color.WHITE); // Set title color to white
                title.setAlignmentX(Component.CENTER_ALIGNMENT);

                // About Us content
                JLabel aboutUsContent = new JLabel("Wala pa natapos LOL haha");
                aboutUsContent.setFont(new Font("Arial", Font.PLAIN, 18));
                aboutUsContent.setForeground(Color.WHITE);

                // Back Button
                RoundedButton backBtn = new RoundedButton("Back");
                backBtn.setFont(new Font("Gameplay", Font.PLAIN, 18));
                backBtn.setBackground(new Color(171, 3, 3));
                backBtn.setForeground(Color.WHITE);
                backBtn.addActionListener(e1 -> {
                    frame.dispose();
                    showMenu();
                });

                // Panel for About Us and buttons
                JPanel contentPanel = new JPanel();
                contentPanel.setOpaque(false);
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.add(title);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space before About Us content
                contentPanel.add(aboutUsContent);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space before the back button
                contentPanel.add(backBtn);

                background.add(contentPanel);
                frame.setContentPane(background);
                frame.setVisible(true);
            }
        });

        // Show the frame
        frame.setVisible(true);
    }

}
