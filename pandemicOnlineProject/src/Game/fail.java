package Game;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import pandemic.Client;

public class fail extends JFrame {
	public fail() {
		Socket gsocket = MainPanel.gSocket;
		
		setTitle("버튼으로 띄우는 새로운 창");
		
		//DataOutputStream gameOutput = new DataOutputStream(gsocket.getOutputStream());
		//gameOutput.writeUTF("[제어]fail");
		
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setResizable(false);
        setVisible(true);
	}
}
