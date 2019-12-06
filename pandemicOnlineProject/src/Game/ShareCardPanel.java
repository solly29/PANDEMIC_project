package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pandemic.Client;

public class ShareCardPanel extends ControlShape implements MouseListener {
	private ControlPanel controlPanel;
	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/back.png"));
	String myUserCity;
	Card cityCard;
	Font font = new Font("HY헤드라인M", Font.PLAIN, 20);

	public ShareCardPanel(ControlPanel controlPanel) {
		// TODO Auto-generated constructor stub
		this.controlPanel = controlPanel;
		myUserCity = controlPanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
		addUserLable();

		JLabel Back = new JLabel("뒤로", button, JLabel.CENTER);
		Back.setVerticalTextPosition(JLabel.BOTTOM);
		Back.setHorizontalTextPosition(JLabel.CENTER);
		Back.setFont(font);
		Back.setForeground(Color.white);
		
		Back.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				breakPanel();
			}
		});
		add(Back);
	}

	public void addUserLable() {
		Collection<Character> userCh = controlPanel.Mainpanel.characterList.values();
		for (Character ch : userCh) {
			if (!ch.getUserName().equals(Client.name) && ch.getCurrentposition().equals(myUserCity)) {
				JLabel t = new JLabel("", button, JLabel.CENTER);
				t.setText(ch.getUserName());
				t.setVerticalTextPosition(JLabel.BOTTOM);
				t.setHorizontalTextPosition(JLabel.CENTER);
				t.setFont(font);
				t.setForeground(Color.white);
				t.addMouseListener(this);
				add(t);
			}
		}
	}

	// 해당 도시카드 있는지 확인
	public boolean CityCardConfirm() {
		for (Card c : controlPanel.Havecard.List) {
			if (c.getCityName().equals(myUserCity)) {
				cityCard = c;
				return true;
			}
		}

		return false;
	}

	public void breakPanel() {
		controlPanel.invalidate();
		controlPanel.removeAll();
		controlPanel.add(new BasicSelect(controlPanel));
		controlPanel.revalidate();
		controlPanel.repaint();
		controlPanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if (!Client.CardPrint) {
				JLabel label = (JLabel) e.getSource();
				String userName = label.getText();

				int result = JOptionPane.showConfirmDialog(null, "카드를 받으시겠습니까?(예:받음, 아니오:보냄)", "공유 선택",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.CLOSED_OPTION) {
					// 엑스 버튼 눌렀을때
				} else if (result == JOptionPane.YES_OPTION) {
					// 예를 눌렀을때
					controlPanel.Mainpanel.GameOutStream.writeUTF("[공유]" + userName + ":" + myUserCity + ":전달");
					Client.CardPrint = true;
				} else {
					// 아니오(보낸다)
					if(controlPanel.Mainpanel.myjob.equals("researcher")) {
						ArrayList<Card> list = controlPanel.Havecard.List;
						String[] texts = new String[list.size()];// 카드 숫자만큼 배열을 만든다.

						for (int i = 0; i < list.size(); i++) {
							texts[i] = list.get(i).getCityName();// 카드숫자배열에 이름을 지정한다.
						}

						String city = (String) JOptionPane.showInputDialog(null, "보낼 카드를 선택 하세요", "선택",
								JOptionPane.INFORMATION_MESSAGE, null, texts, texts[0]);
						
						controlPanel.Mainpanel.GameOutStream.writeUTF("[공유]" + userName + ":" + city + ":보냄");
						controlPanel.Havecard.removeCard(city);
					}else {
						if (CityCardConfirm()) {
							controlPanel.Mainpanel.GameOutStream.writeUTF("[공유]" + userName + ":" + myUserCity + ":보냄");
							controlPanel.Havecard.removeCard(myUserCity);
						} else {
							JOptionPane.showMessageDialog(null, "해당 카드가 없습니다.");
						}
					}
					breakPanel();
				}
			}
		} catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
