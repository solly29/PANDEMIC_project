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
	String id, pw;
	
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
		
		while(true) {
			System.out.print("로그인 : 2   회원가입 : 1");
			String login = sc.nextLine();

			System.out.print("아이디 : ");
			id = sc.nextLine();
			System.out.print("비번 : ");
			pw = sc.nextLine();
			try {
				out.writeUTF(login);
				out.writeUTF(id);
				out.writeUTF(pw);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String str = null;
			try {
				str = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(login.equals("login")) {
				if(str.equals("true")) {
					break;
				}else {
					System.out.println("실패");
				}
			}else {
				if(str.equals("true")) {
					System.out.println("회원가입 성공");
				}else {
					System.out.println("회원가입 실패");
				}
			}
			
		}
			System.out.println(s1);
			
			run1 = new ClientReceiverThread(s1);
			th = new Thread(run1);
			th.start();
			
			try {
				
				
				System.out.println("방 생성 or 입장 or 목록(0 or 1 or 2) : ");
				String room = sc.nextLine();
				
				while(true) {
					if(room.equals("0")) {
						out.writeUTF("Create");
						break;
					}else if(room.equals("1")){
						out.writeUTF("Connect");
						System.out.println("방 번호 입력 : ");
						String num = sc.nextLine();
						out.writeUTF(num);
						break;
					}else {
						out.writeUTF("refresh");
						str = in.readUTF();
						System.out.println(str);
					}
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