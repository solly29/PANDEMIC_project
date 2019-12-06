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
import javax.swing.JTextField;


// 회원가입 창 만드는 클래스

public class FindWindow extends JFrame {
	DataOutputStream output;
	DataInputStream input;

	public FindWindow(Socket socket) {
		// 회원가입창에 쓰는 이미지 생성
		Image Jback = new ImageIcon(Client.class.getResource("../Login_Image/Jback.png")).getImage();
		ImageIcon Ffindimage = new ImageIcon(Client.class.getResource("../Login_Image/Find.png"));
		ImageIcon Ffindpush = new ImageIcon(Client.class.getResource("../Login_Image/Find2.png"));
		ImageIcon Fexitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
		ImageIcon Fexitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));

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

		
		//회원가입 창에서 쓰는 버튼, 라벨, 텍스트필드 생성
		JButton Fexit = new JButton(Fexitimage);
		JButton ID_Find = new JButton(Ffindimage);

		JLabel Name = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/Jname.PNG")));
		JLabel Number = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/Jnumber.PNG")));

		JTextField Name_text = new JTextField();
		JTextField Number_text = new JTextField();

		setTitle("ID/PWD 찾기");
		setSize(500, 750);
		setLocation(300, 120);
		
		//텍스트필드에서 쓸 폰트 지정
		Name_text.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));
		Number_text.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));

		
		// 버튼, 텍스트필드, 라벨 위치 조정
		Name.setBounds(100, 75, 100, 30);
		Name_text.setBounds(200, 80, 100, 30);

		Number.setBounds(100, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 30);

		Fexit.setBounds(250, 400, 140, 50);
		ID_Find.setBounds(80, 400, 140, 50);

		
		//프레임에 버튼, 텍스트필드, 라벨 추가
		this.add(Number_text);
		this.add(Name_text);
		this.add(Name);
		this.add(Number);
		this.add(ID_Find);
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUndecorated(true); // 작업표시줄 없애기

		
		//버튼 액션리스너 생성
		
		Fexit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Fexit.setIcon(Fexitpush);
			}

			public void mouseExited(MouseEvent e) {
				Fexit.setIcon(Fexitimage);
			}

			public void mousePressed(MouseEvent e) {
				Fexit.setIcon(Fexitpush);
			}

		});
		
		
		ID_Find.addMouseListener(new MouseAdapter() { 
			public void mouseEntered(MouseEvent e) {
				ID_Find.setIcon(Ffindpush);
			}

			public void mouseExited(MouseEvent e) {
				ID_Find.setIcon(Ffindimage);
			}

			public void mousePressed(MouseEvent e) {
				ID_Find.setIcon(Ffindpush);
			}

		});
		
		
		 // ID, PWD 찾기를 눌렀을때 이벤트 처리

		ID_Find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					output.writeUTF("find");
					output.writeUTF(Name_text.getText());
					output.writeUTF(Number_text.getText());
					if (input.readUTF().equals("true")) { // 학번과 이름이 맞다면 ID,PWD 보여줌
						String in = input.readUTF();
						JOptionPane.showMessageDialog(null, in);
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "이름 또는 학번을 잘못 입력 하셨습니다.", "오류",
								JOptionPane.WARNING_MESSAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
		// 나가기 버튼 누르면 창 종료
		Fexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		getContentPane().add(Fexit);
		setVisible(true);

		JPanel fback = new JPanel() {
			public void paintComponent(Graphics g) {

				g.drawImage(Jback, 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		fback.setBounds(0, 0, 500, 750);
		this.add(fback);

		// 해당 버튼 투명하게, 외각선 없애기, 내용영역 채우기 없음, 테두리 사용안함
		
		Fexit.setOpaque(false);
		Fexit.setBorderPainted(false);
		Fexit.setContentAreaFilled(false);
		Fexit.setFocusPainted(false);

		ID_Find.setOpaque(false);
		ID_Find.setBorderPainted(false);
		ID_Find.setContentAreaFilled(false);
		ID_Find.setFocusPainted(false);

	}

}
