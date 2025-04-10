import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                    //do not use BufferedReader because it is suitable for reading text only
//                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//                    char[] buf = new char[32];
//                    int bytesRead = in.read(buf,16, 32);
//                    System.out.println(buf);
//                    int messageSize = 4; //4 bytes
                    OutputStream out = clientSocket.getOutputStream();
                    DataOutputStream dsOut = new DataOutputStream(clientSocket.getOutputStream());
                    //we wrapped Output stream by DOS, so that we can use advance functionalities like out.writeInt()
                    out.write(new byte[] {0, 1, 2, 3}); //writing message size
//                    out.write(buf);
//                    byte[] temp = new byte[4];
//                    int bytesRead = in.read(temp, 0, 4);
                    in.readInt(); //message size
                    //A Kafka request specifies the API its calling by using the request_api_key header field
                    int apiKey =  in.readShort();
                    int apiVersion = in.readShort();
                    System.out.println("API version is: "+apiVersion);
                    int corelationId = in.readInt();
                    dsOut.writeInt(corelationId);
                    if(apiVersion<0 || apiVersion>4) {
                        System.out.println("Recd data is: "+ corelationId);
                    } else {
                        dsOut.writeShort(35);
                    }
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
//        System.out.println("args[1] is: " + args[1]);
    }
}
