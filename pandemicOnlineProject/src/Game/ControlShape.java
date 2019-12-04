package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.MainPanel.Map;

public class ControlShape extends JPanel {
	
	Image shape = new ImageIcon(Map.class.getResource("../Image/buttonzone.png")).getImage();

	public ControlShape() {
		//setSize(1920,300);
		setOpaque(false);
		setBackground(Color.CYAN);
		//setPreferredSize(new Dimension(1920, 300));
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(shape, 0, 0, null);

	}
}
