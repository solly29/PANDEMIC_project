package pandemic;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.SwingUtilities;

public class Login extends JPanel implements ActionListener {

	//차례대로 배경화면, 로그인버튼, 나가기버튼, 회원가입 버튼, ID/PWD찾기 버튼에 넣을 이미지 생성
	public Music_Back bgm=new Music_Back("bgm.mp3", true);
	private Image background = new ImageIcon(Client.class.getResource("../Login_Image/background.png")).getImage();
	private ImageIcon loginpush = new ImageIcon(Client.class.getResource("../Login_Image/Login2.png"));
	private ImageIcon exitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));
	private ImageIcon joinpush = new ImageIcon(Client.class.getResource("../Login_Image/Join2.png"));
	private ImageIcon findpush = new ImageIcon(Client.class.getResource("../Login_Image/Find2.png"));
	private ImageIcon loginimage = new ImageIcon(Client.class.getResource("../Login_Image/Login.png"));
	private ImageIcon exitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
	private ImageIcon joinimage = new ImageIcon(Client.class.getResource("../Login_Image/Join.png"));
	private ImageIcon findimage = new ImageIcon(Client.class.getResource("../Login_Image/Find.png"));
	
	//차례대로 로그인, 나가기, 회원가입, 찾기 버튼 생성

	private JButton login = new JButton(loginimage);
	private JButton exit = new JButton(exitimage); 
	private JButton join = new JButton(joinimage);
	private JButton find = new JButton(findimage);
	
	private static JFrame top; //Client의 FFrame
	private JTextField idtext;
	private JPasswordField pwdtext;
	
	

	DataInputStream input;
	DataOutputStream output;

	Client c;

	Socket gsocket, socket2;

	JoinWindow j;
	FindWindow f;

	
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
		JLabel id = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/ID.png")));
		JLabel pwd = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/PWD.png")));

		idtext = new JTextField();
		pwdtext = new JPasswordField();


		setLayout(null);

		//버튼들 정렬하는 부분
		
		login.setBounds(1153, 500, 150, 170);
		exit.setBounds(1050, 710, 140, 50);
		join.setBounds(750, 710, 140, 50);
		find.setBounds(900, 710, 140, 50);

		id.setBounds(695, 495, 100, 100);
		pwd.setBounds(690, 555, 100, 100);
		
		idtext.setFont(new Font("HY헤드라인M",Font.PLAIN,20));
		pwdtext.setFont(new Font("굴림",Font.PLAIN,20));

		idtext.setBounds(790, 530, 300, 30);
		pwdtext.setBounds(790, 590, 300, 30);
		
		//차례대로 버튼을 투명하게, 외각선을 없애기, 내용영역 채우기 없애기, 테두리 사용안함.

		login.setOpaque(false);
		 login.setBorderPainted(false); 
		 login.setContentAreaFilled(false);
		 login.setFocusPainted(false);

		exit.setOpaque(false);
		 exit.setBorderPainted(false);


		join.setOpaque(false);
		join.setBorderPainted(false);
		join.setFocusPainted(false);

		find.setOpaque(false);
		find.setBorderPainted(false);
		find.setFocusPainted(false);

		
		// 버튼을 패널에 넣기
		
		add(login);
		add(exit);
		add(join);
		add(find);
		add(id);
		add(pwd);
		add(idtext);
		add(pwdtext);
		
		//각 버튼들의 액션리스너 생성
		
		login.addActionListener(this);
		exit.addActionListener(this);
		join.addActionListener(this);
		find.addActionListener(this);
		pwdtext.addActionListener(this);
		setVisible(true);
		bgm.start();
		
		
		// 차례대로 해당 버튼에 커서 닿으면 이미지 변경, 떼어졌을때 원래대로, 클릭했을때 이미지 변경
		
		  login.addMouseListener(new MouseAdapter() { 
		         public void mouseEntered(MouseEvent e) {
		        	 login.setIcon(loginpush);
		         }

		         public void mouseExited(MouseEvent e) {
		        	 login.setIcon(loginimage);
		         }

		         public void mousePressed(MouseEvent e) {
		        	 login.setIcon(loginpush);
		         }

		      });
		  
		  exit.addMouseListener(new MouseAdapter() { 
		         public void mouseEntered(MouseEvent e) {
		        	 exit.setIcon(exitpush);
		         }

		         public void mouseExited(MouseEvent e) {
		        	 exit.setIcon(exitimage);
		         }

		         public void mousePressed(MouseEvent e) {
		        	 exit.setIcon(exitpush);
		         }

		      });
		  
		  find.addMouseListener(new MouseAdapter() { 
		         public void mouseEntered(MouseEvent e) {
		        	 find.setIcon(findpush);
		         }

		         public void mouseExited(MouseEvent e) {
		        	 find.setIcon(findimage);
		         }

		         public void mousePressed(MouseEvent e) {
		        	 find.setIcon(findpush);
		         }

		      });
		  
		  join.addMouseListener(new MouseAdapter() {
		         public void mouseEntered(MouseEvent e) {
		        	 join.setIcon(joinpush);

		         }
		         public void mouseExited(MouseEvent e) {
		        	 join.setIcon(joinimage);
		         }

		         public void mousePressed(MouseEvent e) {
		        	 join.setIcon(joinpush);
		         }

		      });
		  

	}
	
	
	// 해당 버튼을 눌렀을때 이벤트 처리

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//나가기 버튼 -> 게임 꺼짐
		if (e.getSource() == exit) {
			System.exit(0);


		} else if (e.getSource() == join) { // 회원가입 버튼 누르면 JoinWindow 클래스로 감
			j = new JoinWindow(gsocket);
		} else if (e.getSource() == find) { // ID, PWD 찾기 버튼 누르면 FindWindow 클래스로 감
			f = new FindWindow(gsocket);
		}else if (e.getSource() == pwdtext) { // 엔터 누르면 로그인 됨
			login.doClick();
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
			} else {
				JOptionPane.showMessageDialog(null, "ID 또는 Password를 잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);

			}

		}
	}

	public static JFrame getTop() {
		return top;
	}
}
