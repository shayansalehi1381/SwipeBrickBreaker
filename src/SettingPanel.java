import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel implements ActionListener {
    JButton backButton;
    GameFrame frame;
    MainPanel mainPanel;
    public SettingPanel(GameFrame frame ,MainPanel mainPanel){
        this.frame = frame;
        this.mainPanel = mainPanel;
        setBackground(Color.BLACK);
        setLayout(null);
        backButton = new JButton("Back");
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.black);
        backButton.setBounds(50,600,100,50);
        backButton.addActionListener(this);
        this.add(backButton);



        JLabel mainPageLabel = new JLabel("Setting page");
        mainPageLabel.setForeground(Color.white);
        mainPageLabel.setBackground(Color.black);
        mainPageLabel.setBounds(40,40,240,60);
        mainPageLabel.setFont(new Font("Arial",Font.PLAIN,20));

        add(mainPageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(this.mainPanel);
        frame.revalidate();
        frame.repaint();
    }
}
