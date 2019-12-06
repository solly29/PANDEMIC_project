package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pandemic.Client;

public class AirplanePanel extends ControlShape {
	ControlPanel Controlpanel;
	JPanel Scrollin;//스크롤 안에 넣을 패널
	JScrollPane scroll;//스크롤

	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/button.png"));//버튼용 이미지
	String CharacterCurrentCity;

	public AirplanePanel(ControlPanel Controlpanel) {
		Scrollin = new ControlShape();
		Scrollin.setPreferredSize(new Dimension(1920, 600));
		Scrollin.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		scroll = new JScrollPane(Scrollin);
		this.Controlpanel = Controlpanel;
		scroll.setPreferredSize(new Dimension(1920, 600));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정
		
		setLayout(new BorderLayout());

		ArrayList<Card> HandCards = Controlpanel.Havecard.ReArray();//현재 유저가 가지고있는 손패
		String[] text = Controlpanel.Mainpanel.citys.returntext();// 시티명은 앞의 공백 포함 49개다
		JLabel[] lcity = new JLabel[48];//선택할 도시들의 이름
		String CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();//현재도시

		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		this.setFont(font);
		this.setForeground(Color.white);
		
		Scrollin.add(new BackLabel(Controlpanel));//뒤로가기버튼
		for (int i = 0; i < HandCards.size(); i++) {
			if (CharacterCurrentCity.equals(HandCards.get(i).getCityName())) {
				//캐릭터의 손패와 현재위치한 도시가 같은지 확인
				for (int j = 1; j < 48; j++) {
					lcity[j] = new JLabel(button);
					lcity[j].setText(text[j + 1]);
					lcity[j].setVerticalTextPosition(JLabel.BOTTOM);
					lcity[j].setHorizontalTextPosition(JLabel.CENTER);
					lcity[j].addMouseListener(new AirplaneEvent());
					lcity[j].setFont(font);
					lcity[j].setForeground(Color.white);
					Scrollin.add(lcity[j]);
				}
			}
		}
		add(scroll);
	}


	//건설업자 특수능력용 
	public AirplanePanel(ControlPanel Controlpanel, String builder) {
		this.Controlpanel = Controlpanel;
		Scrollin = new ControlShape();
		Scrollin.setPreferredSize(new Dimension(1920, 600));
		scroll = new JScrollPane(Scrollin);
		Scrollin.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		scroll.setOpaque(false);
		scroll.setPreferredSize(new Dimension(1920,600));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정
		setLayout(new BorderLayout());

		CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();

		String[] text = Controlpanel.Mainpanel.citys.returntext();
		JLabel[] lcity = new JLabel[48];
		
		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		this.setFont(font);
		this.setForeground(Color.white);

		Scrollin.add(new BackLabel(Controlpanel));
		for (int j = 0; j < 48; j++) {
			lcity[j] = new JLabel(button);
			lcity[j].setText(text[j + 1]);
			lcity[j].setVerticalTextPosition(JLabel.BOTTOM);
			lcity[j].setHorizontalTextPosition(JLabel.CENTER);
			lcity[j].addMouseListener(new AirplaneEvent());
			lcity[j].setFont(font);
			lcity[j].setForeground(Color.white);
			Scrollin.add(lcity[j]);
		}
		add(scroll);
	}

	class AirplaneEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (!Client.CardPrint) {
				JLabel label = (JLabel) e.getSource();
				String Choicecity = label.getText();
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]" + Client.name + ":" + Choicecity);
					Controlpanel.Havecard.removeCard(CharacterCurrentCity);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}