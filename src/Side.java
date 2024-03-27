import java.awt.*;

public class Side extends Rectangle {
    int yPos ;
    public Side(int x, int y, int width, int height) {

        super(x,  y, width, height);
        yPos = y;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.white); // Change color as needed
        g.fillRect(x, (int) yPos, width, height);
    }

    public boolean intersects(Ball ball) {
        Rectangle ballRect = new Rectangle(ball.ballPosX, ball.ballPosY, ball.width, ball.height);
        Rectangle sideRect = new Rectangle(x,  yPos, width, height);
        return ballRect.intersects(sideRect);
    }

}
