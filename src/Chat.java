import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Chat{
    private JTextField textField1;
    private JTextArea TextArea;
    private JButton sendButton;
    private JButton backButton;
    private JPanel panel1;

    public Chat(JFrame f, GameNetwork n, GameFrame gf) {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText()!=null) {
                    try {
                        n.sendMessage(textField1.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gf.mainMenu();
            }
        });
        f.setContentPane(panel1);
        f.setVisible(true);
    }

    public void addChat(String name, String chat) {
        TextArea.append(name+": "+chat+"\n");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
