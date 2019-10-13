import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerLogin {
	private Socket Csocket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	public ServerLogin(Socket Csocket) {
		this.Csocket = Csocket;
		
		try {
			OutputStream out = Csocket.getOutputStream();
			dos = new DataOutputStream(out);
			InputStream in = Csocket.getInputStream();
			dis = new DataInputStream(in);
			System.out.println("hi");
			
			while(true) {
				String ID = null;
				String PW = null;
				ID = dis.readUTF();
				PW = dis.readUTF();
				
				
				if(ID.equals("12") && PW.equals("34")) {
					System.out.println(ID);
					System.out.println(PW);
					System.out.println("true");
					dos.writeUTF("true");
					break;
				}
				else {
					dos.writeUTF("false");
					System.out.println(ID);
					System.out.println(PW);
					System.out.println("false");
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
