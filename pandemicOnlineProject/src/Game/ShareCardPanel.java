package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.MoveLabatoryPanel.MoveLabatoryLabel;
import pandemic.Client;

public class ShareCardPanel extends ControlShape implements MouseListener{
	private ControlPanel controlPanel;
	ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/button.png"));
	String myUserCity;
	Card cityCard;
	
	public ShareCardPanel(ControlPanel controlPanel) {
		// TODO Auto-generated constructor stub
		this.controlPanel = controlPanel;
		myUserCity = controlPanel.Mainpanel.characterList.get(Client.name).getCurrentposition();
		addUserLable();
		
		JLabel Back = new JLabel("Back", button, JLabel.CENTER);
		Back.setVerticalTextPosition(JLabel.CENTER);
		Back.setHorizontalTextPosition(JLabel.CENTER);
		Back.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				breakPanel();
			}
		});
		add(Back);
	}
	
	public void addUserLable() {
		Collection<Character> userCh = controlPanel.Mainpanel.characterList.values();
		for(Character ch : userCh) {
			if(!ch.getUserName().equals(Client.name) && ch.getCurrentposition().equals(myUserCity)) {
				JLabel t = new JLabel("", button, JLabel.CENTER);
				t.setText(ch.getUserName());
				t.setVerticalTextPosition(JLabel.CENTER);
				t.setHorizontalTextPosition(JLabel.CENTER);
				t.addMouseListener(this);
				add(t);
			}
		}
	}
	
	//해당 도시카드 있는지 확인
	public boolean CityCardConfirm() {
		for(Card c : controlPanel.Havecard.List) {
			if(c.getCityName().equals(myUserCity)) {
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
			
			
			
			int result = JOptionPane.showConfirmDialog(null, "카드를 받으시겠습니까?(예:받음, 아니오:보냄)", "공유 선택", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.CLOSED_OPTION) {
				//엑스 버튼 눌렀을때
			}else if(result == JOptionPane.YES_OPTION) {
				//예를 눌렀을때
				controlPanel.Mainpanel.GameOutStream.writeUTF("[공유]"+userName+":"+myUserCity);
				Client.CardPrint = true;
			}else {
				//아니오(보낸다)
				if(CityCardConfirm()) {
					controlPanel.Mainpanel.GameOutStream.writeUTF("[공유]"+userName+":보냄");
					controlPanel.Havecard.removeCard(myUserCity);
					
					breakPanel();
				}else {
					JOptionPane.showMessageDialog(null, "해당 카드가 없습니다.");
				}
			}
		}
		}catch (Exception e1) {
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
