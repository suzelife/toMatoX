package ui;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameMain extends JFrame {

	private JPanel contentPane;
	private static FrameMain instance;

	private FrameMain() {
		initComponents();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(670, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().createImage("images/icon.png"));
		setFrameTitle("Train");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(
					FrameMain.getInstance(), 
					"Are you sure you want to exit?", 
					"Exit TomatoX", 
					JOptionPane.YES_NO_OPTION
				);
				if (choice == JOptionPane.YES_OPTION) { //yes
					System.exit(1);
				}
			}
		});
	}
	
	public static FrameMain getInstance() {
		if (instance == null)
			instance = new FrameMain();
		
		return instance;
	}
	
	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PanelTitle panelTitle = new PanelTitle();
		panelTitle.setLocation(10, 11);
		contentPane.add(panelTitle);
		
		PanelContainer panelContainer = new PanelContainer();
		panelContainer.setLocation(190, 87);
		contentPane.add(panelContainer);
		
		PanelMenu panelMenu = new PanelMenu(panelContainer);
		panelMenu.setLocation(10, 88);
		contentPane.add(panelMenu);
	}
	
	public void setFrameTitle(String title) {
		setTitle("TomatoX - " + title);
	}
	
}
