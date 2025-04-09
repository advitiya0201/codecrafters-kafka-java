import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        System.err.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
//                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//                    char[] buf = new char[32];
//                    int bytesRead = in.read(buf,16, 32);
//                    System.out.println(buf);
//                    int messageSize = 4; //4 bytes
//                    OutputStream out = clientSocket.getOutputStream();
//                    out.write(new byte[] {0, 1, 2, 3});
//                    out.write(buf);
                    byte[] temp = new byte[4];
                    int bytesRead = in.read(temp, 5, 4);
                    System.out.println("Recd data is: "+Arrays.toString(temp));
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
//        System.out.println("args[1] is: " + args[1]);
    }
}
