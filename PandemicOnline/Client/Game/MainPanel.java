package Game;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Dimension;
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

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import pandemic.*;

public class MainPanel extends JLayeredPane implements KeyListener, MouseListener {
	// JLayerdPane이기 때문에 겹쳐서 패널을 올려넣을수가 있다.
	MainPanel Mainpanel = this;// 하위 클래스들에게 Mainpanel의 자원을 쓸 수 있도록 하기 위해서
	Map map = new Map();// 지도 단순히 이미지만 그린다.
	Characters characters = new Characters();// C키를 눌렀을 때 유저들이 어떤 카드를 가지고 있는지 확인하기 위해
	Chat chat;
	ControlPanel Controlpanel = new ControlPanel(Mainpanel);// 밑에 컨트롤을 위해 필요한 패널
	Citys citys = new Citys();// 도시 그래프
	Character character = new Character(Mainpanel);// 캐릭터말 캐릭터 좌표등
	Socket gSocket, cSocket;
	DataOutputStream ChatOutStream,GameOutStream;
	ClientReceiverThread ChatClass;
	JTextArea textArea;
	ClientGameReceiverThread GameRun;
	Thread GameTh;

	public MainPanel(Socket gSocket, Socket cSocket, ClientReceiverThread ChatClass) {
		this.gSocket = gSocket;
		this.cSocket = cSocket;
		this.ChatClass = ChatClass;
		
		try {
			ChatOutStream = new DataOutputStream(cSocket.getOutputStream());
			GameOutStream = new DataOutputStream(gSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chat = new Chat();// 투명 채팅창
		
		this.setPreferredSize(new Dimension(1920, 1080));
		
		// setSize(1920,1080);
		this.add(map, new Integer(0));// 맵은 단순히 이미지만 그려주기때문에 최하위
		map.setBounds(0, 0, 3000, 2000);

		this.add(characters, new Integer(10));// C키를 눌렀을 때 활성화 되는 현재 캐릭터들의 현황창.
		characters.setBounds(1520, 0, 400, 1080);

		this.add(chat, new Integer(10));// 반 투명 채팅창
		chat.setBounds(600, 550, 400, 250);

		this.add(Controlpanel, new Integer(20));// 이 컨트롤 패널에서 유저들의 행동 모든 것을 처리한다
		Controlpanel.setBounds(0, 800, 1920, 300);
		// addFocusListener(new MyFocuseListener());//현재 패널이 키보드 포커싱을 알아먹는지 못 알아 먹는지
		// 알아보기 위하여
		
		GameRun = new ClientGameReceiverThread(gSocket, Mainpanel);
		GameTh = new Thread(GameRun);
		GameTh.start();
		
		this.setFocusable(true);
		this.requestFocusInWindow();// 키포커싱 준것
		//this.addMouseListener(this);
		this.addKeyListener(this);
		
		
	}

	public ArrayList returnCity() {
		ArrayList<String> list = citys.AdjacencyCitys(character.CurrentPositon);
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
			character.draw(g);// 캐릭터를 그린다
		}
	}

	public class Character {// 유저 자신 캐릭터다
		Image CharacterIcon = new ImageIcon(ControlPanel.class.getResource("../Image/Scientist.png")).getImage();
		String CurrentPositon = "애틀란타";// 제일 처음 시작위치는 애틀란타다. 캐릭터의 이동위치가 바뀔 때 마다 업데이트

		// 시작좌표
		int x = 0, y = 0;
		MainPanel Mainpanel;

		public Character(MainPanel Mainpanel) {
			this.Mainpanel = Mainpanel;
			Point c = Mainpanel.citys.CityPosition(CurrentPositon);
			x = c.getX();
			y = c.getY();
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
			repaint();
		}// 위에 단일 setX,setY는 나중에 멀티플레이에서 여러 캐릭터들이 한 맵에 있을때 쓰일듯 setX(getX+10) 막이런식으로 말이지
		//, 진짜로 이동할때는 이걸쓴다. 그리고 이동하자마자 repaint로 그려준다.

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setCurrentposition(String e) {
			this.CurrentPositon = e;
		}// 캐릭터의 현재 도시(String)를 수정

		public String getCurrentposition() {
			return CurrentPositon;
		}//캐릭터의 현재 도시(String)을 반환

		public void draw(Graphics g) {
			g.drawImage(CharacterIcon, x, y, CharacterIcon.getWidth(null), CharacterIcon.getHeight(null), null);
		}// 캐릭터를 그려준다.
	}

	class Characters extends JPanel {// 캐릭터들 모든 유저들의 현재상태를 보여주는 창 (단축키C)
		public Characters() {
			setSize(new Dimension(400, 1080));
		}
	}

	class Chat extends JPanel {
		
		JTextField textField;

		public Chat() {
			this.setOpaque(false);
			textArea = new JTextArea(12, 35);
			textField = new JTextField(35);
			textArea.setEditable(false);
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
			
			ChatClass.ChangeTextArea(textArea);
			
			add(scrollPane, BorderLayout.CENTER);
			add(textField, BorderLayout.SOUTH);
			textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_ESCAPE:
						textField.setFocusable(false);
						break;
					case KeyEvent.VK_ENTER:
						try {
							ChatOutStream.writeUTF("[채팅]"+textField.getText());
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
		case KeyEvent.VK_C:

			if (characters.isVisible()) {
				characters.setVisible(false);
			} else {
				characters.setVisible(true);
			}
			break;
		case KeyEvent.VK_ENTER:
			//this.setFocusable(false);
			//this.requestFocus(false);
			
			chat.textField.setFocusable(true);
			chat.textField.requestFocus();
			break;

		case KeyEvent.VK_J:
			System.out.println("J눌러짐");
			character.setXY(500, 500);
			repaint();
			revalidate();
			break;

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