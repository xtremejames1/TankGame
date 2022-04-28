import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel;
    private JButton xButton;
    private JButton settingsButton;
    private JTextField playerNameTextField;
    private JButton hostButton;
    private JButton connectButton;
    private JLabel title;

    public MainMenu(JFrame f, GameInfo g, GameFrame gf) {
        f.setContentPane(panel);
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!playerNameTextField.getText().equals("")) {
                    g.setName(playerNameTextField.getText());
                    gf.host();
                }
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!playerNameTextField.getText().equals("")) {
                    g.setName(playerNameTextField.getText());
                    gf.client();
                }
            }
        });
    }
}
