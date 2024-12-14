import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatServerGUI {
    private JTextArea textArea;
    private JTextField inputField;
    private JButton sendButton;
    private ChatServer chatServer;

    public ChatServerGUI(ChatServer chatServer) {
        this.chatServer = chatServer;
        initializeGUI();
    }

    private void initializeGUI() {
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
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Initialize input field and send button
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 30));
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBackground(Color.GRAY);
        inputField.setForeground(Color.WHITE);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sendButton.setBackground(Color.GRAY);
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
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            chatServer.sendMessage(message);
            inputField.setText("");
        }
    }

    public void displayMessage(String sender, String message) {
        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        textArea.append(String.format("[%s] %s: %s\n", timestamp, sender, message));
    }
}
