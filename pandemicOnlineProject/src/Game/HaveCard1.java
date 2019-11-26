package Game;

import java.io.IOException;
import java.util.ArrayList;

import pandemic.Client;

public class HaveCard1 {
	ArrayList<Card> List = new ArrayList<Card>();
	int count = 0;
	ControlPanel Controlpanel;

	HaveCard1(ControlPanel Controlpanel) {
		this.Controlpanel = Controlpanel;
	}

	public void insertCard(ControlPanel Controlpanel, String CityName, String Color) {// 뒤에 카드 추가.
		Card card = new Card(Controlpanel,CityName, Color);
		List.add(card);
		++count;
		// 만약 카드 숫자가 7장 초과면 버리는 이벤트 추가.
	}

	public void removeCard(String CityName) {
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getCityName().equals(CityName)) {
				List.remove(i);
				--count;
			}
		}
	}

	public void removeCards(String[] removecards) {
		for (int i = 0; i < removecards.length; i++)
			removeCard(removecards[i]);
	}

	public void DevelopeCure(String Color) {
		int Cure_Source = 0;
		String color;
		String[] PreditedRemove = new String[5];
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getColor().equals(Color)) {
				++Cure_Source;
				PreditedRemove[i] = List.get(i).getCityName();
			}
		}
		if (Cure_Source == 5) {
			if (Color.equals("Red")) {
				Game.RedCure = true;
				color = "Red";
			} else if (Color.equals("Blue")) {
				Game.BlueCure = true;
				color = "Blue";
			} else if (Color.equals("Yellow")) {
				Game.YellowCure = true;
				color = "Yellow";
			} else {
				Game.BlackCure = true;
				color = "Black";
			}
			removeCards(PreditedRemove);// 5장의 카드를 한번에 삭제
			try {
				Controlpanel.Mainpanel.GameOutStream.writeUTF("[개발]"+color);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void printList() {
		for (int i = 0; i < List.size(); i++) {
			System.out.print(List.get(i).getCityName());
		}
		System.out.println();
	}

	public void BuildLabatory(String CityName) {
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getCityName().equals(CityName)
					&& (Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory == false)) {
				removeCard(CityName);
				Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory = true;
				Controlpanel.Mainpanel.repaint();
			}
		}
	}
	
	public ArrayList<Card> ReArray(){
		return List;
	}

}
