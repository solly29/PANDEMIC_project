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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JPanel implements ActionListener {

	
	Image background=new ImageIcon(Client.class.getResource("../Login_Image/background.png")).getImage();

	JButton login = new JButton("Login"); // 로그인 버튼
	JButton exit = new JButton("EXIT"); // 나가기 버튼
	JButton join = new JButton("회원가입"); // 회원가입 버튼
	JButton find = new JButton("ID/PWD 찾기"); // ID/PWD 찾기 버튼
	private static  JFrame top;
	JTextField idtext;
	JTextField pwdtext;
	
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

	public Login( Socket gsocket, Socket socket2) {

	
		this.gsocket = gsocket;
		this.socket2 = socket2;
		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		JLabel id = new JLabel("ID ");
		JLabel pwd = new JLabel("PWD ");

		idtext = new JTextField();
		pwdtext = new JTextField();

		Font RLfont, ChatFont, PFfont; // 폰트 추가 차례대로 방목록,채팅,내정보

		RLfont = new Font("Serif", Font.BOLD, 50);


		setLayout(null);

		login.setBounds(1200, 525, 100, 100);
		exit.setBounds(1190, 700, 120, 60);
		join.setBounds(800, 700, 85, 50);
		find.setBounds(930, 700, 140, 50);

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
			c.dispose();
			j.dispose();
			f.dispose();
		} else if (e.getSource() == join) {
			j = new joinE();
		}
		else if (e.getSource() == find) {
			f = new findE();
		}
		else if(e.getSource()==login) {
			String str=null;
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
			
			if(str.equals("true")) {
				top=(JFrame)SwingUtilities.getWindowAncestor(this);
				top.getContentPane().removeAll();
				
				top.getContentPane().add(new Lobby(gsocket, socket2));
				top.revalidate();
				top.repaint();
			}
			
			
		}
	}
	public static JFrame getTop() {
		return top;
	}
}

class joinE extends JFrame // 회원가입 창 만드는 클래스
{
	public joinE() {

		JButton Jexit = new JButton("exit");
		setTitle("회원가입");
		setSize(500, 600);
		setLocation(0, 120);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Jexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});// 회원가입 창에 있는 버튼 누르면 창 종료

		Jexit.setBounds(100, 10, 100, 50);
		getContentPane().add(Jexit);
		setVisible(true);
	}
}

class findE extends JFrame //  회원가입 창 만드는 클래스
{
	public findE() {

		JButton Jfind = new JButton("find");
		setTitle("ID/PWD 찾기");
		setSize(500, 600);
		setLocation(0, 120);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Jfind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});// 회원가입 창에 있는 버튼 누르면 창 종료

		Jfind.setBounds(100, 10, 100, 50);
		getContentPane().add(Jfind);
		setVisible(true);
	}
}

