package Game;
//손패 손에가지고있는 카드들을 보여주는 패널
import java.awt.Color;
import java.util.ArrayList;

public class HandPanel extends ControlShape {
	ControlPanel Controlpanel;
	ArrayList<Card> CardsLabel;//카드들 리스트

	public HandPanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		CardsLabel = Controlpanel.Havecard.ReArray();//내가 지금가지고있는 모든 카드들을 리스트에 추가한다.
		for(int i=0;i<CardsLabel.size();i++) {
		add(CardsLabel.get(i));//그 후 패널에 추가
		}
		Controlpanel.Mainpanel.Controlpanel.setBounds(0, 750, 1920, 500);
		
		add(new BackLabel(Controlpanel));
	}
}
