package Game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pandemic.Client;

public class AirplanePanel extends ControlShape {
	ControlPanel Controlpanel;
	JScrollPane scroll;
	JPanel panel;
	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/button.png"));
	String CharacterCurrentCity;

	public AirplanePanel(ControlPanel Controlpanel) {
		panel = new JPanel();
		panel.setLayout(new GridLayout(8, 8, 1, 1));
		scroll = new JScrollPane(panel);
		scroll.setSize(this.getSize());

		this.Controlpanel = Controlpanel;
		setLayout(new BorderLayout());
		CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
		ArrayList<Card> HandCards = Controlpanel.Havecard.ReArray();
		String[] text = Controlpanel.Mainpanel.citys.returntext();
		JLabel[] lcity = new JLabel[49];

		// panel.add(new BackLabel(Controlpanel));
		lcity[0] = new JLabel("Back", button, JLabel.CENTER);
		lcity[0].setVerticalTextPosition(JLabel.CENTER);
		lcity[0].setHorizontalTextPosition(JLabel.CENTER);
		lcity[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
				Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
			}
		});
		panel.add(lcity[0]);

		for (int i = 0; i < HandCards.size(); i++) {
			if (CharacterCurrentCity.equals(HandCards.get(i).getCityName())) {
				for (int j = 1; j < 49; j++) {
					lcity[j] = new JLabel(button);
					lcity[j].setText(text[j]);
					lcity[j].setVerticalTextPosition(JLabel.CENTER);
					lcity[j].setHorizontalTextPosition(JLabel.CENTER);
					lcity[j].addMouseListener(new AirplaneEvent());

					panel.add(lcity[j]);
				}
				panel.add(new JLabel());
			}
		}
		add(scroll);
	}
	
	public AirplanePanel(ControlPanel Controlpanel, String builder) {
		panel = new JPanel();
		panel.setLayout(new GridLayout(8, 8, 1, 1));
		scroll = new JScrollPane(panel);
		scroll.setSize(this.getSize());

		this.Controlpanel = Controlpanel;
		setLayout(new BorderLayout());
		CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();

		String[] text = Controlpanel.Mainpanel.citys.returntext();
		JLabel[] lcity = new JLabel[49];

		// panel.add(new BackLabel(Controlpanel));
		lcity[0] = new JLabel("Back", button, JLabel.CENTER);
		lcity[0].setVerticalTextPosition(JLabel.CENTER);
		lcity[0].setHorizontalTextPosition(JLabel.CENTER);
		lcity[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
				Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
			}
		});
		panel.add(lcity[0]);

		for (int j = 1; j < 49; j++) {
			lcity[j] = new JLabel(button);
			lcity[j].setText(text[j]);
			lcity[j].setVerticalTextPosition(JLabel.CENTER);
			lcity[j].setHorizontalTextPosition(JLabel.CENTER);
			lcity[j].addMouseListener(new AirplaneEvent());

			panel.add(lcity[j]);
		}
		panel.add(new JLabel());

		add(scroll);
	}

	class AirplaneEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (!Client.CardPrint) {
				JLabel label = (JLabel) e.getSource();
				String Choicecity = label.getText();
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]"+Client.name+":"+Choicecity);
					Controlpanel.Havecard.removeCard(CharacterCurrentCity);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*Point ChoicePoint = Controlpanel.Mainpanel.citys.CityPosition(Choicecity);
				Controlpanel.Mainpanel.characterList.get(Client.name).setXY(ChoicePoint.x, ChoicePoint.y);
				Controlpanel.Mainpanel.characterList.get(Client.name).setCC(Choicecity,
						Controlpanel.Mainpanel.citys.returnCity(Choicecity).getColor());
				Controlpanel.Havecard.removeCard(CharacterCurrentCity);

				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
				Controlpanel.Mainpanel.repaint();*/
			}
		}
	}
}
