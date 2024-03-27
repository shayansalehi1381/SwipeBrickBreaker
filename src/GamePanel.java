import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
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
   // Brick brick;
    boolean GameOver = false;
    boolean ballFirstTouch = false;
    int ballFirstCollisionToGround = 0;
    private boolean levelIncremented = false;
    MapGenerator map ;
    long startTime;
    long elapsedTime;


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

        addMouseListener(this);
        addMouseMotionListener(this);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameThread = new Thread(this);
        gameThread.start();
        map = new MapGenerator();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void move() {
        for (Brick brick:Brick.allBricks){
            brick.brickSlowMove();
        }
        bricksSuddenMove();
        ball.move();
    }


    public void checkCollision() {

        checkCollisionForBorders(ball);
        checkCollisionForBricks(ball);
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
        endTime = System.currentTimeMillis();
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
                GameOver();
                repaint();
                delta--;
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


            mouseX = e.getX();
            mouseY = e.getY();

            // Check if the mouse is pressed within the ball area
            if (mouseX >= ball.ballPosX && mouseX <= ball.ballPosX + ball.width &&
                    mouseY >= ball.ballPosY && mouseY <= ball.ballPosY + ball.height) {
                playIsON = false;
                isDragging = true;
                ballFirstTouch = true;
                initialMouseX = mouseX;
                initialMouseY = mouseY;
           /*     System.out.println("you pressed the ball");
                System.out.println("ready to start the game");
                System.out.println("isPlay: "+ playIsON);*/
            }



    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (playIsON == false){
            if (ballFirstTouch == true){
                playIsON = true;
                if (isDragging) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    // Calculate velocity based on the difference between initial press and release positions
                    int releaseVelocityX = ((mouseX - initialMouseX) / 45); // Adjust the division factor as needed
                    int releaseVelocityY = ((mouseY - initialMouseY) / 35);

                    // Set the ball's velocity to the calculated velocity
                    ball.xVelocity = releaseVelocityX;
                    ball.yVelocity = releaseVelocityY;
                    ball.savedXvelocity = ball.xVelocity;
                    ball.savedYvelocity = ball.yVelocity;
                    ball.move();
                    ballGrounded = false;
                    levelIncremented = false;
                    isDragging = false;

                    // Repaint the panel to remove the aiming line
                    repaint();
                }
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
            ballFirstCollisionToGround ++;
            ballGrounded = true;
            levelIncremented = true;
            playIsON = false;
        }
    }


    public void checkCollisionForBricks(Ball ball) {
        Iterator<Brick> iterator = Brick.allBricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if ((ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide))){
                ball.xVelocity = -ball.xVelocity;
                brick.value--;

            }
            if (ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide)){
                ball.yVelocity = -ball.yVelocity;
                brick.value--;

            }
            if (brick.value <= 0){
                iterator.remove();

            }
        }
        checkLevel();
    }


    public void paint(Graphics g) {
        //backGround
        g.setColor(Color.white);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        //time Passed
        String timeString = "Time Passed: " + formatTime(elapsedTime);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(timeString, 350, 30);

        // draw Game Level
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("Level: "+GamePanel.level,50,30);


        //bricks
        map.paint((Graphics2D) g);


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



    public void checkLevel(){
        if (Brick.allBricks.size() <= 0){
         if (ballGrounded){
             GamePanel.level += 1;
             map = new MapGenerator();
         }
        }
    }




    public void bricksSuddenMove() {

    }

    public void GameOver(){
        for (Brick brick:Brick.allBricks){
            if (brick.brickYpos + brick.height >= southBorder.y){
                System.out.println("game over");
            }
        }
    }


    public String formatTime(long milliseconds) {
        // Convert milliseconds to seconds and minutes
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        // Format the time string
        return String.format("%02d:%02d", minutes, seconds);
    }

}

