package testServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainClient{
	Scanner sc = new Scanner(System.in);
	String str = "";
	Socket s1;
	Socket s2;
	DataOutputStream out;
	DataInputStream in;
	Runnable run1;
	Thread th;
	
	public MainClient() {
		try {
			s1 = new Socket("127.0.0.1",9002);
			s2 = new Socket("127.0.0.1",9003);
			
			out = new DataOutputStream(s1.getOutputStream());
			in = new DataInputStream(s1.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
			System.out.print("아이디 : ");
			String id = sc.nextLine();
			System.out.print("비번 : ");
			String pw = sc.nextLine();
			
			System.out.println(s1);
			
			run1 = new ClientReceiverThread(s1);
			th = new Thread(run1);
			th.start();
			
			try {
				out.writeUTF(id);
				out.writeUTF(pw);
				
				System.out.println("방 생성 or 입장(0 or 1) : ");
				String room = sc.nextLine();
				
				if(room.equals("0")) {
					out.writeUTF("Create");
				}else {
					out.writeUTF("Connect");
					System.out.println("방 번호 입력 : ");
					String num = sc.nextLine();
					out.writeUTF(num);
				}
				
				while(true) {
					System.out.println(id+" > ");
					String str = sc.nextLine();
					System.out.println("--------------------------------");
					out.writeUTF(str);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainClient();
	}

}
