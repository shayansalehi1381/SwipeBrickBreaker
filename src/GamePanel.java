import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements  MouseMotionListener,MouseListener,ActionListener, Runnable {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 700;
    static final Dimension Screen_Size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    boolean play = false;
    Border northBorder;
    Border southBorder;
    Border rightBorder;
    Border leftBorder;
    private Random randomInt = new Random();
    Thread gameThread;
    int randomNumber = randomInt.nextInt(3) + 3;
    int randomSignedNumber = randomInt.nextBoolean() ? randomNumber : -randomNumber;
    private int ballXdir = randomSignedNumber;
    private int ballYdir = randomSignedNumber;
    private MapGenerator map;
    long lastTime;
    long endTime;
    long timeLeft;
    Ball ball;


    GamePanel() {
        setPreferredSize(Screen_Size);
        northBorder = new Border(0,0,GAME_WIDTH,10);
        southBorder = new Border(0,GAME_HEIGHT-46,GAME_WIDTH,10);
        rightBorder = new Border(GAME_WIDTH-23,0,10,GAME_HEIGHT);
        leftBorder = new Border(0,0,10,GAME_HEIGHT);
        map = new MapGenerator(3, 7);
        ball = new Ball();

        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameThread = new Thread(this);
        gameThread.start();


    }


    public void paint(Graphics g) {
        //backGround
        g.setColor(Color.white);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);


        //border
        northBorder.paint(g);
        southBorder.paint(g);
        rightBorder.paint(g);
        leftBorder.paint(g);

        //map
        map.draw((Graphics2D) g);

        //ball
        ball.paint(g);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void move() {

        ball.move();
    }






    public void checkCollision(){

    }

    @Override
    public void run() {
        lastTime = System.nanoTime();
        endTime = System.currentTimeMillis() + 120000;
        double FPS = 60.0;
        double ns = 1000000000 / FPS;
        double delta = 0;
        timeLeft = endTime - System.currentTimeMillis();
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                 move();
                 checkCollision();

                repaint();
                delta--;


            }
        }
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
        System.out.println("ball touched");
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
//        System.out.println(mouseX);
//        System.out.println(mouseY);
//        System.out.println(ball.ballPosY+" by");
//        System.out.println(ball.ballPosX+" bx");
        if (mouseX>= ball.ballPosX && mouseX <=ball.ballPosX+20){
            if (mouseY >= ball.ballPosY && mouseY <= ball.ballPosY+20 ){
                System.out.println("you are on the ball");
            }
        }

    }
}

