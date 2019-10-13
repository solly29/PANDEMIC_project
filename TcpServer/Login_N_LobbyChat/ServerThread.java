

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket Csocket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	public ServerThread(Socket Csocket) {
		this.Csocket = Csocket;
	}
	
	public void run() {
		
		new ServerLogin(Csocket);
		
		
	}
}
