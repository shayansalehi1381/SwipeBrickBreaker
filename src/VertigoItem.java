import java.awt.*;
import java.util.ArrayList;

public class VertigoItem extends RegularItem {
    static ArrayList<VertigoItem> vertigoItems = new ArrayList<>();

    public VertigoItem() {
        super();
        vertigoItems.add(this);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.MAGENTA);
        g.fillOval(xPos, yPos, width, height);

    }
}
