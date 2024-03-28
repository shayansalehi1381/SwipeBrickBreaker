import java.awt.*;
import java.util.ArrayList;

public class Ballitem extends RegularItem {
        static ArrayList<Ballitem> ballitems = new ArrayList<>();
    boolean collidedWithBall = false;
    public Ballitem() {
        super();
        ballitems.add(this);
    }

    @Override
    public void paint(Graphics g) {

            g.setColor(Color.blue);
            g.fillOval(xPos,yPos,width,height);

    }

}
