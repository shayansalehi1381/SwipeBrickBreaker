import java.awt.*;
import java.util.ArrayList;

public class PowerItem extends RegularItem{


    static ArrayList<PowerItem> powerItems = new ArrayList<>();

    public PowerItem() {
        super();
        powerItems.add(this);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.orange);
        g.fillOval(xPos,yPos,width,height);

    }
}
