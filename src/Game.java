import java.awt.*;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game
{
    public static void main(String[] args) {
        Tank bTank = new Tank(50,50, Color.blue);
        Tank rTank = new Tank(50,50, Color.red);
        GameInfo game = new GameInfo(bTank, rTank);

        GameFrame gameFrame = new GameFrame(game);
    }
}
