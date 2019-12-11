package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import Game.MainPanel.Map;
import pandemic.Client;

public class Citys {
	static boolean fail = false;

	public static String[][] ad = new String[49][49];// 인접시 o 소문자 o가 넣어진다 아닐시에는"", 도시의 인접상만 판단
	String[] name = { "", "애틀란타", "워싱턴", "시카고", "마이애미", "멕시코 시티", "몬트리올", "뉴욕", "보고타", "리마", "로스앤젤레스", "샌프란시스코", "런던",
			"마드리드", "산티아고", "상파울루", "부에노스아이레스", "도쿄", "마닐라", "시드니", "에센", "파리", "알제", "라고스", "오사카", "서울", "상하이", "홍콩",
			"호치민 시티", "자카르타", "타이베이", "상트페테르부르크", "밀라노", "이스탄불", "카이로", "카르툼", "킨샤샤", "베이징", "콜카타", "방콕", "첸나이", "모스크바",
			"바그다드", "리야드", "요하네스버그", "델리", "뭄바이", "카라치", "테헤란" };// 총 49개 첫번째는 공백

	static City[] city = new City[48];// 도시 그 자체의 자원을 나타낸다.
	// ad배열같은 경우에는 한눈에 알아보기 쉽고, 또 인접성판단을 위해 공백을 넣어 index가 +1 되있지만, city 배열은 그럴
	// 필요가없어서 공백이 없다.

	final String[] eventCard = { "평온한 하룻밤", "긴급공중수송", "항체보유자", "예측", "정부보조금", "파워업" };
	public List<String> EventCard = Arrays.asList(eventCard);
	// 이벤트카드 항목

	public Citys() {
		for (int i = 0; i < 49; i++)
			for (int j = 0; j < 49; j++)
				ad[i][j] = "";// 다 공백으로 채워넣은 뒤에

		for (int i = 0; i < 49; i++) {
			ad[i][0] = name[i];
			ad[0][i] = name[i];
			// 0행 or 0열 기준으로 모든 도시를 구분
		}
		ad[1][2] = ad[2][1] = "o";
		ad[1][3] = ad[3][1] = "o";
		ad[1][4] = ad[4][1] = "o";
		ad[2][6] = ad[6][2] = "o";
		ad[2][7] = ad[7][2] = "o";
		ad[2][4] = ad[4][2] = "o";
		ad[3][5] = ad[5][3] = "o";
		ad[3][6] = ad[6][3] = "o";
		ad[3][11] = ad[11][3] = "o";
		ad[3][10] = ad[10][3] = "o";
		ad[4][5] = ad[5][4] = "o";
		ad[4][8] = ad[8][4] = "o";
		ad[5][8] = ad[8][5] = "o";
		ad[5][9] = ad[9][5] = "o";
		ad[5][10] = ad[10][5] = "o";
		ad[6][7] = ad[7][6] = "o";
		ad[7][12] = ad[12][7] = "o";
		ad[7][13] = ad[13][7] = "o";
		ad[8][9] = ad[9][8] = "o";
		ad[8][16] = ad[16][8] = "o";
		ad[8][15] = ad[15][8] = "o";
		ad[9][14] = ad[14][9] = "o";
		ad[10][11] = ad[11][10] = "o";
		ad[10][19] = ad[19][10] = "o";
		ad[11][17] = ad[17][11] = "o";
		ad[11][18] = ad[18][11] = "o";
		ad[12][13] = ad[13][12] = "o";
		ad[12][20] = ad[20][12] = "o";
		ad[12][21] = ad[21][12] = "o";
		ad[13][15] = ad[15][13] = "o";
		ad[13][22] = ad[22][13] = "o";
		ad[13][21] = ad[21][13] = "o";
		ad[15][16] = ad[16][15] = "o";
		ad[15][23] = ad[23][15] = "o";
		ad[17][24] = ad[24][17] = "o";
		ad[17][25] = ad[25][17] = "o";
		ad[17][26] = ad[26][17] = "o";
		ad[18][19] = ad[19][18] = "o";
		ad[18][27] = ad[27][18] = "o";
		ad[18][28] = ad[28][18] = "o";
		ad[18][30] = ad[30][18] = "o";
		ad[19][29] = ad[29][19] = "o";
		ad[20][21] = ad[21][20] = "o";
		ad[20][31] = ad[31][20] = "o";
		ad[20][32] = ad[32][20] = "o";
		ad[21][22] = ad[22][21] = "o";
		ad[21][32] = ad[32][21] = "o";
		ad[22][33] = ad[33][22] = "o";
		ad[22][34] = ad[34][22] = "o";
		ad[23][35] = ad[35][23] = "o";
		ad[23][36] = ad[36][23] = "o";
		ad[24][30] = ad[30][24] = "o";
		ad[25][26] = ad[26][25] = "o";
		ad[25][37] = ad[37][25] = "o";
		ad[26][27] = ad[27][26] = "o";
		ad[26][30] = ad[30][26] = "o";
		ad[26][37] = ad[37][26] = "o";
		ad[27][28] = ad[28][27] = "o";
		ad[27][30] = ad[30][27] = "o";
		ad[27][38] = ad[38][27] = "o";
		ad[27][39] = ad[39][27] = "o";
		ad[28][29] = ad[29][28] = "o";
		ad[28][39] = ad[39][28] = "o";
		ad[29][39] = ad[39][29] = "o";
		ad[29][40] = ad[40][29] = "o";
		ad[31][41] = ad[41][31] = "o";
		ad[31][33] = ad[33][31] = "o";
		ad[32][33] = ad[33][32] = "o";
		ad[33][34] = ad[34][33] = "o";
		ad[33][41] = ad[41][33] = "o";
		ad[33][42] = ad[42][33] = "o";
		ad[34][35] = ad[35][34] = "o";
		ad[34][42] = ad[42][34] = "o";
		ad[34][43] = ad[43][34] = "o";
		ad[35][36] = ad[36][35] = "o";
		ad[35][44] = ad[44][35] = "o";
		ad[36][44] = ad[44][36] = "o";
		ad[38][39] = ad[39][38] = "o";
		ad[38][45] = ad[45][38] = "o";
		ad[38][40] = ad[30][38] = "o";
		ad[39][40] = ad[40][39] = "o";
		ad[40][46] = ad[46][40] = "o";
		ad[40][45] = ad[45][40] = "o";
		ad[41][48] = ad[48][41] = "o";
		ad[42][43] = ad[43][42] = "o";
		ad[42][47] = ad[47][42] = "o";
		ad[42][48] = ad[48][42] = "o";
		ad[43][47] = ad[47][43] = "o";
		ad[45][46] = ad[46][45] = "o";
		ad[45][47] = ad[47][45] = "o";
		ad[48][45] = ad[45][48] = "o";
		ad[46][47] = ad[47][46] = "o";
		ad[47][48] = ad[48][47] = "o";
		// 인접성 설정

		/*
		 * for (int i = 0; i < 49; i++) { for (int j = 0; j < 49; j++)
		 * System.out.printf("%s\t", ad[i][j]); System.out.println(); }
		 */
		// 위는 인접성이 잘 설정됬는지 확인하기 위한 반복문이다.

		// 도시정보 설정
		city[0] = new City("애틀란타", "Blue", 1, 760, 900);
		city[0].Labatory = true;// 애틀란타에는 실험실이 건설되어있다
		city[1] = new City("워싱턴", "Blue", 2, 960, 904);
		city[2] = new City("시카고", "Blue", 3, 648, 700);
		city[3] = new City("마이애미", "Yellow", 4, 866, 1020);
		city[4] = new City("멕시코 시티", "Yellow", 5, 669, 1032);
		city[5] = new City("몬트리올", "Blue", 6, 857, 683);
		city[6] = new City("뉴욕", "Blue", 7, 1003, 762);
		city[7] = new City("보고타", "Yellow", 8, 913, 1180);
		city[8] = new City("리마", "Yellow", 9, 734, 1227);
		city[9] = new City("로스앤젤레스", "Yellow", 10, 458, 1029);
		city[10] = new City("샌프란시스코", "Blue", 11, 444, 852);
		city[11] = new City("런던", "Blue", 12, 1346, 598);
		city[12] = new City("마드리드", "Blue", 13, 1313, 870);
		city[13] = new City("산티아고", "Yellow", 14, 795, 1400);
		city[14] = new City("상파울루", "Yellow", 15, 1126, 1300);
		city[15] = new City("부에노스아이레스", "Yellow", 16, 1004, 1402);
		city[16] = new City("도쿄", "Red", 17, 2699, 800);
		city[17] = new City("마닐라", "Red", 18, 2592, 1127);
		city[18] = new City("시드니", "Red", 19, 2693, 1395);
		city[19] = new City("에센", "Blue", 20, 1549, 583);
		city[20] = new City("파리", "Blue", 21, 1480, 780);
		city[21] = new City("알제", "Black", 22, 1497, 930);
		city[22] = new City("라고스", "Yellow", 23, 1432, 1100);
		city[23] = new City("오사카", "Red", 24, 2729, 970);
		city[24] = new City("서울", "Red", 25, 2553, 733);
		city[25] = new City("상하이", "Red", 26, 2376, 820);
		city[26] = new City("홍콩", "Red", 27, 2403, 1005);
		city[27] = new City("호치민 시티", "Red", 28, 2395, 1203);
		city[28] = new City("자카르타", "Red", 29, 2240, 1300);
		city[29] = new City("타이베이", "Red", 30, 2545, 985);
		city[30] = new City("상트페테르부르크", "Blue", 31, 1725, 570);
		city[31] = new City("밀라노", "Blue", 32, 1620, 705);
		city[32] = new City("이스탄불", "Black", 33, 1690, 850);
		city[33] = new City("카이로", "Black", 34, 1680, 1000);
		city[34] = new City("카르툼", "Yellow", 35, 1770, 1155);
		city[35] = new City("킨샤샤", "Yellow", 36, 1600, 1215);
		city[36] = new City("베이징", "Red", 37, 2360, 650);
		city[37] = new City("콜카타", "Black", 38, 2230, 855);
		city[38] = new City("방콕", "Red", 39, 2290, 1098);
		city[39] = new City("첸나이", "Black", 40, 2145, 1040);
		city[40] = new City("모스크바", "Black", 41, 1880, 590);
		city[41] = new City("바그다드", "Black", 42, 1873, 880);
		city[42] = new City("리야드", "Black", 43, 1886, 1027);
		city[43] = new City("요하네스버그", "Yellow", 44, 1700, 1354);
		city[44] = new City("델리", "Black", 45, 2135, 700);
		city[45] = new City("뭄바이", "Black", 46, 2040, 1073);
		city[46] = new City("카라치", "Black", 47, 2023, 900);
		city[47] = new City("테헤란", "Black", 48, 1945, 700);
	}

	public String[] returntext() {
		return name;
	}// 도시의 이름을 배열로 넘겨준다.49개첫번째 글자는 공백

	public void draw(Graphics g) {
		for (int i = 0; i < city.length; i++) {
			city[i].draw(g);
		}
	}// 도시들을 다 그리기

	public ArrayList<String> AdjacencyCitys(String e) {
		// 인접성을 식별해서 인접한 도시들을 배열로 보내준다.
		// 인접성을 판별해서 인접한 도시들의 ArrayList<String> 값으로 반환
		// 매개변수 e로 현재 캐릭터의 도시 위치(String)가 들어온다.
		ArrayList<String> Adjacency = new ArrayList<String>();
		int position = 0;
		for (position = 0; position < 49; position++) {
			if (ad[0][position].equals(e))
				break;
			// 행이 0일 때 즉 e 값이 어딨는지 찾는다.
		}

		for (int i = 0; i < 49; i++) {
			// 위에서 현재도시가 판별됬으니 그 도시를 기준으로 o가 되있는 도시들을 다 Adjacency에 넣는다
			if (ad[i][position].equals("o")) {
				Adjacency.add(ad[0][i]);
			}
		}
		return Adjacency;
	}

	// 인접 도시의 도시객체를 반환한다.
	public static ArrayList<City> AdjacencyCity(String e) {
		// 인접성을 식별해서 인접한 도시들을 배열로 보내준다.
		// 인접성을 판별해서 인접한 도시들의 ArrayList<String> 값으로 반환
		// 매개변수 e로 현재 캐릭터의 도시 위치(String)가 들어온다.
		ArrayList<City> Adjacency = new ArrayList<City>();
		int position = 0;
		for (position = 0; position < 49; position++) {
			if (ad[0][position].equals(e))
				break;
			// 행이 0일 때 즉 e값 도시가 그래프에서 어딨는지 찾는다.
		}

		for (int i = 0; i < 49; i++) {
			// 위에서 현재도시가 판별됬으니 그 도시를 기준으로 o가 되있는 도시들을 다 Adjacency에 넣는다
			if (ad[i][position].equals("o")) {
				Adjacency.add(city[i - 1]);// 도시리스트에 인접 도시 정보를 넣는다.
				// 도시 이중배열의 경우 ad인접성 배열고 달리 공백이없어 크기가 48이다.
			}
		}
		return Adjacency;
	}

	public ArrayList WhatVirus(String CurrentCity) {
		ArrayList VirusList = new ArrayList<String>();
		for (int i = 0; i < 48; i++) {
			if (CurrentCity.equals(city[i].getName())) {
				if (city[i].Red > 0)
					VirusList.add("Red");
				if (city[i].Blue > 0)
					VirusList.add("Blue");
				if (city[i].Black > 0)
					VirusList.add("Black");
				if (city[i].Yellow > 0)
					VirusList.add("Yellow");
			} // 빨파검노순으로 바이러스가 있다면 리스트에 추가시킨다.
		}
		return VirusList;
	}// 현재 그 도시에 위치하는 바이러스의 갯수들을 알아내는 메소드

	public Point CityPosition(String CurrentCity) {
		// 현재의 도시명(String)을 입력 받아서 그 도시의 좌표 Point(x,y)를 반환하는 메소드
		Point current = new Point(0, 0);
		System.out.println("작동됨");
		for (int i = 0; i < 48; i++) {
			if (CurrentCity.equals(city[i].getName())) {
				current.setX(city[i].getX());
				current.setY(city[i].getY());
			}
		}
		return current;
	}

	public City returnCity(String CurrentCity) {
		// 도시명을 입력받아서 그 도시 객체를 반환하는 메소드
		City rcity;
		int num = 0;
		for (int i = 0; i < 48; i++) {
			if (CurrentCity.equals(city[i].getName())) {
				num = i;
				break;
			}
		}
		rcity = city[num];
		return rcity;
	}
}

class City {// 도시 클래스
	String name;// 도시명
	String color;// 해당도시색
	private int x, y;// 좌표
	int myNum;// 해당 도시의 ad그래프 순서
	int Red = 0, Blue = 0, Black = 0, Yellow = 0;// 바이러스
	boolean Labatory = false;// 실험실 기본적으로 애틀란타를 제외하고 다 false
	Image LabatoryImage;
	Image RedCube = new ImageIcon(Map.class.getResource("../Image/RedCube.png")).getImage();
	private boolean protection = false;// true일시 바이러스가 놓이지 않는다.

	public static ArrayList<String> visit = new ArrayList<String>();

	public City() {
	}

	public City(String name, String color, int myNum, int x, int y) {
		this.name = name;
		this.color = color;
		this.myNum = myNum;
		this.x = x;
		this.y = y;
		LabatoryImage = new ImageIcon(Map.class.getResource("../Image/Labatory.png")).getImage();
	}

	public void setProtection(boolean t) {
		this.protection = t;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean getLabatory() {
		return Labatory;
	}

	public void setLabatory() {
		Labatory = true;
	}

	public void draw(Graphics g) {
		if (Labatory)//실험실이 true라면 실험실을 그린다
			g.drawImage(LabatoryImage, x, y, null);
		drawCube(g);//도시에 해당되는 바이러스들을 그림다.
	}

	public void PlusVirus(String color, int i) {
		// 일단 Red,Black,Yellow,Blue 텍스트를 읽어서 해당 텍스트면 해당 색깔의 바이러스가 그려지도록 만들겠다.
		if (protection)// 방역시 바이러스가 놓일일이 없으니 바로 return
			return;

		if (color.equals("Red")) {// 빨간색 바이러스
			if (MainPanel.RedVirus == 0 && MainPanel.RedCure) {
				return;// 빨간치료제가 개발되고 지도에 빨간 바이러스가 없다. 즉 근절상태는 바이러스가 놓이지 않는다.
			}
			MainPanel.RedVirus = MainPanel.RedVirus + i;
			if ((Red = Red + i) > 3) {
				Red = Red - i;
				MainPanel.RedVirus = MainPanel.RedVirus - i;
				diffusionVirus(color);
			}
		} else if (color.equals("Blue")) {// 파란색 바이러스
			if (MainPanel.BlueVirus == 0 && MainPanel.BlueCure) {
				return;// 파란치료제 개발 && 지도에 파란바이러스가 없다. 파란색 바이러스 근절상태 파란색 바이러스는 더 이상 놓이지 않는다.
			}
			MainPanel.BlueVirus = MainPanel.BlueVirus + i;
			if ((Blue = Blue + i) > 3) {
				Blue = Blue - i;
				MainPanel.BlueVirus = MainPanel.BlueVirus + i;
				diffusionVirus(color);
			}
		} else if (color.equals("Yellow")) {// 노란색 바이러스
			if (MainPanel.YellowVirus == 0 && MainPanel.YellowCure) {
				return;// 노란색치료제개발&&지도에 노란바이러스가 없다. 노란색 바이러스 근절 노란색 바이러스는 더이상 놓이지 않는다.
			}
			MainPanel.YellowVirus = MainPanel.YellowVirus + i;
			if ((Yellow = Yellow + i) > 3) {
				Yellow = Yellow - i;
				MainPanel.YellowVirus = MainPanel.YellowVirus - i;
				diffusionVirus(color);
			}
		} else if (color.equals("Black")) {// 검은색 바이러스
			if (MainPanel.BlackVirus == 0 && MainPanel.BlackCure) {
				return;// 검은색치료제개발&&지도에 검은색바이러스가 없다. 검은색 바이러스근절 검은색 바이러스는 더이상놓이지 않는다.
			}
			MainPanel.BlackVirus = MainPanel.BlackVirus + i;
			if ((Black = Black + i) > 3) {
				Black = Black - i;
				MainPanel.BlackVirus = MainPanel.BlackVirus - i;
				diffusionVirus(color);
			}
		} else {// 에러!!!!
			System.out.println("Citys PlusVirus에 잘못된 문자가 입력 되었다.");
		}

		// 패배 조건 중 하나인 바이러스 큐브가 24개 이상일 때 실패하는 부분
		if (MainPanel.RedVirus >= 24 || MainPanel.BlueVirus >= 24 || MainPanel.YellowVirus >= 24
				|| MainPanel.BlackVirus >= 24) {
			System.out.println("패배 : 바이러스 큐브 24개 오버");
			try {
				MainPanel.GameOutStream.writeUTF("[제어]fail");// 게임에서 졌다는 문을 서버에 보낸다.
				Citys.fail = true;// 패배...
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MainPanel.setVirusCount();// 유저가 현재 진행상황을 쉽게 보도록
	}

	//확산이 일어 났을때 이벤트 메소드이다. 이때 매개변수로 바이러스의 색깔을 넘겨받는다.
	public void diffusionVirus(String color) {
		//한번 확산이 일어난곳은 더이상 확산이 일어나지 않도록 visit라는 리스트를 만들어서 저장한다.
		City.visit.add(name);
		//해당 도시 주위의 인접한 도시들의 객체를 가지고온다.
		ArrayList<City> adCity = Citys.AdjacencyCity(name);
		for (int j = 0; j < adCity.size(); j++) {
			//인접되어 있는 도시에 plusVirus()메소드를 호출해서 바이러스를 바이러스를 발생시킨다.
			//인접된 도시가 방문되지 않았을때 바이러스를 발생시킴
			if (!City.visit.contains(adCity.get(j).name)) //방문된 도시인지 확인
				adCity.get(j).PlusVirus(color, 1);
		}

		MainPanel.setDiffusion();// 메인패널의 유저가 현재상황을보는 Count패널 업데이트

		// 패배 조건 중 하나인 확산이 7번 일어날 때 실패하는 부분
		if (MainPanel.DiffusionCount == 7) {
			try {
				MainPanel.GameOutStream.writeUTF("[제어]fail");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" 확산 마커 패배");
			Citys.fail = true;
		}

	}

	public void drawCube(Graphics g) {// 도시에 바이러스를 그린다.
		int height = RedCube.getHeight(null);
		int width = RedCube.getWidth(null);
		// 바이러스 큐브의 너비와 높이 계산
		int x = this.getX() - 20;
		int y = this.getY() - 30;

		for (int i = 0; i < Red; i++) {
			DrawCubeIn("Red", x, y, g);
			x += width;
		}
		for (int i = 0; i < Blue; i++) {
			DrawCubeIn("Blue", x, y, g);
			x += width;
		}
		for (int i = 0; i < Black; i++) {
			DrawCubeIn("Black", x, y, g);
			x += width;
		}
		for (int i = 0; i < Yellow; i++) {
			DrawCubeIn("Yellow", x, y, g);
			x += width;
		}
		// 큐브들을 그린다
	}

	public void DrawCubeIn(String color, int x, int y, Graphics g) {
		Image Cube = new ImageIcon(Map.class.getResource("../Image/" + color + "Cube.png")).getImage();
		g.drawImage(Cube, x, y, null);
	}// x,y좌표에 큐브그린다

}
