import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel implements ActionListener {
    JButton backButton;
    ButtonGroup groupTrigger;
    JRadioButton triggerOn ;
    JRadioButton triggerOff;


    ButtonGroup groupSong;
    JRadioButton songEnabled;
    JRadioButton songDisabled;

    ButtonGroup groupSaveHistory;
    JRadioButton saveEnabeld;
    JRadioButton saveDiabled;

    GameFrame frame;
    MainPanel mainPanel;
    public SettingPanel(GameFrame frame ,MainPanel mainPanel){
        groupTrigger = new ButtonGroup();
        triggerOn = new JRadioButton("Enable");
        triggerOn.setBackground(Color.red);
        triggerOn.setForeground(Color.black);
        triggerOn.setBounds(320,140,80,40);
        triggerOn.addActionListener(this);
        triggerOff = new JRadioButton("Disable");
        triggerOff.setBackground(Color.red);
        triggerOff.setForeground(Color.black);
        triggerOff.setBounds(420,140,80,40);
        triggerOff.addActionListener(this);
        groupTrigger.add(triggerOn);
        groupTrigger.add(triggerOff);
        add(triggerOn);
        add(triggerOff);
        triggerOn.setSelected(true);


        JLabel aimingLineLabel = new JLabel("Aiming Line State:");
        aimingLineLabel.setForeground(Color.white);
        aimingLineLabel.setBounds(100,140,150,40);
        aimingLineLabel.setFont(new Font("Arial",Font.PLAIN,15));
        add(aimingLineLabel);

        //********************************************************************
        groupSong = new ButtonGroup();
         songEnabled= new JRadioButton("Enable");
        songEnabled.setBackground(Color.red);
        songEnabled.setForeground(Color.black);
        songEnabled.setBounds(320,200,80,40);
        songEnabled.addActionListener(this);
        songDisabled = new JRadioButton("Disable");
        songDisabled.setBackground(Color.red);
        songDisabled.setForeground(Color.black);
        songDisabled.setBounds(420,200,80,40);
        songDisabled.addActionListener(this);
        groupSong.add(songEnabled);
        groupSong.add(songDisabled);
        add(songEnabled);
        add(songDisabled);
        songEnabled.setSelected(true);

        JLabel songThemeLabel = new JLabel("Theme Song State:");
        songThemeLabel.setForeground(Color.white);
        songThemeLabel.setBounds(100,200,150,40);
        songThemeLabel.setFont(new Font("Arial",Font.PLAIN,15));
        add(songThemeLabel);
        //*********************************************************************
        groupSaveHistory = new ButtonGroup();
        saveEnabeld = new JRadioButton("Enable");
        saveEnabeld.setBackground(Color.red);
        saveEnabeld.setForeground(Color.black);
        saveEnabeld.setBounds(320,260,80,40);
        saveEnabeld.addActionListener(this);
        saveDiabled = new JRadioButton("Disable");
        saveDiabled.setBackground(Color.red);
        saveDiabled.setForeground(Color.black);
        saveDiabled.setBounds(420,260,80,40);
        saveDiabled.addActionListener(this);
        groupSaveHistory.add(saveEnabeld);
        groupSaveHistory.add(saveDiabled);
        add(saveEnabeld);
        add(saveDiabled);
        saveDiabled.setSelected(true);

        JLabel saveLabel = new JLabel("Save History State:");
        saveLabel.setForeground(Color.white);
        saveLabel.setBounds(100,260,150,40);
        saveLabel.setFont(new Font("Arial",Font.PLAIN,15));
        add(saveLabel);
        //***********************************************************************



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
            if (e.getSource() == triggerOn){
                GamePanel.Aiming = true;
            }
            else if (e.getSource() == triggerOff){
                GamePanel.Aiming = false;
            }


            if (e.getSource() == songEnabled){
                GameFrame.themeSongEnabled = true;
                System.out.println(GameFrame.themeSongEnabled);
                if (frame.clip != null && !frame.clip.isRunning()) {
                    frame.clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
            else if (e.getSource() == songDisabled){
                GameFrame.themeSongEnabled = false;
                System.out.println(GameFrame.themeSongEnabled);
                if (frame.clip != null && frame.clip.isRunning()) {
                    frame.clip.stop();
                }
            }

            if (e.getSource() == saveEnabeld){
                GamePanel.savingHistory = true;
            }
            else if (e.getSource() == saveDiabled){
                GamePanel.savingHistory = false;
            }

        if (e.getSource() == backButton){
            frame.getContentPane().removeAll();
            frame.getContentPane().add(this.mainPanel);
            frame.revalidate();
            frame.repaint();
        }

    }
}
