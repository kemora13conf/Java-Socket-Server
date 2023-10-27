import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    public static void main(String[] args) {
        System.out.println("__     __        _____  _____ ______  _   _  _____ ______        __     __");
        System.out.println("\\ \\    \\ \\      /  ___||  ___|| ___ \\| | | ||  ___|| ___ \\      / /    / /");
        System.out.println(" \\ \\    \\ \\     \\ `--. | |__  | |_/ /| | | || |__  | |_/ /     / /    / /");
        System.out.println("  \\ \\    \\ \\     `--. \\|  __| |    / | | | ||  __| |    /     / /    / /");
        System.out.println("   \\ \\    \\ \\   /\\__/ /| |___ | |\\ \\ \\ \\_/ /| |___ | |\\ \\    / /    / /");
        System.out.println("    \\_\\    \\_\\  \\____/ \\____/ \\_| \\_| \\___/ \\____/ \\_| \\_|  /_/    /_/\n\n");
        ExecutorService threadPool = Executors.newFixedThreadPool(10); 

        try {
            InetAddress ip = InetAddress.getByName(InetAddress.getLocalHost().getHostName());
            ServerSocket serverSocket = new ServerSocket(7887, 0, ip);

            System.out.println("Server is waiting for clients to connect on "+ip.getHostAddress()+":7887");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();
                System.out.println("[NEW CONNECTION] - " + clientIP+":"+ clientPort);

                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static String getCurrentTime() {
    //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //     Date date = new Date();
    //     return sdf.format(date);
    // }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String ip;
    private int port;
    private String name;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.ip = this.clientSocket.getInetAddress().getHostAddress();
        this.port = this.clientSocket.getPort();
        this.name = this.clientSocket.getInetAddress().getHostName();
    }

    @Override
    public void run() {
        try {
            // Communication with the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("["+name+"] : " + message);
                out.println("Hello from the server!");
            }
            // Check if the clientSocket is closed and print "closed"
            if (message == null) {
                System.out.println("["+this.name+"] - DISCONNECTED X_X");
            }
            // Close the sockets
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
