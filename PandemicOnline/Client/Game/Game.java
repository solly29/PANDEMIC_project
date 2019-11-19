package Game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Game extends JFrame {
	MainPanel Mainpanel;//게임이 보여지는 Main패널

	/*
	 * private Image screenImage; private Graphics screenGraphic;
	 */
	public Game() {
		/*setLayout(null);
		// setPreferredSize(new Dimension(1920, 1080));
		setSize(1920, 1080);
		Mainpanel = new MainPanel();
		add(Mainpanel);
		Mainpanel.setBounds(0, 0, 1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);*/
	}

	public static void main(String[] args) {
		new Game();
	}
	
	/*
	 * public void paintComponent(Graphics g) {
	 * screenImage=createImage(Game.WIDTH,Game.HEIGHT);
	 * screenGraphic=screenImage.getGraphics(); screenDraw(screenGraphic);
	 * g.drawImage(screenImage, 0, 0, null); } public void screenDraw(Graphics g) {
	 * this.repaint(); }
	 */
}
