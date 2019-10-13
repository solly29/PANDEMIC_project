import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.xml.soap.Text;

public class Login extends JPanel {
	private JLabel lblNewLabel;
	private JButton LoginButton;
	private TextField ID_TextField;
	private TextField PW_TextField;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	private Client F;
	
	public Login(Client f, Socket socket) { 
		setSize(500,500); 
		
		
		
		F = f; 
		
		LoginButton = new JButton("Change Panel");
		ID_TextField = new TextField(50);
		PW_TextField = new TextField(50);
		LoginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID;
				String PW;
				String anw;
				
				ID = ID_TextField.getText();
				PW = PW_TextField.getText();
				try {
					OutputStream out = socket.getOutputStream();
					dos = new DataOutputStream(out);
					InputStream in = socket.getInputStream();
					dis = new DataInputStream(in);
					
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				
				try {
					System.out.println("ID 전송");
					dos.writeUTF(ID);
					dos.writeUTF(PW);
					System.out.println("anw 수신");
					anw = dis.readUTF();
					System.out.println(anw);
					if(anw.equals("true")) {
						System.out.println("lobby");
						F.changePanel();
					}
					else {
						System.out.println("wrong");
						new errorWindow();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		LoginButton.setBounds(12, 35, 113, 23); 
		add(ID_TextField);
		add(PW_TextField);
		add(LoginButton);
		setVisible(true); 
	}
}

// 에러 창
class errorWindow extends JFrame{
	
	public errorWindow() {
		setTitle("error");
		JPanel jpanel = new JPanel();
		
		JLabel jlable = new JLabel("ID or PW is not correct");
		
		jpanel.add(jlable);
		
		setSize(300, 300);
		setResizable(false);
		setVisible(true);
		
	}
}
