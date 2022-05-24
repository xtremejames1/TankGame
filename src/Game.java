import java.awt.*;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game
{
    public static void main(String[] args) {
        Tank playerTank = new Tank(50,50, Color.blue);
        GameInfo game = new GameInfo(playerTank);

        GameFrame gameFrame = new GameFrame(game);
    }
}
