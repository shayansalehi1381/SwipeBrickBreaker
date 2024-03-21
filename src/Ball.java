import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Ball extends Rectangle  {
    int ID;
    Random random;

     int ballPosX = GamePanel.GAME_WIDTH/2 - 20;
     int ballPosY = GamePanel.GAME_HEIGHT - 69;
     int xVelocity;
     int yVelocity;
     int speed = 5;
   static ArrayList<Ball> allBalls = new ArrayList<>();
    public Ball(){

        allBalls.add(this);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection==0){
            randomXDirection--;
            setXdirection(randomXDirection * speed);
        }
        else {
            setXdirection(randomXDirection * speed);
        }
        int randomYDirection = random.nextInt(2);
        if (randomYDirection==0){
            randomYDirection--;
            setYdirection(randomYDirection * speed);
        }
        else {
            setYdirection(randomYDirection * speed);
        }

    }

    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillOval(ballPosX,ballPosY,20,20);
    }

    public void setXdirection(int randomXDirection){
        xVelocity=randomXDirection;

    }
    public void setYdirection(int randomYDirection){
        yVelocity=randomYDirection;
    }
    public void move(){
        x+=xVelocity;
        y+=yVelocity;
    }


}
