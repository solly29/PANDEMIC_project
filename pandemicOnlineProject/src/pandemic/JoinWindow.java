package pandemic;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;

//회원가입 창 만드는 클래스

public class JoinWindow extends JFrame {

	DataOutputStream output;
	DataInputStream input;

	public JoinWindow(Socket socket) {
		
		//회원가입창에서 쓰는 이미지 생성하는 부분
		
		Image Jback = new ImageIcon(Client.class.getResource("../Login_Image/Jback.png")).getImage();
		ImageIcon Jjoinimage = new ImageIcon(Client.class.getResource("../Login_Image/Join.png"));
		ImageIcon Jexitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
		ImageIcon Jjoinpush = new ImageIcon(Client.class.getResource("../Login_Image/Join2.png"));
		ImageIcon Jexitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));
		ImageIcon dupleimage = new ImageIcon(Client.class.getResource("../Login_Image/duple.png"));
		ImageIcon duplepush = new ImageIcon(Client.class.getResource("../Login_Image/duple2.png"));

		Client c;

		Socket gsocket, socket2;

		try {
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		//버튼, 라벨, 텍스트필드 생성
		JButton Jexit = new JButton(Jexitimage);
		JButton Jjoin = new JButton(Jjoinimage);
		JButton Duple = new JButton(dupleimage);

		JLabel ID = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/JID.PNG")));
		JLabel PWD = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/JPWD.png")));
		JLabel Name = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/Jname.png")));
		JLabel Number = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/Jnumber.png")));

		JTextField Name_text = new JTextField();
		JTextField Number_text = new JTextField();
		JTextField ID_text = new JTextField();
		JPasswordField PWD_text = new JPasswordField();

		setSize(500, 750);
		setTitle("회원가입");
		setLocation(300, 120);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true); // 작업표시줄 없애기

		//텍스트필드 폰트 지정
		Name_text.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));
		Number_text.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));
		ID_text.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));
		PWD_text.setFont(new Font("굴림", Font.PLAIN, 20));

		
		// 해당 버튼에 이미지변경 하는 부분
		Jjoin.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Jjoin.setIcon(Jjoinpush);
			}

			public void mouseExited(MouseEvent e) {
				Jjoin.setIcon(Jjoinimage);
			}

			public void mousePressed(MouseEvent e) {
				Jjoin.setIcon(Jjoinpush);
			}

		});
		Duple.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Duple.setIcon(duplepush);
			}

			public void mouseExited(MouseEvent e) {
				Duple.setIcon(dupleimage);
			}

			public void mousePressed(MouseEvent e) {
				Duple.setIcon(duplepush);
			}

		});
		Jexit.addMouseListener(new MouseAdapter() { 
			public void mouseEntered(MouseEvent e) {
				Jexit.setIcon(Jexitpush);
			}

			public void mouseExited(MouseEvent e) {
				Jexit.setIcon(Jexitimage);
			}

			public void mousePressed(MouseEvent e) {
				Jexit.setIcon(Jexitpush);
			}

		});
		
		
		
		// 회원가입 창에 있는 버튼 누르면 창 종료
		Jexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		// 중복확인 버튼 누르면 같은 ID가 있는지 확인 함
		Duple.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				try {

					output.writeUTF("duple");
					output.writeUTF(ID_text.getText());

					if (input.readUTF().equals("false")) { // ID가 같으면 경고 메세지
						JOptionPane.showMessageDialog(null, "다른 ID를 사용해주세요", "오류", JOptionPane.WARNING_MESSAGE);

					} else
						JOptionPane.showMessageDialog(null, "사용가능한 ID입니다");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		//회원가입버튼 누르면 정보들을 확인 후 회원가입 조건 맞으면 회원가입 성공!
		Jjoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					output.writeUTF("join");
					output.writeUTF(ID_text.getText());
					output.writeUTF(PWD_text.getText());
					output.writeUTF(Name_text.getText());
					output.writeUTF(Number_text.getText());
					if (input.readUTF().equals("true")) {
						JOptionPane.showMessageDialog(null, "회원가입 성공! 축하드립니다~");
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "회원가입 실패", "오류", JOptionPane.WARNING_MESSAGE);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		this.add(ID);
		this.add(ID_text);
		this.add(PWD);
		this.add(Jjoin);
		this.add(PWD_text);
		this.add(Number_text);
		this.add(Name_text);
		this.add(Name);
		this.add(Number);
		this.add(Duple);

		Name.setBounds(100, 75, 100, 30);
		Name_text.setBounds(200, 80, 100, 30);

		Number.setBounds(100, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 30);

		ID.setBounds(100, 215, 100, 30);
		ID_text.setBounds(200, 220, 100, 30);

		PWD.setBounds(100, 285, 100, 30);
		PWD_text.setBounds(200, 290, 100, 30);

		Jexit.setBounds(250, 400, 140, 50);
		Jjoin.setBounds(80, 400, 140, 50);

		Duple.setBounds(310, 200, 100, 50);

		getContentPane().add(Jexit);
		getContentPane().add(Jjoin);
		setVisible(true);

		
		// 차례대로 버튼 투명하게, 외곽선없애기, 내용영역 채우기 없앰, 테두리 사용안함
		
		Jjoin.setOpaque(false);
		Jjoin.setBorderPainted(false);
		Jjoin.setContentAreaFilled(false);
		Jjoin.setFocusPainted(false);

		Duple.setOpaque(false);
		Duple.setBorderPainted(false);
		Duple.setContentAreaFilled(false);
		Duple.setFocusPainted(false);

		Jexit.setOpaque(false);
		Jexit.setBorderPainted(false);
		Jexit.setContentAreaFilled(false);
		Jexit.setFocusPainted(false);

		JPanel jback = new JPanel() {
			public void paintComponent(Graphics g) {

				g.drawImage(Jback, 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		jback.setBounds(0, 0, 500, 750);
		this.add(jback);

	}
}