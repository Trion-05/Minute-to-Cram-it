package mtci;

import java.util.ArrayList;
import java.util.List;
import mtci.Question;
import mtci.Question;

public class CurrentGame {

    private static CurrentGame instance;
    private final List<AnswerRecord> answerHistory;

    private CurrentGame() {
        answerHistory = new ArrayList<>();
    }

    // Get the singleton instance of CurrentGame
    public static CurrentGame getInstance() {
        if (instance == null) {
            instance = new CurrentGame();
        }
        return instance;
    }

    // Store the answer (correct or incorrect)
    public void storeAnswer(Question question, String selectedAnswer, boolean correct) {
        answerHistory.add(new AnswerRecord(question, selectedAnswer, correct));
    }

    // Get the list of stored answers for review
    public List<AnswerRecord> getAnswerHistory() {
        return answerHistory;
    }
}
