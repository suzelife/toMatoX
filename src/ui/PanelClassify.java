package ui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PanelClassify extends JPanel {
	
	private PanelClassifyIndividual panelClassifyIndividual;
	private PanelClassifyBatch panelClassifyBatch;
	
	public PanelClassify() {
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
		
		panelClassifyIndividual = new PanelClassifyIndividual();
		tabbedPane.addTab("Individual", null, panelClassifyIndividual, null);
		
		panelClassifyBatch = new PanelClassifyBatch();
		tabbedPane.addTab("Batch", null, panelClassifyBatch, null);
	}
	
}
