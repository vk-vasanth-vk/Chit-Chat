import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GroupChatServer {

    private static final int PORT = 12345;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer);
                ClientHandler clientHandler = new ClientHandler(clientSocket, writer);
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    static void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;

    public ClientHandler(Socket socket, PrintWriter writer) {
        this.clientSocket = socket;
        this.writer = writer;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                GroupChatServer.broadcastMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
