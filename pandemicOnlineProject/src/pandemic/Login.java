package pandemic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;



public class Lobby extends JPanel {

	Image background = new ImageIcon(Client.class.getResource("../Lobby_Image/background.png")).getImage();
	Socket gsocket, csocket;
	DataInputStream input;
	DataOutputStream output;
	JFrame top;
	ClientReceiverThread ChatClass = null;
	JTextArea ChatList;

	public Lobby(Socket gsocket, Socket csocket) {
		this.gsocket = gsocket;
		this.csocket = csocket;
		ChatList = new JTextArea(20, 20); // 채팅이 표시되는 영역
		ChatClass = new ClientReceiverThread(csocket, ChatList);
		top = Login.getTop();
		System.out.println(top + "1");
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
		add(new RoomList(gsocket, csocket, top, ChatClass)).setBounds(475, 170, 1000, 465);
		add(new Chat(csocket, ChatClass, ChatList)).setBounds(510, 730, 1230, 320);

		add(new logOut(gsocket, csocket, top, ChatClass)).setBounds(1800, 10, 100, 100);

		setVisible(true);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}

class logOut extends JButton { //로그아웃하기위한 버튼
	JFrame top;
	Socket gsocket, csocket;
	ClientReceiverThread ChatClass;
	DataInputStream input;
	DataOutputStream output;

	public logOut(Socket gsocket, Socket csocket, JFrame top, ClientReceiverThread ChatClass) {
		this.top = top;
		this.gsocket = gsocket;
		this.csocket = csocket;
		
		

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("로그아웃시도(클)");
					output = new DataOutputStream(gsocket.getOutputStream());
					output.writeUTF("logout");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("앙기모띠");
					e1.printStackTrace();
				}
				top.getContentPane().removeAll();
				top.getContentPane().add(new Login(gsocket, csocket));
				top.revalidate();
				top.repaint();

				// input.readUTF("logout")
			}
		});

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
	ImageIcon roomMakeIcon = new ImageIcon(Client.class.getResource("../Lobby_Image/Make.png"));
	ImageIcon Search = new ImageIcon(Client.class.getResource("../Lobby_Image/Search.png"));
	ImageIcon Re = new ImageIcon(Client.class.getResource("../Lobby_Image/Refresh.png"));
	
	ImageIcon MakePush = new ImageIcon(Client.class.getResource("../Lobby_Image/MakePush.png"));
	ImageIcon SearchPush = new ImageIcon(Client.class.getResource("../Lobby_Image/SearchPush.png"));
	ImageIcon RePush = new ImageIcon(Client.class.getResource("../Lobby_Image/RefreshPush.png"));
	Socket gsocket, csocket;
	DataInputStream input;
	DataOutputStream output, output1;
	JLabel la = new JLabel();
	DefaultTableModel model;
	JTable roomListTable;
	JScrollPane scroll;
	String list;
	JFrame top;
	JPanel roomListPanel;

	public RoomList(Socket gsocket, Socket csocket, JFrame top, ClientReceiverThread ChatClass) {
		this.gsocket = gsocket;
		this.csocket = csocket;
		this.top = top;

		model = new DefaultTableModel(0, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		roomListTable = new JTable(model);
		scroll = new JScrollPane(roomListTable);
		model.addColumn("방 목록");
		model.addColumn("비밀번호");
		roomListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// roomListTable.disable();

		roomListPanel = new JPanel();

		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setLayout(null);
		JButton roomMakeButton = new JButton(roomMakeIcon); // 방만들기 버튼추가
		JButton roomSearchButton = new JButton(Search); // 방찾기 버튼추가
		JButton roomRefreshButton = new JButton(Re); // 새로고침버튼 추가

		roomMakeButton.setContentAreaFilled(false); // 버튼 내용영역 채우지않기,이미지로 해놨으니깐
		roomSearchButton.setContentAreaFilled(false);
		roomRefreshButton.setContentAreaFilled(false);
		roomMakeButton.setBorderPainted(false); // 버튼 테두리 없애기
		roomSearchButton.setBorderPainted(false);
		roomRefreshButton.setBorderPainted(false);
		roomMakeButton.setFocusPainted(false); // 눌렀을때 테두리 안뜨게
		roomSearchButton.setFocusPainted(false);
		roomRefreshButton.setFocusPainted(false);
		
		JLabel roomMakeButtonMessage = new JLabel("이것은 방만들기 버튼의 라벨");// 마우스 올릴때 라벨 추가하려고 만든 라벨
		roomMakeButtonMessage.setBounds(50, 100, 200, 50);
		
	

		// 배열로 줄이자
		roomMakeButton.addMouseListener(new MouseAdapter() { //방만들기버튼 마우스액션
			public void mouseEntered(MouseEvent e) {
				roomMakeButton.setIcon(MakePush);// 버튼 클릭했을때 모양이 바뀐다
				roomMakeButtonMessage.setVisible(true);
				add(roomMakeButtonMessage);
			
			}				
			public void mouseExited(MouseEvent e) {
				
				roomMakeButtonMessage.setVisible(false);
				roomMakeButton.setIcon(roomMakeIcon);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
			}
			public void mousePressed(MouseEvent e) {
				
				new makeRoom(top, gsocket, csocket, ChatClass);
			}

			
		});
		roomSearchButton.addMouseListener(new MouseAdapter() { //방찾기버튼 마우스액션
			public void mouseEntered(MouseEvent e) {
				roomSearchButton.setIcon(SearchPush);// 버튼 도달했을때 모양이 바뀐다
			}			
			public void mouseExited(MouseEvent e) {				
				roomSearchButton.setIcon(Search);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
			}
			
			public void mousePressed(MouseEvent e) {
				roomSearchButton.setIcon(SearchPush);// 버튼 클릭했을때 모양이 바뀐다				
			}

			
		});
		
		roomRefreshButton.addMouseListener(new MouseAdapter() { //새로고침버튼 마우스액션
			public void mouseEntered(MouseEvent e) {
				roomRefreshButton.setIcon(RePush);// 버튼 클릭했을때 모양이 바뀐다			
						
			}				
			public void mouseExited(MouseEvent e) {							
				roomRefreshButton.setIcon(Re);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
			}
			public void mousePressed(MouseEvent e) {
				printListRoom();
			}

			
		});

		// this.setBounds(475, 170, 1000, 465);//식별용

		roomListTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				try {
					output1 = new DataOutputStream(csocket.getOutputStream());
					System.out.println(csocket);

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				int row = roomListTable.getSelectedRow();
				int col = roomListTable.getSelectedColumn();
				String str = null;
				String str2 = roomListTable.getValueAt(row, 0) + ""; // 방 이름
				String str3 = roomListTable.getValueAt(row, 1) + ""; // 비밀번호 유무
				if (str3.equals("****")) {
					new checkRoomPassword(top, gsocket, csocket, ChatClass, str2);
				} else {
					try {
						output.writeUTF("Join");
						output.writeUTF(roomListTable.getValueAt(row, col) + "");
						output.writeUTF(""); // 비밀번호 없음
						str = input.readUTF();

						if (str.equals("true")) {
							// output1.writeUTF("[제어]stop");
							top.getContentPane().removeAll();
							top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
							top.revalidate();
							top.repaint();
						} else {
							JOptionPane.showMessageDialog(null, "입장 불가능");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						str = "false";
					}
				}

			}
		});
		
		
	

		// 테이블 수정 불가
		roomListTable.getTableHeader().setReorderingAllowed(false);
		roomListTable.getTableHeader().setResizingAllowed(false);
		
		roomListTable.getColumn("방 목록").setPreferredWidth(600); 
		roomListTable.getColumn("비밀번호").setPreferredWidth(100);
		
		//listTable.setBackground(Color.red);


		

		this.setOpaque(false);
		this.add(roomMakeButton); // 방만들기 버튼 적용
		this.add(roomSearchButton); // 방찾기 버튼 적용
		this.add(roomRefreshButton); // 새로고침 버튼 적용
		roomListPanel.add(scroll);
		this.add(roomListPanel);
		roomListPanel.setBounds(100, 100, 700, 300);
		roomListPanel.setOpaque(false);
		roomMakeButton.setBounds(0, 0, 250, 100);
		roomSearchButton.setBounds(270, 0, 410, 100);
		roomRefreshButton.setBounds(700, 0, 300, 100);
		// this.setBackground(Color.blue);//식별용
		setVisible(true);
		printListRoom();
	}

	public void printListRoom() {
		try {
			output.writeUTF("refresh");
			list = input.readUTF();
			System.out.println(list);

			if (list.length() != 4) {
				list = list.substring(1, list.length() - 1);

				String[] list2 = list.split(", ");
				for (int i = 0; i < list2.length; i++) {

				}
				System.out.println(list2);
				model.setNumRows(0);

				for (int i = list2.length - 1; i >= 0; i--) {
					String[] test2 = list2[i].split("=");
					if (!test2[0].equals("로비")) {
						String[] test = { test2[0], test2[1] };
						model.addRow(test);
					}

				}
			}

			// la.setText(Arrays.toString(list2));
			// System.out.println(list);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class checkRoomPassword extends JFrame implements ActionListener { // 비밀번호 확인창 (있을시에만 뜨도록)
	JFrame top;
	Socket gsocket, csocket;
	DataOutputStream output;
	DataInputStream input;
	ClientReceiverThread ChatClass;
	JPasswordField inputPassword = new JPasswordField();
	String str2 = "";

	public checkRoomPassword(JFrame top, Socket gsocket, Socket csocket, ClientReceiverThread ChatClass, String str2) {
		this.top = top;
		this.gsocket = gsocket;
		this.csocket = csocket;
		this.ChatClass = ChatClass;
		this.str2 = str2;

		setTitle("비밀번호 확인");
		setSize(400, 200);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		JButton PWbutton = new JButton();
		JLabel roomPassword = new JLabel("비밀번호");

		roomPassword.setBounds(50, 50, 100, 50);
		inputPassword.setBounds(150, 50, 100, 50);

		PWbutton.add(new JLabel("확인"));
		PWbutton.addActionListener(this);
		PWbutton.setBounds(270, 90, 90, 40);
		this.add(roomPassword);
		this.add(PWbutton);
		this.add(inputPassword);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = null;
		System.out.println("액션발생까지는 성공함");
		try {

			output = new DataOutputStream(gsocket.getOutputStream());
			input = new DataInputStream(gsocket.getInputStream());
			System.out.println(csocket);

			output.writeUTF("Join");
			output.writeUTF(str2);
			output.writeUTF(inputPassword.getText());
			str = input.readUTF();
			System.out.println("비밀번호 보내기는 성공함");
			if (str.equals("false")) {
				JOptionPane.showMessageDialog(null, "입장불가.");
				// output1.writeUTF("[제어]stop");

			} else {
				top.getContentPane().removeAll();
				top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
				top.revalidate();
				top.repaint();
				this.dispose();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class makeRoom extends JFrame implements ActionListener { // 방만들기 누르면 뜨는 창
	JFrame top;
	Socket gsocket, csocket;
	DataOutputStream output, output1;
	DataInputStream input;
	ClientReceiverThread ChatClass;
	JTextField roomNameField = new JTextField();
	JPasswordField roomPasswordField = new JPasswordField();
	// Image background2 = new
	// ImageIcon(Client.class.getResource("../Lobby_Image/blood.jpg")).getImage();

	public makeRoom(JFrame top, Socket gsocket, Socket csocket, ClientReceiverThread ChatClass) {
		this.top = top;
		this.gsocket = gsocket;
		this.csocket = csocket;
		this.ChatClass = ChatClass;

		setTitle("방만들기");
		setSize(500, 300);
		setLayout(null);
		// this.setBackground(Color.green);
		setResizable(false);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// setContentPane(new JLabel(new ImageIcon("../Lobby_Image/blood.jpg")));
		JButton makeRoomButton = new JButton();
		makeRoomButton.add(new JLabel("생성"));
		makeRoomButton.addActionListener(this);
		makeRoomButton.setBounds(270, 90, 90, 40);
		this.add(makeRoomButton);
		// 방만드는 버튼 설정
		JLabel roomNameLabel = new JLabel("방 이름 ");
		JLabel roomPasswordLabel = new JLabel("비밀번호");

		roomNameLabel.setBounds(105, 90, 70, 20);
		roomPasswordLabel.setBounds(105, 115, 70, 20);
		roomNameField.setBounds(160, 90, 110, 20);
		roomPasswordField.setBounds(160, 115, 110, 20);
		this.add(roomNameLabel);
		this.add(roomPasswordLabel);
		this.add(roomNameField);
		this.add(roomPasswordField);

	}
	/*
	 * public void paintComponent(Graphics g) { g.drawImage(background2, 0, 0,
	 * null); paintComponent(g);
	 * 
	 * }
	 */

	public void actionPerformed(ActionEvent e) {
		// JFrame top=(JFrame)SwingUtilities.getWindowAncestor(Lobby);
		String str = null;
		try {
			output = new DataOutputStream(gsocket.getOutputStream());
			output1 = new DataOutputStream(csocket.getOutputStream());
			input = new DataInputStream(gsocket.getInputStream());
			System.out.println(csocket);
			output.writeUTF("Create");
			output.writeUTF(roomNameField.getText());
			output.writeUTF(roomPasswordField.getText());
			str = input.readUTF();
			if (str.equals("true")) {
				// output1.writeUTF("[제어]stop");
				top.getContentPane().removeAll();
				top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
				top.revalidate();
				top.repaint();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "방을 만들 수 없습니다.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

/*
 * class MkRoomBg extends JPanel { // 방만들기 창에 넣을 배경 패널 public MkRoomBg() {
 * JLabel label = new JLabel("123"); this.add(label);
 * this.setBackground(Color.green); this.add(new MKRP()); // this.setBounds(0,
 * 0, 500, 300); setVisible(true); } }
 */
/*
 * class MKRP extends JPanel { // 방만들기 창에 기능할 패널 public MKRP() { JLabel RoomID =
 * new JLabel("방 이름 "); // 방이름 적으라는 글자 JLabel RoomPW = new JLabel("비밀번호"); //
 * 비밀번호 적으라는 글자 RoomID.setBounds(100, 100, 50, 50); RoomPW.setBounds(150, 150,
 * 50, 50); this.add(RoomID); this.add(RoomPW); } }
 */
class Chat extends JPanel {
	DataOutputStream output;

	Thread ChatTh;
	JTextArea ChatList;

	public Chat(Socket csocket, ClientReceiverThread ChatClass, JTextArea ChatList) {

		setLayout(null);
		// setBounds(510, 730, 1230, 320);//식별용
		this.setOpaque(false); // 판넬 안보이게하기
		JTextField ChatField = new JTextField(); // 채팅치는 필드

		this.ChatList = ChatList;

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

		ChatTh = new Thread(ChatClass);
		ChatTh.start();

		ChatField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				// ChatList.append(t.getText() + "\n");
				try {
					output.writeUTF("[채팅]" + t.getText());
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
