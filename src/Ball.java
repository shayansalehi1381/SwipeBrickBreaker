import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Ball extends Rectangle  {
    int ID;
    Random random;

     int ballPosX ;
     int ballPosY ;
     int xVelocity;
     int yVelocity = -5;
     int speed = 5;
     int width = 20;
     int height = 20;
   static ArrayList<Ball> allBalls = new ArrayList<>();
    public Ball(){
        super();
        ballPosY = GamePanel.GAME_HEIGHT - 69;
        ballPosX = GamePanel.GAME_WIDTH/2 - 20;
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
    }

    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillOval(ballPosX,ballPosY,20,20);
    }

    public void setXdirection(int randomXDirection){
        xVelocity=randomXDirection;

    }

    public void move(){
        ballPosX+=xVelocity;
        ballPosY+=yVelocity;

    }

    public String toString(){
        return "x: "+ ballPosX +" "+"y:"+ballPosY+" "+"width:"+width+" "+"height: "+height;
    }


}
