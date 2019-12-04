package Game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.MainPanel.Map;
//컨트롤 패널은 캐릭터의 모든 행동을 제어하는 행동이 있는 패널이다.
public class ControlPanel extends ControlShape {// 컨트롤 패널
	ImageIcon back = new ImageIcon(Map.class.getResource("../Image/back.png"));// 뒤로가기버튼
	MainPanel Mainpanel;// 상위자원 MainPanel을 사용하기 위해 선언
	ControlPanel Controlpanel = this;//자기 자신의 자원을 넘겨주기 위해
	HaveCard1 Havecard = new HaveCard1(Controlpanel);// 손패 카드

	ControlPanel(MainPanel Mainpanel) {
		this.Mainpanel = Mainpanel;// 상위자원을 연결해서 쓸수있게
		
		//Havecard.insertCard(Controlpanel,"항체보유자");
		/*Havecard.insertCard(Controlpanel,"서울", "Red");
		Havecard.insertCard(Controlpanel,"상하이", "Red");
		Havecard.insertCard(Controlpanel,"홍콩", "Red");
		Havecard.insertCard(Controlpanel,"도쿄", "Red");*/
		//위는 테스트를 위해 임의의 카드를 넣어 둔것이다.
		
	//	setSize(new Dimension(1920, 200));
		setLayout(new BorderLayout()); // 컨트롤 패널의 기본
		add(new BasicSelect(Controlpanel));
	}
}
