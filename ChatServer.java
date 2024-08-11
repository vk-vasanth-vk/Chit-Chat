import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatServer {
 public static void main(String... abc) {
  new ServerGUI();

  try(ServerSocket ss = new ServerSocket(1234)) {
	Socket socket = ss.accept();

	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	String msg = in.readLine();
	
	ServerGUI.printMsg(msg);
  } catch(IOException e) { e.printStackTrace(); }
 }
}

class ServerGUI extends JFrame{
 static TextArea txt;

 ServerGUI() {
  setVisible(true);
  setSize(400,400);
  setLayout(new FlowLayout());
  setDefaultCloseOperation(3);
  setLocationRelativeTo(null);

  txt = new TextArea();
  add(txt);
 }

 static void printMsg(String msg) {
	txt.setText(msg);
 }
}