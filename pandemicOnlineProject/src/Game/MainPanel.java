package Game;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import pandemic.Client;
import pandemic.ClientReceiverThread;

public class MainPanel extends JLayeredPane implements KeyListener, MouseListener {
	// JLayerdPane이기 때문에 겹쳐서 패널을 올려넣을수가 있다.
	MainPanel Mainpanel = this;// 하위 클래스들에게 Mainpanel의 자원을 쓸 수 있도록 하기 위해서
	Map map = new Map();// 지도 단순히 이미지만 그린다.
	Chat chat;
	List<String> ReAbandonedCard = new ArrayList<String>();// special카드 특수카드를 사용하기 위해

	Citys citys = new Citys();// 도시 그래프
	ControlPanel Controlpanel;
	History history = new History();
	// Character character = new Character(Mainpanel);// 캐릭터말 캐릭터 좌표등
	static Socket gSocket, cSocket;
	static DataOutputStream ChatOutStream, GameOutStream;
	ClientReceiverThread ChatClass;
	JTextArea textArea;
	ClientGameReceiverThread GameRun;
	Thread GameTh;
	String myjob;
	String[] otherjob;
	Count count = new Count();
	static int InfectionCount = 0;// 전염카운터이다.
	static int DiffusionCount = 0;// 확산카운터이다. 7이 될시 패배
	static ImageIcon Diffusion = new ImageIcon(Map.class.getResource("../Image/Diffusion.png"));// 확산카운터 이미지
	static JLabel Diffusion_label = new JLabel(Diffusion);// 확산 라벨;

	Hashtable<String, Character> characterList = new Hashtable<String, Character>();

	public MainPanel(Socket gSocket, Socket cSocket, ClientReceiverThread ChatClass, String myjob, String[] otherjob) {
		this.gSocket = gSocket;
		this.cSocket = cSocket;
		this.ChatClass = ChatClass;
		this.myjob = myjob;
		this.otherjob = otherjob;

		for (int i = 0; i < otherjob.length; i++) {
			if (!otherjob[i].equals("")) {
				this.otherjob[i] = otherjob[i];
				System.out.println(i + "의 " + this.otherjob[i] + " 직업이다");
			}
		}

		try {
			ChatOutStream = new DataOutputStream(cSocket.getOutputStream());
			GameOutStream = new DataOutputStream(gSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Controlpanel = new ControlPanel(Mainpanel);// 밑에 컨트롤을 위해 필요한 패널

		// 비상대책설계자 클라이언트직업이름이 emergency즉 비대설이면 시작 시 정부 보조금을 가지고 시작한다.
		if (myjob.equals("emergency")) {
			Controlpanel.Havecard.insertCard(Controlpanel, "정부보조금");
			Controlpanel.Havecard.insertCard(Controlpanel, "긴급공중수송");
		}

		chat = new Chat();// 투명 채팅창

		GameRun = new ClientGameReceiverThread(gSocket, Mainpanel);
		GameTh = new Thread(GameRun);
		GameTh.start();

		this.setPreferredSize(new Dimension(1920, 1080));

		// setSize(1920,1080);
		this.add(map, new Integer(0));// 맵은 단순히 이미지만 그려주기때문에 최하위
		map.setBounds(0, 0, 3000, 2000);

		this.add(chat, new Integer(10));// 반 투명 채팅창
		chat.setBounds(600, 550, 500, 220);

		this.add(Controlpanel, new Integer(20));// 이 컨트롤 패널에서 유저들의 행동 모든 것을 처리한다
		Controlpanel.setBounds(0, 840, 1920, 240);
		// Controlpanel.setOpaque(false);
		// addFocusListener(new MyFocuseListener());//현재 패널이 키보드 포커싱을 알아먹는지 못 알아 먹는지
		// 알아보기 위하여
		this.add(count, new Integer(50));// 확산, 감염, 백신개발 여부 알림판
		count.setBounds(0, 0, 70, 130);

		this.add(history, new Integer(10));
		history.setBounds(0, 400, history.getHeight(), history.getWidth());

		this.setFocusable(true);
		this.requestFocusInWindow();// 키포커싱 준것
		// this.addMouseListener(this);
		this.addKeyListener(this);

	}

	class History extends JPanel {
		JScrollPane scroll;// 스크롤 기능 구현
		JTextArea textarea = new JTextArea(10, 10);

		public History() {
			this.setOpaque(false);

			setSize(new Dimension(240, 350));
			scroll = new JScrollPane(textarea);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정
			setLayout(new BorderLayout());
			textarea.setFocusable(false);
			textarea.setFont(new Font("HY헤드라인M", 20, 20));

			textarea.setOpaque(false);
			scroll.getViewport().setOpaque(false);
			scroll.setOpaque(false);

			textarea.setOpaque(false);
			textarea.setForeground(Color.white);
			add(scroll);
		}

		public void addHistory(String text) {
			textarea.append(text + "\n");
			textarea.setCaretPosition(textarea.getDocument().getLength());
		}
	}

		class Count extends JPanel {
		ImageIcon Infection = new ImageIcon(Map.class.getResource("../Image/Infection.png"));
		// 전염카운터 이미지
		

		ImageIcon RedCureIcon = new ImageIcon(Map.class.getResource("../Image/red.png"));// 빨간치료제아이콘
		ImageIcon DevelopeRedCureIcon = new ImageIcon(Map.class.getResource("../Image/red2.png"));// 빨간치료제아이콘개발시
		ImageIcon BlueCureIcon = new ImageIcon(Map.class.getResource("../Image/blue.png"));// 파란치료재아이콘
		ImageIcon DevelopeBlueCureIcon = new ImageIcon(Map.class.getResource("../Image/blue2.png"));// 파란치료제아이콘개발시
		ImageIcon YellowCureIcon = new ImageIcon(Map.class.getResource("../Image/yellow.png"));// 노란치료제아이콘
		ImageIcon DevelopeYellowCureIcon = new ImageIcon(Map.class.getResource("../Image/yellow2.png"));// 노란치료제개발시아이콘
		ImageIcon BlackCureIcon = new ImageIcon(Map.class.getResource("../Image/black.png"));// 검은치료제아이콘
		ImageIcon DevelopeBlackCureIcon = new ImageIcon(Map.class.getResource("../Image/black2.png"));// 검은치료제개발시아이콘

		JLabel RedCureIcon_label = new JLabel(RedCureIcon);// 빨간치료제기본아이콘설정
		JLabel BlueCureIcon_label = new JLabel(BlueCureIcon);// 파란치료제기본아이콘설정
		JLabel BlackCureIcon_label = new JLabel(BlackCureIcon);// 검은치료제기본아이콘설정
		JLabel YellowCureIcon_label = new JLabel(YellowCureIcon);// 노란치료제기본아이콘설정
		JLabel Infection_label = new JLabel(Infection);// 전염 라벨
		

		public Count() {

			Diffusion_label.setText(" : 0");// 확산라벨의 카운터. 처음 확산라벨은 0이다.
			Diffusion_label.setFont(new Font("굴림", Font.BOLD, 20));
			Diffusion_label.setForeground(Color.white);

			Infection_label.setText(" : 2");// 전염라벨의 카운터 전염라벨의 의미는 뽑히는 전염카드숫자다.
			Infection_label.setFont(new Font("굴림", Font.BOLD, 20));
			Infection_label.setForeground(Color.white);

			add(Diffusion_label);
			add(Infection_label);
			add(RedCureIcon_label);
			add(BlueCureIcon_label);
			add(BlackCureIcon_label);
			add(YellowCureIcon_label);

			setOpaque(false);
		}

		public void DevelopeRedCure() {
			RedCureIcon_label.setIcon(DevelopeRedCureIcon);
		}

		public void DevelopeBlueCure() {
			BlueCureIcon_label.setIcon(DevelopeBlueCureIcon);
		}

		public void DevelopeYellowCure() {
			YellowCureIcon_label.setIcon(DevelopeYellowCureIcon);
		}

		public void DevelopeBlackCure() {
			BlackCureIcon_label.setIcon(DevelopeBlackCureIcon);
		}

	}

	public void setInfection() {// 전염이벤트 발생시 ClientGameReciverThread에서 실행
		++InfectionCount;
		if (InfectionCount > 1) {
			count.Infection_label.setText(" : 3");
		} else if (InfectionCount > 3) {
			count.Infection_label.setText(" : 4");
		}
	}

	public static void setDiffusion() {
		++DiffusionCount;
		Diffusion_label.setText(" : " + DiffusionCount);
	}


	public ArrayList returnCity() {
		ArrayList<String> list = citys.AdjacencyCitys(characterList.get(Client.name).CurrentPositon);
		// Citys의 character.CurrentPosition String으로 현재 내가 위치한 도시를 알려준다.
		// AdjacencyCitys인접한 도시들을 ArraysList로 반환하는 메소드
		return list;
	}

	class Map extends JPanel {// 맵을 그려주는 패널, 맵만 그려줄뿐만아니라 현재 게임의 진행상황동 여기 그려준다.
		Image background = new ImageIcon(Map.class.getResource("../Image/map.png")).getImage();

		public Map() {
			setSize(3000, 2000);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, 3000, 2000, null, null);
			citys.draw(g);// 도시들의 원을 그려준다.
			for (Character c : characterList.values()) {
				c.draw(g);
			}
			// character.draw(g);// 캐릭터를 그린다
		}
	}

	class Chat extends JPanel {

		JTextField textField;

		public Chat() {
			setLayout(null);
			this.setOpaque(false);
			textArea = new JTextArea(12, 35);
			textField = new JTextField(35);

			textArea.setEditable(false);
			textArea.setFocusable(false);
			addFocusListener(new MyFocuseListener());

			JScrollPane scrollPane = new JScrollPane(textArea) {
				@Override
				protected void paintComponent(Graphics g) {
					try {
						Composite composite = ((Graphics2D) g).getComposite();

						((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g.setColor(getBackground());
						g.fillRect(0, 0, getWidth(), getHeight());

						((Graphics2D) g).setComposite(composite);
						paintChildren(g);
					} catch (IndexOutOfBoundsException e) {
						super.paintComponent(g);
					}
				}
			};
			scrollPane.addFocusListener(new MyFocuseListener());

			textArea.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setOpaque(false);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			textArea.setFont(new Font("HY헤드라인M", Font.PLAIN, 18)); // 채팅표시되는 부분 이다 궁서체 바꿔야함
			textField.setFont(new Font("HY헤드라인M", Font.PLAIN, 15)); // 채팅입력하는 부분 , 궁서체 바꾸자

			scrollPane.setBounds(0, 0, 500, 190); // chat 패널을 layout(null)줘서 셋바운드로 위치조정함 현재 패널이랑 딱맞게 되어있기 때문에 위치수정하려면 위에
													// 챗패널 자체의 셋바운드도 수정해야한다.
			textField.setBounds(0, 190, 500, 30);

			ChatClass.ChangeTextArea(textArea);

			add(scrollPane);
			add(textField);
			textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_ESCAPE:
						textField.setFocusable(false);
						break;
					case KeyEvent.VK_ENTER:
						try {
							textArea.setCaretPosition(textArea.getDocument().getLength()); // 채팅창 맨아레로 스크롤
							ChatOutStream.writeUTF("[채팅]" + textField.getText());
							textField.setText("");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}
	}

	// 포커스 확인

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			map.setLocation(map.getLocation().x, map.getLocation().y + 30);
			break;

		case KeyEvent.VK_S:
			map.setLocation(map.getLocation().x, map.getLocation().y - 30);
			break;

		case KeyEvent.VK_D:
			map.setLocation(map.getLocation().x - 30, map.getLocation().y);
			break;

		case KeyEvent.VK_A:
			map.setLocation(map.getLocation().x + 30, map.getLocation().y);
			break;

		case KeyEvent.VK_ENTER:
			// this.setFocusable(false);
			// this.requestFocus(false);

			chat.textField.setFocusable(true);
			chat.textField.requestFocus();
			break;

		case KeyEvent.VK_J:
			System.out.println("J눌러짐");
			characterList.get(Client.name).setXY(500, 500);
			repaint();
			revalidate();
			break;
		case KeyEvent.VK_F:
			if (history.isVisible()) {
				history.setVisible(false);
			} else {
				history.setVisible(true);
			}
			break;

		case KeyEvent.VK_ESCAPE:
			new ESC();
			break;

		case KeyEvent.VK_SPACE:
			int x = characterList.get(Client.name).getX();
			int y = characterList.get(Client.name).getY();
			map.setLocation(-x + (1960 / 2), -y + (1020 / 2) - 120);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.requestFocus();
		this.setFocusable(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
