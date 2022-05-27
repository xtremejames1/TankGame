import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Chat{
    private GameInfo game;
    private JFrame frame = new JFrame();
    private JTextArea text;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel label;
    private JTextField input;
    private GameNetwork net;
    private String oldChat = "";
    public Chat(GameInfo g, GameNetwork n) {
        game = g;
        net = n;
        panel = new JPanel(new GridBagLayout());

        label = new JLabel(game.getRName());
        label.setPreferredSize(new Dimension(1280, 30));

        text = new JTextArea();
        text.setEditable(false);

        scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1280, 720));

        input = new JTextField();
        input.setPreferredSize((new Dimension(1280, 30)));

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
        c.gridy=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weighty = 0.1;
        panel.add(input);
        input.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!input.getText().equals("")) {
                        String chat = input.getText();
                        input.setText("");
                        game.addMsg(game.getName(), chat);
                        try {
                            net.sendMessage(chat);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
                frame.setSize(1280, 720);
        frame.setTitle("JamesChat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public void update() {
        text.setText(game.getReceiveData());
        if(!oldChat.equals(text.getText())) {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
            oldChat = text.getText();
        }
        frame.pack();
    }

}
