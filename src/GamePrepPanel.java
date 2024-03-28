import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePrepPanel extends JPanel implements ActionListener {


    JButton backButton;
    GameFrame frame;
    MainPanel mainPanel;
    ButtonGroup buttonGroup;
    JRadioButton easyButton;
    JRadioButton normalButton;
    JRadioButton hardButton;
    JButton colorButton;
    static JTextField textField;

    JButton startGame;
    GamePanel gamePanel;

    public GamePrepPanel(GameFrame frame ,MainPanel mainPanel){
        this.frame = frame;
        this.mainPanel = mainPanel;
        buttonGroup = new ButtonGroup();
        easyButton = new JRadioButton("Easy");
        normalButton = new JRadioButton("Normal");
        hardButton = new JRadioButton("Hard");
        easyButton.setBackground(Color.red);
        normalButton.setBackground(Color.red);
        hardButton.setBackground(Color.red);
        easyButton.setForeground(Color.black);
        normalButton.setForeground(Color.black);
        hardButton.setForeground(Color.black);

        easyButton.setBounds(360,140,60,40);
        normalButton.setBounds(435,140,70,40);
        hardButton.setBounds(520,140,60,40);

        easyButton.addActionListener(this);
        normalButton.addActionListener(this);
        hardButton.addActionListener(this);
        //set default:
        easyButton.setSelected(true);


        buttonGroup.add(easyButton);
        buttonGroup.add(normalButton);
        buttonGroup.add(hardButton);
       this.add(easyButton);
       this.add(normalButton);
       this.add(hardButton);

        setBackground(Color.BLACK);
        setLayout(null);
        backButton = new JButton("Back");
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.black);
        backButton.setBounds(50,600,100,50);
        backButton.addActionListener(this);
        this.add(backButton);


        JLabel mainPageLabel = new JLabel("Game preparation page");
        mainPageLabel.setForeground(Color.white);
        mainPageLabel.setBackground(Color.black);
        mainPageLabel.setBounds(40,40,240,60);
        mainPageLabel.setFont(new Font("Arial",Font.PLAIN,20));

        add(mainPageLabel);





        JLabel difficultyLabel = new JLabel("Choose the difficulty level of the game:");
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setBackground(Color.black);
        difficultyLabel.setBounds(40,140,300,40);
        difficultyLabel.setFont(new Font("Arial",Font.PLAIN,15));

        add(difficultyLabel);


        JLabel colorLabel = new JLabel("Choose the Ball Color:");
        colorLabel.setForeground(Color.white);
        colorLabel.setBackground(Color.black);
        colorLabel.setBounds(40,240,300,40);
        colorLabel.setFont(new Font("Arial",Font.PLAIN,15));

        add(colorLabel);


        colorButton = new JButton("Pick Color");
        colorButton.addActionListener(this);
        colorButton.setBackground(Color.red);
        colorButton.setForeground(Color.black);
        colorButton.setBounds(400,240,100,40);
        add(colorButton);


        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setForeground(Color.white);
        nameLabel.setBackground(Color.black);
        nameLabel.setBounds(40,320,300,40);
        nameLabel.setFont(new Font("Arial",Font.PLAIN,15));

        add(nameLabel);

        textField = new JTextField();
        textField.setBounds(300,320,200,40);
        add(textField);

        startGame = new JButton("Start the Game");
        startGame.setForeground(Color.black);
        startGame.setBackground(Color.red);
        startGame.setBounds(220,450,150,40);
        startGame.addActionListener(this);
        add(startGame);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            frame.getContentPane().removeAll();
            frame.getContentPane().add(this.mainPanel);
            frame.revalidate();
            frame.repaint();
        }
        if (e.getSource() == colorButton){
            JColorChooser colorChooser=new JColorChooser();

            Color newcolor=JColorChooser.showDialog(null,"pick a color",Color.white);
                Ball.color = newcolor;
                GamePanel.aimColor = newcolor;
        }
        if (e.getSource() == startGame){
            if (textField.getText().isEmpty()){

                JOptionPane.showMessageDialog(this,"Please Enter the Name!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                    gamePanel = new GamePanel(frame);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(gamePanel);
                frame.revalidate();
                frame.repaint();
            }
        }

        if (e.getSource() == easyButton){
            GamePanel.easy = true;
            GamePanel.medium = false;
            GamePanel.hard = false;
        }
        else if (e.getSource() == normalButton){
            GamePanel.easy = false;
            GamePanel.medium = true;
            GamePanel.hard = false;
        }
        else if (e.getSource() == hardButton){
            GamePanel.easy = false;
            GamePanel.medium = false;
            GamePanel.hard = true;
        }
    }
}
