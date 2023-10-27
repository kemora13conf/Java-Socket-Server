// import java.io.*;
import java.net.*;

public class LearnSocket {
    public static void main(String[] args){
        try {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println(host.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
