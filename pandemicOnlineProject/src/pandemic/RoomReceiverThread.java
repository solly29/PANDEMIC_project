package pandemic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.*;



class RoomReceiverThread implements Runnable{
	JFrame top;
	Socket socket;
	Socket cSocket;
	ClientReceiverThread ChatClass;
	JLabel[] RoomList;
	String[] job = { "emergency", "traffic", "soilder", "builder", "random", "quarantine", "researcher",
            "scientist", "empty" };
	String[] otherjob = { "" , "", "", ""};
	
	public RoomReceiverThread(Socket socket, Socket cSocket, JLabel[] RoomList, JFrame top, ClientReceiverThread ChatClass) {
		this.top = top;
		this.socket = socket;
		this.RoomList = RoomList;
		this.ChatClass = ChatClass;
		this.cSocket = cSocket;
		//System.out.println(RoomList);
	}
	
	public void run() {   
		try {
			
			System.out.println("RRT start");
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			DataOutputStream gameOutput = new DataOutputStream(socket.getOutputStream());
			
			while(true) {
				String str = reader.readUTF();
				System.out.println("RRT receive");
				System.out.println(str);
				
				int usernumber = -1;
				String userjob= str.substring(1,str.length());
				System.out.println(userjob);
				
				if(str.substring(0,4).equals("RDOF")) {
					usernumber = str.charAt(4)-48;
					System.out.println(usernumber);
					RoomList[usernumber].setText(str.substring(5,str.length()));
				} else if (str.substring(0,4).equals("RDON")) {
					usernumber = str.charAt(4)-48;
					System.out.println(usernumber);
					RoomList[usernumber].setText(str.substring(5,str.length()) + " is Ready");
				} else {
					usernumber = str.charAt(0)-48;
				}
				// 서버로 부터 몇번째 유저인지 아이디가 무엇인지 받고
				// 화면에 표시하는 부분
				if( str.substring(0,4).equals("[ID]") ) {
					int num = str.charAt(4)-48;
					System.out.println(num);
					System.out.println("num : " + num);
					for(int i=0; i<4; i++) {
						if(num ==i) {
							RoomList[i].setText(str.substring(5,str.length()));
						}
					}
					
				}
				
				// 서버로 게임스타트를 받으면 다시 게임스타트를 보내고 게임화면으로 넘어가는 부분
				if(str.equals("gameStart")) {
					gameOutput.writeUTF("gameStart");
					gameOutput.writeUTF("");
					String job = Room.myjob;
					
					top.getContentPane().removeAll();
					top.getContentPane().add(new MainPanel(socket, cSocket, ChatClass , job , otherjob)).requestFocusInWindow();
	                top.revalidate();
	                top.repaint();
	                
	                break;
				}
				

				if(str.equals("[제어]stop") || reader == null) {
					System.out.println("RRT end");
					break;
				}
				// 몇번째 유저의 직업이 무엇인지 서버로 받고 화면에 표시하는 부분
				if(usernumber == 0) {
					System.out.println(usernumber + "번 쨰 ");
					for(int i =0; i< job.length; i++) {
						if(userjob.equals(job[i])){
							System.out.println(usernumber + "번 쨰  직업 : " + userjob);
							RoomList[usernumber].setIcon(getCharacterImage(userjob));
							System.out.println();
							otherjob[usernumber] = job[i];
						}
					}
				}
				else if(usernumber == 1) {
					System.out.println(usernumber + "번 쨰 ");
					for(int i =0; i< job.length; i++) {
						if(userjob.equals(job[i])){
							System.out.println(usernumber + "번 쨰  직업 : " + userjob);
							RoomList[usernumber].setIcon(getCharacterImage(userjob));
							otherjob[usernumber] = job[i];
							System.out.println();
						}
					}
				}

				else if(usernumber == 2) {
					System.out.println(usernumber + "번 쨰 ");
					for(int i =0; i< job.length; i++) {
						if(userjob.equals(job[i])){
							System.out.println(usernumber + "번 쨰  직업 : " + userjob);
							RoomList[usernumber].setIcon(getCharacterImage(userjob));
							System.out.println();
							otherjob[usernumber] = job[i];
						}
					}
				}

				else if(usernumber == 3) {
					System.out.println(usernumber + "번 쨰 ");
					for(int i =0; i< job.length; i++) {
						if(userjob.equals(job[i])){
							System.out.println(usernumber + "번 쨰  직업 : " + userjob);
							RoomList[usernumber].setIcon(getCharacterImage(userjob));
							System.out.println();
							otherjob[usernumber] = job[i];
						}
					}
				}
				
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e);
			e.printStackTrace();
		} finally {
			System.out.println("RRT Close");
		}
	}
	
	public ImageIcon getCharacterImage(String Character) {
	      Image Back = new ImageIcon(Room.class.getResource("../Room_Characters/" + Character + "_back.png")).getImage();
	      Image front = new ImageIcon(Room.class.getResource("../Room_Characters/" + Character + ".png")).getImage();
	      BufferedImage Overlap = new BufferedImage(Back.getWidth(null), Back.getHeight(null),
	            BufferedImage.TYPE_3BYTE_BGR);
	      Graphics2D Overlap2 = (Graphics2D) Overlap.getGraphics();
	      Overlap2.drawImage(Back, 0, 0, null);
	      Overlap2.drawImage(front, 87, 51, null);
	      ImageIcon merged = new ImageIcon(Overlap);
	      return merged;
	   }
}
