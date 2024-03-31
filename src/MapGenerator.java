import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    HashSet<Brick> brickHashSet = new HashSet<>();
    Random random;
    int number = 0;
    int limit = 0;
    public MapGenerator(){
        random = new Random();

        makeRandomBricks();
    }

    public void makeRandomBricks(){

        while (true){
            int randomBrickRow = random.nextInt(3);
            int randomBrickCol = random.nextInt(5);
            brickHashSet.add(new Brick(randomBrickRow,randomBrickCol));
            number++;
            if (GamePanel.hard == true){
                GamePanel.medium = false;
                limit = 3;
            }
            else if (GamePanel.medium == true){
                GamePanel.hard = false;
                limit = 2;
            }
            else {
                GamePanel.medium = false;
                GamePanel.hard = false;
                limit = 1;
            }
            if (number > limit){
                number = 0;
                break;
            }

        }
    }

    public void paint(Graphics2D g ){
     for (int i = 0 ; i < Brick.allBricks.size(); i++){
         Brick brick = Brick.allBricks.get(i);
         brick.paint(g);
     }
    }
}
