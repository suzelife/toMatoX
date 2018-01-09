package ui;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelContainer extends JPanel {
	
	private PanelTrain panelTrain;
	private PanelClassify panelClassify;
	private PanelTools panelTools;
	private PanelAbout panelAbout;

	public PanelContainer() {
		initComponents();
		setBackground(Color.WHITE);
		setSize(464, 373);
		setLayout(null);
	}

	private void initComponents() {
		panelTrain = new PanelTrain();
		panelTrain.setVisible(true);
		add(panelTrain);
		
		panelClassify = new PanelClassify();
		panelClassify.setVisible(false);
		add(panelClassify);
		
		panelTools = new PanelTools();
		panelTools.setVisible(false);
		add(panelTools);
		
		panelAbout = new PanelAbout();
		panelAbout.setVisible(false);
		add(panelAbout);
	}
	
	private void hidePanels() {
		panelTrain.setVisible(false);
		panelClassify.setVisible(false);
		panelTools.setVisible(false);
		panelAbout.setVisible(false);
	}
	
	public void showPanelTrain() {
		hidePanels();
		panelTrain.setVisible(true);
	}
	
	public void showPanelClassify() {
		hidePanels();
		panelClassify.setVisible(true);
	}
	
	public void showPanelTools() {
		hidePanels();
		panelTools.setVisible(true);
	}
	
	public void showPanelAbout() {
		hidePanels();
		panelAbout.setVisible(true);
	}
	
}
