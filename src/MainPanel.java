import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    JButton startGameButton;
    JButton gameHistoryButton;
    JButton settingsButton;
    JButton exitButton;

    SettingPanel settingPanel;
    GamePrepPanel gamePrepPanel;
    HistoryPanel historyPanel;

    GameFrame gameFrame;

    public MainPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        setBackground(Color.BLACK);
        setLayout(null);
        startGameButton = new JButton("Start New Game");
        gameHistoryButton = new JButton("History List");
        settingsButton = new JButton("Setting");
        exitButton = new JButton("Exit");
        settingsButton.setBackground(Color.RED);
        startGameButton.setBackground(Color.red);
        gameHistoryButton.setBackground(Color.red);
        exitButton.setBackground(Color.red);
        startGameButton.setForeground(Color.black);
        gameHistoryButton.setForeground(Color.black);
        settingsButton.setForeground(Color.black);
        exitButton.setForeground(Color.black);


        exitButton.addActionListener(this);
        settingsButton.addActionListener(this);
        startGameButton.addActionListener(this);
        gameHistoryButton.addActionListener(this);

        startGameButton.setBounds(GamePanel.GAME_WIDTH / 2 - 100, 100, 200, 50);
        gameHistoryButton.setBounds(GamePanel.GAME_WIDTH / 2 - 100, 170, 200, 50);
        settingsButton.setBounds(GamePanel.GAME_WIDTH / 2 - 100, 240, 200, 50);
        exitButton.setBounds(GamePanel.GAME_WIDTH / 2 - 100, 310, 200, 50);

        // Create label for displaying overall record
        JLabel overallRecordLabel = new JLabel("Record: ");
        overallRecordLabel.setForeground(Color.white);
        overallRecordLabel.setBounds(GamePanel.GAME_WIDTH / 2 - 100, 380, 200, 50);

        //Main Page Label
        JLabel mainPageLabel = new JLabel("Main Page");
        mainPageLabel.setForeground(Color.white);
        mainPageLabel.setBackground(Color.black);
        mainPageLabel.setBounds(40,40,100,60);
        mainPageLabel.setFont(new Font("Arial",Font.PLAIN,20));


        //add  labels to the panel
        add(overallRecordLabel);
        add(mainPageLabel);


        // Add buttons to the panel
        add(startGameButton);
        add(gameHistoryButton);
        add(settingsButton);
        add(exitButton);


        // Initialize the setting panel
        settingPanel = new SettingPanel(this.gameFrame,this);
        gamePrepPanel = new GamePrepPanel(this.gameFrame,this);
        historyPanel = new HistoryPanel(this.gameFrame,this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == settingsButton) {
            gameFrame.getContentPane().removeAll();
            gameFrame.getContentPane().add(settingPanel);
            gameFrame.revalidate();
            gameFrame.repaint();
        }
        else if (e.getSource() == startGameButton) {
            gameFrame.getContentPane().removeAll();
            gameFrame.getContentPane().add(gamePrepPanel);
            gameFrame.revalidate();
            gameFrame.repaint();
        }
        else if (e.getSource() == gameHistoryButton) {
            gameFrame.getContentPane().removeAll();
            gameFrame.getContentPane().add(historyPanel);
            gameFrame.revalidate();
            gameFrame.repaint();
        }

    }
}
