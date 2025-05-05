package mtci.classes;

import java.util.List;

public class Question {

    private String questionText;
    private List<String> choices;
    private String correctAnswer;

    public Question(String questionText, List<String> choices, String correctAnswer) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        return questionText;
    }
}
