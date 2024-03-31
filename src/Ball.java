import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ball extends Rectangle  {
    int ID = 0;
    static int nextID = 1;
    Random random;

     int ballPosX ;
     int ballPosY ;

     int xVelocity;
     int yVelocity ;
     int speed = 5;
     int width = 15;
     int height = 15;
     boolean power2 = false;


   static ArrayList<Ball> allBalls = new ArrayList<>();
   static ArrayList<Integer> IDs = new ArrayList<>();
  static Color color = new Color(0x04E884);
    public Ball(int ballPosX,int ballPosY){
        super();
        ID = nextID++;
        this.ballPosY = ballPosY;
        this.ballPosX = ballPosX;
        allBalls.add(this);
        //random = new Random();
        IDs.add(ID);
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(ballPosX,ballPosY,width,height);
        g.setColor(Color.black);


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

    public boolean intersects(Item item){
        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,width,height);
        Rectangle itemRect = new Rectangle(item.xPos,item.yPos,item.width,item.height);
        return  ballRect.intersects(itemRect);
    }



    public String toString(){
        return "x: "+ ballPosX +" "+"y:"+ballPosY+" "+"width:"+width+" "+"height: "+height +" ID:"+ID;
    }

    public static Ball getBallById(int ID) {
        for (Ball ball : allBalls) {
            if (ball.ID == ID) {
                return ball;
            }
        }
        // Return null if no ball with the given ID is found
        return null;
    }





}
