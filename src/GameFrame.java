

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    ArrayList<GamePanel> gamePanels = new ArrayList<>();
     MainPanel mainPanel;
    public Clip clip;
    static boolean themeSongEnabled = true;





    GameFrame() {




        // Load and play the theme song
        try {
            File themeSongFile = new File("Destroyer-Of-Worlds.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(themeSongFile);
            clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loops the audio continuously
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel = new MainPanel(this);

        this.add(mainPanel);


        this.setTitle("Swipe Brick Breaker");
        this.setBounds(10, 10, 600, 700);
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }





}
