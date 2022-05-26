import javax.swing.*;
import java.awt.*;

public class ChatThread extends Thread{
    private GameInfo game;
    private JFrame frame = new JFrame();
    private JTextArea text;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel label;
    public ChatThread(GameInfo g) {
        game = g;
        panel = new JPanel();
        text = new JTextArea();
        text.setEditable(false);
        label = new JLabel("Chat with "+game.getRName());
        scrollPane = new JScrollPane(text);

        panel.add(scrollPane);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
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
            frame.repaint();
            /*
            System.out.println("\033[H\033[2J");
            System.out.flush();
            System.out.println(game.getReceiveData());
            */
        }
    }
}
