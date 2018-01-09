package ui;

import imageprocessing.ImageProcessor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ui.components.ButtonIcon;
import utilities.Debugger;
import utilities.SolutionReader;
import utilities.TomatoXChooser;
import utilities.TomatoXConstants;
import elm.Classifier;

@SuppressWarnings("serial")
public class PanelClassifyIndividual extends JPanel {

	private ImageProcessor iProcessor;
	private Classifier classifier;
	
	private TomatoXChooser chooserImage, chooserTx;
	
	private double[] solution = null;
	private BufferedImage input, temp;
	private String[] classifications = { "GREEN", "BREAKER", "TURNING", "PINK", "LIGHT RED", "RED" };
	private Color[] classColors = { new Color(44, 151, 5), new Color(176, 206, 25), new Color(227, 253, 96), 
									new Color(209, 125, 40), new Color(223, 94, 33), new Color(224, 40, 40),};
	
	private JTextField textFieldClassifier;
	private JLabel labelFilename;
	private JLabel labelGrade;
	private JLabel labelImage;

	public PanelClassifyIndividual() {
		iProcessor = new ImageProcessor();
		initComponents();
		setBackground(Color.WHITE);
		setSize(440, 330);
		setLayout(null);
	}
	
	private void initComponents() {
		labelImage = new JLabel("");
		labelImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		labelImage.setBounds(10, 77, 200, 200);
		add(labelImage);
		
		ButtonIcon buttonBrowseInput = new ButtonIcon("browse", "Browse for Input");
		buttonBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseInputAction();
			}
		});
		buttonBrowseInput.setLocation(180, 288);
		add(buttonBrowseInput);
		
		labelFilename = new JLabel("<filename>");
		labelFilename.setHorizontalAlignment(SwingConstants.CENTER);
		labelFilename.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		labelFilename.setBounds(10, 293, 160, 14);
		add(labelFilename);
		
		JLabel lblClassifier = new JLabel("Classifier");
		lblClassifier.setVerticalAlignment(SwingConstants.BOTTOM);
		lblClassifier.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblClassifier.setBounds(220, 77, 150, 25);
		add(lblClassifier);
		
		textFieldClassifier = new JTextField();
		textFieldClassifier.setColumns(10);
		textFieldClassifier.setBounds(220, 113, 170, 25);
		add(textFieldClassifier);
		
		ButtonIcon buttonBrowseClassifier = new ButtonIcon("browse", "Browse for Classifier");
		buttonBrowseClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseClassifierAction();
			}
		});
		buttonBrowseClassifier.setLocation(400, 113);
		add(buttonBrowseClassifier);
		
		JLabel lblClassification = new JLabel("Classification");
		lblClassification.setVerticalAlignment(SwingConstants.BOTTOM);
		lblClassification.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblClassification.setBounds(220, 185, 170, 25);
		add(lblClassification);
		
		labelGrade = new JLabel("---");
		labelGrade.setHorizontalAlignment(SwingConstants.CENTER);
		labelGrade.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
		labelGrade.setBounds(220, 221, 210, 35);
		add(labelGrade);
		
		ButtonIcon buttonClassify = new ButtonIcon("classify", "Classify the Input");
		buttonClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClassifyAction();
			}
		});
		buttonClassify.setLocation(220, 288);
		add(buttonClassify);
		
		JPanel panelBanner = new JPanel();
		panelBanner.setLayout(null);
		panelBanner.setBackground(Color.GRAY);
		panelBanner.setBounds(10, 11, 420, 55);
		add(panelBanner);
		
		JLabel lblIndividualClassification = new JLabel("Individual Classification");
		lblIndividualClassification.setVerticalAlignment(SwingConstants.TOP);
		lblIndividualClassification.setForeground(Color.WHITE);
		lblIndividualClassification.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblIndividualClassification.setBounds(5, 5, 405, 30);
		panelBanner.add(lblIndividualClassification);
		
		JLabel labelDescription = new JLabel("Select a classifier and classify a tomato");
		labelDescription.setForeground(Color.WHITE);
		labelDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		labelDescription.setBounds(5, 31, 405, 15);
		panelBanner.add(labelDescription);
		
		chooserImage = new TomatoXChooser(TomatoXConstants.IMAGE);
		chooserTx = new TomatoXChooser(TomatoXConstants.TX);
	}
	
	private void buttonBrowseInputAction() {
		File file = null;
		if (chooserImage.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) {  
			file = chooserImage.getSelectedFile();
		}
		if (file != null) {
			displayInputImage(file);
		}
	}
	
	private void buttonBrowseClassifierAction() {
		if (chooserTx.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) { 
			textFieldClassifier.setText(chooserTx.getSelectedFile()+"");
			solution = SolutionReader.read(chooserTx.getSelectedFile());
		}
	}
	
	private void buttonClassifyAction() {
		if(solution != null && input != null) {
			classifyInput();
		}
		else {
			JOptionPane.showMessageDialog(FrameMain.getInstance(), "Please fill in appropriately.");
		}
	}
	
	private void displayInputImage(File inputFile) {
		labelFilename.setText(inputFile.getName());
		try {
			input = ImageIO.read(inputFile);
		} catch (IOException e1) {
			Debugger.printError("IO error in " + this.getClass().getName());
		}
		temp = iProcessor.resizeImage(input, 200, 200);
		labelImage.setIcon(new ImageIcon(temp));
		labelImage.updateUI();
	}
	
	private void classifyInput() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				classifier = new Classifier(
								solution, 
								SolutionReader.getHiddenOutputSeparator() - SolutionReader.getInputHiddenSeparator() 
							);
				BufferedImage temp = input;
				temp = iProcessor.process(input, 200, 200);
				double[] features = iProcessor.getFeatures(temp);
				int classIndex = classifier.classify(features);
				labelGrade.setForeground(classColors[classIndex]);
				labelGrade.setText(classifications[classIndex] + "");
			}
		}).start();
	}
	
}
