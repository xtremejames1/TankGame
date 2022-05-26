import javax.swing.*;
import java.awt.*;

public class Chat{
    private GameInfo game;
    private JFrame frame = new JFrame();
    private JTextArea text;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel label;
    public Chat(GameInfo g) {
        game = g;
        panel = new JPanel(new GridBagLayout());
        text = new JTextArea();
        text.setEditable(false);
        label = new JLabel("Chat with "+game.getRName());
        scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        scrollPane.setPreferredSize(new Dimension(1280, 720));
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy=0;
        panel.add(label, c);
        c.gridy=1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel.add(scrollPane,c);
        frame.setSize(1280, 720);
        frame.setTitle("JamesChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public void update() {
        text.setText(game.getReceiveData());
        label.setText(game.getRName());
        frame.pack();
    }
}
