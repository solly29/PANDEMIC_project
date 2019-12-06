package Game;

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
import Game.MainPanel.Map;
import pandemic.Client;

class MovePanel extends ControlShape {
	ArrayList<JLabel> labels = new ArrayList<JLabel>();// 선택라벨
	ArrayList<String> CityTexts = new ArrayList<String>();// MainPanel에 returncity가 String 이기 때문에 일단 String 으로저장
	ControlPanel Controlpanel;
	ImageIcon back = new ImageIcon(Map.class.getResource("../Image/back.png"));// 뒤로가기버튼
	ImageIcon move = new ImageIcon(Map.class.getResource("../Image/move-2.png"));// 이동 이미지아이콘

	MovePanel(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		CityTexts.addAll(Controlpanel.Mainpanel.returnCity());// 텍스트를 다 추가

		setSize(new Dimension(1920, 300));// 공간

		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		setLayout(new FlowLayout(FlowLayout.CENTER,50,50));//무브 버튼 간격 조절
		
		// 임시 맵그래프에서 인접한도시들의 정보를 가지고와서 label에 넣어서 출력해야함
		for (int i = 0; i < CityTexts.size(); i++) {
			labels.add(new JLabel(move));// 라벨에 이미지
			labels.get(i).setText((CityTexts.get(i)));// 라벨에 텍스트
			labels.get(i).setForeground(Color.white);
			labels.get(i).setVerticalTextPosition(JLabel.BOTTOM);
			labels.get(i).setHorizontalTextPosition(JLabel.CENTER);
			labels.get(i).setFont(font);
			// 라벨 중에서 텍스틑 아래 중간
			add(labels.get(i));// 라벨을 창에 붙이기
			
			String CityName = CityTexts.get(i);// 내 현재 위치를 정확하게 주기 위해서 따로 텍스트를 뽑아냈다. addmouse내에서 사용하기위해서 바인딩.
			Point pos = Controlpanel.Mainpanel.citys.CityPosition(CityTexts.get(i));// 해당 도시의 좌표를 뽑아낸다.
			
			String Color=Controlpanel.Mainpanel.citys.returnCity(CityName).color;// 해당 도시의 컬러를 뽑아낸다.

			labels.get(i).addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(!Client.CardPrint) {
						try {
							Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]"+Client.name+":"+CityName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}

		add(new BackLabel(Controlpanel));
	}
}
