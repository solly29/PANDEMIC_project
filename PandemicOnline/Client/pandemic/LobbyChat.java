package pandemic;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LobbyChat extends Thread {
	private Socket Csocket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private Map<String, DataOutputStream> clientMap;
	private Map<String, String> JoinMap;
	private String clientKey = "";
	private String nickname = "";
	
	public LobbyChat(Socket Csocket, Map<String, DataOutputStream> clientMap, Map<String, String> JoinMap){
		this.clientMap = clientMap;
		this.Csocket = Csocket;
		this.JoinMap = JoinMap;
		nickname = String.valueOf(Csocket.getPort());
	}
	
	public void run() {
		try {
			// �떎瑜� �겢�씪�씠�뼵�듃�뿉寃� 蹂대궪 硫붿꽭吏�瑜� 諛쏄린 �쐞�븳 DataInputStream �깮�꽦
			dis = new DataInputStream(Csocket.getInputStream());
			
			// �떎瑜� �겢�씪�뿉寃� �뜲�씠�꽣瑜� �쟾�넚�븯湲� �쐞�븳 dataoutputStream �깮�꽦
			dos = new DataOutputStream(Csocket.getOutputStream());
			
			//map �뿉 �궎媛� �꽕�젙
			// �쁽�옱�뒗 ID瑜� port 媛믪쑝濡� ��泥댄븿
			clientMap.put(String.valueOf(Csocket.getPort()), dos);		
			
			
			String msg = "";
			
			// �겢�씪�씠�뼵�듃濡쒕��꽣 �삤�뒗 硫붿꽭吏�瑜� sendBoreadCast 硫붿냼�뱶瑜� �궗�슜�븯�뿬 �쟾�넚�븳�떎.
			while(true) {
				msg = dis.readUTF();
				sendBroadCast("[" + nickname + "]" + msg);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Server 媛� 紐⑤뱺 �겢�씪�뿉寃� �뜲�씠�꽣瑜� �쟾�넚�븯�뒗 硫붿냼�뱶
	private void sendBroadCast(String msg) {
		Set<String> keyset = clientMap.keySet();
		
		Iterator<String> keyIter = keyset.iterator();
		
		while(keyIter.hasNext()) {
			String key = keyIter.next();
			
			try {
				clientMap.get(key).writeUTF(msg + "\n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
