import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener {
    GameFrame frame;
    GamePanel gamePanel;
    MainPanel mainPanel;
    GamePrepPanel gamePrepPanel;

    JButton playAgain;
    JButton mainPanelButton;
    JButton gamePrepButton ;

    public GameOverPanel(GameFrame frame, GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.frame = frame;
        setBackground(Color.BLACK);
        setLayout(null);

        JLabel mainPageLabel = new JLabel("! GAME OVER !");
        mainPageLabel.setForeground(Color.white);
        mainPageLabel.setBackground(Color.black);
        mainPageLabel.setBounds(150,60,250,40);
        mainPageLabel.setFont(new Font("Arial",Font.BOLD,30));

        add(mainPageLabel);




        playAgain = new JButton("Play Again");
        playAgain.setBackground(Color.red);
        playAgain.setForeground(Color.black);
        playAgain.addActionListener(this);
        playAgain.setBounds(170,180,150,50);
        add(playAgain);



       gamePrepButton = new JButton("Game Prep Page");
        gamePrepButton.setBackground(Color.red);
        gamePrepButton.setForeground(Color.black);
        gamePrepButton.addActionListener(this);
        gamePrepButton.setBounds(170,250,150,50);
        add(gamePrepButton);


        mainPanelButton = new JButton("Main Page");
        mainPanelButton.setBackground(Color.red);
        mainPanelButton.setForeground(Color.black);
        mainPanelButton.addActionListener(this);
        mainPanelButton.setBounds(170,320,150,50);
        add(mainPanelButton);




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgain){
            gamePanel.resetGame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new GamePanel(frame));
            frame.revalidate();
            frame.repaint();

        }

        else if (e.getSource() ==mainPanelButton ){
            gamePanel.resetGame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new MainPanel(frame));
            frame.revalidate();
            frame.repaint();
        }

        else if (e.getSource() == gamePrepButton){
            gamePanel.resetGame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new GamePrepPanel(frame,new MainPanel(frame)));
            frame.revalidate();
           frame.repaint();
        }
    }
}
