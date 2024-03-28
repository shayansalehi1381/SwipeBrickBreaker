import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener, Runnable {
    Player player;
    static Color aimColor = new Color(0x04E884);

    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 700;
    static final Dimension Screen_Size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    Border northBorder;
    Border southBorder;
    Border rightBorder;
    Border leftBorder;
    private Random randomInt = new Random();
    Thread gameThread;
    long lastTime;
    long endTime;
    long timeLeft;
    Ball firstBall;
    private int mouseX, mouseY;
    private boolean isDragging = false;
    private int initialMouseX, initialMouseY;
    boolean ballGrounded = true;
    static boolean playIsON = false;
    static int level = 0;

    boolean ballFirstTouch = false;
    int ballFirstCollisionToGround = 0;

    MapGenerator map;
    long startTime;
    long elapsedTime;
    static double score;
    double scoreFromBricks;
    long TotalTime;
    private boolean brickAdded = false;
    private boolean levelIncremented = false;
    static boolean gameOver = false;
    private boolean addNewBall = false;
    int numberOfBallItemsToBuildBalls = 0;
    ArrayList<Integer> ballitemArrayListToBuildBalls = new ArrayList<>();
    static boolean easy = false;
    static boolean medium = false;
    static boolean hard = false;
    GameFrame frame;
    GameOverPanel gameOverPanel;
    double FPS ;
    int played = 0;


    GamePanel(GameFrame frame) {
        this.frame = frame;
        player = new Player();
        setPreferredSize(Screen_Size);
        northBorder = new Border(0, 0, GAME_WIDTH, 10);
        southBorder = new Border(0, GAME_HEIGHT - 46, GAME_WIDTH, 10);
        rightBorder = new Border(GAME_WIDTH - 23, 0, 10, GAME_HEIGHT);
        leftBorder = new Border(0, 0, 10, GAME_HEIGHT);
        firstBall = new Ball(GAME_WIDTH / 2 - 20, GAME_HEIGHT);


        addMouseListener(this);
        addMouseMotionListener(this);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameThread = new Thread(this);
        gameThread.start();
        map = new MapGenerator();
        gameOverPanel = new GameOverPanel(this.frame, this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void move() {
        for (Ball ball : Ball.allBalls) {
            ball.move();
        }
    }


    public void checkCollision() {
        for (int i = 0; i < Ball.allBalls.size(); i++) {
            Ball ball = Ball.allBalls.get(i);
            checkCollisionForBorders(ball);
            checkCollisionForBricks(ball);
            checkCollisionForItemBall(ball);
        }

    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
        endTime = System.currentTimeMillis();
         FPS = 40.0;
       played++;
        System.out.println("played: "+played);
        double ns = 1000000000 / FPS;
        double delta = 0;
        timeLeft = endTime - System.currentTimeMillis();
        while (!gameOver) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {

                move();
                checkCollision();
                brickSlowMove();
                GameOver();
                ScoreCalculator();
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
        for (int i = 0; i < Ball.allBalls.size(); i++) {
            Ball ball = Ball.allBalls.get(i);
            // Check if the mouse is pressed within the ball area
            if (mouseX >= ball.ballPosX && mouseX <= ball.ballPosX + ball.width &&
                    mouseY >= ball.ballPosY && mouseY <= ball.ballPosY + ball.height) {
                playIsON = false;
                isDragging = true;
                ballFirstTouch = true;
                initialMouseX = mouseX;
                initialMouseY = mouseY;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (playIsON == false) {
            if (ballFirstTouch == true) {
                playIsON = true;
                if (isDragging) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    // Calculate velocity based on the difference between initial press and release positions
                    int releaseVelocityX = ((mouseX - initialMouseX) / 70); // Adjust the division factor as needed
                    int releaseVelocityY = ((mouseY - initialMouseY) / 70);
                    for (int i = 0; i < Ball.allBalls.size(); i++) {
                        Ball ball = Ball.allBalls.get(i);
                        // Set the ball's velocity to the calculated velocity
                        ball.xVelocity = releaseVelocityX;
                        ball.yVelocity = releaseVelocityY;
                        ball.savedXvelocity = ball.xVelocity;
                        ball.savedYvelocity = ball.yVelocity;
                        ball.move();
                        ballGrounded = false;
                        isDragging = false;

                        // Repaint the panel to remove the aiming line
                        repaint();
                    }
                }
            }
        }
        levelIncremented = false;
        brickAdded = false;
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
            ballFirstCollisionToGround++;
            ballGrounded = true;
            playIsON = false;
            if (!levelIncremented) {
                level++;
                bricksSuddenMove();
                levelIncremented = true;
            }
            if (!brickAdded) {
                // Add a brick only if one hasn't been added already
                map.makeRandomBricks();
                brickAdded = true;
                new Ballitem();
            }
            if (addNewBall) {
                Ball ball1 = Ball.getBallById(Ball.allBalls.size());
                for (int i = 0; i < numberOfBallItemsToBuildBalls; i++) {
                    new Ball(ball1.ballPosX, ball1.ballPosY);
                }
                addNewBall = false;

            }
        }
    }


    public void checkCollisionForItemBall(Ball ball) {
        Iterator<Ballitem> iterator = Ballitem.ballitems.iterator();
        for (Item item : Item.items) {
            if (item instanceof Ballitem) {
                if (ball.intersects(item) == true) {


                    // System.out.println("hi");
                    ballitemArrayListToBuildBalls.add(1);
                    ((Ballitem) item).collidedWithBall = true;
                    Ballitem.ballitems.remove(item);

                }
                if (((Ballitem) item).collidedWithBall == true) {
                    if (ballGrounded) {
                        addNewBall = true;
                        ((Ballitem) item).collidedWithBall = false;
                    }
                }
            }
        }
        numberOfBallItemsToBuildBalls = ballitemArrayListToBuildBalls.size();
       // System.out.println(ballitemArrayListToBuildBalls.size());
        ballitemArrayListToBuildBalls.clear();
    }


    public void checkCollisionForBricks(Ball ball) {
        Iterator<Brick> iterator = Brick.allBricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if ((ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide))) {
                ball.xVelocity = -ball.xVelocity;
                brick.value--;


            }
            if (ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide)) {
                ball.yVelocity = -ball.yVelocity;
                brick.value--;


            }
            if (brick.value <= 0) {
                scoreFromBricks += brick.firstValue;
                iterator.remove();

            }
        }

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

        //bricks
        map.paint((Graphics2D) g);


        //border
        northBorder.paint(g);
        southBorder.paint(g);
        rightBorder.paint(g);
        leftBorder.paint(g);


        //ballItem
        for (Ballitem ballitem : Ballitem.ballitems) {
            ballitem.paint(g);
        }


        //ball
        for (int i = 0; i < Ball.allBalls.size(); i++) {
            Ball ball = Ball.allBalls.get(i);
            ball.paint(g);
        }


        // Draw aiming line if the mouse is being dragged
        if (isDragging) {
            g.setColor(aimColor); // Change color as needed

            // Draw dashed line
            Graphics2D g2d = (Graphics2D) g;
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(initialMouseX, initialMouseY, mouseX, mouseY);
        }


        //score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + (int) score, 30, 50);

        // draw Game Level
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: " + GamePanel.level, 30, 30);

        //draw number of balls
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Ball Numbers: " + Ball.allBalls.size(), 350, 50);

        g.dispose();
    }


    public void bricksSuddenMove() {
        for (Brick brick : Brick.allBricks) {
            brick.brickYpos += brick.height;
            brick.rightSide.yPos += brick.height;
            brick.leftSide.yPos += brick.height;
            brick.topSide.yPos += brick.height;
            brick.bottomSide.yPos += brick.height;
        }
    }

    public void brickSlowMove() {
        for (Brick brick : Brick.allBricks) {
            if (GamePanel.playIsON == false) {
                brick.leftSide.yPos += Brick.speed;
                brick.rightSide.yPos += Brick.speed;
                brick.topSide.yPos += Brick.speed;
                brick.bottomSide.yPos += Brick.speed;
                brick.brickYpos += Brick.speed;
            }
        }
    }

    public void GameOver() {
        for (Brick brick : Brick.allBricks) {
            if (brick.brickYpos + brick.height >= southBorder.y) {
                gameOver = true;
                if (gameOver == true) {

                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(gameOverPanel);
                    frame.revalidate();
                    frame.repaint();
                }


            }
        }
    }

    public void resetGame() {
        // Clear all lists and reset necessary variables
        Ball.allBalls.clear();
        Brick.allBricks.clear();
        Ballitem.ballitems.clear();
        level = -1;
        score = 0;
        ballFirstCollisionToGround = 0;
        scoreFromBricks = 0;
        numberOfBallItemsToBuildBalls = 0;
        isDragging = false;
        ballGrounded = true;
        playIsON = false;
        ballFirstTouch = false;
        brickAdded = false;
        levelIncremented = false;
        addNewBall = false;
        gameOver = false;

        stopGameThread();

        // Reset time related variables
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
        endTime = System.currentTimeMillis();
        elapsedTime = 0;

        startGameThread();
    }


    public String formatTime(long milliseconds) {
        // Convert milliseconds to seconds and minutes
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        TotalTime = totalSeconds;
        // Format the time string
        return String.format("%02d:%02d", minutes, seconds);
    }


    public int ScoreCalculator() {
        score = (scoreFromBricks - TotalTime / 4);
        return (int) score;
    }





    public void stopGameThread() {
        // Check if the game thread is running and interrupt it
     /*   if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            gameThread = null;
        }*/


        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();

        }
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();




    }




}

