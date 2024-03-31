import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Timer;

public class HistoryPanel extends JPanel implements ActionListener {
    static ArrayList<Player> players = new ArrayList<>();
    JList<String> historyList;
    DefaultListModel<String> listModel;
    JButton backButton;
    GameFrame frame;
    MainPanel mainPanel;
    public HistoryPanel(GameFrame frame ,MainPanel mainPanel){



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

        JLabel mainPageLabel = new JLabel("History page");
        mainPageLabel.setForeground(Color.white);
        mainPageLabel.setBackground(Color.black);
        mainPageLabel.setBounds(40,40,240,60);
        mainPageLabel.setFont(new Font("Arial",Font.PLAIN,20));

        add(mainPageLabel);




        listModel = new DefaultListModel<>();
        historyList = new JList<>(listModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane scrollPane = new JScrollPane(historyList);
        add(scrollPane, BorderLayout.CENTER);





        displayHistory();
    }


    public void displayHistory() {
        listModel.clear();
        for (Player player : players) {
            listModel.addElement(player.toString());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(this.mainPanel);
        frame.revalidate();
        frame.repaint();
    }



}
