import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    HashSet<Brick> brickHashSet = new HashSet<>();
    Random random;
    int number = 0;
    public MapGenerator(){
        random = new Random();

        makeRandomBricks();
    }

    public void makeRandomBricks(){

        while (true){
            int randomBrickRow = random.nextInt(9);
            int randomBrickCol = random.nextInt(9);
            brickHashSet.add(new Brick(randomBrickRow,randomBrickCol));
            number++;
            if (number > 2){
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
