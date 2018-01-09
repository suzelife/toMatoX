package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import utilities.ImageLoader;

@SuppressWarnings("serial")
public class PanelTitle extends JPanel {
	
	private BufferedImage icon;

	public PanelTitle() {
		icon = ImageLoader.getImage("title");
		setSize(644, 65);
		setLayout(null);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(icon, 0, 0, null);
		g.dispose();
	}
	
}
