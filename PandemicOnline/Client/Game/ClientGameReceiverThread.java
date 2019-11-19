package Game;

import java.io.DataInputStream;
import java.net.Socket;
import javax.swing.JTextArea;


public class ClientGameReceiverThread implements Runnable{
	private Socket socket;
	private MainPanel mainPanel;
	public ClientGameReceiverThread(Socket socket, MainPanel mainPanel) {
		this.socket = socket;
		this.mainPanel = mainPanel;
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
					if(str.substring(0,4).equals("[이동]")) {
						System.out.println("ad");
						str = str.substring(4);
						mainPanel.character.setCurrentposition(str);// 캐릭터의 현재 위치를 이동시키기 위해 이동도시(String)를 바꾸고
						Point pos = mainPanel.citys.CityPosition(str);
						mainPanel.character.setXY(pos.getX(), pos.getY());// 캐릭터의 좌표를 바꾼다.(xy)
						
						mainPanel.Controlpanel.invalidate();
						mainPanel.Controlpanel.removeAll();
						mainPanel.Controlpanel.add(mainPanel.Controlpanel.new BasicSelect());
						mainPanel.Controlpanel.revalidate();
						mainPanel.Controlpanel.repaint();
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
}
