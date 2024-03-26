import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Ball extends Rectangle  {
    int ID = 0;
    static int nextID = 1;
    static ArrayList<Ball> balls = new ArrayList<>();
    Random random;

     int ballPosX ;
     int ballPosY ;

     int xVelocity;
     int yVelocity ;
     int speed = 5;
     int width = 20;
     int height = 20;

    int savedYvelocity ;
    int savedXvelocity ;
   static ArrayList<Ball> allBalls = new ArrayList<>();
    public Ball(){
        super();

        ballPosY = GamePanel.GAME_HEIGHT - 69;
        ballPosX = GamePanel.GAME_WIDTH/2 - 20;
        allBalls.add(this);
        random = new Random();
        balls.add(this);
        ID = nextID++;

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
    public boolean intersects(Side side) {
        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, width, height);
        Rectangle sideRect = new Rectangle(side.x, side.y, side.width, side.height);
        return ballRect.intersects(sideRect);
    }



    public String toString(){
        return "x: "+ ballPosX +" "+"y:"+ballPosY+" "+"width:"+width+" "+"height: "+height +" ID:"+ID;
    }


}
