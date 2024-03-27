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
     int width = 15;
     int height = 15;

    int savedYvelocity ;
    int savedXvelocity ;
   static ArrayList<Ball> allBalls = new ArrayList<>();
    public Ball(){
        super();
        ID = nextID++;
        ballPosY = GamePanel.GAME_HEIGHT - 69;
        ballPosX = GamePanel.GAME_WIDTH/2 - 20;
        allBalls.add(this);
        random = new Random();
        balls.add(this);


    }

    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillOval(ballPosX,ballPosY,width,height);
    }
    public void move(){
        ballPosX+=xVelocity;
        ballPosY+=yVelocity;

    }
    public boolean intersects(Side side) {
        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, width, height);
        Rectangle sideRect = new Rectangle(side.x, side.yPos, side.width, side.height);
        return ballRect.intersects(sideRect);
    }



    public String toString(){
        return "x: "+ ballPosX +" "+"y:"+ballPosY+" "+"width:"+width+" "+"height: "+height +" ID:"+ID;
    }




}
