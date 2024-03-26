import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Brick extends Rectangle{
    static ArrayList<Brick> allBricks = new ArrayList<>();
    Random random;
    Side topSide;
    Side bottomSide;
    Side leftSide;
    Side rightSide;
   // public Brick [][] bricks = new Brick[GamePanel.row][GamePanel.col];
    int width =58;
    int height =34;
    int rowNum = 0;
    int colNum = 0;
    int brickXpos;
    int brickYpos;

    int speed;
    int value ;
    int ID = 0;

    static int nextID = 1;

    public Brick(int randomRow,int randomCol){
        value = GamePanel.level;
        rowNum = randomRow;
        colNum = randomCol;
        brickXpos = colNum*width;
        brickYpos = rowNum*height;
        allBricks.add(this);
        topSide = new Side(brickXpos, brickYpos-3, width+3, 1);
        bottomSide = new Side(brickXpos, brickYpos+3 + height , width+3, 1);
        leftSide = new Side(brickXpos-3, brickYpos, 1, height+3);
        rightSide = new Side(brickXpos + width+3, brickYpos, 1, height+3);
        ID = nextID++;
        System.out.println(this);
    }

    public void paint(Graphics2D g){
        if (value > 0){
            g.setColor(Color.red);
            g.fillRect(brickXpos,brickYpos,width,height);
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.white);
            g.drawRect(brickXpos ,brickYpos,width,height);
            g.setColor(Color.white);
            g.setFont(new Font("Consolas",Font.BOLD,15));
            g.drawString(String.valueOf(value),colNum*width + 26,rowNum*height + 21);


            topSide.paint(g);
            bottomSide.paint(g);
            leftSide.paint(g);
            rightSide.paint(g);
        }
    }


    public void bricksSlowMove(){

    }

    public void bricksSuddenMove(){

    }



    public String toString(){
        return "row:"+rowNum+" "+"col:"+colNum +" "+"ID:"+ID;
    }
}
