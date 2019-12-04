package Game;

import java.awt.Color;
import java.util.ArrayList;

public class HandPanel extends ControlShape {
	ControlPanel Controlpanel;
	ArrayList<Card> CardsLabel;

	public HandPanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		CardsLabel = Controlpanel.Havecard.ReArray();
		for(int i=0;i<CardsLabel.size();i++) {
		add(CardsLabel.get(i));}
		Controlpanel.Mainpanel.Controlpanel.setBounds(0, 750, 1920, 500);
		
		add(new BackLabel(Controlpanel));
	}
}
