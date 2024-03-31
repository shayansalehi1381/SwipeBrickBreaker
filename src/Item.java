import com.sun.tools.javac.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Item extends Rectangle {
   public int width = 20;
    public int height = 20;
    Random random;
   public int xPos;
    public int yPos;
    static int speed = 1;
    public boolean collidedWithBall = false;

    public static ArrayList<Item> items = new ArrayList<>();

    public Item(){
        random = new Random();
        xPos = x;
        yPos = y;
        // Retrieve the dimensions of the game area
        int gameWidth = GamePanel.GAME_WIDTH;
        int gameHeight = GamePanel.GAME_HEIGHT;

        // Generate random x and y positions within the game area
        do {
            xPos = random.nextInt(gameWidth - width -10);
            yPos = random.nextInt(gameHeight/4 )+10;
        } while (collidesWithBrick(xPos, yPos)); // Check if the position collides with any brick

        setBounds(xPos, yPos, width, height);
        items.add(this);
    }

    private boolean collidesWithBrick(int x, int y) {
        for (Brick brick : Brick.allBricks) {
            if (brick.intersects(new Rectangle(x, y, width, height))) {
                return true;
            }
        }
        return false;
    }





    public boolean intersects(Ball ball){
        Rectangle ballRect = new Rectangle(ball.ballPosX,ball.ballPosY,ball.width,ball.height);
        Rectangle itemRect = new Rectangle(xPos,yPos,width,height);
        return  ballRect.intersects(itemRect);
    }



}
