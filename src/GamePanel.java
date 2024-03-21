import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener,KeyListener, ActionListener {
    private int score;
    private Random randomInt = new Random();
    private  boolean play;
    private  int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int playerSpeed = 20;
    private int ballPosX = randomInt.nextInt(550)+20;
    private int ballPosY = 350;
    int randomNumber = randomInt.nextInt(3) + 3;
    int randomSignedNumber = randomInt.nextBoolean() ? randomNumber : -randomNumber;
    private int ballXdir = randomSignedNumber;
    private int ballYdir = randomSignedNumber;
    private MapGenerator map;

    GamePanel(){

        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();

    }


    public void paint(Graphics g){
        //backGround
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);


        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0,4,692);
        g.fillRect(0,0,592,5);
        g.fillRect(583,0,3,692);

        //map
        map.draw((Graphics2D) g);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX,550,100,8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);


        g.dispose();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play){
            if (new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

         A:   for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0){
                        int BrickX = j*map.brickWidth + 25;
                        int BrickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(BrickX,BrickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if (ballPosX + 19 <=brickRect.x || ballPosX + 1 >= brickRect.x +brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }

                    }
                }
            }
            ballPosX+=ballXdir;
            ballPosY+=ballYdir;
            if (ballPosY < 0){
             ballYdir = -ballYdir;
            }
            if (ballPosX < 0){
                ballXdir = -ballXdir;
            }
            if (ballPosX > 570){
                ballXdir = -ballXdir;
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                if (playerX >= 490){
                    playerX = 490;
                }
                else movieRight();
            }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
            if (playerX <= 10){
                playerX = 10;
            }
            else movieLeft();
        }
    }

    public void movieRight(){
        play = true;
        playerX+=playerSpeed;
    }

    public void movieLeft(){
        play = true;
        playerX-=playerSpeed;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
