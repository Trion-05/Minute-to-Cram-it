package mtci.classes;

import javax.swing.JLabel;
import javax.swing.Timer;

public class GameTimer {

    private int timeLeft;
    private Timer timer;
    private final JLabel timeLabel;
    private final Runnable onFinish;

    public GameTimer(int seconds, JLabel label, Runnable onFinish) {
        this.timeLeft = seconds;
        this.timeLabel = label;
        this.onFinish = onFinish;
    }

    public void start() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timeLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                onFinish.run();
            }
        });
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}
