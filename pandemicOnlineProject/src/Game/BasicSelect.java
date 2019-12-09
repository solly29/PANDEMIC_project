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
//컨트롤 패널위에서 그려지는 기본능력패널이다.
public class BasicSelect extends ControlShape {// 기본선택 사항
	ImageIcon special = new ImageIcon(Map.class.getResource("../Image/special.png"));// 특수능력아이콘
	ImageIcon move = new ImageIcon(Map.class.getResource("../Image/move-1.png"));// 이동아이콘
	ImageIcon cure = new ImageIcon(Map.class.getResource("../Image/Cure.png"));// 치료 아이콘
	ImageIcon build = new ImageIcon(Map.class.getResource("../Image/build.png"));// 건설아이콘
	ImageIcon share = new ImageIcon(Map.class.getResource("../Image/share.png"));// 공유(카드)아이콘
	ImageIcon develop = new ImageIcon(Map.class.getResource("../Image/developCure.png"));// 백신개발 아이콘
	ImageIcon airplane = new ImageIcon(Map.class.getResource("../Image/airplane.png"));// 비행기아이콘
	ImageIcon movelabatory = new ImageIcon(Map.class.getResource("../Image/labatorymove.png"));// 실험실아이콘

	String[] texts = { "이동", "치료", "건설", "공유", "개발", "카드", "능력" };// 8개의 기본 컨트롤
	JLabel[] labels = new JLabel[9];// 특수능력이 있는 캐릭터들이 있기때문에 9개다.
	ControlPanel Controlpanel;

	public BasicSelect(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;

		setLayout(new FlowLayout(FlowLayout.CENTER, 60, 40)); // 해당 이미지 가운데로 정렬하고 위, 옆 간격 맞춤
		labels[0] = new JLabel("이동", move, JLabel.CENTER);
		labels[1] = new JLabel("치료", cure, JLabel.CENTER);
		labels[2] = new JLabel("건설", build, JLabel.CENTER);
		labels[3] = new JLabel("공유", share, JLabel.CENTER);
		labels[4] = new JLabel("개발", develop, JLabel.CENTER);
		labels[5] = new JLabel("카드", share, JLabel.CENTER);
		labels[6] = new JLabel("항공기이동", airplane, JLabel.CENTER);
		labels[7] = new JLabel("연구소 이동", movelabatory, JLabel.CENTER);
		// 기본능력 이미지 및 텍스트설정

		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);
		for (int i = 0; i < 8; i++) {
			labels[i].setVerticalTextPosition(JLabel.BOTTOM);
			labels[i].setHorizontalTextPosition(JLabel.CENTER);
			labels[i].setFont(font);// 폰트적용
			labels[i].setForeground(Color.white);// 글자 흰색
			add(labels[i]);// 패널에 라벨(버튼)추가
		}

		labels[0].addMouseListener(new MouseAdapter() {// 인접도시로 이동하는 이동패널로 이동
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				// 이전에 있었던 그래픽적인 모든 자원 해제
				Controlpanel.add(new MovePanel(Controlpanel));// 그 후 MovePanel을 추가한다
				Controlpanel.revalidate();
				Controlpanel.repaint();
				// 새로 그려주기
			}
		});
		labels[1].addMouseListener(new MouseAdapter() {// ProtectionPanel 방역패널로이동
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new ProtectionPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});
		labels[2].addMouseListener(new MouseAdapter() {// 건설기능
			public void mousePressed(MouseEvent e) {
				if (!Client.CardPrint) {
					// 클라가 현재 턴이라면
					String Pos = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
					// 현재 유저의 위치를 즉 도시를 String으로 뽑아낸뒤.
					Controlpanel.Havecard.BuildLabatory(Pos);
					// 내 손패랑 비교한다. 만약 이때 내 손패 Havecard 메소드에 도시와 같은 이름의 카드가 있다면 해당 도시에 실험실이 건설된다.
				}
			}
		});

		labels[3].addMouseListener(new MouseAdapter() {// 카드를 공유할수있는 공유 패널로 이동
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
				// 만약 returnCity는 매개변수로 받은 String값의 도시 객체를 반환하는 메소드이다. 반환된 도시메소드에서 getLabatory를
				// 통해 실험실이 건설됬는지 안됬는지 확인한다.
				if (Controlpanel.Mainpanel.citys
						.returnCity(Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition())
						.getLabatory()) {
					// 만약 실험실이 건설되었다면 치료제개발 패널로 이동할수있다.
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new CurePanel(Controlpanel));
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}
			}
		});

		labels[5].addMouseListener(new MouseAdapter() {// 내가 가진 현재 카드를 확인할수있는 HandPanel로 이동
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new HandPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});

		labels[6].addMouseListener(new MouseAdapter() {// 항공기 이동 패널로 이동. 해당도시의 카드를 버리고 항공기이용
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new AirplanePanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});

		labels[7].addMouseListener(new MouseAdapter() {// 현재 위치가 실험실이라면 실험실끼리 이동할수있다.
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new MoveLabatoryPanel(Controlpanel));
				Controlpanel.revalidate();
				Controlpanel.repaint();
			}
		});

		String tempjob = Controlpanel.Mainpanel.myjob;// 특수능력라벨(버튼)이 필요한지 아닌지 구분

		if (tempjob.equals("builder")) {
			// 만약 직업이 건설자라면 특수능력 라벨이 필요하다
			labels[8] = new JLabel("특수능력", special, JLabel.CENTER);
			labels[8].setVerticalTextPosition(JLabel.BOTTOM);
			labels[8].setHorizontalTextPosition(JLabel.CENTER);
			labels[8].setFont(font);
			labels[8].setForeground(Color.white);
			add(labels[8]);
			// 건설업자의 특수능력은 만약 현재위치에 실험실이 있고 카드한장을 버린다면 항공기패널과같은 효과를 사용할수있다.
			labels[8].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (Controlpanel.Mainpanel.citys
							.returnCity(Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition())
							.getLabatory()) {
						if (Controlpanel.Havecard.ChoiceAbandonedCard() != null) {
							// ChoiceAbandonedCard 메소드는 현재 들고있는 카드 중 한장을 버리는 메소드이다. null이 아니라는 의미는 카드한장을
							// 선택했다는 뜻!
							Controlpanel.invalidate();
							Controlpanel.removeAll();
							Controlpanel.add(new AirplanePanel(Controlpanel));
							Controlpanel.revalidate();
							Controlpanel.repaint();
						}
					}
				}
			});
		}
	}
}
