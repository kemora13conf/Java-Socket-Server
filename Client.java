import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        System.out.println("__     __        _____  _      _____  _____  _   _  _____       __     __");
        System.out.println("\\ \\    \\ \\      /  __ \\| |    |_   _||  ___|| \\ | ||_   _|     / /    / /");
        System.out.println(" \\ \\    \\ \\     | /  \\/| |      | |  | |__  |  \\| |  | |      / /    / /");
        System.out.println("  \\ \\    \\ \\    | |    | |      | |  |  __| | . ` |  | |     / /    / /");
        System.out.println("   \\ \\    \\ \\   | \\__/\\| |____ _| |_ | |___ | |\\  |  | |    / /    / /");
        System.out.println("    \\_\\    \\_\\   \\____/\\_____/ \\___/ \\____/ \\_| \\_/  \\_/   /_/    /_/\n\n");
        try {
            String host = InetAddress.getByName(InetAddress.getLocalHost().getHostName()).getHostAddress();
            int port = 7887;
            Socket socket = new Socket(host, port);

            // Communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                try {
                    System.out.print("[SEND MESSAGE - (or 'exit' to quit)]$- ");
                    String message = userInput.readLine();

                    if (message.equalsIgnoreCase("exit")) {
                        break; // Exit the loop if the user enters 'exit'
                    }
                    out.println(message);
                } catch (Exception e) {
                    System.out.println("    [SERVER IS DOWN]   ");
                    break;
                }

                String response = in.readLine();
                System.out.println("[FROM SERVER]: " + response);
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
