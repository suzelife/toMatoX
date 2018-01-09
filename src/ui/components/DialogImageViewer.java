package ui.components;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.FrameClassificationResult;

@SuppressWarnings("serial")
public class DialogImageViewer extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public DialogImageViewer(FrameClassificationResult frameClassificationResult, String url) {
		super(frameClassificationResult, true);
		setTitle(url);
		setSize(500, 250);
		setLocationRelativeTo(frameClassificationResult);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(url));
		contentPanel.add(lblNewLabel, BorderLayout.CENTER);
	}

}
