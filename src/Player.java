import java.time.LocalDate;
import java.time.LocalTime;

public class Player {
    int score;
    String name;
    LocalDate localDate;
    LocalTime localTime;
    public Player(){
        score = (int)GamePanel.score;
        name = GamePrepPanel.textField.getText();
        HistoryPanel.players.add(this);

    }
    public String toString(){
        return "Name: "+name+" , "+"Score: "+score+" , "+"Date: "+localDate+" , "+"Time: "+localTime;
    }

}
