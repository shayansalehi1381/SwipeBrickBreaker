import java.awt.*;
import java.util.ArrayList;

public class Ball extends Rectangle {
    int number;
     int ballPosX = 120;
     int ballPosY = 350;
   static ArrayList<Ball> allBalls = new ArrayList<>();
    public Ball(){
        allBalls.add(this);
    }

    public void paint(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(ballPosX,ballPosY,20,20);
    }

}
