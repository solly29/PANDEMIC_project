package Game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Game extends JFrame {
	MainPanel Mainpanel;// 게임이 보여지는 Main패널
	public static boolean RedCure = false;//빨간 치료제
	public static boolean BlueCure = false;//파란 치료제
	static boolean YellowCure = false;//노란 치료제
	static boolean BlackCure = false;//검은 치료제

	public Game() {
		/*setLayout(null);
		setSize(1920, 1080);
		Mainpanel = new MainPanel();//Game이 작동할 MainPanel
		add(Mainpanel);
		Mainpanel.setBounds(0, 0, 1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);*/
	}

	public static void main(String[] args) {
		new Game();
	}
}
