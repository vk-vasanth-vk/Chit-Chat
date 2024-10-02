import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private static JTextArea textArea;
    private static JTextField inputField;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        // Set the look and feel to the system's native look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize frame
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Initialize text area for chat
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
	textArea.setFont(new Font("Arial", Font.PLAIN, 16));
	textArea.setBackground(Color.DARK_GRAY); // Set background color
        textArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Initialize input panel
        inputField = new JTextField();
	inputField.setPreferredSize(new Dimension(300,30));
	inputField.setFont(new Font("Arial", Font.PLAIN, 16));	
	inputField.setBackground(Color.GRAY); // Set background color
        inputField.setForeground(Color.WHITE);

        JButton sendButton = new JButton("Send");
	sendButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        sendButton.setBackground(Color.GRAY); // Set background color
        sendButton.setForeground(Color.BLACK);

        // Add action listener for the send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Send message on Enter key press
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Layout input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Start server
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            Socket socket = serverSocket.accept();

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                displayMessage("She", message);
            }
        } catch (IOException ex) {
            textArea.append("Error: " + ex.getMessage() + "\n");
        }
    }

    private static void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            displayMessage("You", message);
            out.println(message);
            inputField.setText("");
        }
    }

    private static void displayMessage(String sender, String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        textArea.append(String.format("[%s] %s: %s\n", timestamp, sender, message));
    }
}
