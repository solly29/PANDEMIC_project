
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Lobby {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.add(new Background());
		f.setSize(1920, 1080);
		f.setResizable(false); // 자바 크기 변경 X
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}

class Background extends JPanel {
	JScrollPane scrollPane;
	ImageIcon icon = new ImageIcon("C:\\Users\\WIN10\\Desktop\\img\\Lobby3.png");

	public void paintComponent(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, null);
		setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
		super.paintComponent(g);
	}

	public Background() {
		setSize(1920, 1080);
		setLayout(null);
		add(new Profile()).setBounds(190, 730, 310, 320);
		add(new RoomList()).setBounds(475, 170, 1000, 465);
		add(new Chat()).setBounds(510, 730, 1220, 320);

		// scrollPane = new JScrollPane(this);
		// setContentPane(scrollPane);
		setVisible(true);

	}
}

class Profile extends JPanel {
	public Profile() {
		setLayout(null);
		JLabel label1 = new JLabel("내정보"); // 내정보 창 제목?
		// this.setBounds(190, 730, 310, 320);
		this.setOpaque(false); // ㅈ같은 판넬 안보이게하기
		// this.setBackground(Color.green);
		this.add(label1);
		label1.setBounds(0, 0, 100, 70);
		setVisible(true);
	}
}

class RoomList extends JPanel {
	public RoomList() {
		setLayout(null);
		JButton RoomMake = new JButton(new ImageIcon("C:\\Users\\WIN10\\Desktop\\img\\MKRoom.png")); // 방만들기 버튼추가
																											// // // 추가
		JButton RoomSearch = new JButton(new ImageIcon("C:\\Users\\WIN10\\Desktop\\img\\SerRoom.png")); // 방찾기 버튼추가
		JButton Refresh = new JButton(new ImageIcon("C:\\Users\\WIN10\\Desktop\\img\\Re.png")); // 새로고침버튼 추가
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
		// this.setBounds(475, 170, 1000, 465);
		this.setOpaque(false);
		this.add(RoomMake); // 방만들기 버튼 적용
		this.add(RoomSearch); // 방찾기 버튼 적용
		this.add(Refresh); // 새로고침 버튼 적용
		RoomMake.setBounds(0, 0, 250, 100);
		RoomSearch.setBounds(270, 0, 410, 100);
		Refresh.setBounds(700, 0, 300, 100);
		// this.setBackground(Color.blue);
		// ㅈ같은 판넬 안보이게하기
		setVisible(true);
	}
}

class Chat extends JPanel {
	public Chat() {
		setLayout(null);		
		// setBounds(510, 730, 1220, 320);
		this.setOpaque(false); // ㅈ같은 판넬 안보이게하기		
		JTextField ChatField = new JTextField(); //채팅치는 필드
		JTextArea ChatList = new JTextArea(); //채팅이 표시되는 영역		
		this.add(new JLabel("Enter")).setBounds(0, 0, 100, 100);
		this.add(ChatField);
		this.add(new JScrollPane(ChatList));
		ChatField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)e.getSource();
				ChatList.append(t.getText()+"\n");
				t.setText("");
			}
		});
		this.setOpaque(false); // ㅈ같은 판넬 안보이게하기
		ChatList.setBounds(0, 300, 200, 200);
		ChatField.setBounds(0, 290, 1200, 30);
		setVisible(true);
	}
}
