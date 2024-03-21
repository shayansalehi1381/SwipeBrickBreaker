import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel panel;

    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Swipe Brick Breaker");
        this.setBounds(10, 10, 600, 700);
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
