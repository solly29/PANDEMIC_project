package Game;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Character {// 유저 자신 캐릭터다
		Image CharacterIcon = new ImageIcon(ControlPanel.class.getResource("../Image/Scientist.png")).getImage();
		String CurrentPositon = "애틀란타";// 제일 처음 시작위치는 애틀란타다. 캐릭터의 이동위치가 바뀔 때 마다 업데이트
		String Color = "Blue";// 시작색깔
		int x = 0, y = 0, plus = 0;//캐릭터들의 좌표
		MainPanel Mainpanel;

		public Character(MainPanel Mainpanel, int i) {
			this.Mainpanel = Mainpanel;
			Point c = Mainpanel.citys.CityPosition(CurrentPositon);
			plus = i;
			x = c.getX()+plus;
			y = c.getY();
		}
		
		public void setPlus(int i) {
			this.plus = i;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setXY(int x, int y) {
			this.x = x+plus;
			this.y = y;
			Mainpanel.repaint();
		}// 위에 단일 setX,setY는 나중에 멀티플레이에서 여러 캐릭터들이 한 맵에 있을때 쓰일듯 setX(getX+10) 막이런식으로 말이지
			// , 진짜로 이동할때는 이걸쓴다. 그리고 이동하자마자 repaint로 그려준다.

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setCurrentposition(String e) {
			this.CurrentPositon = e;
		}// 캐릭터의 현재 도시(String)를 수정

		public String getCurrentposition() {
			return CurrentPositon;
		}// 캐릭터의 현재 도시(String)을 반환

		public void setColor(String Color) {
			this.Color = Color;
		}

		public String getColor() {
			return Color;
		}

		public void setCC(String City, String Color) {
			this.CurrentPositon = City;
			this.Color = Color;
			System.out.println("메인패널의 캐릭터의 CC메소드" + CurrentPositon + " " + Color);
		}

		public void draw(Graphics g) {
			g.drawImage(CharacterIcon, x, y, CharacterIcon.getWidth(null), CharacterIcon.getHeight(null), null);
		}// 캐릭터를 그려준다.
	}
