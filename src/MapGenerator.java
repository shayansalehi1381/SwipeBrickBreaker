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
            if (GamePanel.level <= 5){
                limit = 1;
            }
            else {
                limit = 0;
            }
            if (number > limit){
                number = 0;
                break;
            }

        }
    }

    public void paint(Graphics2D g ){
        for (Brick brick : Brick.allBricks){
            brick.paint(g);
        }
    }
}
