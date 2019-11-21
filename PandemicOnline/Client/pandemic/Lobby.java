package pandemic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
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
	Socket gsocket, csocket;
	DataInputStream input;
	DataOutputStream output, output1;
	JLabel la = new JLabel();
	DefaultTableModel model;
	JTable listTable;
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
		listTable = new JTable(model);
		scroll = new JScrollPane(listTable);
		model.addColumn("방 목록");
		listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// listTable.disable();

		roomListPanel = new JPanel();

		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
				new makeRoom(top, gsocket, csocket, ChatClass);
			}
		});

		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printListRoom();
			}
		});
		// this.setBounds(475, 170, 1000, 465);//식별용

		listTable.addMouseListener(new MouseListener() {

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

				int row = listTable.getSelectedRow();
				int col = listTable.getSelectedColumn();
				String str = null;
				System.out.println(listTable.getValueAt(row, col));
				try {
					output.writeUTF("Join");
					output.writeUTF(listTable.getValueAt(row, col) + "");
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
		});

		// 테이블 수정 불가
		listTable.getTableHeader().setReorderingAllowed(false);
		listTable.getTableHeader().setResizingAllowed(false);

		this.setOpaque(false);
		this.add(RoomMake); // 방만들기 버튼 적용
		this.add(RoomSearch); // 방찾기 버튼 적용
		this.add(Refresh); // 새로고침 버튼 적용
		roomListPanel.add(scroll);
		this.add(roomListPanel);
		roomListPanel.setBounds(100, 100, 700, 300);
		roomListPanel.setOpaque(false);
		RoomMake.setBounds(0, 0, 250, 100);
		RoomSearch.setBounds(270, 0, 410, 100);
		Refresh.setBounds(700, 0, 300, 100);
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
				System.out.println(list2);
				model.setNumRows(0);
				for (int i = list2.length - 1; i >= 1; i--) {
					String[] test = { list2[i] };
					model.addRow(test);
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

class makeRoom extends JFrame implements ActionListener { // 방만들기 누르면 뜨는 창
	JFrame top;
	Socket gsocket, csocket;
	DataOutputStream output, output1;
	DataInputStream input;
	ClientReceiverThread ChatClass;
	JTextField RN = new JTextField();
	JTextField RP = new JTextField();
	//Image background2 = new ImageIcon(Client.class.getResource("../Lobby_Image/blood.jpg")).getImage();
	
	public makeRoom(JFrame top, Socket gsocket, Socket csocket, ClientReceiverThread ChatClass) {			
		this.top = top;
		this.gsocket = gsocket;
		this.csocket = csocket;
		this.ChatClass = ChatClass;			
		setTitle("방만들기");
		setSize(500, 300);
		setLayout(null);
		//this.setBackground(Color.green);
		setResizable(false);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	//	setContentPane(new JLabel(new ImageIcon("../Lobby_Image/blood.jpg")));
		JButton MKRB = new JButton();
		MKRB.add(new JLabel("생성"));
		MKRB.addActionListener(this);
		MKRB.setBounds(270, 90, 90, 40);
		this.add(MKRB);
		// 방만드는 버튼 설정
		JLabel RoomName = new JLabel("방 이름 ");
		JLabel RoomPW = new JLabel("비밀번호");

		RoomName.setBounds(110, 90, 50, 20);
		RoomPW.setBounds(110, 115, 50, 20);
		RN.setBounds(160, 90, 100, 20);
		RP.setBounds(160, 115, 100, 20);
		this.add(RoomName);
		this.add(RoomPW);
		this.add(RN);
		this.add(RP);

	}
	/*public void paintComponent(Graphics g) {
		g.drawImage(background2, 0, 0, null);
		paintComponent(g);
		
	}*/
	

	public void actionPerformed(ActionEvent e) {
		// JFrame top=(JFrame)SwingUtilities.getWindowAncestor(Lobby);
		String str = null;
		try {
			output = new DataOutputStream(gsocket.getOutputStream());
			output1 = new DataOutputStream(csocket.getOutputStream());
			input = new DataInputStream(gsocket.getInputStream());
			System.out.println(csocket);
			output.writeUTF("Create");
			output.writeUTF(RN.getText());
			output.writeUTF(RP.getText());
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
