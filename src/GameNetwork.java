import java.net.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Adds networking functionality to the game
 *
 * @author James Xiao
 * @version 05/26/2022
 */
public class GameNetwork
{
    private DatagramSocket clientUDP, serverUDP; //UDP client and server socket
    private StringBuilder sb = new StringBuilder();
    private ServerSocket socket; //TCP server socket
    private Socket clientSocket; //TCP client socket
    private BufferedReader in; //TCP in reader
    private PrintWriter out; //TCP out writer
    private DatagramPacket DpSend;
    
    private InetAddress remoteIP; //Remote IP Address
    
    private GameInfo game; //GameInfo that the GameNetwork communicates with
    
    private int type; //Game type, 0 for host, 1 for client
    private String data; //data for tank
    private int tcp, udp; //TCP and UDP ports

    /**
     * Constructor for the game network
     * @param t Game type, 0 is host, 1 is client
     * @param g GameInfo that this links to
     * @param tcpport Port for TCP communication
     * @param udpport Port for UDP communication
     */
    public GameNetwork(int t, GameInfo g, int tcpport, int udpport)
    {
        game = g;
        type=t;
        tcp = tcpport;
        udp = udpport;
        System.out.println("Created type "+type+" Network with ports "+tcpport+" and "+udpport);
    }

    /**
     * Constructor for the game network
     * @param t Game type, 0 is host, 1 is client
     * @param g GameInfo that this links to
     * @param tcpport Port for TCP communication
     * @param udpport Port for UDP communication
     * @param ip String representation of IP
     * @throws UnknownHostException
     */
    public GameNetwork(int t, GameInfo g, int tcpport, int udpport, String ip) throws UnknownHostException {
        game = g;
        type=t;
        tcp = tcpport;
        udp = udpport;
        remoteIP = InetAddress.getByName(ip);
        System.out.println("Created type "+type+" Network with ports"+tcpport+" and "+udpport+" and IP "+ip);
    }

    /**
     * Starts the game network server
     * @throws InterruptedException
     * @throws IOException
     */
    public void server() throws InterruptedException, IOException {
        synchronized(this) //Synchronizes to make sure that the Host client-side receives IP
        {
            System.out.println("Server synchronized stage started.");
            if(type==0)
            {
                System.out.println("Host TCP sequence started.");
                socket = new ServerSocket(tcp); //Creates TCP server socket with port defined in constructor
                clientSocket = socket.accept(); //Creates client socket from server socket
                System.out.println("Host TCP network started.");

                out = new PrintWriter(clientSocket.getOutputStream(), true); //creates TCP output
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //creates TCP input

                System.out.println("Host waiting for IP...");
                remoteIP = InetAddress.getByName(in.readLine()); //Receives IP
                System.out.println("Host received IP. Host waiting for remote player name...");
                game.setRName(in.readLine()); //Receives remote name
                System.out.println("Host received remote player name. Host sent local player name.");
                out.println(game.getName()); //Sends local name
                
                System.out.println(game.getRName()+" connected to local host with IP "+remoteIP+".");
                game.setClientFound(true); //used for gameframe
                notify(); //Notifys client thread that IP has been found
            }
        }
        serverUDP = new DatagramSocket(udp);
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);
        while(true)
        {
            serverUDP.receive(DpReceive);
            data = data(receive).toString();
            game.addMsg(game.getRName(), data);
            System.out.println(data);
            Arrays.fill(receive, (byte)0);

        }
    }
    
    public void client() throws IOException,InterruptedException
    {
        synchronized(this) //Synchronized to make sure that host receives IP
        {
            if(type==1)
            {
                System.out.println("Client TCP sequence started.");
                clientSocket= new Socket(remoteIP, tcp);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Client TCP network started.");
                
                out.println(clientSocket.getLocalAddress().getHostAddress());                
                out.println(game.getName());
                System.out.println("Client sent IP and local player name. Waiting for remote player name...");
                
                game.setRName(in.readLine());
                System.out.println("Client received remote player name.");
                
                System.out.println("Local client connected to "+game.getRName()+" with IP "+remoteIP+".");
            }
            else if(type==0)
            {
                System.out.println("Client thread waiting to receive IP");
                wait(); //waits for server thread to receive IP
                game.setClientFound(true);
                System.out.println("Client thread received IP.");
            }
        }
        System.out.println("Client connection made to "+remoteIP);
    }
    public void sendMessage(String msg) throws IOException {
        clientUDP = new DatagramSocket();
        byte buf[] = msg.getBytes();
        DpSend = new DatagramPacket(buf, buf.length, remoteIP, udp);

        clientUDP.send(DpSend);
    }

    public StringBuilder data(byte[] a)
    {
        sb.setLength(0);
        if (a == null)
            return null;
        int i = 0;
        while (a[i] != 0)
        {
            sb.append((char) a[i]);
            i++;
        }
        return sb;
    }
    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public int getTCP() {
        return tcp;
    }
    public int getUDP() {
        return udp;
    }
    public void reset() throws IOException {
        if(socket != null)
            socket.close();
        if(clientSocket != null)
            clientSocket.close();
        if(clientUDP != null)
            clientUDP.close();
        if(serverUDP != null)
            serverUDP.close();
    }
}
