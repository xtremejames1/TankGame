import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostMenuPanel {
    private JPanel panel;
    private JLabel hostLabel;
    private JButton backButton;
    private JLabel ipLabel;
    private JLabel tcpLabel;
    private JLabel udpLabel;

    public HostMenuPanel(JFrame f, GameFrame gf, String ip, int tcp, int udp) {
        f.setContentPane(panel);
        ipLabel.setText("IP: "+ip);
        tcpLabel.setText("TCP: "+tcp);
        udpLabel.setText("UDP: "+udp);
        f.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gf.mainMenu();
            }
        });
    }

    public JButton getBackButton() {
        return backButton;
    }
}
