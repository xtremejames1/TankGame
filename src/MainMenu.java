import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel;
    private JButton xButton;
    private JButton settingsButton;
    private JTextField playerNameTextField;
    private JButton startGame;
    private JButton connectButton;
    private JLabel title;

    public MainMenu(JFrame f, GameInfo g, GameFrame gf) {
        f.setContentPane(panel);
        f.setVisible(true);

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
