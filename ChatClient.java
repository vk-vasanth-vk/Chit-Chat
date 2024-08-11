import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClient {
 static PrintWriter out;

 public static void main(String... abc) {
  new ClientGUI();

  try(Socket socket = new Socket("localhost",1234)) {
	out = new PrintWriter(socket.getOutputStream(),true);
  } catch(IOException e) { e.printStackTrace(); }
 }

 static void sendMsg(String msg) {
	out.println(msg);
 }
}

class ClientGUI extends JFrame {
 ClientGUI() {
  setVisible(true);
  setSize(400,400);
  setLayout(new FlowLayout());
  setDefaultCloseOperation(3);
  setLocationRelativeTo(null);

  TextField txt = new TextField(20);
  Button btn = new Button("Send");
  add(txt);
  add(btn);

  btn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	 String msg = txt.getText();	
	 ChatClient.sendMsg(msg);
	}
  });
 }
}
