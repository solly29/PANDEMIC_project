package Game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.MainPanel.Map;
import pandemic.Client;

//치료제 개발 패널. 버튼(라벨)을 누를시 조건이 만족된다면 해당 색의 치료제를 개발한다
public class CurePanel extends ControlShape {
	ControlPanel Controlpanel;

	public CurePanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JLabel red = CreateLabel("Red");// 빨간치료제
		JLabel blue = CreateLabel("Blue");// 파란치료제
		JLabel yellow = CreateLabel("Yellow");// 노란치료제
		JLabel black = CreateLabel("Black");// 검은치료제
		add(red);
		add(blue);
		add(yellow);
		add(black);
		add(new BackLabel(Controlpanel));
	}

	public JLabel CreateLabel(String color) {//치료라벨의 설정을 담당
		JLabel e = new JLabel(color + "치료제 개발", new ImageIcon(Map.class.getResource("../Image/" + color + "Virus.png")),
				JLabel.CENTER);
		e.setVerticalTextPosition(JLabel.BOTTOM);
		e.setHorizontalTextPosition(JLabel.CENTER);
		e.setForeground(Color.white);
		e.setFont(new Font("HY헤드라인M", Font.PLAIN, 20));
		e.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!Client.CardPrint) {
					//현재 턴일시 가능
					Controlpanel.Havecard.DevelopeCure(color);//치료제 개발은 다른 클래스의 메소드에게 맡긴다
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
