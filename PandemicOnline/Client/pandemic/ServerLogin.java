package pandemic;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ServerLogin {
	private Socket Csocket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private Map<String, String> JoinMap;
	
	
	public ServerLogin(Socket Csocket,  Map<String, String> JoinMap) {
		this.Csocket = Csocket;
		this.JoinMap = JoinMap;
		try {
			OutputStream out = Csocket.getOutputStream();
			dos = new DataOutputStream(out);
			InputStream in = Csocket.getInputStream();
			dis = new DataInputStream(in);
			
			//클占쏙옙占싱억옙트占쏙옙 占쏙옙占쏙옙 ID, PW 占쏙옙 占쌨아울옙占쏙옙 占쏙옙占쏙옙 占쏙옙占싼뤄옙占쏙옙占쏙옙
			while(true) {
				String ID = null;
				String PW = null;
				String state = "0";
				
				state = dis.readUTF();
				ID = dis.readUTF();
				PW = dis.readUTF();
				
				if(state.equals("1")) {
					if(JoinMap.containsKey(ID)) {
						dos.writeUTF("false");
					}
					else {
						JoinMap.put(ID, PW);
						System.out.println(ID+ " " + PW);
						dos.writeUTF("true");
					}
							
				}
				else if(state.equals("2")) {
					System.out.println("login");
					if(JoinMap.containsKey(ID)) {
						System.out.println("ID �솗�씤");
						Set set = JoinMap.entrySet();
						System.out.println(set);
						set = JoinMap.keySet();
						System.out.println(set);
						System.out.println((String)JoinMap.get(ID));
						if(PW.equals(JoinMap.get(ID))) {
							System.out.println("PW �씪移�");
							dos.writeUTF("true");
							break;
						}
					}
					
					else {
						dos.writeUTF("false");
						System.out.println(ID);
						System.out.println(PW);
						System.out.println("false");
					}
				}
				// 占쏙옙占쏙옙占� ID 12 PW 34 占쏙옙 占실듸옙占쏙옙 占싹울옙占쏙옙
				// 占쏙옙占쏙옙 占쏙옙占쏙옙
				// 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占� ID PW 占쏙옙占쏙옙 占싣닌곤옙占� false 占쏙옙 占쏙옙占쏙옙占싼댐옙.
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
