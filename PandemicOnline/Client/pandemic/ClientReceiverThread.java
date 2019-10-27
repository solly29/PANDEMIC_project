package pandemic;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class ClientReceiverThread implements Runnable{   // 채팅 내역 화면에 출력
	Socket socket;
	JTextArea ChatList;
	public ClientReceiverThread(Socket socket, JTextArea ChatList) {
		this.socket = socket;
		this.ChatList = ChatList;
	}
	
	public void run() {   
		try {
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			while(true) {
				System.out.println(socket);
				String str = reader.readUTF();   // 채팅 내역 입력
				if(reader == null) break;
				ChatList.append(str + "\n");
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}