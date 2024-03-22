import java.awt.*;
import java.util.ArrayList;

public class Border extends Rectangle {

    int x,y,width,height;
    public Border(int x,int y,int width,int height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x,y,width,height);
    }

    public String toString(){
        return x+"_"+y+"_"+width+"_"+height;
    }
}
