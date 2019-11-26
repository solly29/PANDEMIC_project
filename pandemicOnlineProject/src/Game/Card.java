package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pandemic.Client;

//유저가 들고있는 손패카드들이다.
public class Card extends JLabel {
	String CityName;// 도시 이름은 알아야 도시 이름을 적지. 그리고 어디어디서 교환이 가능한지 알지
	String color;// 색깔을 판별해야지
	ControlPanel Controlpanel;
	Point pos = new Point(0, 0);// 현재 그 도시카드의 좌표를 받아온다.

	public Card(ControlPanel Controlpanel, String CityName, String color) {
		this.Controlpanel = Controlpanel;
		this.CityName = CityName;// 카드 이름
		this.color = color;// 카드 색상
		pos = Controlpanel.Mainpanel.citys.CityPosition(CityName);// 그 도시의 위치를 받아온다

		Image Card = new ImageIcon(Card.class.getResource("../Image/CityCard" + color + ".png")).getImage();
		Image CardPush = new ImageIcon(Card.class.getResource("../Image/CityCard" + color + "Push.png")).getImage();
		BufferedImage BufferedCard = new BufferedImage(Card.getWidth(null), Card.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);// 주의! 이미지에서 바로 getGraphics이 먹히지 않으므로 버퍼드이미지에서 글자를 그린다.
		Graphics2D g = (Graphics2D) BufferedCard.getGraphics();
		g.drawImage(Card, 0, 0, null);
		g.setFont(new Font("굴림", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawString(CityName, 7, 41);
		g.drawString(CityName, 7, 260);
		
		BufferedImage BufferedCardPush = new BufferedImage(CardPush.getWidth(null), CardPush.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D gp = (Graphics2D) BufferedCardPush.getGraphics();
		gp.drawImage(CardPush, 0, 0, null);
		gp.setFont(new Font("굴림", Font.BOLD, 30));
		gp.setColor(Color.BLACK);
		gp.drawString(CityName, 7, 41);
		gp.drawString(CityName, 7, 260);

		this.setIcon(new ImageIcon(BufferedCard));

		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIcon(new ImageIcon(BufferedCardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
			}

			public void mouseExited(MouseEvent e) {
				setIcon(new ImageIcon(BufferedCard));// 마우스가 떼졌을 때 카드의 색상이 원래대로
			}

			public void mousePressed(MouseEvent e) {
				/*Controlpanel.Mainpanel.characterList.get(Client.name).setXY(pos.getX(), pos.getY());
				Controlpanel.Mainpanel.characterList.get(Client.name).setCC(CityName, color);*/
				Controlpanel.Havecard.removeCard(CityName);
				/*Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));
				Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
				Controlpanel.revalidate();
				Controlpanel.repaint();*/
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

	public String getCityName() {
		return CityName;
	}

	public String getColor() {
		return color;
	}
}
