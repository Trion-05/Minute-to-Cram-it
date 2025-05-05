package mtci.classes;

public class Player {

    private final String name;

    private double ge01Score;
    private double ge02Score;
    private double ge03Score;
    private double ge04Score;
    private double ge05Score;
    private double ge06Score;
    private double ge07Score;
    private double ge08Score;
    private double ge09Score;
    private double ge10Score;
    private double ge11Score;

    private double totalScore;

    public Player(String name) {
        this.name = name;
        this.totalScore = 0;
    }

  //player Score Constructor
    public Player(String name, double ge01, double ge02, double ge03, double ge04,
            double ge05, double ge06, double ge07, double ge08,
            double ge09, double ge10, double ge11) {
        this.name = name;
        this.ge01Score = ge01;
        this.ge02Score = ge02;
        this.ge03Score = ge03;
        this.ge04Score = ge04;
        this.ge05Score = ge05;
        this.ge06Score = ge06;
        this.ge07Score = ge07;
        this.ge08Score = ge08;
        this.ge09Score = ge09;
        this.ge10Score = ge10;
        this.ge11Score = ge11;
        recalculateTotalScore();
    }

    // Setters 
    public void setGe01Score(double score) {
        this.ge01Score = score;
        recalculateTotalScore();
    }

    public void setGe02Score(double score) {
        this.ge02Score = score;
        recalculateTotalScore();
    }

    public void setGe03Score(double score) {
        this.ge03Score = score;
        recalculateTotalScore();
    }

    public void setGe04Score(double score) {
        this.ge04Score = score;
        recalculateTotalScore();
    }

    public void setGe05Score(double score) {
        this.ge05Score = score;
        recalculateTotalScore();
    }

    public void setGe06Score(double score) {
        this.ge06Score = score;
        recalculateTotalScore();
    }

    public void setGe07Score(double score) {
        this.ge07Score = score;
        recalculateTotalScore();
    }

    public void setGe08Score(double score) {
        this.ge08Score = score;
        recalculateTotalScore();
    }

    public void setGe09Score(double score) {
        this.ge09Score = score;
        recalculateTotalScore();
    }

    public void setGe10Score(double score) {
        this.ge10Score = score;
        recalculateTotalScore();
    }

    public void setGe11Score(double score) {
        this.ge11Score = score;
        recalculateTotalScore();
    }

    public void setScore(int geNumber, double score) {
        switch (geNumber) {
            case 1 ->
                setGe01Score(score);
            case 2 ->
                setGe02Score(score);
            case 3 ->
                setGe03Score(score);
            case 4 ->
                setGe04Score(score);
            case 5 ->
                setGe05Score(score);
            case 6 ->
                setGe06Score(score);
            case 7 ->
                setGe07Score(score);
            case 8 ->
                setGe08Score(score);
            case 9 ->
                setGe09Score(score);
            case 10 ->
                setGe10Score(score);
            case 11 ->
                setGe11Score(score);
            default ->
                throw new IllegalArgumentException("Invalid Subject: " + geNumber);
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getGe01Score() {
        return ge01Score;
    }

    public double getGe02Score() {
        return ge02Score;
    }

    public double getGe03Score() {
        return ge03Score;
    }

    public double getGe04Score() {
        return ge04Score;
    }

    public double getGe05Score() {
        return ge05Score;
    }

    public double getGe06Score() {
        return ge06Score;
    }

    public double getGe07Score() {
        return ge07Score;
    }

    public double getGe08Score() {
        return ge08Score;
    }

    public double getGe09Score() {
        return ge09Score;
    }

    public double getGe10Score() {
        return ge10Score;
    }

    public double getGe11Score() {
        return ge11Score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    private void recalculateTotalScore() {
        totalScore = ge01Score + ge02Score + ge03Score + ge04Score + ge05Score
                + ge06Score + ge07Score + ge08Score + ge09Score + ge10Score + ge11Score;
    }

    @Override
    public String toString() {
        return name + ": " + String.format("%.1f", totalScore);
    }
}
