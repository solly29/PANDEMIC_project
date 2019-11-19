package Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Game.MainPanel.Map;

public class ControlPanel extends JPanel {// 컨트롤 패널
	ImageIcon back = new ImageIcon(Map.class.getResource("../Image/back.png"));// 뒤로가기버튼
	MainPanel Mainpanel;// 상위자원 MainPanel을 사용하기 위해 선언
	ControlPanel Controlpanel = this;

	ControlPanel(MainPanel main) {
		Mainpanel = main;// 상위자원을 연결해서 쓸수있게
		setSize(new Dimension(1920, 300));
		setLayout(new BorderLayout()); // 컨트롤 패널의 기본
		// setLayout(new CardLayout());
		add(new BasicSelect());
	}

	public void changePanel(String panel) {

	}

	public class BasicSelect extends JPanel {// 기본선택 사항
		ImageIcon temp = new ImageIcon(Map.class.getResource("../Image/temp.png"));// 임시이미지아이콘
		String[] texts = { "이동", "치료", "건설", "공유", "개발", "카드", "능력" };
		JLabel[] labels = new JLabel[7];

		public BasicSelect() {
			setSize(new Dimension(1920, 400));
			labels[0] = new JLabel("이동", temp, JLabel.CENTER);
			labels[1] = new JLabel("치료", temp, JLabel.CENTER);
			labels[2] = new JLabel("건설", temp, JLabel.CENTER);
			labels[3] = new JLabel("공유", temp, JLabel.CENTER);
			labels[4] = new JLabel("개발", temp, JLabel.CENTER);
			labels[5] = new JLabel("능력", temp, JLabel.CENTER);
			for (int i = 0; i < 6; i++) {
				labels[i].setVerticalTextPosition(JLabel.BOTTOM);
				labels[i].setHorizontalTextPosition(JLabel.CENTER);
				// 라벨내에서 글자를 맨 밑 텍스트는 정중앙으로
				add(labels[i]);
			}

			labels[0].addMouseListener(new MouseAdapter() {// 이동 라벨을 눌렀을 때 Move 패널로 이동
				public void mousePressed(MouseEvent e) {
					invalidate();
					removeAll();
					// 이전에 있었던 모든 자원 해제
					add(new MovePanel(Controlpanel));
					revalidate();
					repaint();
					// 새로 그려주기
				}

				public void mouseReleased(MouseEvent e) {
				}
			});
		}
	}

	class MovePanel extends JPanel {
		ArrayList<JLabel> labels = new ArrayList<JLabel>();// 선택라벨
		ArrayList<String> CityTexts = new ArrayList<String>();// MainPanel에 returncity가 String 이기 때문에 일단 String 으로저장
		ControlPanel Controlpanel;

		MovePanel(ControlPanel Controlpanel) {
			this.Controlpanel = Controlpanel;
			CityTexts.addAll(Mainpanel.returnCity());// 텍스트를 다 추가

			setSize(new Dimension(1920, 300));// 공간

			// 임시 맵그래프에서 인접한도시들의 정보를 가지고와서 label에 넣어서 출력해야함
			for (int i = 0; i < CityTexts.size(); i++) {
				labels.add(new JLabel(back));// 라벨에 이미지
				labels.get(i).setText((CityTexts.get(i)));// 라벨에 텍스트
				labels.get(i).setVerticalTextPosition(JLabel.BOTTOM);
				labels.get(i).setHorizontalTextPosition(JLabel.CENTER);
				// 라벨 중에서 텍스틑 아래 중간
				add(labels.get(i));// 라벨을 창에 붙이기
				String temp = CityTexts.get(i);// 내 현재 위치를 정확하게 주기 위해서 따로 텍스트를 뽑아냈다.
				Point pos = Mainpanel.citys.CityPosition(CityTexts.get(i));

				labels.get(i).addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						try {
							Mainpanel.GameOutStream.writeUTF("[이동]"+temp);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						/*Mainpanel.character.setCurrentposition(temp);// 캐릭터의 현재 위치를 이동시키기 위해 이동도시(String)를 바꾸고
						Mainpanel.character.setXY(pos.getX(), pos.getY());// 캐릭터의 좌표를 바꾼다.(xy)
					//	System.out.println(pos);//도시좌표 출력 
						Controlpanel.invalidate();
						Controlpanel.removeAll();
						Controlpanel.add(new BasicSelect());
						Controlpanel.revalidate();
						Controlpanel.repaint();*/
					}

					public void mouseReleased(MouseEvent e) {

					}
				});
			}

			add(new BackLabel(Controlpanel));
		}
	}

	class BackLabel extends JLabel {
		// 뒤로가기 라벨은 많이 쓰이니까 그냥 라벨로 만들었다.
		ControlPanel Controlpanel;
		ImageIcon BackIcon = new ImageIcon(Map.class.getResource("../Image/back.png"));// 뒤로가기버튼

		public BackLabel(ControlPanel Controlpanel) {
			this.setText("뒤로");
			this.setIcon(BackIcon);
			this.Controlpanel = Controlpanel;
			// this.setFont("굴림");
			this.setVerticalTextPosition(JLabel.BOTTOM);
			this.setHorizontalTextPosition(JLabel.CENTER);
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new BasicSelect());
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}

				public void mouseReleased(MouseEvent e) {

				}
			});
		}
	}
}
