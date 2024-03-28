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


        JLabel scoreLabel = new JLabel("SCORE: "+GamePanel.score);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBackground(Color.black);
        scoreLabel.setBounds(150,120,200,60);
        scoreLabel.setFont(new Font("Arial",Font.PLAIN,20));

        add(scoreLabel);

        playAgain = new JButton("Play Again");
        playAgain.setBackground(Color.red);
        playAgain.setForeground(Color.black);
        playAgain.addActionListener(this);
        playAgain.setBounds(150,180,150,50);
        add(playAgain);






    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgain){
            GamePanel newGamePanel = new GamePanel(frame);

            newGamePanel.resetGame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new GamePanel(this.frame));
            frame.revalidate();
            frame.repaint();

        }
    }
}
