package pandemic;

import java.io.DataInputStream;
import java.net.Socket;
import javax.swing.JTextArea;

class ClientReceiverThread implements Runnable{
	Socket socket;
	JTextArea ChatList;
	public ClientReceiverThread(Socket socket, JTextArea ChatList) {
		this.socket = socket;
		this.ChatList = ChatList;
		System.out.println(ChatList);
	}
	
	public void run() {   
		try {
			
			System.out.println("CRT start");
			while(true) {
				DataInputStream reader = new DataInputStream(socket.getInputStream());
				System.out.println(socket);
				String str = reader.readUTF();
				System.out.println(str);
				if(str.equals("[제어]stop") || reader == null) {
					System.out.println("CRT end");
					break;
				}
				else {
					ChatList.append(str + "\n");
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
}
