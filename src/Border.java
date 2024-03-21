import java.awt.*;

public class Border extends Rectangle {
    int x,y,width,height;
    public Border(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x,y,width,height);
    }
}
