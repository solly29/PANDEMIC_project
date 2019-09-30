package testServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client{
	Scanner sc = new Scanner(System.in);
	String str = "";
	Socket s1;
	Socket s2;
	DataOutputStream out;
	DataInputStream in;
	Runnable run;
	Thread th;
	
	public client() {
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
			
			run = new ClientReceiverThread(s1);
			th = new Thread(run);
			th.start();
			
			try {
				out.writeUTF("admin");
				out.writeUTF(pw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("aaaaa");
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new client();
	}

}
