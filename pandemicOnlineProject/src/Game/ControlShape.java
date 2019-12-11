package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.MainPanel.Map;

public class ControlShape extends JPanel {
	//컨트롤 패널들의 모양 및 뒷배경
	Image shape = new ImageIcon(Map.class.getResource("../Image/buttonzone.png")).getImage();

	public ControlShape() {
		setOpaque(false);
		setPreferredSize(new Dimension(1920, 300));	//크기설정
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(shape, 0, 0, null);//뒷배경을 그린다

	}
}
