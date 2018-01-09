package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class PanelProcess extends JPanel {

	public PanelProcess(JLabel labelImage, String labelText) {
		setLayout(new BorderLayout(0, 0));
		
		add(labelImage, BorderLayout.CENTER);
		
		JLabel lblText = new JLabel(labelText);
		lblText.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblText, BorderLayout.SOUTH);
	}

}
