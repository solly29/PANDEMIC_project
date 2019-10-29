package pandemic;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Room extends JPanel {
//JFrame의 기본 창은 1920,1080이다. 반띵하면 960 540.
	Chat chat;
	UserList user;
	CharacterList character;
	StartExit startexit;
	BufferedImage background;
	Socket gsocket, socket2;

	public Room(Socket gsocket, Socket socket2) {
		
		this.gsocket = gsocket;
		this.socket2 = socket2;
		
		setLayout(null);
		setPreferredSize(new Dimension(1920, 1080));
		// setLayout(new GridLayout(2, 2, 10, 10));//GridLayout Version

		user = new UserList();
		this.add(user);
		user.setBounds(0, 0, 880, 540);// Null Version

		character = new CharacterList();
		this.add(character);
		character.setBounds(960, 0, 960, 720);// Null Version

		chat = new Chat();
		this.add(chat);
		chat.setBounds(0, 540, 960, 540);// NullVersion

		startexit = new StartExit();
		this.add(startexit);
		startexit.setBounds(960, 720, 960, 360);// NullVersion

		String background_path = "Room_Characters\\background.png";
		try {
			background = ImageIO.read(new File(background_path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("자바 배경이미지를 불러오지 못했습니다.");
		}
		setVisible(true);
	}

	class UserList extends JPanel {
		JButton[] list = new JButton[4];

		private UserList() {
			setOpaque(false);
			setLayout(new GridLayout(2, 2, 2, 2));
			// this.setBackground(Color.YELLOW);
			setPreferredSize(new Dimension(880, 500));
			for (int i = 0; i < 4; i++) {
				list[i] = new JButton(i + "유저");
				this.add(list[i]);
			}
		}
	}

	class CharacterList extends JPanel {
		JButton[] list = new JButton[9];
		String[] job = { "emergency", "traffic", "soilder", "builder", "random", "quarantine", "researcher", "scientist", "empty" };

		private CharacterList() {
			setPreferredSize(new Dimension(960, 720));
			setLayout(new GridLayout(3, 3, 10, 10));
			// this.setBackground(Color.GREEN);
			setOpaque(false);
			setPreferredSize(new Dimension(960, 540));
			
			for (int i = 0; i < 9; i++) {
				list[i] = new JButton(getCharacterImage(job[i]));
				add(list[i]);
				list[i].setBorderPainted(false);
				list[i].setContentAreaFilled(false);
				list[i].setFocusPainted(false);
				System.out.println(list[i].getHeight());
			}
		}
	}

	class Chat extends JPanel implements ActionListener {

		JTextField textField;// 우리가 글자를 치는 곳
		JTextArea textArea;// 우리가 친 글자가 보이는 곳
		JScrollPane scroll;

		public Chat() {
			// setBackground(Color.pink);
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
			String temp_nickName = "김영근";
			textArea.append(temp_nickName + " : " + text + "\n");
			textField.selectAll();
			textArea.setCaretPosition(textArea.getDocument().getLength());
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
				buttons[i].setHorizontalAlignment(JButton.CENTER);
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
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

	/*public static void main(String[] args) {
		// testJFrame test = new testJFrame();
		// test.add(new Room());
		JFrame f = new JFrame();
		f.add(new Room());
		f.setSize(1920, 1080);
		f.setResizable(false);// 자바 크기 변경 금지
		f.setLocationRelativeTo(null);// 창 정중앙 배치
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}*/
}
