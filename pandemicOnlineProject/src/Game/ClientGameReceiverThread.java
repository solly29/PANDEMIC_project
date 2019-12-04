package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import pandemic.Client;

//클라에서 게임 행동을 받는 메소드이다.
public class ClientGameReceiverThread implements Runnable {
	private Socket socket;
	private MainPanel mainPanel;
	private cardLabel labelTh;// 화면위에 잠깐 띄어줄 카드 라벨이다.
	private boolean turnStart = false;// 자기가 현제 턴인지 확인한다.
	private String turnUser = Client.name;

	public ClientGameReceiverThread(Socket socket, MainPanel mainPanel) {
		this.socket = socket;
		this.mainPanel = mainPanel;
	}

	public boolean CityCardConfirm() {
		String city = mainPanel.characterList.get(Client.name).getCurrentposition();
		for (Card c : mainPanel.Controlpanel.Havecard.List) {
			if (c.getCityName().equals(city)) {
				return true;
			}
		}
		return false;
	}

	// 공동으로 쓴다. 컨트롤패널을 지웠다가 다시 그려준다.
	public void panelRepaint() {
		mainPanel.Controlpanel.invalidate();
		mainPanel.Controlpanel.removeAll();
		mainPanel.Controlpanel.add(new BasicSelect(mainPanel.Controlpanel));
		mainPanel.Controlpanel.revalidate();
		mainPanel.Controlpanel.repaint();
		mainPanel.repaint();
		mainPanel.Controlpanel.setBounds(0, 840, 1920, 240);
	}

	// 치료를 할때 해당 도시와 질병 큐브 색깔을 보내서 수행한다.
	public void colorSelect(String color, String cityName) {
		City city = mainPanel.Controlpanel.Mainpanel.citys.returnCity(cityName);
		String tempjob = mainPanel.myjob;// 상상지역변수 찬영이가 이어줄꺼야
		if (color.equals("Red") && (Game.RedCure || tempjob.equals("soilder"))) {
			Game.RedVirus -= city.Red;
			city.Red = 0;
		} else if (color.equals("Red") && !Game.RedCure) {
			--city.Red;
			Game.RedVirus--;
		} else if (color.equals("Black") && (Game.BlackCure || tempjob.equals("soilder"))) {
			Game.BlackVirus -= city.Black;
			city.Black = 0;
		} else if (color.equals("Black") && !Game.BlackCure) {
			--city.Black;
			Game.BlackVirus--;
		} else if (color.equals("Blue") && (Game.BlueCure || tempjob.equals("soilder"))) {
			Game.BlueVirus -= city.Blue;
			city.Blue = 0;
		} else if (color.equals("Blue") && !Game.BlueCure) {
			--city.Blue;
			Game.BlueVirus--;
		} else if (color.equals("Yellow") && (Game.YellowCure || tempjob.equals("soilder"))) {
			Game.YellowVirus -= city.Yellow;
			city.Yellow = 0;
		} else if (color.equals("Yellow") && !Game.YellowCure) {
			--city.Yellow;
			Game.YellowVirus--;
		}

	}

	public void run() {
		try {
			System.out.println("CRT start");
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while (true) {
				System.out.println(socket);
				String str = reader.readUTF();
				System.out.println(str);
				if (str.equals("[제어]stop") || reader == null) {
					System.out.println("CRT end");
					break;
				} else {
					if (str.substring(0, 4).equals("[이동]")) {
						System.out.println("ad");
						str = str.substring(4);
						String[] str2 = str.split(":");
						mainPanel.characterList.get(str2[0]).setCurrentposition(str2[1]);// 캐릭터의 현재 위치를 이동시키기 위해
																							// 이동도시(String)를 바꾸고

						Point pos = mainPanel.citys.CityPosition(str2[1]);
						String Color = mainPanel.citys.returnCity(str2[1]).color;
						mainPanel.characterList.get(str2[0]).setCC(str2[1], Color);
						mainPanel.characterList.get(str2[0]).setXY(pos.getX(), pos.getY());// 캐릭터의 좌표를 바꾼다.(xy)

						if (str2[0].equals(Client.name))
							panelRepaint();
					} else if (str.substring(0, 4).equals("[목록]")) {
						str = str.substring(4);
						str = str.substring(1, str.length() - 1);

						String[] str2 = str.split(", ");
						System.out.println(str2);
						int c = 0;
						int j=0;
						for (int i = str2.length-1; i >= 0; i--) {
							mainPanel.characterList.put(str2[i],
									new Character(mainPanel, c, str2[i], mainPanel.otherjob[j]));
							c = c + 10;
							j++;
						}

					} else if (str.substring(0, 4).equals("[치료]")) {
						str = str.substring(4);
						String[] str2 = str.split(",");// 누가,색깔,도시

						colorSelect(str2[1], str2[2]);

						if (str2[0].equals(Client.name))
							panelRepaint();
						mainPanel.Controlpanel.Mainpanel.repaint();

					} else if (str.substring(0, 4).equals("[감염]")) {
						str = str.substring(4);
						String[] str2 = str.split(", ");
						
						

						Client.CardPrint = true;
						
						labelTh = new cardLabel(mainPanel, str2);
						labelTh.start();

						if (turnStart)
							Client.CardPrint = false;
						
						for (int t = 0; t < str2.length; t++) {
		                     mainPanel.history.addHistory(str2[t]);
		                  }

						Thread.sleep(1500*str2.length+1000);

						// 캐릭터로 화면이동 아직 오류있음
						
						/*int x = mainPanel.characterList.get(turnUser).getX(); 
						int y = mainPanel.characterList.get(turnUser).getX();
						mainPanel.map.setLocation(-x+(1960/2), -y+(1020/2)-120);*/
						

					} else if (str.substring(0, 4).equals("[도시]")) {
						str = str.substring(4);
						String[] str2 = str.split(", ");
						boolean InfectionCard = false;
						Card card = null;
						for (int i = 0; i < str2.length; i++) {
							if (str2[i].equals("전염")) {
								out.writeUTF("[제어]전염");
								InfectionCard = true;
							} else {
								String Color = "";
								if (mainPanel.citys.EventCard.contains(str2[i])) {
									Color = "special";
									mainPanel.Controlpanel.Havecard.insertCard(mainPanel.Controlpanel, str2[i]);
								} else {
									Color = mainPanel.citys.returnCity(str2[i]).color;
									mainPanel.Controlpanel.Havecard.insertCard(mainPanel.Controlpanel, str2[i], Color);
								}
								card = new Card(str2[i], Color);

								mainPanel.add(card, new Integer(20));
								card.setBounds(700, 200, 500, 600);
								Thread.sleep(1000);

								mainPanel.remove(card);
								mainPanel.repaint();
								mainPanel.revalidate();
								Thread.sleep(500);

							}

						}
						if (!InfectionCard) {
							out.writeUTF("");
						}

					} else if (str.substring(0, 4).equals("[건설]")) {
						str = str.substring(4);
						String[] str2 = str.split(":");
						mainPanel.citys.returnCity(str2[1]).Labatory = true;
						mainPanel.repaint();

						if (str2[0].equals(Client.name))
							panelRepaint();
					} else if (str.substring(0, 4).equals("[개발]")) {
						str = str.substring(4);
						if (str.equals("Red")) {
							Game.RedCure = true;
						} else if (str.equals("Blue")) {
							Game.BlueCure = true;
						} else if (str.equals("Yellow")) {
							Game.YellowCure = true;
						} else {
							Game.BlackCure = true;
						}
					} else if (str.substring(0, 4).equals("[제어]")) {
						str = str.substring(4);
						String[] str2 = str.split(":");
						if (str2[0].equals("turnStart") && str2[1].equals(Client.name)) {
							if (Game.BlackCure && Game.BlueCure && Game.RedCure && Game.YellowCure) {
								out.writeUTF("[제어]win");
							} else {
								out.writeUTF("a");
							}
							turnUser = str2[1];
							Client.CardPrint = false;
							turnStart = true;
						} else if (str2[0].equals("turnStop") && str2[1].equals(Client.name)) {
							if (Game.BlackCure && Game.BlueCure && Game.RedCure && Game.YellowCure) {
								out.writeUTF("[제어]win");
							} else {
								out.writeUTF("a");
							}
							turnUser = "";
							Client.CardPrint = true;
							turnStart = false;
						} else if (str2[0].equals("win")) {
							System.out.println("승리");
						}
						else if (str.equals("패배")) {//////////////////////
							System.out.println("패배");
							new fail();
						}
					} else if (str.equals("[전염]")) {
						Card card = new Card("", "전염");

						mainPanel.add(card, new Integer(20));
						card.setBounds(700, 200, 500, 600);
						Thread.sleep(1000);

						mainPanel.remove(card);
						mainPanel.repaint();
						mainPanel.revalidate();
						Thread.sleep(500);
					} else if (str.substring(0, 4).equals("[공유]")) {
						System.out.println(str);
						str = str.substring(4);
						String[] str2 = str.split(":");
						if (str2[1].equals("보냄")) {
							String city = mainPanel.characterList.get(Client.name).getCurrentposition();
							String Color = mainPanel.citys.returnCity(city).color;
							mainPanel.Controlpanel.Havecard.insertCard(mainPanel.Controlpanel, city, Color);
							if (turnStart) {
								mainPanel.GameOutStream.writeUTF("[공유]" + str2[0] + ":받음");
								Client.CardPrint = false;
								panelRepaint();
							}
						} else if (str2[1].equals("안됨")) {
							JOptionPane.showConfirmDialog(null, "상대방이 카드가 없거나 거절했습니다.", "에러",
									JOptionPane.YES_NO_OPTION);
							if (turnStart) {
								Client.CardPrint = false;
								panelRepaint();
							}
						} else {
							String city = mainPanel.characterList.get(Client.name).getCurrentposition();
							if (!CityCardConfirm()) {
								mainPanel.GameOutStream.writeUTF("[공유]" + str2[0] + ":안됨");
								continue;
							}

							int result = JOptionPane.showConfirmDialog(null, "카드를 보내시겠습니까?", "공유 선택",
									JOptionPane.YES_NO_OPTION);
							if (result == JOptionPane.CLOSED_OPTION) {
								// 엑스 버튼 눌렀을때
							} else if (result == JOptionPane.YES_OPTION) {
								// 예를 눌렀을때
								mainPanel.GameOutStream.writeUTF("[공유]" + str2[0] + ":보냄");
								mainPanel.Controlpanel.Havecard.removeCard(str2[1]);
							} else {
								// 아니오(보낸다)
								mainPanel.GameOutStream.writeUTF("[공유]" + str2[0] + ":안됨");
							}
						}
					} else if (str.substring(0, 4).equals("[예측]")) {
						str = str.substring(4);
						System.out.println(str);
						String[] str2 = str.split(", ");
						ArrayList<String> cardlist = new ArrayList<String>(Arrays.asList(str2));
						String[] selectCard = new String[6];
						for (int i = 0; i < 5; i++) {
							String card = (String) JOptionPane.showInputDialog(null, "버려진 카드 목록", "선택",
									JOptionPane.INFORMATION_MESSAGE, null, cardlist.toArray(), cardlist.toArray());
							cardlist.remove(card);
							selectCard[i] = card;
						}
						selectCard[5] = cardlist.get(0);
						mainPanel.GameOutStream.writeUTF("[예측]" + Arrays.toString(selectCard));

						panelRepaint();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
