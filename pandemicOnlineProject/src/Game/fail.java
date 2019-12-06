package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Game.MainPanel.Map;
import pandemic.Client;
import pandemic.Lobby;
import pandemic.Login;

public class fail extends JPanel {

	Image background = new ImageIcon(fail.class.getResource("../Image/fail.png")).getImage();
	Socket gsocket;
	Socket csocket;
	public fail() {

		setBounds(600, 0, 711, 793);
		setOpaque(false);
		gsocket = MainPanel.gSocket;
		csocket = MainPanel.cSocket;

		System.out.println("fail 에서 실행  됨");
		
		addMouseListener(new myMouseListener());
	}
	
	class myMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("클릭함");
			
			try {
				MainPanel.GameOutStream.writeUTF("[Exit]");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JFrame top = Login.getTop();
			
			 top.getContentPane().removeAll();
			 top.getContentPane().add(new Lobby(gsocket, csocket)).requestFocusInWindow();
			 top.revalidate();
			 top.repaint();
             
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		setVisible(true);
	}
}
