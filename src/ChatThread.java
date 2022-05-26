public class ChatThread extends Thread{
    private GameInfo game;
    public ChatThread(GameInfo g) {
        game = g;
    }
    @Override
    public void run() {
        while(true) {
            System.out.println("\033[H\033[2J");
            System.out.flush();
            System.out.println(game.getReceiveData());
        }
    }
}
