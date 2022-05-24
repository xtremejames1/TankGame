import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HostMenuPanel {
    private JPanel panel;
    private JLabel hostLabel;
    private JButton backButton;
    private JLabel ipLabel;
    private JLabel tcpLabel;
    private JLabel udpLabel;

    public HostMenuPanel(JFrame f, GameFrame gf, String ip, int tcp, int udp, GameNetwork n) {
        f.setContentPane(panel);
        ipLabel.setText("IP: "+ip);
        tcpLabel.setText("TCP: "+tcp);
        udpLabel.setText("UDP: "+udp);
        f.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    n.reset(); //closes all current connections
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gf.mainMenu();
            }
        });
    }
}
