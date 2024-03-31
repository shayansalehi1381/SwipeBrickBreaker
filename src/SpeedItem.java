import java.awt.*;
import java.util.ArrayList;

public class SpeedItem extends RegularItem{
    static ArrayList<SpeedItem> speedItems = new ArrayList<>();

    public SpeedItem() {
        super();
        speedItems.add(this);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.black);
        g.fillOval(xPos,yPos,width,height);

    }
}
