package Game;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyFocuseListener implements FocusListener {

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println(e.getSource().getClass().getName());
		System.out.println("MyFocuseListener.focusGained()");
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println(e.getSource().getClass().getName());
		System.out.println("MyFocuseListener.focusLost()");
	}

}