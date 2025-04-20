package mtci;

import mtci.Question;
import mtci.Question;

public class AnswerRecord {

    private final Question question;
    private final String selectedAnswer;
    private final boolean correct;

    public AnswerRecord(Question question, String selectedAnswer, boolean correct) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }
}
