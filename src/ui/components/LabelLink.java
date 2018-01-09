package ui.components;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LabelLink extends JLabel {
	
	public LabelLink(String text) {
		setText("<html><u>" + text + "</u></html>");
		setFont(new Font("Segoe UI", Font.PLAIN, 18));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
	}

}
