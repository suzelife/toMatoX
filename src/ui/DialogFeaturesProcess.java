package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class DialogFeaturesProcess extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] imageLabels = { 
			"200 x 200", 
			"Blue Channel", 
			"Grayscale", 
			"Binary Mask",
			"Segmented",
			"Cropped",
			"Processed"
		};

	public DialogFeaturesProcess() {
		super(FrameMain.getInstance(), true);
		setSize(450, 300);
		setLocationRelativeTo(FrameMain.getInstance());
		setTitle("Feature Extraction Process");
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.GRAY);
		
		JScrollPane scrollPane = new JScrollPane(contentPanel, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setSize(670, 220);	
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	public void showProcess(BufferedImage[] images) {
		contentPanel.removeAll();
		for (int i = 0; i < images.length; i++) {
			contentPanel.add(new PanelProcess(new JLabel(new ImageIcon(images[i])), imageLabels[i]));
		}
	}

}
