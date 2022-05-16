import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMenu {
    private JButton back;
    private JTextField ipField;
    private JTextField tcpField;
    private JTextField udpField;
    private JButton connectButton;
    private JLabel udpLabel;
    private JLabel tcpLabel;
    private JLabel ipLabel;
    private JLabel clientLabel;
    private JPanel panel;

    public ClientMenu(JFrame f, GameNetwork n, GameFrame gf) {
        f.setContentPane(panel);
        f.setVisible(true);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gf.mainMenu();
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!ipField.getText().equals("")&&!tcpField.getText().equals("")&&!udpField.getText().equals("")) {
                    try {
                        gf.clientStart(Integer.parseInt(tcpField.getText()), Integer.parseInt(udpField.getText()), ipField.getText());
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
