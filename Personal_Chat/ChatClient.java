import java.io.*;
import java.net.*;

public class ChatClient {
    private PrintWriter out;
    private BufferedReader in;
    private ChatClientGUI gui;

    public ChatClient() {
        gui = new ChatClientGUI(this);
        connectToServer();
    }

    private void connectToServer() {
        try (Socket socket = new Socket("localhost", 12345)) {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read messages from the server
            String message;
            while ((message = in.readLine()) != null) {
                gui.displayMessage("Him", message);
            }
        } catch (IOException ex) {
            gui.displayMessage("Error", ex.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            gui.displayMessage("You", message);
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
