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

//AirPlane패널은 항공기 능력으로 유저가 현재위치한도시명과 같은 이름의 카드를 가진다면, 그 카드를 버리는 것으로 어디든 이동할수있다.
public class AirplanePanel extends ControlShape {
	ControlPanel Controlpanel;
	JPanel Scrollin;// 스크롤 안에 넣을 패널
	JScrollPane scroll;// 스크롤

	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/airplane2.png"));// 버튼용 이미지
	String CharacterCurrentCity;// 캐릭터의 현재 위치 즉 현재 도시를 나타낸다.

	public AirplanePanel(ControlPanel Controlpanel) {
		Scrollin = new ControlShape();// 스크롤 안에 이미지를 넣으려니 스크롤이 이미지를 가려서 아예 스크롤안에넣는 패널에 이런 이미지를달았다.
		Scrollin.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
		// 처음에 GridLayout을 사용해봤지만, 상당히 불완전했다. 특히 밑에 잘리는 현상이 일어났다.
		Scrollin.setPreferredSize(new Dimension(1920, 600));// 스크롤 안 패널 크기 설정
		scroll = new JScrollPane(Scrollin);// 스크롤 안에 스크롤인패널을 넣는다.
		scroll.setPreferredSize(new Dimension(1920, 600));// 스크롤도 사이즈를 설정해준다
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정

		this.Controlpanel = Controlpanel;
		setLayout(new BorderLayout());

		ArrayList<Card> HandCards = Controlpanel.Havecard.ReArray();// 현재 유저가 가지고있는 손패
		String[] text = Controlpanel.Mainpanel.citys.returntext();// 시티명은 앞의 한글자는 공백. 총 49개다
		JLabel[] lcity = new JLabel[48];// 선택할 도시들의 이름 라벨(버튼역할)
		CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
		// 캐릭터 현재 위치 도시

		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);// 폰트

		Scrollin.add(new BackLabel(Controlpanel));// 뒤로가기버튼
		for (int i = 0; i < HandCards.size(); i++) {
			if (CharacterCurrentCity.equals(HandCards.get(i).getCityName())) {
				// 캐릭터의 손패와 현재위치한 도시가 같은지 확인 만약 일치하다면 이제 항공기로 플레이어가 어디든 갈수있게 해야한다.
				for (int j = 1; j < 48; j++) {
					lcity[j] = new JLabel(text[j+1],button,JLabel.CENTER);// 라벨에 이미지,텍스트 설정 중앙
					lcity[j].setVerticalTextPosition(JLabel.BOTTOM);// 텍스트 설정
					lcity[j].addMouseListener(new AirplaneEvent());// 라벨(버튼)에 마우스 액션 추가
					lcity[j].setFont(font);// 폰트 설정
					lcity[j].setForeground(Color.white);// 글자색 설정
					Scrollin.add(lcity[j]);
				}
			}
		}
		add(scroll);
	}

	// 마우스 어뎁터
	class AirplaneEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (!Client.CardPrint) {// 만약 현재 유저 턴이라면
				JLabel label = (JLabel) e.getSource();// 라벨의 값을 가지고 온다
				String Choicecity = label.getText();// 그 라벨의 텍스트 즉 유저가 가고 싶은 도시명을 읽은 후
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]" + Client.name + ":" + Choicecity);
					// 게임소켓을 통해 유저가 해당 위치로 이동했다고 알린다.
					Controlpanel.Havecard.removeCard(CharacterCurrentCity);
					// 그 후 그전 위치에 있던 도시카드를 제거한다.
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
