package ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utilities.ImageLoader;

@SuppressWarnings("serial")
public class PanelAbout extends JPanel {

	public PanelAbout() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setSize(464, 373);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(ImageLoader.getImage("about")));
		lblNewLabel.setBounds(10, 11, 444, 351);
		add(lblNewLabel);
	}
}
