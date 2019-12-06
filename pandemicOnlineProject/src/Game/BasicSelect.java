package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.MainPanel.Map;
import Game.ControlPanel;
import Game.AirplanePanel;
import Game.MoveLabatoryPanel;
import pandemic.Client;

public class BasicSelect extends ControlShape {// 기본선택 사항
	ImageIcon special = new ImageIcon(Map.class.getResource("../Image/special.png"));// 임시이미지아이콘
	ImageIcon Protection = new ImageIcon(Map.class.getResource("../Image/Protection.png"));// 치료 이미지
	ImageIcon move = new ImageIcon(Map.class.getResource("../Image/move-1.png"));// 이동 이미지아이콘
	ImageIcon cure = new ImageIcon(Map.class.getResource("../Image/Cure.png"));// 치료 이미지아이콘
	ImageIcon build = new ImageIcon(Map.class.getResource("../Image/build.png"));// 건설 이미지아이콘
	ImageIcon share = new ImageIcon(Map.class.getResource("../Image/share.png"));// 공유 이미지아이콘
	ImageIcon develop = new ImageIcon(Map.class.getResource("../Image/developCure.png"));// 백신개발 이미지아이콘
	ImageIcon airplane = new ImageIcon(Map.class.getResource("../Image/airplane.png"));// 백신개발 이미지아이콘
	ImageIcon movelabatory = new ImageIcon(Map.class.getResource("../Image/labatorymove.png"));
	
	String[] texts = { "이동", "치료", "건설", "공유", "개발", "카드", "능력" };
	JLabel[] labels = new JLabel[9];
	ControlPanel Controlpanel;

	public BasicSelect(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
		
		//setSize(new Dimension(1920, 400));
		setLayout(new FlowLayout(FlowLayout.CENTER,60, 40)); // 해당 이미지 가운데로 정렬하고 위, 옆 간격 맞춤
		labels[0] = new JLabel("이동", move, JLabel.CENTER);
		labels[1] = new JLabel("치료", cure, JLabel.CENTER);
		labels[2] = new JLabel("건설", build, JLabel.CENTER);
		labels[3] = new JLabel("공유", share, JLabel.CENTER);
		labels[4] = new JLabel("개발", develop, JLabel.CENTER);
		labels[5] = new JLabel("카드", share, JLabel.CENTER);
		labels[6] = new JLabel("항공기이동", airplane, JLabel.CENTER);
		labels[7] = new JLabel("연구소 이동", movelabatory, JLabel.CENTER);
		
		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		for (int i = 0; i < 8; i++) {// Active능력을 가진 캐릭터가 상속받을 시에는 수정해준다.
			labels[i].setVerticalTextPosition(JLabel.BOTTOM);
			labels[i].setHorizontalTextPosition(JLabel.CENTER);
			labels[i].setFont(font);
			labels[i].setForeground(Color.white);
			// 라벨내에서 글자를 맨 밑 텍스트는 정중앙으로
			add(labels[i]);
		}

		labels[0].addMouseListener(new MouseAdapter() {// 이동 라벨을 눌렀을 때 Move 패널로 이동
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				// 이전에 있었던 모든 자원 해제
				Controlpanel.add(new MovePanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
				// 새로 그려주기
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
		labels[1].addMouseListener(new MouseAdapter() {// 치료 패널로 이동
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new ProtectionPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});
		labels[2].addMouseListener(new MouseAdapter() {// 건설패널로 이동
			public void mousePressed(MouseEvent e) {

				if (!Client.CardPrint) {
					String Pos = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
					// 현재 유저의 위치를 즉 도시를 String으로 뽑아낸뒤.
					Controlpanel.Havecard.BuildLabatory(Pos);
					// 내 손패랑 비교한다. 만약 이때 내 손패 Havecard 메소드에 도시와 같은 이름의 카드가 있다면 해당 도시에 건설이 된다.
				}
			}
		});
		
		labels[3].addMouseListener(new MouseAdapter() {//공유
			public void mousePressed(MouseEvent e) {

				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new ShareCardPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});

		labels[4].addMouseListener(new MouseAdapter() {// 치료제 개발 패널
			public void mousePressed(MouseEvent e) {
				//만약 returnCity는 매개변수로 받은 String값의 도시 객체를 반환하는 메소드이다. 반환된 도시메소드에서 getLabatory를 통해 실험실이 건설됬는지 안됬는지 확인한다.
				if (Controlpanel.Mainpanel.citys
						.returnCity(Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition())
						.getLabatory()) {
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new CurePanel(Controlpanel));
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}
			}
		});

		labels[5].addMouseListener(new MouseAdapter() {// 내 현재 카드를 확인
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new HandPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});
		labels[6].addMouseListener(new MouseAdapter() {// 항공기 이동 패널로 해당도시의 카드를 버리고 항공기이용
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new AirplanePanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});
		labels[7].addMouseListener(new MouseAdapter() {// 실험실끼리는 이동이 가능하다.
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new MoveLabatoryPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});
		
		String tempjob = Controlpanel.Mainpanel.myjob;
		if (tempjob.equals("builder")) {
			labels[8] = new JLabel("특수능력", special, JLabel.CENTER);
			labels[8].setVerticalTextPosition(JLabel.BOTTOM);
			labels[8].setHorizontalTextPosition(JLabel.CENTER);
			labels[8].setFont(font);
			labels[8].setForeground(Color.white);
			add(labels[8]);
			labels[8].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (Controlpanel.Mainpanel.citys
							.returnCity(Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition())
							.getLabatory()) {
						if (Controlpanel.Havecard.ChoiceAbandonedCard() != null) {
							Controlpanel.invalidate();
							Controlpanel.removeAll();
							Controlpanel.add(new AirplanePanel(Controlpanel, "builder"));
							Controlpanel.revalidate();
							Controlpanel.repaint();
						}
					}
				}
			});
		}
	}
}
