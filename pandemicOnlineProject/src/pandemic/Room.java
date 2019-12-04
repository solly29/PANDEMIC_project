package pandemic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;

public class Room extends JPanel {
//JFrame의 기본 창은 1920,1080이다. 반띵하면 960 540.
	Chat chat;
	UserList user;
	CharacterList character;
	StartExit startexit;
	JFrame top = Login.getTop();
	Image background2 = new ImageIcon(Client.class.getResource("../Room_Common/background.png")).getImage();
	Socket gsocket, socket2;
	DataOutputStream output, gameOutput;
	JTextArea textArea;// 우리가 친 글자가 보이는 곳
	JLabel[] list = new JLabel[4];
	public static String myjob = "";
	
	Thread RoomTh;
	ClientReceiverThread Chatclass;

	public Room(Socket gsocket, Socket socket2, ClientReceiverThread Chatclass) {
	
		this.gsocket = gsocket;
		this.socket2 = socket2;
		this.Chatclass = Chatclass;
		
		
		try {
			gameOutput = new DataOutputStream(gsocket.getOutputStream());
			gameOutput.writeUTF(Client.name);
			gameOutput.writeUTF("random");
			gameOutput.writeUTF(Client.name);
			gameOutput.writeUTF("random");
			gameOutput.writeUTF("Enter");
			gameOutput.writeUTF(Client.name);
			gameOutput.writeUTF("Join");
			gameOutput.writeUTF("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setLayout(null);
		setPreferredSize(new Dimension(1920, 1080));
		// setLayout(new GridLayout(2, 2, 10, 10));//GridLayout Version

		user = new UserList();
		user.setOpaque(false);
		this.add(user);
		user.setBounds(50, 0, 880, 540);// Null Version

		character = new CharacterList();
		this.add(character);
		character.setBounds(960, 0, 960, 720);// Null Version

		chat = new Chat();
		this.add(chat);
		chat.setBounds(0, 540, 960, 540);// NullVersion

		/*Runnable ChatRun = new ClientReceiverThread(socket2, textArea);
		Thread ChatTh = new Thread(ChatRun);
		ChatTh.start();*/
		Chatclass.ChangeTextArea(textArea);

		Runnable RoomRun = new RoomReceiverThread(gsocket, socket2, list, top, Chatclass);
		RoomTh = new Thread(RoomRun);
		RoomTh.start();

		startexit = new StartExit();
		this.add(startexit);
		startexit.setBounds(960, 720, 960, 360);// NullVersion

		String background_path = "Room_Characters\\background.png";
		setVisible(true);
	}

	class UserList extends JPanel {

		private UserList() {
			// setOpaque(false);//투명
			setLayout(new GridLayout(2, 2, 2, 2));
			// this.setBackground(Color.YELLOW);
			setPreferredSize(new Dimension(880, 500));
			Font font = new Font("굴림", Font.BOLD, 20);
			for (int i = 0; i < 4; i++) {
				list[i] = new JLabel(i + "유저");
				list[i].setVerticalTextPosition(JLabel.BOTTOM);
				list[i].setHorizontalTextPosition(JLabel.CENTER);
				list[i].setFont(font);
				list[i].setForeground(Color.WHITE);
				this.add(list[i]);
			}
		}
	}

	class CharacterList extends JPanel {
		JButton[] list = new JButton[9];
		String[] job = { "emergency", "traffic", "soilder", "builder", "random", "quarantine", "researcher",
				"scientist", "empty" };
		
		JLabel clist = new JLabel("SSS");
		
		
		
		
		
		private CharacterList() {
			
			setPreferredSize(new Dimension(960, 720));
			setLayout(new GridLayout(3, 3, 10, 10));
			// this.setBackground(Color.GREEN);
			setOpaque(false);
			setPreferredSize(new Dimension(960, 540));
			ToolTipManager m = ToolTipManager.sharedInstance();
			m.setDismissDelay(10000); // 툴팁 지속시간 10초
			
			for (int i = 0; i < 9; i++) {
				list[i] = new JButton(getCharacterImage(job[i]));
				
				switch(i){
					case 0 : 
						list[i].setToolTipText("<html>" + "<h1>비상 대책 설계자<h1/><h2>·  한 번의 행동으로, 버려진 이벤트 카드 중 한장을 골라<h2/> "
								+ "<h2>ㅤ이 직업 카드 위에 보관할 수 있습니다.<h2/> <h2> ·  이렇게 보관된 이벤트 카드를 사용하면, 그 카드는 게임에서 제거됩니다.<h2/>"
								+ "<h3>제한 : 이 카드 위에는 이벤트 카드를 한번에 1장씩만 놓을 수 있습니다.<h3/>"
								+ "<h3>여기 놓을 카드는 손에 든 카드가 아닙니다.<h3/>");
						break;
					case 1 : 
						list[i].setToolTipText("<html>" + "<h1>운항관리자<h1/><h2>·  다른 플레이어의 말을 자신의 말처럼 움직일 수 있습니다.<h2/> "
								+ "<h2> ·  한 번의 행동으로, 아무 말 하나를 다른 말이 있는 도시로 보낼 수 있습니다.<h2/>");
						break;  
					case 2 : 
						list[i].setToolTipText("<html>" + "<h1>위생병<h1/><h2>·  질병 치료 행동 한 번으로 그 도시에서 한 색깔 질병 큐브를 모두 제거합니다.<h2/>"
								+ "<h2> ·  치료제가 개발된 질병의 큐브는 위생병이 위치한 도시에서 자동으로 모두 제거됩니다.<h2/>"
								+ "<h2> (해당 큐브가 그 도시에 놓이는 것도 방지됩니다.)<h2/>");
						break;   
					case 3 : 
						list[i].setToolTipText("<html>" + "<h1>건축 전문가<h1/><h2>·  한 번의 행동으로, 도시 카드를 사용하지 않고 현재 위치한 도시에 연구소를 지을 수 있습니다.<h2/>"
								+ "<h2> ·  차례마다 한 번, 한 번의 행동으로, 연구소가 있는 도시에 있을 때 <h2/>"
								+ "<h2>    아무 도시 카드나 1장 버림으로써 원하는 도시로 이동할 수 있습니다.<h2/>");
						break;   
					case 4 : 
						list[i].setToolTipText("<html>" + "<h1> 랜덤 <h1/><h2>·  임의의 직업이 선택됩니다.<h2/>");
						break;   
					case 5 : 
						list[i].setToolTipText("<html>" + "<h1>검역 전문가<h1/><h2>·  검역 전문가가 현재 위치한 도시와 이에 이웃한 도시에는<h2/>"
								+ "<h2>   질병 큐브가 놓이지 않으며, 확산도 일어나지 않습니다.<h2/>");
						break;  
					case 6 : 
						list[i].setToolTipText("<html>" + "<h1>연구자<h1/><h2>·  정보 공유 행동을 할 때 아무 도시 카드나 줄 수 있을습니다.<h2/>"
								+ "<h2>   다른 플레이어는 자신의 차례에 정보공유 행동으로써 <h2/>"
								+ "<h2>   연구자에게서 아무 도시 카드 1장을 가져갈 수 있습니다.<h2/>"
								+ "<h2>   이 효과는 연구자가 도시 카드를 받을 때는 적용되지 않습니다.<h2/>"
								);
						break;  
					case 7 : 
						list[i].setToolTipText("<html>" + "<h1>과학자<h1/><h2>·  치료제 개발 행동을 할 때, 같은 색깔 도시 카드 4장만 버려도 됩니다.<h2/>"
								);
						break;   
				}
				
				add(list[i]);
				
				JButton temp = list[i];
				String tjob = job[i];
				
				
				int x = i;
				
				list[i].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {

					}

					@Override
					public void mousePressed(MouseEvent e) {
						temp.setIcon(getCharacterImage(tjob, "push"));
						try {
							gameOutput.writeUTF(Client.name);
							gameOutput.writeUTF(tjob);
							System.out.println("server send : ");
							System.out.println(Client.name);
							System.out.println(tjob);
							myjob = tjob;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						temp.setIcon(getCharacterImage(tjob));
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
						
					}

				});
				list[i].setBorderPainted(false);
				list[i].setContentAreaFilled(false);
				list[i].setFocusPainted(false);
				System.out.println(list[i].getHeight());
			}
		}

	}

	class Chat extends JPanel implements ActionListener {

		JTextField textField;// 우리가 글자를 치는 곳

		JScrollPane scroll;

		public Chat() {
			// setBackground(Color.pink);

			try {
				output = new DataOutputStream(socket2.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setOpaque(false);
			setPreferredSize(new Dimension(900, 500));
			textArea = new JTextArea(26, 85);
			textArea.setEditable(false);
			scroll = new JScrollPane(textArea);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			textField = new JTextField(85);
			textField.addActionListener(this);

			add(scroll, BorderLayout.CENTER);
			add(textField, BorderLayout.SOUTH);

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String text = textField.getText();
			String temp_nickName = socket2.toString();
			System.out.println(text);
			System.out.println(socket2);
			try {
				output.writeUTF("[채팅]" + text);
				textField.setText("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// textArea.append(temp_nickName + " : " + text + "\n");
			// textField.selectAll();
			// textArea.setCaretPosition(textArea.getDocument().getLength());
		}

	}

	class StartExit extends JPanel implements ActionListener {
		JButton[] buttons = new JButton[2];
		String[] text = { "Start(F5)", "Exit(F4)" };

		public StartExit() {
			setPreferredSize(new Dimension(960, 360));

			// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setOpaque(false);
			setLayout(new FlowLayout());

			for (int i = 0; i < 2; i++) {
				buttons[i] = new JButton(text[i]);
				buttons[i].setPreferredSize(new Dimension(500, 100));
				add(buttons[i]);
				buttons[i].addActionListener(this);
				buttons[i].setHorizontalAlignment(JButton.CENTER);
				JButton temp = buttons[i];
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (e.getSource() == buttons[1]) {
							
							try {
								gameOutput.writeUTF("[Exit]");
								gameOutput.writeUTF("");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							System.out.println(temp.getText());
							top.getContentPane().removeAll();

							top.getContentPane().add(new Lobby(gsocket, socket2));

							top.revalidate();
							top.repaint();
						} else {
							try {
								//list[0].setText("ready");
								gameOutput.writeUTF("[Ready]");
								gameOutput.writeUTF(Client.name);
								System.out.println("Ready");
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background2, 0, 0, null);
	}

	public ImageIcon getCharacterImage(String Character) {
		Image Back = new ImageIcon(Room.class.getResource("../Room_Characters/" + Character + "_back.png")).getImage();
		Image front = new ImageIcon(Room.class.getResource("../Room_Characters/" + Character + ".png")).getImage();
		BufferedImage Overlap = new BufferedImage(Back.getWidth(null), Back.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D Overlap2 = (Graphics2D) Overlap.getGraphics();
		Overlap2.drawImage(Back, 0, 0, null);
		Overlap2.drawImage(front, 65, 45, null); // 사진 위치 조정
		ImageIcon merged = new ImageIcon(Overlap);
		return merged;
	}

	public ImageIcon getCharacterImage(String Character, String push) {
		Image Back = new ImageIcon(Room.class.getResource("../Room_Characters/push.png")).getImage();
		Image front = new ImageIcon(Room.class.getResource("../Room_Characters/" + Character + ".png")).getImage();
		BufferedImage Overlap = new BufferedImage(Back.getWidth(null), Back.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D Overlap2 = (Graphics2D) Overlap.getGraphics();
		Overlap2.drawImage(Back, 0, 0, null);
		Overlap2.drawImage(front, 65, 45, null);// 사진 위치 조정
		ImageIcon merged = new ImageIcon(Overlap);
		return merged;
	}

	/*
	 * public static void main(String[] args) { // testJFrame test = new
	 * testJFrame(); // test.add(new Room()); JFrame f = new JFrame(); f.add(new
	 * Room()); f.setSize(1920, 1080); f.setResizable(false);// 자바 크기 변경 금지
	 * f.setLocationRelativeTo(null);// 창 정중앙 배치
	 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true); }
	 */
}
