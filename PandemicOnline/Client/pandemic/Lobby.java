package pandemic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Lobby extends JPanel {

	Image background = new ImageIcon(Client.class.getResource("../Lobby_Image/background.png")).getImage();
	Socket gsocket, csocket;
	DataInputStream input;
	DataOutputStream output;
	
	

	public Lobby(Socket gsocket, Socket csocket) {
		this.gsocket = gsocket;
		this.csocket = csocket;
		
		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		setSize(1920, 1080);
		setLayout(null);
		add(new Profile()).setBounds(190, 730, 310, 320);
		add(new RoomList()).setBounds(475, 170, 1000, 465);
		add(new Chat(csocket)).setBounds(510, 730, 1230, 320);
		setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}

class Profile extends JPanel {
	public Profile() {
		setLayout(null);
		JLabel label1 = new JLabel("내정보"); // 내정보 창 제목?
		// this.setBounds(190, 730, 310, 320);//식별용
		this.setOpaque(false); // 판넬 안보이게하기
		// this.setBackground(Color.green);//식별용
		this.add(label1);
		label1.setBounds(0, 0, 100, 70);
		setVisible(true);
	}
}

class RoomList extends JPanel {
	ImageIcon Make = new ImageIcon(Client.class.getResource("../Lobby_Image/Make.png"));
	ImageIcon Search = new ImageIcon(Client.class.getResource("../Lobby_Image/Search.png"));
	ImageIcon Re = new ImageIcon(Client.class.getResource("../Lobby_Image/Refresh.png"));

	public RoomList() {
		setLayout(null);
		JButton RoomMake = new JButton(Make); // 방만들기 버튼추가
		JButton RoomSearch = new JButton(Search); // 방찾기 버튼추가
		JButton Refresh = new JButton(Re); // 새로고침버튼 추가

		RoomMake.setContentAreaFilled(false); // 버튼 내용영역 채우지않기,이미지로 해놨으니깐
		RoomSearch.setContentAreaFilled(false);
		Refresh.setContentAreaFilled(false);
		RoomMake.setBorderPainted(false); // 버튼 테두리 없애기
		RoomSearch.setBorderPainted(false);
		Refresh.setBorderPainted(false);
		RoomMake.setFocusPainted(false); // 눌렀을때 테두리 안뜨게
		RoomSearch.setFocusPainted(false);
		Refresh.setFocusPainted(false);
		// 배열로 줄이자
		RoomMake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new makeRoom();
			}
		});
		// this.setBounds(475, 170, 1000, 465);//식별용
		
		this.setOpaque(false);
		this.add(RoomMake); // 방만들기 버튼 적용
		this.add(RoomSearch); // 방찾기 버튼 적용
		this.add(Refresh); // 새로고침 버튼 적용
		RoomMake.setBounds(0, 0, 250, 100);
		RoomSearch.setBounds(270, 0, 410, 100);
		Refresh.setBounds(700, 0, 300, 100);
		// this.setBackground(Color.blue);//식별용
		setVisible(true);
	}
}

class makeRoom extends JFrame { // 방만들기 누르면 뜨는 창

	public makeRoom() {
		setTitle("방만들기");
		setSize(500, 300);
		this.setBackground(Color.green);
		setResizable(false);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

class MkRoomBg extends JPanel { // 방만들기 창에 넣을 배경 패널
	public MkRoomBg() {
		JLabel label = new JLabel("123");
		this.add(label);
		this.setBackground(Color.green);
		this.add(new MKRP());
		// this.setBounds(0, 0, 500, 300);
		setVisible(true);
	}
}

class MKRP extends JPanel { // 방만들기 창에 기능할 패널
	public MKRP() {
		JLabel RoomID = new JLabel("방 이름 "); // 방이름 적으라는 글자
		JLabel RoomPW = new JLabel("비밀번호"); // 비밀번호 적으라는 글자
		RoomID.setBounds(100, 100, 50, 50);
		RoomPW.setBounds(150, 150, 50, 50);
		this.add(RoomID);
		this.add(RoomPW);

	}
}

class Chat extends JPanel {
	DataOutputStream output;
	
	Runnable ChatRun;
	Thread ChatTh;
	
	
	public Chat(Socket csocket) {
		
		setLayout(null);
		// setBounds(510, 730, 1230, 320);//식별용
		this.setOpaque(false); // 판넬 안보이게하기
		JTextField ChatField = new JTextField(); // 채팅치는 필드
		JTextArea ChatList = new JTextArea(20, 20); // 채팅이 표시되는 영역
		ChatList.setEditable(false);
		JScrollPane scroll;
		scroll = new JScrollPane(ChatList);
		this.add(scroll);
		this.add(ChatField);
		
		try {
			output = new DataOutputStream(csocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChatRun = new ClientReceiverThread(csocket, ChatList);
		ChatTh = new Thread(ChatRun);
		ChatTh.start();
		
		
		ChatField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				//ChatList.append(t.getText() + "\n");
				try {
					output.writeUTF("[채팅]"+t.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				t.setText("");
			}
		});
		this.setOpaque(false); // 판넬 안보이게하기
		scroll.setBounds(0, 0, 1230, 285);
		// ChatList.setBounds(0, 0, 1200, 200);
		ChatField.setBounds(0, 290, 1230, 30);
		setVisible(true);
	}
}
