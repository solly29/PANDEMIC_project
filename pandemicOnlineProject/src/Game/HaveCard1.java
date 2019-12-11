package Game;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Game.*;
import pandemic.Client;
//손패 그 자체다.
public class HaveCard1 {
	ArrayList<Card> List = new ArrayList<Card>();// 현재 유저가 가지고있는 카드들
	int count = 0;// 카드숫자를 센다.
	ControlPanel Controlpanel;
	
	HaveCard1(){
		
	}

	HaveCard1(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
	}
	
	public void insertCard(ControlPanel Controlpanel, String CityName, String Color) {// 뒤에 카드 추가.
		Card card = new Card(Controlpanel, CityName, Color);
		List.add(card);
		++count;// 카드를 받았으니 카드 숫자를 늘려준다.
		// 만약 카드 숫자가 7장 초과면 버리는 이벤트 추가.
		while (count > 7) {
			ChoiceAbandonedCard();
		}// 7장이 남을때까지반복한다.
	}//일반카드를 추가

	public void insertCard(ControlPanel Controlpanel, String CityName) {
		Card card = null;
		if (CityName.equals("평온한 하룻밤"))
			card = new PeaceNightCard(Controlpanel, CityName);
		else if(CityName.equals("파워업"))
			card=new PowerUp(Controlpanel, CityName);
		else if(CityName.equals("예측"))
			card=new PredictCard(Controlpanel, CityName);
		else if(CityName.equals("긴급공중수송"))
			card=new EmergencyAirCard(Controlpanel,CityName);
		else if(CityName.equals("정부보조금"))
			card=new GrandOfMoneyCard(Controlpanel,CityName);
		List.add(card);
		++count;
		// 만약 카드 숫자가 7장 초과면 버리는 이벤트 추가.
		while (count > 7) {
			ChoiceAbandonedCard();
		}
	}//특수카드를 추가한다.

	public String ChoiceAbandonedCard() {// 손패가 7장 초과시 어느 카드를 버릴 지 선택하는 메소드
		String[] texts = new String[List.size()];// 카드 숫자만큼 배열을 만든다.

		for (int i = 0; i < List.size(); i++) {
			texts[i] = List.get(i).getCityName();// 카드숫자배열에 이름을 지정한다.
		}

		String AbandonedCard = (String) JOptionPane.showInputDialog(null, "버릴 카드를 선택하세요", "선택",
				JOptionPane.INFORMATION_MESSAGE, null, texts, texts[0]);
		// JOptioPane으로 버릴 카드들을 선택한다.
		removeCard(AbandonedCard);
		return AbandonedCard;
	}

	public void removeCard(String CityName) {// 해당 이름의 카드를 버리는 메소드
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getCityName().equals(CityName)) {
				List.remove(i);
				--count;// 버린 후 카드 숫자를 꼭 줄여주자
			}
		}
	}

	public void removeCards(String[] removecards) {// 여러장의 카드를 한번에 버릴때
		for (int i = 0; i < removecards.length; i++)
			removeCard(removecards[i]);
	}

	public void DevelopeCure(String Color) {// 치료제 개발 메소드
		int Cure_Source = 0;// 5개가 되면 백신을 개발할수있다.
		String color="";
		String[] PreditedRemove = new String[5];
		for (int i = 0; i < List.size(); i++) {
	         if (List.get(i).getColor().equals(Color)) {//매개변수의 색깔과 손패의카드색이 같다!
	            PreditedRemove[Cure_Source] = List.get(i).getCityName();//그렇다면 배열에 넣는다
	            ++Cure_Source;//카운터를올리고
	         }
	      }
		
		// 과학자 이벤트
				String job = Controlpanel.Mainpanel.myjob;// 현재 임의의 값이다. 이 job은 캐릭터의 직업을 받아와야하는 데 쓰여야할 String값이다/
				if (Cure_Source >= 4 && job.equals("scientist")) {// 만약 과학자가 Cure_Source가 4이상 즉 과학자가 같은 카드를 4장이상 모았다.
					if (Color.equals("Red")) {
						MainPanel.RedCure = true;
						color = "Red";
					} else if (Color.equals("Blue")) {
						MainPanel.BlueCure = true;
						color = "Blue";
					} else if (Color.equals("Yellow")) {
						MainPanel.YellowCure = true;
						color = "Yellow";
					} else if (Color.equals("Black")) {
						MainPanel.BlackCure = true;
						color = "Black";
					}
					for (int i = 0; i < 4; i++)
						removeCard(PreditedRemove[i]);// 배열 중 딱 4장만 제거한다.
					try {
						Controlpanel.Mainpanel.GameOutStream.writeUTF("[개발]" + color);
					//개발이 되었다고 서버에 보낸다
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		
		if (Cure_Source == 5) {//과학자 외 캐릭터의 백신개발이벤트
			if (Color.equals("Red")) {
				MainPanel.RedCure = true;
				color = "Red";
			} else if (Color.equals("Blue")) {
				MainPanel.BlueCure = true;
				color = "Blue";
			} else if (Color.equals("Yellow")) {
				MainPanel.YellowCure = true;
				color = "Yellow";
			} else if (Color.equals("Black")){
				MainPanel.BlackCure = true;
				color = "Black";
			}
			removeCards(PreditedRemove);// 5장의 카드를 한번에 삭제
			try {
				Controlpanel.Mainpanel.GameOutStream.writeUTF("[개발]" + color);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void BuildLabatory(String CityName) {// 연구소 건설
		for (int i = 0; i < List.size(); i++) {
			// 손패에 해당되는 해당되는 도시카드와 매개변수로 받은 도시의 이름이 같고, 해당도시에 연구소가 없을 때 연구소를 건설한다
			if (List.get(i).getCityName().equals(CityName)
					&& (Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory == false)) {
				removeCard(CityName);// 해당카드는 삭제
				try {
					Controlpanel.Mainpanel.GameOutStream.writeUTF("[건설]" + Client.name + ":" + CityName);
					//서버에 연구소 건설을 알림
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String tempjob = Controlpanel.Mainpanel.myjob;
		if (tempjob.equals("builder")) {//만약 현재 직업이 건축가일때
			String build=null;
			build=ChoiceAbandonedCard();//아무카드를 버림으로써 
			if(build!=null)
			try {
				Controlpanel.Mainpanel.GameOutStream.writeUTF("[건설]" + Client.name + ":" + CityName);
				//실험실을 건설할수있다.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Card> ReArray() {
		return List;
	}

}
