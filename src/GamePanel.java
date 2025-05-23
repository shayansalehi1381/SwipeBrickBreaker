

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener, Runnable {


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
  //  int numberOfBallItemsToBuildBalls = 0;
    ArrayList<Integer> ballitemArrayListToBuildBalls = new ArrayList<>();
    static boolean medium = false;
    static boolean hard = false;
    GameFrame frame;
    GameOverPanel gameOverPanel;
    double FPS;
    int played = 0;
    private boolean paused = false;
    static int scoreBeforeResetGame;
    boolean firstBallCollision = false;
    private boolean executed = false;
    private Map<Integer, Long> ballShotTimes = new HashMap<>();

    // Define a constant for the delay between shots (in milliseconds)
    private static final int BALL_MOVE_DELAY_MS = 80;
    boolean vertigoCollision = false;
     static int Resualt = 0;
     int panelID = 0;
    static int nextID = 1;
    static boolean Aiming = true;
    static boolean savingHistory = false;



    GamePanel(GameFrame frame) {
        frame.gamePanels.add(this);
        panelID=nextID++;
        setLayout(null);



        this.frame = frame;
        setPreferredSize(Screen_Size);
        northBorder = new Border(0, 0, GAME_WIDTH, 10);
        southBorder = new Border(0, GAME_HEIGHT - 40, GAME_WIDTH, 50);
        rightBorder = new Border(GAME_WIDTH - 10, 0, 10, GAME_HEIGHT);
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


        // Set up key bindings for pausing and resuming the game
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pauseGame");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "resumeGame");
        actionMap.put("pauseGame", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });
        actionMap.put("resumeGame", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });


    }


    private void pauseGame() {
        paused = true;
        stopGameThread();
        // Additional pause-related actions if needed
    }

    // Method to resume the game
    private void resumeGame() {
        paused = false;
        startGameThread();
        // Additional resume-related actions if needed
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void move() {
        long currentTime = System.currentTimeMillis();

        // Iterate through all the balls
        for (int i = Ball.allBalls.size(); i >= 1; i--) {
            Ball ball = Ball.allBalls.get(i - 1); // Adjust index to start from 0

            // Check if enough time has elapsed since the ball was shot
            if (ballShotTimes.containsKey(i) && currentTime - ballShotTimes.get(i) >= BALL_MOVE_DELAY_MS) {
                ball.move();

                // Check if there's a next ball
                if (i > 1) {
                    // If the previous ball has finished moving, shoot the next ball
                    long previousBallShotTime = ballShotTimes.get(i);
                    long nextBallShotTime = previousBallShotTime + BALL_MOVE_DELAY_MS;
                    if (currentTime >= nextBallShotTime) {
                        ballShotTimes.put(i - 1, nextBallShotTime);
                    }
                }
            }
        }
    }


    public void checkCollision() {
        for (int i = 0; i < Ball.allBalls.size(); i++) {
            Ball ball = Ball.allBalls.get(i);
            checkCollisionForBorders(ball);
            checkCollisionForBricks(ball);
            checkCollisionForItemBall(ball);
            checkCoillisionForSpeedItem(ball);
            checkCollisionForPowerItem(ball);
            checkCollisionForVertigoItems(ball);
        }

    }

    @Override
    public void run() {

        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
        endTime = System.currentTimeMillis();
        FPS = 60.0;
        played++;
        double ns = 1000000000 / FPS;
        double delta = 0;
        timeLeft = endTime - System.currentTimeMillis();
        while (!gameOver) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (!paused) {
                    move();
                    checkCollision();
                    brickSlowMove();
                    GameOver();
                    ScoreCalculator();
                    repaint();
                    delta--;
                    elapsedTime = System.currentTimeMillis() - startTime;
                }
                try {
                    Thread.sleep((long) (1000 / FPS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
                //    ballFirstTouch = true;
                initialMouseX = mouseX;
                initialMouseY = mouseY;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (playIsON == false) {
            //    if (ballFirstTouch == true) {
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

                    ball.move();
                    ballGrounded = false;
                    isDragging = false;

                    // Repaint the panel to remove the aiming line
                    repaint();
                }
            }
            //   }
        }
        levelIncremented = false;
        brickAdded = false;
        // Add the current time when the ball is shot for the current ball ID
        int currentBallID = Ball.allBalls.size();
        long currentTime = System.currentTimeMillis();
        ballShotTimes.put(currentBallID, currentTime);
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
            if (ball.ID != 1) {
                ball.ballPosX = firstBall.ballPosX;
                ball.ballPosY = firstBall.ballPosY;
            }

            //   GAME_WIDTH / 2 - 20, GAME_HEIGHT


            ballGrounded = true;
            playIsON = false;


            if (!levelIncremented) {
                level++;

                bricksSuddenMove();

                if (level > 1) {
                    firstBallCollision = true;
                    new Ball(firstBall.ballPosX, firstBall.ballPosY);

                }
                levelIncremented = true;
            }
            if (!brickAdded) {
                // Add a brick only if one hasn't been added already
                map.makeRandomBricks();
                brickAdded = true;
                if (medium){
                    if ( level %2 == 0){
                        new Ballitem();
                    }
                    if (level % 5 == 0) {
                        new SpeedItem();
                    }
                    if (level % 7 == 0) {
                        new PowerItem();
                    }

                    if (level % 4 == 0) {
                        new VertigoItem();
                    }
                }
                else if (hard){
                    if ( level %3 == 0){
                        new Ballitem();
                    }
                    if (level % 6 == 0) {
                        new SpeedItem();
                    }
                    if (level % 8 == 0) {
                        new PowerItem();
                    }

                    if (level % 3 == 0) {
                        new VertigoItem();
                    }
                }
                else {

                        new Ballitem();

                    if (level % 4 == 0) {
                        new SpeedItem();
                    }
                    if (level % 5 == 0) {
                        new PowerItem();
                    }

                    if (level % 6 == 0) {
                        new VertigoItem();
                    }
                }
            }
        }
    }


    public void checkCollisionForItemBall(Ball ball) {
        for (int i = 0; i < Ballitem.ballitems.size(); i++) {
            Ballitem ballitem = Ballitem.ballitems.get(i);
            if (ball.intersects(ballitem) == true) {

                ballitem.collidedWithBall = true;
                Ballitem.ballitems.remove(ballitem);
            }
            if (ballitem.collidedWithBall == true) {
                Ball ball1 = new Ball(firstBall.ballPosX, firstBall.ballPosY);
                ball1.xVelocity = firstBall.xVelocity;
                ball1.yVelocity = firstBall.yVelocity;
            }
        }
    }

    public void checkCollisionForPowerItem(Ball ball) {
        for (int i = 0; i < PowerItem.powerItems.size(); i++) {
            PowerItem powerItem = PowerItem.powerItems.get(i);
            if (ball.intersects(powerItem) == true) {
                powerItem.collidedWithBall = true;
                PowerItem.powerItems.remove(powerItem);
            }
            if (powerItem.collidedWithBall == true) {
                for (Ball ball1 : Ball.allBalls) {
                    ball1.power2 = true;
                }
            }
        }
    }

    public void checkCoillisionForSpeedItem(Ball ball) {
        Iterator<SpeedItem> iterator = SpeedItem.speedItems.iterator();
        while (iterator.hasNext()) {
            SpeedItem speedItem = iterator.next();
            if (ball.intersects(speedItem)) {
                speedItem.collidedWithBall = true;
                iterator.remove(); // Remove the SpeedItem from the list

                // Apply speed boost to the ball
                for (Ball ball1 : Ball.allBalls) {
                    ball1.xVelocity *= 2;
                    ball1.yVelocity *= 2;
                }

                // Schedule to revert the velocity after 15 seconds
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ball.xVelocity /= 2;
                        ball.yVelocity /= 2;// Reset the velocity to its original value
                    }
                }, 15000); // 15 seconds in milliseconds
            }
        }
    }


    public void checkCollisionForVertigoItems(Ball ball) {
        for (int i = 0; i < VertigoItem.vertigoItems.size(); i++) {
            VertigoItem vertigoItem = VertigoItem.vertigoItems.get(i);
            if (ball.intersects(vertigoItem) == true) {
                vertigoItem.collidedWithBall = true;
                VertigoItem.vertigoItems.remove(vertigoItem);
                vertigoCollision = true;

                // Schedule to reset the aiming line after 5 seconds
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        vertigoCollision = false; // Reset the flag after 5 seconds
                    }
                }, 5000); // 5 seconds in milliseconds
            }
        }
    }


    public void checkCollisionForBricks(Ball ball) {
        Iterator<Brick> iterator = Brick.allBricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if ((ball.intersects(brick.rightSide) || ball.intersects(brick.leftSide))) {
                ball.xVelocity = -ball.xVelocity;
                if (ball.power2 == true) {
                    brick.value -= 2;
                } else
                    brick.value--;


            }
            if (ball.intersects(brick.topSide) || ball.intersects(brick.bottomSide)) {
                ball.yVelocity = -ball.yVelocity;
                if (ball.power2 == true) {
                    brick.value -= 2;
                } else
                    brick.value--;


            }
            if (brick.value <= 0) {
                scoreFromBricks += brick.firstValue;
                iterator.remove();

            }
        }

    }


    public void paint(Graphics g) {

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


        //****************************************************************************************
        items:


        //RegularItems:

        for (Ballitem ballitem : Ballitem.ballitems) {
            ballitem.paint(g);
        }
        for (SpeedItem speedItem : SpeedItem.speedItems) {
            speedItem.paint(g);
        }
        for (PowerItem powerItem : PowerItem.powerItems) {
            powerItem.paint(g);
        }
        for (VertigoItem vertigoItem : VertigoItem.vertigoItems) {
            vertigoItem.paint(g);
        }


        //******************************************************************************************
        //ball
        for (int i = 0; i < Ball.allBalls.size(); i++) {
            Ball ball = Ball.allBalls.get(i);
            ball.paint(g);

        }
        for (int i =0; i<Ball.allBalls.size(); i++){
            Ball ball = Ball.allBalls.get(i);
            g.setFont(new Font("Arial",Font.BOLD,8));
            g.drawString(String.valueOf(i+1),ball.ballPosX+4,ball.ballPosY+10);
        }


        // Draw aiming line if the mouse is being dragged
        if (Aiming){
            if (isDragging) {
                g.setColor(aimColor); // Change color as needed

                // Draw dashed line
                Graphics2D g2d = (Graphics2D) g;
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                g2d.setStroke(dashed);
                if (vertigoCollision) {
                    int xVertigo = -mouseX;
                    int yVertigo = -mouseY;
                    g2d.drawLine(initialMouseX, initialMouseY, xVertigo, yVertigo);
                    // vertigoCollision = false;
                } else
                    g2d.drawLine(initialMouseX, initialMouseY, mouseX, mouseY);


            }
        }



        //score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + (int) score, 30, 50);

        //pause & resume
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Press 'P' to pause the game", 150, 20);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Press 'R' to resume the game", 150, 35);

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
        boolean allBallsGrounded = false;
        for (Ball ball : Ball.allBalls) {
            if (ball.ballPosY + ball.height >= southBorder.y) {
                allBallsGrounded = true;
            }
        }
        if (allBallsGrounded) {
            for (Brick brick : Brick.allBricks) {
                brick.brickYpos += brick.height;
                brick.rightSide.yPos += brick.height;
                brick.leftSide.yPos += brick.height;
                brick.topSide.yPos += brick.height;
                brick.bottomSide.yPos += brick.height;
            }
        }

    }

    public void brickSlowMove() {
        boolean allBallsGrounded = false;
        for (Ball ball : Ball.allBalls) {
            if (ball.ballPosY + ball.height >= southBorder.y) {
                allBallsGrounded = true;
            }
        }
        for (Brick brick : Brick.allBricks) {
            if (allBallsGrounded) {
                brick.leftSide.yPos += Brick.speed;
                brick.rightSide.yPos += Brick.speed;
                brick.topSide.yPos += Brick.speed;
                brick.bottomSide.yPos += Brick.speed;
                brick.brickYpos += Brick.speed;
            }
        }
    }

    public void GameOver() {
        if (gameOver == false){
            for (Brick brick : Brick.allBricks) {
                if (brick.brickYpos + brick.height >= southBorder.y) {

                    gameOver = true;
                    break;
                }

            }
        }

         if (gameOver == true) {
           JOptionPane.showMessageDialog(this,"Your Score: "+score,"SCORE",JOptionPane.INFORMATION_MESSAGE);
           frame.getContentPane().removeAll();
            frame.getContentPane().add(gameOverPanel);
            frame.revalidate();
            frame.repaint();


        }
    }

    public void resetGame() {
        // Clear all balls


        // Clear all bricks
        Brick.allBricks.clear();

        // Clear all items
        Ballitem.ballitems.clear();
        SpeedItem.speedItems.clear();
        PowerItem.powerItems.clear();
        VertigoItem.vertigoItems.clear();
        Item.items.clear();
        Ball.allBalls.clear();

        // Reset game-related variables
        score = 2;
        scoreFromBricks = 0;
        TotalTime = 0;
        level = -1;
        gameOver = false;
        ballGrounded = true;
        playIsON = false;
        firstBallCollision = false;
        levelIncremented = false;
        brickAdded = false;
        vertigoCollision = false;

        // Clear ball shot times
        ballShotTimes.clear();

        // Reset player position or any other necessary variables

        // Start a new game thread
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
        score = (scoreFromBricks - TotalTime / 10);
        return (int) score;
    }


    public void stopGameThread() {
        // Check if the game thread is running and interrupt it
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();

        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();


    }




}

