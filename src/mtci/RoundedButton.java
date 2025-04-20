package mtci;



import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;

// RoundedButton class
public class RoundedButton extends JButton {

    private static final long serialVersionUID = 1L;
    private Color borderColor = Color.BLACK; // Default border color

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Allow setting custom border color
    public void setCustomBorderColor(Color color) {
        this.borderColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground());
        }
        //  rounded background
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
        // Draw the rounded border
        g.setColor(borderColor);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }

}
