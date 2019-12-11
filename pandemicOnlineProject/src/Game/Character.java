package Game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Game.MainPanel.Map;

public class Character {// 유저 자신 캐릭터다
	String job;//직업이름
	String CurrentPositon = "애틀란타";// 제일 처음 시작위치는 애틀란타다. 캐릭터의 이동위치가 바뀔 때 마다 업데이트
	String Color = "Blue";// 시작도시 애틀란타의 색깔
	Image CharacterIcon;
	String name = "";
	int x = 0, y = 0, plus = 0;// 캐릭터들의 좌표 plus는 캐릭터들이 겹치지 않게 공간을 주는 것이다.
	MainPanel Mainpanel;
	Image turnImage = new ImageIcon(Map.class.getResource("../Image/턴화살표.png")).getImage();// 턴이미지

	public Character(MainPanel Mainpanel, int i, String name, String job) {
		this.job = job;
		CharacterIcon = new ImageIcon(ControlPanel.class.getResource("../Image/" + job + ".png")).getImage();
		this.Mainpanel = Mainpanel;
		this.name = name;
		Point c = Mainpanel.citys.CityPosition(CurrentPositon);//현재 위치한 도시의 좌표
		plus = i;
		x = c.getX() + plus;
		y = c.getY();
		
		if(job.equals("quarantine")) {
			quarantineSetting(true);
		}//만약 캐릭터 직업이 방역업자일시.
	}
	
	public void quarantineSetting(boolean t) {
		City myCity = Mainpanel.citys.returnCity(CurrentPositon);
		ArrayList<City> adCity = Citys.AdjacencyCity(CurrentPositon);
		for(City c : adCity) {
			c.setProtection(t);
		}
		myCity.setProtection(t);
	}//캐릭터가 방역업자일시 주변도시들에는 바이러스가 발생하지 않는다.

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
		this.x = x + plus;
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

	public String getUserName() {
		return name;
	}

	public void setCurrentposition(String e) {
		if(job.equals("quarantine")) {
			quarantineSetting(false);
			this.CurrentPositon = e;
			quarantineSetting(true);
		}else
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
	}//캐릭터 현재 위치및 색깔 설정

	public void draw(Graphics g) {
		g.drawImage(CharacterIcon, x, y, CharacterIcon.getWidth(null), CharacterIcon.getHeight(null), null);
		
		//만약 현재 자신의 턴이라면 위에 삼각형
		if(ClientGameReceiverThread.turnUser.equals(name))
			g.drawImage(turnImage, x, y-50, turnImage.getWidth(null), turnImage.getHeight(null), null);
		g.setColor(java.awt.Color.white);
		g.setFont(new Font("굴림", 30, 30));
		g.drawString(name + "/" + job, x - 40, y - 5);
	}// 캐릭터를 그려준다.
}
