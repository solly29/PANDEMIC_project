package pandemic;

import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import DB.DOA;

public class Login extends JPanel implements ActionListener {

	private Image background = new ImageIcon(Client.class.getResource("../Login_Image/background.png")).getImage();

	private JButton login = new JButton("Login"); // 로그인 버튼
	private JButton exit = new JButton("EXIT"); // 나가기 버튼
	private JButton join = new JButton("회원가입"); // 회원가입 버튼
	private JButton find = new JButton("ID/PWD 찾기"); // ID/PWD 찾기 버튼
	private static JFrame top;
	private JTextField idtext;
	private JTextField pwdtext;

	DataInputStream input;
	DataOutputStream output;

	Client c;

	Socket gsocket, socket2;

	joinE j;
	findE f;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

	public Login(Socket gsocket, Socket socket2) {
		System.out.println("Login 소켓연결 전");
		this.gsocket = gsocket;
		this.socket2 = socket2;
		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Login 소켓연결 후");
		JLabel id = new JLabel("ID ");
		JLabel pwd = new JLabel("PWD ");

		idtext = new JTextField();
		pwdtext = new JTextField();

		Font RLfont, ChatFont, PFfont; // 폰트 추가 차례대로 방목록,채팅,내정보

		RLfont = new Font("Serif", Font.BOLD, 50);

		setLayout(null);

		login.setBounds(1153, 500, 150, 170);
		exit.setBounds(1050, 710, 140, 50);
		join.setBounds(750, 710, 140, 50);
		find.setBounds(900, 710, 140, 50);

		id.setBounds(730, 500, 100, 100);
		pwd.setBounds(730, 560, 100, 100);

		idtext.setBounds(790, 530, 300, 30);
		pwdtext.setBounds(790, 590, 300, 30);

		login.setOpaque(false); // 투명하게

		exit.setOpaque(false);
		// exit.setBorderPainted(false);// 외곽선없애기
		// exit.setContentAreaFilled(false);// 내용영역 채우기 없애기
		// exit.setFocusPainted(false);// 테두리 사용 안함

		join.setOpaque(false);
		join.setBorderPainted(false);
		// join.setContentAreaFilled(false);
		join.setFocusPainted(false);

		find.setOpaque(false);
		find.setBorderPainted(false);
		// find.setContentAreaFilled(false);
		find.setFocusPainted(false);

		add(login);
		add(exit);
		add(join);
		add(find);
		add(id);
		add(pwd);
		add(idtext);
		add(pwdtext);
		login.addActionListener(this);
		exit.addActionListener(this);
		join.addActionListener(this);
		find.addActionListener(this);

		setVisible(true); // 해결과제 채팅창 크기조절, 배경사진넣기(전부)

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit) {
			System.exit(0);
			/*
			 * c.dispose(); j.dispose(); f.dispose(); 이상하게 안됨... 그래서 일단 System.exit(0) 씀
			 */

			

		} else if (e.getSource() == join) { // 회원가입 버튼 누르면 joinE 클래스로 감
			j = new joinE(gsocket);
		} else if (e.getSource() == find) { // ID, PWD 찾기 버튼 누르면 findE 클래스로 감
			f = new findE(gsocket);
		} else if (e.getSource() == login) { // 로그인 버튼 누르면 ID, PWD 확인 후, 맞으면 로비로 틀리면 경고 
			String str = null;
			try {
				output.writeUTF("login");
				output.writeUTF(idtext.getText());
				output.writeUTF(pwdtext.getText());
				str = input.readUTF();
				System.out.println(str);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (str.equals("true")) {
				Client.name = idtext.getText();
				top = (JFrame) SwingUtilities.getWindowAncestor(this);

				top.getContentPane().removeAll();

				top.getContentPane().add(new Lobby(gsocket, socket2));

				top.revalidate();
				top.repaint();
			}else {
				JOptionPane.showMessageDialog(null, "ID 또는 Password를 잘못 입력하셨습니다.","오류" ,JOptionPane.WARNING_MESSAGE);
				
			}

		}
	}

	public static JFrame getTop() {
		return top;
	}
}

class joinE extends JFrame // 회원가입 창 만드는 클래스
{

	private DataOutputStream output;
	private DataInputStream input;

	public joinE(Socket socket) {

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

		JButton Jexit = new JButton("exit");
		JButton Jjoin = new JButton("회원가입");
		JButton Duple = new JButton("중복확인");

		JLabel ID = new JLabel("ID      : ");
		JLabel PWD = new JLabel("PWD : ");
		JLabel Name = new JLabel("이름   : ");
		JLabel Number = new JLabel("학번   : ");

		JTextField Name_text = new JTextField();
		JTextField Number_text = new JTextField();
		JTextField ID_text = new JTextField();
		JTextField PWD_text = new JTextField();

		setSize(500, 750);
		setTitle("회원가입");
		setLocation(300, 120);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Jexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});// 회원가입 창에 있는 버튼 누르면 창 종료
		Duple.addActionListener(new ActionListener() { // 중복확인 버튼 누르면 같은 ID가 있는지 확인 함
			public void actionPerformed(ActionEvent arg0) {
				try {
					
				output.writeUTF("duple");
				output.writeUTF(ID_text.getText());
				
				if (input.readUTF().equals("false")) { // ID가 같으면 경고 메세지
					JOptionPane.showMessageDialog(null, "다른 ID를 사용해주세요","오류" ,JOptionPane.WARNING_MESSAGE);
				
				} else
					JOptionPane.showMessageDialog(null, "사용가능한 ID입니다");
				
				}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

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
						JOptionPane.showMessageDialog(null, "회원가입 실패","오류" ,JOptionPane.WARNING_MESSAGE);

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

		Name.setBounds(150, 75, 100, 30);
		Name_text.setBounds(200, 80, 100, 20);

		Number.setBounds(150, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 20);

		ID.setBounds(150, 215, 100, 30);
		ID_text.setBounds(200, 220, 100, 20);

		PWD.setBounds(150, 285, 100, 30);
		PWD_text.setBounds(200, 290, 100, 20);

		Jexit.setBounds(250, 400, 150, 50);
		Jjoin.setBounds(80, 400, 150, 50);

		Duple.setBounds(310, 220, 100, 20);

		getContentPane().add(Jexit);
		getContentPane().add(Jjoin);
		setVisible(true);
	}
}

class findE extends JFrame // ID/PWD 찾기 창 만드는 클래스
{

	private DataOutputStream output;
	private DataInputStream input;

	public findE(Socket socket) {

		
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

		JButton FEXIT = new JButton("EXIT");
		JButton ID_Find = new JButton("ID/PWD 찾기");

		JLabel Name = new JLabel("이름   : ");
		JLabel Number = new JLabel("학번   : ");

		JTextField Name_text = new JTextField();
		JTextField Number_text = new JTextField();

		setTitle("ID/PWD 찾기");
		setSize(500, 750);
		setLocation(300, 120);

		Name.setBounds(150, 75, 100, 30);
		Name_text.setBounds(200, 80, 100, 20);

		Number.setBounds(150, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 20);

		FEXIT.setBounds(250, 400, 150, 50);
		ID_Find.setBounds(80, 400, 150, 50);

		this.add(Number_text);
		this.add(Name_text);
		this.add(Name);
		this.add(Number);
		this.add(ID_Find);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ID_Find.addActionListener(new ActionListener() { // ID, PWD 찾기를 눌렀을때
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
						JOptionPane.showMessageDialog(null, "이름 또는 학번을 잘못 입력 하셨습니다.","오류" ,JOptionPane.WARNING_MESSAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		FEXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});// 버튼 누르면 창 종료

		getContentPane().add(FEXIT);
		setVisible(true);
	}
}
