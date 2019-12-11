package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import Game.Point;
import Game.AirplanePanel.AirplaneEvent;
import pandemic.Client;

//카드의 사용처
//1. 같은 색상 다섯장을 모아 백신을 만들수있다.
//2. 카드를 사용하여 능력을 쓸수있다. 일반카드의 경우 해당도시로 이동하고 special카드일 경우 쓰여져있는 텍스트의 기능을 발휘한다.
public class Card extends JLabel {
	// 일반적이 카드다. 카드 사용시 CityName 도시로 이동할수있다.
	String CityName;// 도시이름
	String color;// 도시색깔 빨강,파랑,노랑,검
	ControlPanel Controlpanel;
	String filename;// 불러올 이미지 파일 이름
	Point pos = new Point(0, 0);// 현재 그 도시카드의 좌표를 받아온다. 도시카드의 이름으로 알수있다.

	public Card(ControlPanel Controlpanel, String CityName, String color) {
		this.Controlpanel = Controlpanel;
		this.CityName = CityName;
		this.color = color;
		pos = Controlpanel.Mainpanel.citys.CityPosition(CityName);// 그 도시의 위치를 받아온다

		Image Card = new ImageIcon(Card.class.getResource("../Image/CityCard" + color + ".png")).getImage();// 카드의 일반이미지
		Image CardPush = new ImageIcon(Card.class.getResource("../Image/CityCard" + color + "Push.png")).getImage();// 카드눌릴시이미지
		BufferedImage BufferedCard = new BufferedImage(Card.getWidth(null), Card.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);// 주의! 이미지에서 바로 getGraphics이 먹히지 않으므로 버퍼드이미지에서 글자를 그린다.
		// 카드를 합성하기 위해 먼저 버퍼드이미지로 기존 카드 크기만큼 그린뒤
		Graphics2D g = (Graphics2D) BufferedCard.getGraphics();// 그 그래픽을 빼온다.
		g.drawImage(Card, 0, 0, null);// 그 후 거기 카드이미지를 그린다
		g.setFont(new Font("굴림", Font.BOLD, 30));// 폰트를 설정하고
		g.setColor(Color.BLACK);// 색상을 설정하고
		g.drawString(CityName, 7, 41);// 도시의 이름을 카드에 적는다.
		g.drawString(CityName, 7, 260);

		// 카드를 위해 올렸을 때의 이미지
		BufferedImage BufferedCardPush = new BufferedImage(CardPush.getWidth(null), CardPush.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D gp = (Graphics2D) BufferedCardPush.getGraphics();
		gp.drawImage(CardPush, 0, 0, null);
		gp.setFont(new Font("굴림", Font.BOLD, 30));
		gp.setColor(Color.BLACK);
		gp.drawString(CityName, 7, 41);
		gp.drawString(CityName, 7, 260);

		this.setIcon(new ImageIcon(BufferedCard));// 카드(라벨)의 기본 이미지 설정

		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon(new ImageIcon(BufferedCardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon(new ImageIcon(BufferedCard));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				if (!Client.CardPrint) {
					try {
						Controlpanel.Havecard.removeCard(CityName);// 해당카드를 손패에서 제거한다
						Controlpanel.Mainpanel.GameOutStream.writeUTF("[이동]" + Client.name + ":" + CityName);
						// 일반카드의 쓰임새인 이동이다
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public Card(String CityName, String color) {// 특수 카드를 사용하기 위해 만들어진 생성자이다.
		this.CityName = CityName;// 카드 이름
		this.color = color;// 카드 색상

		if (color.equals("special"))
			filename = CityName;
		else
			filename = "CityCard" + color;
		
		Image Card = new ImageIcon(Card.class.getResource("../Image/" + filename + ".png")).getImage();
		BufferedImage BufferedCard = new BufferedImage(Card.getWidth(null), Card.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);// 주의! 이미지에서 바로 getGraphics이 먹히지 않으므로 버퍼드이미지에서 글자를 그린다.
		Graphics2D g = (Graphics2D) BufferedCard.getGraphics();
		g.drawImage(Card, 0, 0, null);
		g.setFont(new Font("굴림", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		if (!color.equals("special")) {
			g.drawString(CityName, 7, 41);
			g.drawString(CityName, 7, 260);
		}

		this.setIcon(new ImageIcon(BufferedCard));
	}

	public String getCityName() {
		return CityName;
	}

	public String getColor() {
		return color;
	}

	public Card(String CityName) {
		this.color = "special";
	}
}

//기존 카드를 상속받는 특수카드들이다.

class PeaceNightCard extends Card {
	// 여기서 CityName은 "평온한 하룻밤". 다음 도시 감연 단계를 생략합니다
	public PeaceNightCard(ControlPanel Controlpanel, String CityName) {
		super(CityName);
		this.CityName = "평온한 하룻밤";
		this.color = "special";

		ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));// 도시의 일반 이미지
		ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));// 커서가위에 올라갔을때
																										// 이미지
		setIcon(Card);// 기본아이콘 설정

		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				// 평온한 하룻밤의 이벤트의 경우 서버에서 처리해준다.
				if (!Client.CardPrint) {
					try {
						Controlpanel.Havecard.removeCard(CityName);
						Controlpanel.invalidate();
						Controlpanel.removeAll();
						Controlpanel.add(new BasicSelect(Controlpanel));
						Controlpanel.revalidate();
						Controlpanel.repaint();
						Controlpanel.Mainpanel.GameOutStream.writeUTF("[특수]평온한 하룻밤");
						Controlpanel.setBounds(0, 840, 1920, 240);
					} catch (Exception e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}
				}
			}
		});
	}
}

class PredictCard extends Card {// 예측 전염카드 덱위에서 6장을 뽑아서 유저가 원하는 순서대로 덱위에 엎을수있다.
	public PredictCard(ControlPanel Controlpanel, String CityName) {
		super(CityName);
		this.CityName = "예측";
		this.color = "special";

		ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
		ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
		setIcon(Card);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				// 이것도 서버가 처리해줘야한다 전염카드 덱위에서 6장을 뽑아서 유저가 원하는 순서대로 덱위에 엎을수있다.
				if (!Client.CardPrint) {
					try {
						Controlpanel.Havecard.removeCard(CityName);
						Controlpanel.Mainpanel.GameOutStream.writeUTF("[예측]사용");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}

class EmergencyAirCard extends Card {// 긴급공중수송 원하는 도시로 이동 가능
	ControlPanel Controlpanel;

	public EmergencyAirCard(ControlPanel Controlpanel, String CityName) {
		super(CityName);
		this.Controlpanel = Controlpanel;
		this.CityName = "긴급공중수송";
		this.color = "special";

		ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
		ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
		setIcon(Card);

		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon((CardPush));
			}

			public void mouseExited(MouseEvent e) {
				setIcon((Card));
			}

			public void mousePressed(MouseEvent e) {
				// 긴급공중수송의 경우 원하는 지역으로 이동할수있다.
				if (!Client.CardPrint) {
					Controlpanel.Havecard.removeCard(CityName);// 긴급공중수송을 삭제하고
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new EmergencyAirPanel());//내부클래스 EmergencyAirPanel을 불러온다.
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}
			}
		});
	}

	public class EmergencyAirPanel extends ControlShape {
		JPanel Scrollin;// 스크롤 안에 넣을 패널
		JScrollPane scroll;// 스크롤
		ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/airplane2.png"));// 버튼용 이미지
		String CharacterCurrentCity;// 캐릭터의 현재 위치 즉 현재 도시를 나타낸다.

		public EmergencyAirPanel() {
		
			Scrollin = new ControlShape();// 스크롤 안에 이미지를 넣으려니 스크롤이 이미지를 가려서 아예 스크롤안에넣는 패널에 이런 이미지를달았다.
			Scrollin.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
			Scrollin.setPreferredSize(new Dimension(1920, 600));// 스크롤 안 패널 크기 설정
			scroll = new JScrollPane(Scrollin);// 스크롤 안에 스크롤인패널을 넣는다.
			scroll.setPreferredSize(new Dimension(1920,600));// 스크롤도 사이즈를 설정해준다
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정
			
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(1920, 600));	
			
			String[] text = Controlpanel.Mainpanel.citys.returntext();// 시티명은 앞의 한글자는 공백. 총 49개다
			JLabel[] lcity = new JLabel[48];// 선택할 도시들의 이름 라벨(버튼역할)
			CharacterCurrentCity = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
			// 캐릭터 현재 위치 도시

			Font font = new Font("HY헤드라인M", Font.PLAIN, 20);// 폰트

			// 캐릭터의 손패와 현재위치한 도시가 같은지 확인 만약 일치하다면 이제 항공기로 플레이어가 어디든 갈수있게 해야한다.
			for (int j = 0; j < 48; j++) {
				lcity[j] = new JLabel(text[j + 1], button, JLabel.CENTER);// 라벨에 이미지,텍스트 설정 중앙
				lcity[j].setVerticalTextPosition(JLabel.BOTTOM);// 텍스트 설정
				lcity[j].addMouseListener(new AirplaneEvent());// 라벨(버튼)에 마우스 액션 추가
				lcity[j].setFont(font);// 폰트 설정
				lcity[j].setForeground(Color.white);// 글자색 설정
				Scrollin.add(lcity[j]);
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
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

class GrandOfMoneyCard extends Card {// 정부보조금
	// 원하는 위치에 실험실을 지을 수 있다.
	ControlPanel Controlpanel;

	public GrandOfMoneyCard(ControlPanel Controlpanel, String CityName) {
		super(CityName);
		this.Controlpanel = Controlpanel;
		this.CityName = "정부보조금";
		this.color = "special";

		ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
		ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
		setIcon(Card);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				if (!Client.CardPrint) {// 만약 클라의 턴이라면
					Controlpanel.Havecard.removeCard(CityName);// 해당 카드를 제거하고
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new GrantOfMoneyPanel(Controlpanel));// 정부보조금 패널을 추가한다
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}
			}
		});
	}

	class GrantOfMoneyPanel extends ControlShape {
		ControlPanel Controlpanel;
		JPanel Scrollin;// 스크롤 안에 넣을 패널
		JScrollPane scroll;// 스크롤
		ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/airplane2.png"));// 버튼용 이미지

		GrantOfMoneyPanel(ControlPanel Controlpanel) {
			setLayout(new BorderLayout());
			this.Controlpanel = Controlpanel;
		
			Scrollin = new ControlShape();// 스크롤 안에 이미지를 넣으려니 스크롤이 이미지를 가려서 아예 스크롤안에넣는 패널에 이런 이미지를달았다.
			Scrollin.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
			Scrollin.setPreferredSize(new Dimension(1920, 600));// 스크롤 안 패널 크기 설정
			scroll = new JScrollPane(Scrollin);// 스크롤 안에 스크롤인패널을 넣는다.
			scroll.setPreferredSize(new Dimension(1920, 600));// 스크롤도 사이즈를 설정해준다
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 열로는 항상설정
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 행으로는 절대 안설정

			String[] text = Controlpanel.Mainpanel.citys.returntext();
			JLabel[] lcity = new JLabel[48];
			Font font = new Font("HY헤드라인M", Font.PLAIN, 20);// 폰트

			for (int i = 0; i < 48; i++) {
				lcity[i] = new JLabel(text[i + 1], button, JLabel.CENTER);// 라벨에 이미지,텍스트 설정 중앙
				lcity[i].setVerticalTextPosition(JLabel.BOTTOM);// 텍스트 설정
				lcity[i].addMouseListener(new GrantOfMoneyControl());
				lcity[i].setFont(font);// 폰트 설정
				lcity[i].setForeground(Color.white);// 글자색 설정
				Scrollin.add(lcity[i]);
			}
			add(scroll);
		}

		class GrantOfMoneyControl extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				JLabel label = (JLabel) e.getSource();
				String Choicecity = label.getText();
				City choice = Controlpanel.Mainpanel.citys.returnCity(Choicecity);
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[건설]" + Client.name + ":" + Choicecity);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}

class PowerUp extends Card {// 파워업 카드
//현재 캐릭터가 위치한 도시 카드를 얻는다.
	public PowerUp(ControlPanel Controlpanel, String CityName) {
		super(CityName);
		this.CityName = "파워업";
		this.color = "special";

		ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
		ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));

		setIcon(Card);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				if (!Client.CardPrint) {
					Controlpanel.Havecard.removeCard(CityName);
					String WillCard = Controlpanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
					// WillCard는 현재 유저의 위치 도시이다.
					String Color = Controlpanel.Mainpanel.citys.returnCity(WillCard).getColor();// 현재 위치 도시의 색을 얻어낸다
					Controlpanel.Havecard.insertCard(Controlpanel, WillCard, Color);// 손패에 해당카드를 추가한다
					Controlpanel.invalidate();
					Controlpanel.removeAll();
					Controlpanel.add(new BasicSelect(Controlpanel));
					Controlpanel.revalidate();
					Controlpanel.repaint();
				}
			}
		});
	}
}
