package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pandemic.Client;

public class MoveLabatoryPanel extends ControlShape {
	ControlPanel Controlpanel;
	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/back.png"));

	public MoveLabatoryPanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		String[] text = Controlpanel.Mainpanel.citys.returntext();
		String CurrentyCityText = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
		if (Controlpanel.Mainpanel.citys.returnCity(CurrentyCityText).getLabatory()) {
			for (int i = 1; i < 49; i++) {
				if (Controlpanel.Mainpanel.citys.returnCity(text[i]).getLabatory()) {
					JLabel t = new JLabel("", button, JLabel.CENTER);
					t.setText(text[i]);
					t.setVerticalTextPosition(JLabel.BOTTOM);
					t.setHorizontalTextPosition(JLabel.CENTER);
					t.addMouseListener(new MoveLabatoryLabel());
					add(t);
					Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
					t.setFont(font);
					t.setForeground(Color.white);
				}
			}
		}
		JLabel Back = new JLabel("뒤로", button, JLabel.CENTER);
		
		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		Back.setFont(font);
		Back.setForeground(Color.white);

		Back.setVerticalTextPosition(JLabel.BOTTOM);
		Back.setHorizontalTextPosition(JLabel.CENTER);
		Back.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
				Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
			}
		});
		add(Back);
	}

	class MoveLabatoryLabel extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (!Client.CardPrint) {
				JLabel label = (JLabel) e.getSource();
				String Choicecity = label.getText();
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]" + Client.name + ":" + Choicecity);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*
				 * Point ChoicePoint = Controlpanel.Mainpanel.citys.CityPosition(Choicecity);
				 * Controlpanel.Mainpanel.characterList.get(Client.name).setXY(ChoicePoint.x,
				 * ChoicePoint.y);
				 * Controlpanel.Mainpanel.characterList.get(Client.name).setCC(Choicecity,
				 * Controlpanel.Mainpanel.citys.returnCity(Choicecity).getColor());
				 * 
				 * Controlpanel.invalidate(); Controlpanel.removeAll(); Controlpanel.add(new
				 * BasicSelect(Controlpanel)); Controlpanel.revalidate();
				 * Controlpanel.repaint(); Controlpanel.Mainpanel.repaint();
				 */
			}
		}
	}
}
