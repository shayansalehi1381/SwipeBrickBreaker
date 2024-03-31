import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Player {
    int score;
    String name;
    LocalDate localDate;
    LocalTime localTime;
    public Player(){
        score = (int)GamePanel.score;
        name = GamePrepPanel.textField.getText();
        localDate = LocalDate.now();
        localTime = LocalTime.now();
        HistoryPanel.players.add(this);


    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        return localDate.format(formatter);
    }
    public String toString(){
        return "Name: "+name+" , "+"Score: "+score+" , "+"Date: "+localDate+" , "+"Time: "+localTime;
    }

}
