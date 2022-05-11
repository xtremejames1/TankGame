import java.awt.*;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game
{
    public static void main(String[] args){
        GameInfo game = new GameInfo(new Tank(50,50, Color.blue), new Tank(50,50, Color.red));

        GameFrame gameFrame = new GameFrame(game);

    }
}
