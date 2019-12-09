package Game;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import pandemic.Login;

public class ESC extends JOptionPane {
//게임플레이시 게임 종료 창
	public ESC() {

		int i = JOptionPane.showConfirmDialog(null,
				"정말로 종료 하시겠습니까? 지금 종료하시면 다시는 들어올 수 없습니다. 당신이 없으면 이 게임에서 승리 할 수 없어요!!", "나가기",
				JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		if (i == 0) {
			System.exit(Login.getTop().EXIT_ON_CLOSE); // 프레임을 종료한다
		} else {
			return;
		}
	}

}
