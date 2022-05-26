import javax.swing.*;

public class ChatThread extends Thread{
    private GameInfo game;
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JTextArea text;
    public ChatThread(GameInfo g) {
        game = g;
        panel = new JPanel();
        text = new JTextArea();
        frame.setLayout(null);
        frame.setSize(1280, 720);
        frame.setTitle("JamesChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    @Override
    public void run() {

        while(true) {
            text.setText(game.getReceiveData());
            /*
            System.out.println("\033[H\033[2J");
            System.out.flush();
            System.out.println(game.getReceiveData());
            */
        }
    }
}
