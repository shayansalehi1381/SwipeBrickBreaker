import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener, Runnable {
    static int row = 20;
    static int col = 10;
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
    long lastTime;
    long endTime;
    long timeLeft;
    Ball ball;
    private int mouseX, mouseY;
    private boolean isDragging = false;
    private int initialMouseX, initialMouseY;
    boolean ballGrounded = true;
    static boolean playIsON = false;
    static int level = 1;
    Brick brick;

    private int aimStartX, aimStartY; // Starting point of aiming line
    private int aimEndX, aimEndY;     // Ending point of aiming line
    private boolean isAiming = false; // Flag to indicate if aiming is in progress




    GamePanel() {
        setPreferredSize(Screen_Size);
        northBorder = new Border(0, 0, GAME_WIDTH, 10);
        southBorder = new Border(0, GAME_HEIGHT - 46, GAME_WIDTH, 10);
        rightBorder = new Border(GAME_WIDTH - 23, 0, 10, GAME_HEIGHT);
        leftBorder = new Border(0, 0, 10, GAME_HEIGHT);
        ball = new Ball();
        brick = new Brick();
        for (Brick brick1:Brick.allBricks){
            brick1.toString();
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameThread = new Thread(this);
        gameThread.start();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void move() {

        ball.move();
    }


    public void checkCollision() {
        checkCollisionForBorders(ball);
        checkCollisionForBricks(ball);
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

        if (playIsON == false){
            mouseX = e.getX();
            mouseY = e.getY();

            // Check if the mouse is pressed within the ball area
            if (mouseX >= ball.ballPosX && mouseX <= ball.ballPosX + ball.width &&
                    mouseY >= ball.ballPosY && mouseY <= ball.ballPosY + ball.height) {
                isDragging = true;
                initialMouseX = mouseX;
                initialMouseY = mouseY;
                System.out.println("you pressed the ball");
                System.out.println("ready to start the game");
                System.out.println("isPlay: "+ playIsON);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (playIsON == false){
            playIsON = true;
            if (isDragging) {
                mouseX = e.getX();
                mouseY = e.getY();

                // Calculate velocity based on the difference between initial press and release positions
                int releaseVelocityX = ((mouseX - initialMouseX) / 35); // Adjust the division factor as needed
                int releaseVelocityY = ((mouseY - initialMouseY) / 35);

                // Set the ball's velocity to the calculated velocity
                ball.xVelocity = releaseVelocityX;
                ball.yVelocity = releaseVelocityY;
                ball.savedXvelocity = ball.xVelocity;
                ball.savedYvelocity = ball.yVelocity;
                ball.move();
                isDragging = false;
                System.out.println(releaseVelocityX);
                System.out.println(releaseVelocityY);
                System.out.println("ball released");
                System.out.println("game started ");
                System.out.println("isPlay: "+playIsON);

                // Repaint the panel to remove the aiming line
                repaint();
            }

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Update current mouse position
        mouseX = e.getX();
        mouseY = e.getY();
        // Repaint the panel to update the aiming line
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void checkCollisionForBorders(Ball ball) {
        if (ball.ballPosX + ball.width >= rightBorder.x) {
            ball.xVelocity = -ball.xVelocity;
        }
        if (ball.ballPosX <= leftBorder.x + leftBorder.width) {
            ball.xVelocity = -ball.xVelocity;
        }

        if (ball.ballPosY <= northBorder.y + northBorder.height) {
            ball.yVelocity = -ball.yVelocity;
        }

        if (ball.ballPosY + ball.height >= southBorder.y) {
             ball.ballPosY = southBorder.y - ball.height;
            ball.yVelocity = 0;
            ball.xVelocity = 0;

            ballGrounded = true;
            playIsON = false;
        }
    }


    public void checkCollisionForBricks(Ball ball) {
        for (Brick brick:Brick.allBricks){

            if ((ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide))){
                ball.xVelocity = -ball.xVelocity;
            }
            if (ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide)){
                ball.yVelocity = -ball.yVelocity;
            }

        /*    if ((ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide)) && !(ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide))){
                ball.xVelocity = -ball.xVelocity;
            }

            if (!(ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide)) && (ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide))){
                ball.yVelocity = -ball.yVelocity;
            }*/


        }
    }


    public void paint(Graphics g) {
        //backGround
        g.setColor(Color.white);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        //brick
        brick.paint((Graphics2D) g);


        //border
        northBorder.paint(g);
        southBorder.paint(g);
        rightBorder.paint(g);
        leftBorder.paint(g);



        //ball
        ball.paint(g);

        // Draw aiming line if the mouse is being dragged
        if (isDragging) {
            g.setColor(Color.green); // Change color as needed

            // Draw dashed line
            Graphics2D g2d = (Graphics2D) g;
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(initialMouseX, initialMouseY, mouseX, mouseY);
        }

        g.dispose();
    }






}

