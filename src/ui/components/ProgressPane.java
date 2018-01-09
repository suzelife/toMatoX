package ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

@SuppressWarnings("serial")
public class ProgressPane extends JPanel {
	
	private JProgressBar progressBar;
	
	public ProgressPane() {
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setSize(420, 30);
		setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setUI(new BasicProgressBarUI());
		progressBar.setOpaque(false);
		progressBar.setBorderPainted(false);
		progressBar.setBounds(0, 0, this.getWidth(), this.getHeight()-1);
		progressBar.setMinimum(0);
		progressBar.setFont(new Font("Consolas", Font.PLAIN, 15));
		progressBar.setForeground(Color.RED);
		add(progressBar, 0);
	}
	
	public void reset(int max) {
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setMaximum(max);
	}

	public void incrementBar() {
		progressBar.setValue(progressBar.getValue()+1);
		this.updateUI();
	}
}
