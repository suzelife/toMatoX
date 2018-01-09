package ui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PanelTools extends JPanel {

	private PanelFeatureExtractor panelFeatureExtractor;
	private PanelDataGenerator panelDataRandomizer;
	
	public PanelTools() {
		initComponents();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setSize(464, 373);
		setLayout(null);
	}

	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 444, 351);
		add(tabbedPane);
		
		panelFeatureExtractor = new PanelFeatureExtractor();
		tabbedPane.addTab("Feature Extraction", null, panelFeatureExtractor, null);
		
		panelDataRandomizer = new PanelDataGenerator();
		tabbedPane.addTab("Generate Data", null, panelDataRandomizer, null);
	}
	
}
