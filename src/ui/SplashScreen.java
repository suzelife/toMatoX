package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.ImageLoader;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class SplashScreen extends JFrame {

	public SplashScreen() {
		setSize(new Dimension(644, 65));
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(ImageLoader.getImage("title"), 0, 0, null);
				g2.dispose();
			}
		};
		setContentPane(panel);
	}
}
