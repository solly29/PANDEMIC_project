package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*public class test extends JFrame{
	Thread th;
	JPanel p;
	public test() {
		// TODO Auto-generated constructor stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(500, 500);
		p = new JPanel();
		JLabel l = new JLabel("ghkrdls");
		testLabel t = new testLabel(p);
		th = new Thread(t);
		th.start();
		p.add(t);
		p.add(l);
		t.setBounds(10, 10, 300, 300);
		add(p);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new test();
	}

}*/

public class cardLabel extends Thread{
	MainPanel t;
	JLabel l;
	String[] text;
	ArrayList<String> arr = new ArrayList<String>();
	Queue<String> qu = new LinkedList<String>();
	int size=0;
	boolean print=false;
	int count;
	public cardLabel(MainPanel panel, String[] str) {
		// TODO Auto-generated constructor stub
		this.t = panel;
		l = new JLabel();
		size = str.length-1;
		count = Integer.parseInt(str[size]);
		for(int i=0;i<size;i++)
			qu.add(str[i]);
		System.out.println("실행2");
		//setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			for(int i=0;i<size;i++) {
				//JLabel l = new JLabel(text[i]);
				String cityName = qu.poll();
				City city = t.citys.returnCity(cityName);
				t.map.setLocation(-city.getX()+(1960/2), -city.getY()+(1020/2)-120);
				String color = t.citys.returnCity(cityName).color;
				/*l.setText(cityName);
				l.setFont(new Font("바탕",Font.BOLD,30));
				l.setForeground(Color.white);*/
				CardPrint(color,cityName);
				t.add(l, new Integer(20));
				l.setBounds(500, 300, 335, 245);
				l.setBackground(null);
				
				Thread.sleep(1000);
				
				city.PlusVirus(color,count);
				City.visit.removeAll(City.visit);
				
				t.remove(l);
				t.repaint();
				t.revalidate();
				Thread.sleep(500);
				
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CardPrint(String color, String CityName) {
		Image Card = new ImageIcon(Card.class.getResource("../Image/" + color + "InfCard.png")).getImage();
		// Image CardPush = new ImageIcon(Card.class.getResource("../Image/CityCard" +
		// color + "Push.png")).getImage();
		BufferedImage BufferedCard = new BufferedImage(Card.getWidth(null), Card.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);// 주의! 이미지에서 바로 getGraphics이 먹히지 않으므로 버퍼드이미지에서 글자를 그린다.
		Graphics2D g = (Graphics2D) BufferedCard.getGraphics();
		g.drawImage(Card, 0, 0, null);
		g.setFont(new Font("굴림", Font.BOLD, 25));
		g.setColor(Color.BLACK);
		g.drawString(CityName, 65, 50);
		//g.drawString(CityName, 7, 260);

		l.setIcon(new ImageIcon(BufferedCard));
	}
}
