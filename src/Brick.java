import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Brick extends Rectangle{
    static ArrayList<Brick> allBricks = new ArrayList<>();
    Side topSide;
    Side bottomSide;
    Side leftSide;
    Side rightSide;
    int width =96;
    int height =40;
    int rowNum = 0;
    int colNum = 0;
    int brickXpos;
    int brickYpos;

    static int speed = 1;
    int value ;
    int ID = 0;
    int firstValue;

    static int nextID = 1;

    public Brick(int randomRow,int randomCol){
        value = GamePanel.level;
        firstValue = value;
        rowNum = randomRow;
        colNum = randomCol;
        brickXpos = colNum*width;
        brickYpos = rowNum*height;
        allBricks.add(this);
        topSide = new Side(brickXpos + 5, brickYpos, width - 10, 1);
        bottomSide = new Side(brickXpos + 5, brickYpos + height -2 , width - 10, 1);
        leftSide = new Side(brickXpos +1, brickYpos, 1, height);
        rightSide = new Side(brickXpos -1 + width, brickYpos, 1, height);
        ID = nextID++;

    }

    public void paint(Graphics2D g){
        if (value > 0){
            g.setColor(Color.red);
            g.fillRect(brickXpos, (int) brickYpos,width,height);
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.white);
            g.drawRect(brickXpos , (int) brickYpos,width,height);
            g.setColor(Color.white);
            g.setFont(new Font("Consolas",Font.BOLD,15));
            g.drawString(String.valueOf(value),brickXpos + width/2 -5, (int) (brickYpos + height/2)+5);


            topSide.paint(g);
            bottomSide.paint(g);
            leftSide.paint(g);
            rightSide.paint(g);
        }
    }








    public String toString(){
        return "row:"+rowNum+" "+"col:"+colNum +" "+"ID:"+ID;
    }
}
