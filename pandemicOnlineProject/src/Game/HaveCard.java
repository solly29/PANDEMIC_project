package Game;
/*package Play;

import java.util.ArrayList;

import javax.swing.JOptionPane;

//핸드 카드들 카드들의 더블링크드리스트다.
public class HaveCard {
	Card head;// 헤드 카드
	int count = 0;// 카드가 ?개 이상이 되는 지 확인하기 위해 새는 count다.
	ControlPanel Controlpanel;

	HaveCard(ControlPanel Controlpanel) {
		this.head = null;
		this.Controlpanel = Controlpanel;
	}

	public void insertCard(String CityName, String Color) {// 제일 뒤에 새 카드를 추가
		Card card = new Card(CityName, Color);// 카드 새엇ㅇ
		if (head == null) {// head가 Null일때. 손패에 아무 카드도 없다.
			head = card;
			card.prev = card;
			card.next = card;
		} else {
			Card current = head;
			while (current.next != head) {
				current = current.next;
			}
			Card next = current.next;
			card.next = next;
			next.prev = card;
			current.next = card;
			card.prev = current;
		}
		++count;// 카드가 추가 될때마다 count를 1증가 시켜준다.
	}

	public void removeCard(String CityName) {
		if (count == 1) {
			head = null;
			count--;
// 이 if조건이 필요한 이유는 링크드리스트를 쓸때 헤드가 하나만 남으니 지워지지 않았다. 
// head가 자기자신을 계속 참조하니 밑에 문장만으로는 지우지 못해서 count가 1 즉 손패에 1장만남았을때 remove명령이 쳐진다면 head를 null로 하는 것이다.
		} else if (head == null) {
			System.out.println("삭제할 카드가 없습니다.");
		} else {
			Card current = head;
			while (current.getCityName() != CityName) {
				current = current.next;
			}
			Card prev = current.prev;
			Card next = current.next;
			prev.next = next;
			next.prev = prev;
			--count;// 카드를 삭제하면 count를 1 뺀다.
		}
	}

	public void removeCards(String[] removecards) {
		for (int i = 0; i < removecards.length; i++)
			removeCard(removecards[i]);
	}// 배열로 여러장의 카드를 한번에 삭제

	public void DevelopeCure(String Color) {// 치료제 개발
		// Color 매개변수를 받는데 이는 Red,Blue,Black,Yellow를 입력받아서 손패에 해당되는 카드가 5장 있는지를 검사한다.
		int Cure_Source = 0;// 5개가 모였는 지 안모였는 지 확인한다.
		String[] PredictRemove = new String[5];// 5개 꽉차면 치료제를 완성할수있다!
		if (head == null) {
			System.out.println("출력할 내용이 존재하지 않습니다.");
		} else {
			Card current = head;
			if (current.getColor().equals(Color)) {// 도시카드의 색깔과 매개변수색깔이 같다면 조건충족!
				PredictRemove[Cure_Source] = current.getCityName();
				Cure_Source++;
				current = current.next;
			}
			while (current != head) {
				System.out.println(current.getCityName() + current.getColor());// 남겨둔 출력문.
				if (current.getColor().equals(Color)) {
					PredictRemove[Cure_Source] = current.getCityName();
					Cure_Source++;
				}
				current = current.next;
			}
		}
		if (Cure_Source == 5) {// 5개다. 백신을 만들수있다.
			if (Color.equals("Red")) {
				Game.RedCure = true;
			} else if (Color.equals("Blue")) {
				Game.BlueCure = true;
			} else if (Color.equals("Yellow")) {
				Game.YellowCure = true;
			} else {
				Game.BlackCure = true;
			}
			removeCards(PredictRemove);// 5장의 카드를 한번에 삭제
		}
	}

	public void printList() {// 전방향 순회
		if (head == null) {
			System.out.println("출력할 내용이 존재하지 않습니다.");
		} else {
			Card current = head;
			while (current.next != head) {
				System.out.println(current.getCityName() + " ");
				current = current.next;
			}
			System.out.println(current.getCityName());
		}
		System.out.println();
	}

	public void BuildLabatory(String CityName) {// 실험실 건설
		// 도시이름과 현재가지고 있는 카드의 이름이 같다면 같은 이름의 도시카드를 쓰는 걸로 실험실을 건설할수있다.
		Card current = head;
		System.out.println("1");
		if (count > 0&&(Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory==false)) {
			while (current.next != head) {
				System.out.println("2");
				if (current.getCityName().equals(CityName)&&(Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory==false)) {// 이름이 같다면
					System.out.println("3");
					removeCard(CityName);// 해당 카드를 삭제시키고
					Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory = true;
					Controlpanel.Mainpanel.repaint();
					current=current.next;
				}
				else {
					current=current.next;
				}
				System.out.println(current.getCityName());
				System.out.println(current.getCityName().equals(CityName));
				//break;
			}
			if (current.getCityName().equals(CityName)) {// 이름이 같다면
				removeCard(CityName);// 해당 카드를 삭제시키고
				Controlpanel.Mainpanel.citys.returnCity(CityName).Labatory = true;
				Controlpanel.Mainpanel.repaint();
			}
		}
	}

	public ArrayList<Card> LinkedToArray() {// Card JLabel을 반환하기 ArrayList형태로 반환하기 위한 메소드
		ArrayList<Card> Change = new ArrayList<Card>();
		Card current = head;

		if (head != null) {
			while (current.next != head) {
				Change.add(current);
				current = current.next;
			}
			Change.add(current);
		}
		return Change;
	}

	public void CheckCard() {
		while (count > 7) {

		}
	}

	/*
	 * class PopupRemove extends JOptionPane { PopupRemove(){
	 * 
	 * } }
	 */
