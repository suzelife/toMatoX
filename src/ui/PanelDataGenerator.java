package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ui.components.ButtonIcon;
import utilities.TomatoXChooser;
import utilities.TomatoXConstants;
import dataset.DataRandomizer;
import dataset.DataWriter;

@SuppressWarnings("serial")
public class PanelDataGenerator extends JPanel {
	
	private JTextField textFieldDataset;
	private JTextField textFieldTraining;
	private JTextField textFieldTesting;
	private JTextField textFieldDirectory;
	
	private TomatoXChooser chooser;

	public PanelDataGenerator() {
		initComponents();
		setSize(440, 330);
		setBackground(Color.WHITE);
		setLayout(null);
	}
	
	private void initComponents() {
		JLabel lblData = new JLabel("Dataset");
		lblData.setVerticalAlignment(SwingConstants.BOTTOM);
		lblData.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblData.setBounds(10, 102, 120, 25);
		add(lblData);
		
		textFieldDataset = new JTextField();
		textFieldDataset.setBounds(140, 102, 250, 25);
		add(textFieldDataset);
		textFieldDataset.setColumns(10);
		
		ButtonIcon buttonBrowseData = new ButtonIcon("browse", "Browse for Data");
		buttonBrowseData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseDatasetAction();
			}
		});
		buttonBrowseData.setLocation(400, 102);
		add(buttonBrowseData);
		
		JLabel lblTraining = new JLabel("Train");
		lblTraining.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTraining.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblTraining.setBounds(10, 174, 120, 27);
		add(lblTraining);
		
		JLabel lblTesting = new JLabel("Test");
		lblTesting.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTesting.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblTesting.setBounds(10, 212, 120, 27);
		add(lblTesting);
		
		ButtonIcon buttonRandomize = new ButtonIcon("random", "Randomize Data");
		buttonRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonRandomizeAction();
			}
		});
		buttonRandomize.setLocation(140, 248);
		add(buttonRandomize);
		
		textFieldTraining = new JTextField();
		textFieldTraining.setBounds(140, 174, 191, 25);
		add(textFieldTraining);
		textFieldTraining.setColumns(10);
		
		textFieldTesting = new JTextField();
		textFieldTesting.setBounds(140, 212, 191, 25);
		add(textFieldTesting);
		textFieldTesting.setColumns(10);
		
		JLabel lblDirectory = new JLabel("Directory");
		lblDirectory.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDirectory.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblDirectory.setBounds(10, 138, 120, 25);
		add(lblDirectory);
		
		textFieldDirectory = new JTextField();
		textFieldDirectory.setBounds(140, 138, 250, 25);
		add(textFieldDirectory);
		textFieldDirectory.setColumns(10);
		
		ButtonIcon buttonBrowseDirectory = new ButtonIcon("browse", "Browse for Directory");
		buttonBrowseDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseDirectoryAction();
			}
		});
		buttonBrowseDirectory.setLocation(400, 138);
		add(buttonBrowseDirectory);
		
		JLabel lbldata = new JLabel(".data");
		lbldata.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		lbldata.setBounds(341, 175, 46, 25);
		add(lbldata);
		
		JLabel label_1 = new JLabel(".data");
		label_1.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		label_1.setBounds(341, 213, 46, 25);
		add(label_1);
		
		JPanel panelBanner = new JPanel();
		panelBanner.setLayout(null);
		panelBanner.setBackground(Color.GRAY);
		panelBanner.setBounds(10, 11, 420, 55);
		add(panelBanner);
		
		JLabel lblGenerateData = new JLabel("Generate Data");
		lblGenerateData.setVerticalAlignment(SwingConstants.TOP);
		lblGenerateData.setForeground(Color.WHITE);
		lblGenerateData.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblGenerateData.setBounds(5, 5, 405, 30);
		panelBanner.add(lblGenerateData);
		
		JLabel labelDescription = new JLabel("Select a dataset and generate a train and test data");
		labelDescription.setForeground(Color.WHITE);
		labelDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		labelDescription.setBounds(5, 31, 405, 15);
		panelBanner.add(labelDescription);
		
		chooser = new TomatoXChooser(TomatoXConstants.DIRECTORY);
	}
	
	private void buttonBrowseDatasetAction() {
		if (chooser.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) { 
			textFieldDataset.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void buttonRandomizeAction() {
		if (textFieldDataset.getText().isEmpty()
				|| textFieldDirectory.getText().isEmpty()
				|| textFieldTraining.getText().isEmpty()
				|| textFieldTesting.getText().isEmpty()) {
			JOptionPane.showMessageDialog(FrameMain.getInstance(), "Please fill in properly.");
		}
		else {
			File dataFile = new File(textFieldDataset.getText());
			DataRandomizer dataRandomizer = new DataRandomizer(dataFile);
			boolean success = dataRandomizer.randomize();
			if(success) {
				DataWriter dataWriterTrain = new DataWriter(textFieldDirectory.getText() + "\\" + textFieldTraining.getText() + ".data");
				boolean bTrain = dataWriterTrain.write(dataRandomizer.getTrainSet());
				DataWriter dataWriterTest = new DataWriter(textFieldDirectory.getText() + "\\" + textFieldTesting.getText() + ".data");
				boolean bTest = dataWriterTest.write(dataRandomizer.getTestSet());
				
				if(bTrain & bTest) {
					JOptionPane.showMessageDialog(FrameMain.getInstance(), "Successfully randomized data.");
				}
				else {
					JOptionPane.showMessageDialog(FrameMain.getInstance(), "There seems to be a problem.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private void buttonBrowseDirectoryAction() {
		if (chooser.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) { 
			textFieldDirectory.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
}
