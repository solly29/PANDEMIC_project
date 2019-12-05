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

	private Image background = new ImageIcon(Client.class.getResource("../Login_Image/background.png")).getImage(); //배경화면
	private ImageIcon loginpush = new ImageIcon(Client.class.getResource("../Login_Image/Login2.png"));
	private ImageIcon exitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));
	private ImageIcon joinpush = new ImageIcon(Client.class.getResource("../Login_Image/Join2.png"));
	private ImageIcon findpush = new ImageIcon(Client.class.getResource("../Login_Image/Find2.png"));
	private ImageIcon loginimage = new ImageIcon(Client.class.getResource("../Login_Image/Login.png"));
	private ImageIcon exitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
	private ImageIcon joinimage = new ImageIcon(Client.class.getResource("../Login_Image/Join.png"));
	private ImageIcon findimage = new ImageIcon(Client.class.getResource("../Login_Image/Find.png"));

	private JButton login = new JButton(loginimage); // 로그인 버튼
	
	private JButton exit = new JButton(exitimage); // 나가기 버튼
	
	private JButton join = new JButton(joinimage); // 회원가입 버튼
	
	private JButton find = new JButton(findimage); // ID/PWD 찾기 버튼
	
	private static JFrame top;
	private JTextField idtext;
	private JPasswordField pwdtext;
	
	

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
		JLabel id = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/ID.png")));
		JLabel pwd = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/PWD.png")));

		idtext = new JTextField();
		pwdtext = new JPasswordField();


		setLayout(null);

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

		login.setOpaque(false); // 투명하게
		 login.setBorderPainted(false);// 외곽선없애기
		 login.setContentAreaFilled(false);// 내용영역 채우기 없애기
		 login.setFocusPainted(false);// 테두리 사용 안함

		exit.setOpaque(false);
		 exit.setBorderPainted(false);// 외곽선없애기


		join.setOpaque(false);
		join.setBorderPainted(false);
		join.setFocusPainted(false);

		find.setOpaque(false);
		find.setBorderPainted(false);
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
		
		  login.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
		         public void mouseEntered(MouseEvent e) {
		        	 login.setIcon(loginpush);// 버튼 도달했을때 모양이 바뀐다
		         }

		         public void mouseExited(MouseEvent e) {
		        	 login.setIcon(loginimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
		         }

		         public void mousePressed(MouseEvent e) {
		        	 login.setIcon(loginpush);// 버튼 클릭했을때 모양이 바뀐다
		         }

		      });
		  
		  exit.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
		         public void mouseEntered(MouseEvent e) {
		        	 exit.setIcon(exitpush);// 버튼 도달했을때 모양이 바뀐다
		         }

		         public void mouseExited(MouseEvent e) {
		        	 exit.setIcon(exitimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
		         }

		         public void mousePressed(MouseEvent e) {
		        	 exit.setIcon(exitpush);// 버튼 클릭했을때 모양이 바뀐다
		         }

		      });
		  
		  find.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
		         public void mouseEntered(MouseEvent e) {
		        	 find.setIcon(findpush);// 버튼 도달했을때 모양이 바뀐다
		         }

		         public void mouseExited(MouseEvent e) {
		        	 find.setIcon(findimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
		         }

		         public void mousePressed(MouseEvent e) {
		        	 find.setIcon(findpush);// 버튼 클릭했을때 모양이 바뀐다
		         }

		      });
		  
		  join.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
		         public void mouseEntered(MouseEvent e) {
		        	 join.setIcon(joinpush);// 버튼 도달했을때 모양이 바뀐다
		         }

		         public void mouseExited(MouseEvent e) {
		        	 join.setIcon(joinimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
		         }

		         public void mousePressed(MouseEvent e) {
		        	 join.setIcon(joinpush);// 버튼 클릭했을때 모양이 바뀐다
		         }

		      });
		  

	}
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit) {
			System.exit(0);
			
			// c.dispose(); j.dispose(); f.dispose(); 이상하게 안됨... 그래서 일단 System.exit(0) 씀
			 

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
			} else {
				JOptionPane.showMessageDialog(null, "ID 또는 Password를 잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);

			}

		}
	}

	public static JFrame getTop() {
		return top;
	}
}

class joinE extends JFrame // 회원가입 창 만드는 클래스

{
	private Image Jback = new ImageIcon(Client.class.getResource("../Login_Image/Jback.png")).getImage();
	private ImageIcon Jjoinimage = new ImageIcon(Client.class.getResource("../Login_Image/Join.png"));
	private ImageIcon Jexitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
	private ImageIcon Jjoinpush = new ImageIcon(Client.class.getResource("../Login_Image/Join2.png"));
	private ImageIcon Jexitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));
	private ImageIcon dupleimage = new ImageIcon(Client.class.getResource("../Login_Image/duple.png"));
	private ImageIcon duplepush = new ImageIcon(Client.class.getResource("../Login_Image/duple2.png"));
	
	
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
		
		 Name_text.setFont(new Font("HY헤드라인M",Font.PLAIN,20));
		 Number_text.setFont(new Font("HY헤드라인M",Font.PLAIN,20));
		 ID_text.setFont(new Font("HY헤드라인M",Font.PLAIN,20));		
		 PWD_text.setFont(new Font("굴림",Font.PLAIN,20));
		
		 Jjoin.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
	         public void mouseEntered(MouseEvent e) {
	        	 Jjoin.setIcon(Jjoinpush);// 버튼 도달했을때 모양이 바뀐다
	         }

	         public void mouseExited(MouseEvent e) {
	        	 Jjoin.setIcon(Jjoinimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
	         }

	         public void mousePressed(MouseEvent e) {
	        	 Jjoin.setIcon(Jjoinpush);// 버튼 클릭했을때 모양이 바뀐다
	         }

	      });
		 Duple.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
	         public void mouseEntered(MouseEvent e) {
	        	 Duple.setIcon(duplepush);// 버튼 도달했을때 모양이 바뀐다
	         }

	         public void mouseExited(MouseEvent e) {
	        	 Duple.setIcon(dupleimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
	         }

	         public void mousePressed(MouseEvent e) {
	        	 Duple.setIcon(duplepush);// 버튼 클릭했을때 모양이 바뀐다
	         }

	      });
		 Jexit.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
	         public void mouseEntered(MouseEvent e) {
	        	 Jexit.setIcon(Jexitpush);// 버튼 도달했을때 모양이 바뀐다
	         }

	         public void mouseExited(MouseEvent e) {
	        	 Jexit.setIcon(Jexitimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
	         }

	         public void mousePressed(MouseEvent e) {
	        	 Jexit.setIcon(Jexitpush);// 버튼 클릭했을때 모양이 바뀐다
	         }

	      });
		
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
						JOptionPane.showMessageDialog(null, "다른 ID를 사용해주세요", "오류", JOptionPane.WARNING_MESSAGE);

					} else
						JOptionPane.showMessageDialog(null, "사용가능한 ID입니다");

				} catch (IOException e) {
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
		Name_text.setBounds(200, 80, 100, 20);

		Number.setBounds(100, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 20);

		ID.setBounds(100, 215, 100, 30);
		ID_text.setBounds(200, 220, 100, 20);

		PWD.setBounds(100, 285, 100, 30);
		PWD_text.setBounds(200, 290, 100, 20);

		Jexit.setBounds(250, 400, 140, 50);
		Jjoin.setBounds(80, 400, 140, 50);

		Duple.setBounds(310, 200, 100, 50);
		

		getContentPane().add(Jexit);
		getContentPane().add(Jjoin);
		setVisible(true);
		
		Jjoin.setOpaque(false); // 투명하게
		Jjoin.setBorderPainted(false);// 외곽선없애기
		Jjoin.setContentAreaFilled(false);// 내용영역 채우기 없애기
		Jjoin.setFocusPainted(false);// 테두리 사용 안함
		
		Duple.setOpaque(false); // 투명하게
		Duple.setBorderPainted(false);// 외곽선없애기
		Duple.setContentAreaFilled(false);// 내용영역 채우기 없애기
		Duple.setFocusPainted(false);// 테두리 사용 안함
		
		Jexit.setOpaque(false); // 투명하게
		Jexit.setBorderPainted(false);// 외곽선없애기
		Jexit.setContentAreaFilled(false);// 내용영역 채우기 없애기
		Jexit.setFocusPainted(false);// 테두리 사용 안함
		
		
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

class findE extends JFrame // ID/PWD 찾기 창 만드는 클래스
{
	
	private Image Jback = new ImageIcon(Client.class.getResource("../Login_Image/Jback.png")).getImage();
	private ImageIcon Ffindimage = new ImageIcon(Client.class.getResource("../Login_Image/Find.png"));
	private ImageIcon Ffindpush = new ImageIcon(Client.class.getResource("../Login_Image/Find2.png"));
	private ImageIcon Fexitimage = new ImageIcon(Client.class.getResource("../Login_Image/Exit.png"));
	private ImageIcon Fexitpush = new ImageIcon(Client.class.getResource("../Login_Image/Exit2.png"));
	
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

		JButton Fexit = new JButton(Fexitimage);
		JButton ID_Find = new JButton(Ffindimage);

		JLabel Name = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/JID.PNG")));
		JLabel Number = new JLabel(new ImageIcon(Client.class.getResource("../Login_Image/Jnumber.PNG")));

		JTextField Name_text = new JTextField();
		JTextField Number_text = new JTextField();

		setTitle("ID/PWD 찾기");
		setSize(500, 750);
		setLocation(300, 120);
		 Name_text.setFont(new Font("HY헤드라인M",Font.PLAIN,20));
		 Number_text.setFont(new Font("HY헤드라인M",Font.PLAIN,20));

		
		
		Name.setBounds(120, 75, 100, 30);
		Name_text.setBounds(200, 80, 100, 20);

		Number.setBounds(100, 145, 100, 30);
		Number_text.setBounds(200, 150, 100, 20);

		Fexit.setBounds(250, 400, 140, 50);
		ID_Find.setBounds(80, 400, 140, 50);

		this.add(Number_text);
		this.add(Name_text);
		this.add(Name);
		this.add(Number);
		this.add(ID_Find);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		setUndecorated(true); // 작업표시줄 없애기
		
		
		Fexit.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
	         public void mouseEntered(MouseEvent e) {
	        	 Fexit.setIcon(Fexitpush);// 버튼 도달했을때 모양이 바뀐다
	         }

	         public void mouseExited(MouseEvent e) {
	        	 Fexit.setIcon(Fexitimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
	         }

	         public void mousePressed(MouseEvent e) {
	        	 Fexit.setIcon(Fexitpush);// 버튼 클릭했을때 모양이 바뀐다
	         }

	      });
		 ID_Find.addMouseListener(new MouseAdapter() { // 방찾기버튼 마우스액션
	         public void mouseEntered(MouseEvent e) {
	        	 ID_Find.setIcon(Ffindpush);// 버튼 도달했을때 모양이 바뀐다
	         }

	         public void mouseExited(MouseEvent e) {
	        	 ID_Find.setIcon(Ffindimage);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
	         }

	         public void mousePressed(MouseEvent e) {
	        	 ID_Find.setIcon(Ffindpush);// 버튼 클릭했을때 모양이 바뀐다
	         }

	      });

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
						JOptionPane.showMessageDialog(null, "이름 또는 학번을 잘못 입력 하셨습니다.", "오류",
								JOptionPane.WARNING_MESSAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		Fexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});// 버튼 누르면 창 종료

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
		
		Fexit.setOpaque(false); // 투명하게
		Fexit.setBorderPainted(false);// 외곽선없애기
		Fexit.setContentAreaFilled(false);// 내용영역 채우기 없애기
		Fexit.setFocusPainted(false);// 테두리 사용 안함
		
		ID_Find.setOpaque(false); // 투명하게
		ID_Find.setBorderPainted(false);// 외곽선없애기
		ID_Find.setContentAreaFilled(false);// 내용영역 채우기 없애기
		ID_Find.setFocusPainted(false);// 테두리 사용 안함
		
		
	}
}
