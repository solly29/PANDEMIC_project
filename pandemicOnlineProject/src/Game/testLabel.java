package Game;

import java.awt.Color;
import java.awt.Font;
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

public class testLabel extends Thread{
	MainPanel t;
	JLabel l;
	String[] text;
	ArrayList<String> arr = new ArrayList<String>();
	Queue<String> qu = new LinkedList<String>();
	int size=0;
	boolean print=false;
	public testLabel(MainPanel panel, String[] str) {
		// TODO Auto-generated constructor stub
		this.t = panel;
		l = new JLabel();
		size = str.length;
		for(int i=0;i<size;i++)
			qu.add(str[i]);
		System.out.println("실행2");
		//setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			/*while(true) {
			synchronized(this){
				this.wait();
				    
			}*/
			print = true;
			
			for(int i=0;i<size;i++) {
				//JLabel l = new JLabel(text[i]);
				l.setText(qu.poll());
				l.setFont(new Font("바탕",Font.BOLD,30));
				l.setForeground(Color.white);
				t.add(l, new Integer(20));
				l.setBounds(700, 400, 500, 100);
				System.out.println(l.getText());
				Thread.sleep(1000);
				t.remove(l);
				t.repaint();
				t.revalidate();
				Thread.sleep(500);
				
			}
			print = false;
			//}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
