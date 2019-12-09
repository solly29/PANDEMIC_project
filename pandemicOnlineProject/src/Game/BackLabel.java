package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.MainPanel.Map;

class BackLabel extends JLabel {
	// 뒤로가기 라벨은 많이 쓰이니까 그냥 라벨로 만들었다.
	ControlPanel Controlpanel;
	ImageIcon BackIcon = new ImageIcon(Map.class.getResource("../Image/back.png"));// 뒤로가기버튼

	public BackLabel(ControlPanel Controlpanel) {
		this.setText("뒤로");
		this.setIcon(BackIcon);
		this.Controlpanel = Controlpanel;
		Font font = new Font("HY헤드라인M", Font.PLAIN, 20);//폰트설정
		this.setFont(font);//폰트적용
		this.setForeground(Color.white);//글자색적용
		this.setVerticalTextPosition(JLabel.BOTTOM);//글자위치설정
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.addMouseListener(new MouseAdapter() {//마우스 이벤트 적용
			public void mousePressed(MouseEvent e) {
				Controlpanel.invalidate();
				Controlpanel.removeAll();
				Controlpanel.add(new BasicSelect(Controlpanel));//베이직셀렉트로 돌아간다.
				Controlpanel.revalidate();
				Controlpanel.repaint();
				Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 300);
				//핸드패널같이 y축으로 크기를 증가시키는 패널들이 있기에 크기를 조절시켜준다.
			}
		});
	}
}