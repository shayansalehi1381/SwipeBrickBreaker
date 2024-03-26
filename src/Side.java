import java.awt.*;

public class Side extends Rectangle {
    public Side(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.white); // Change color as needed
        g.fillRect(x, y, width, height);
    }

    public boolean intersects(Ball ball) {
        Rectangle ballRect = new Rectangle(ball.ballPosX, ball.ballPosY, ball.width, ball.height);
        Rectangle sideRect = new Rectangle(x, y, width, height);
        return ballRect.intersects(sideRect);
    }

}
