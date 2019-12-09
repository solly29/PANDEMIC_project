package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.MainPanel.Map;
import pandemic.Client;

public class CurePanel extends ControlShape {
	ControlPanel Controlpanel;

	public CurePanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		JLabel red = CreateLabel("Red");
		JLabel blue = CreateLabel("Blue");
		JLabel yellow = CreateLabel("Yellow");
		JLabel black = CreateLabel("Black");
		add(red);
		add(blue);
		add(yellow);
		add(black);
		add(new BackLabel(Controlpanel));
	}

	public JLabel CreateLabel(String color) {
		JLabel e = new JLabel(new ImageIcon(Map.class.getResource("../Image/" + color + "Virus.png")));
		e.setText(color + "치료제 개발");
		e.setVerticalTextPosition(JLabel.BOTTOM);
		e.setHorizontalTextPosition(JLabel.CENTER);
		e.setForeground(Color.white);
		e.setFont(new Font("HY헤드라인M",Font.PLAIN,20));
		e.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(!Client.CardPrint) {
					Controlpanel.Havecard.DevelopeCure(color);
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new BasicSelect(Controlpanel));
					Controlpanel.revalidate();
					Controlpanel.repaint();
					Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
				}
			}
		});
		return e;
	}
}
