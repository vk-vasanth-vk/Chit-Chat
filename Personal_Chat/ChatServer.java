import java.io.*;
import java.net.*;

public class ChatServer {
    private PrintWriter out;
    private BufferedReader in;
    private ChatServerGUI serverGUI;

    public ChatServer() {
        serverGUI = new ChatServerGUI(this);
        startServer();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            Socket socket = serverSocket.accept();

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                serverGUI.displayMessage("Her", message);
            }
        } catch (IOException e) {
            serverGUI.displayMessage("Error", e.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            serverGUI.displayMessage("You", message);
        } else {
            serverGUI.displayMessage("System", "Unable to send message. No client connected.");
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}
