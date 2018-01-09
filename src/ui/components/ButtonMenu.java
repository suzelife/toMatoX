package ui.components;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import utilities.ImageLoader;

@SuppressWarnings("serial")
public class ButtonMenu extends JButton {
	
	private BufferedImage icon, iconHover;
	private byte status = 0; //0 = inactive, 1 = hover, 2 = active
	private boolean isActive = false;

	public ButtonMenu(String name, String url) {
		super(name);
		icon = ImageLoader.getImage(url);
		iconHover = ImageLoader.getImage(url + "_hover");
		setSize(150, 40);
		setBorderPainted(false);
		setOpaque(false);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent mE) {
				status = 1;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				updateUI();
			}
			
			@Override
			public void mouseExited(MouseEvent mE) {
				if (isActive) {
					status = 2;
				}
				else {
					status = 0;
				}
				updateUI();
			}
		});
	}
	
	public void setActive() {
		status = 2;
		isActive = true;
		updateUI();
	}
	
	public void setInactive() {
		status = 0;
		isActive = false;
		updateUI();
	}
	
	@Override
	public void paint(Graphics g) {
		BufferedImage image = null;
		switch (status) {
			case 0:
				image = icon;
				break;
			case 1:
				image = iconHover;
				break;
			case 2:
				image = iconHover;
				break;
			default:
				break;
		}
		
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	
}
