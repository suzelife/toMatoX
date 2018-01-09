package ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ui.components.ButtonMenu;
import ui.components.LabelLink;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel {
	
	private PanelContainer panelContainer;
	private ButtonMenu menuTrain;
	private ButtonMenu menuClassify;
	private ButtonMenu menuTools;
	private ButtonMenu menuAbout;
	private LabelLink lblHelp;

	public PanelMenu(PanelContainer panelContainer) {
		this.panelContainer = panelContainer;
		initComponents();
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setSize(170, 372);		
		setLayout(null);
	}

	private void initComponents() {
		menuTrain = new ButtonMenu("Train", "button_train");
		menuTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				buttonTrainAction();
			}
		});
		menuTrain.setLocation(10, 11);
		add(menuTrain);
		
		menuClassify = new ButtonMenu("Classify", "button_classify");
		menuClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				buttonClassifyAction();
			}
		});
		menuClassify.setLocation(10, 62);
		add(menuClassify);
		
		menuTools = new ButtonMenu("Tools", "button_tools");
		menuTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				buttonToolsAction();
			}
		});
		menuTools.setLocation(10, 113);
		add(menuTools);
		
		menuAbout = new ButtonMenu("About", "button_about");
		menuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				buttonAboutAction();
			}
		});
		menuAbout.setLocation(10, 164);
		add(menuAbout);
		
		lblHelp = new LabelLink("Help");
		lblHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mE) {
				try {
					Desktop.getDesktop().open(new File("TomatoX User's Manual.pdf"));
				} catch (Exception ex) {
					System.err.println("Cannot open user's manual");
				}
			}
		});
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelp.setBounds(10, 336, 150, 25);
		add(lblHelp);
		
		menuTrain.setActive();
	}
	
	private void buttonTrainAction() {
		setAllButtonsToInactive();
		menuTrain.setActive();
		panelContainer.showPanelTrain();
		FrameMain.getInstance().setFrameTitle("Train");
	}
	
	private void buttonClassifyAction() {
		setAllButtonsToInactive();
		menuClassify.setActive();
		panelContainer.showPanelClassify();
		FrameMain.getInstance().setFrameTitle("Classify");
	}
	
	private void buttonToolsAction() {
		setAllButtonsToInactive();
		menuTools.setActive();
		panelContainer.showPanelTools();
		FrameMain.getInstance().setFrameTitle("Tools");
	}
	
	private void buttonAboutAction() {
		setAllButtonsToInactive();
		menuAbout.setActive();
		panelContainer.showPanelAbout();
		FrameMain.getInstance().setFrameTitle("About");
	}
	
	private void setAllButtonsToInactive() {
		menuTrain.setInactive();
		menuClassify.setInactive();
		menuTools.setInactive();
		menuAbout.setInactive();
	}
	
}
