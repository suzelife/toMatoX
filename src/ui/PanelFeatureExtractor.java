package ui;

import imageprocessing.FeatureExtractor;
import imageprocessing.ImageProcessor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ui.components.ButtonIcon;
import utilities.Debugger;
import utilities.TomatoXChooser;
import utilities.TomatoXConstants;

@SuppressWarnings("serial")
public class PanelFeatureExtractor extends JPanel {

	private ImageProcessor iProcessor;
	
	private TomatoXChooser chooser;
	
	private BufferedImage input, resized, processed;

	private JLabel labelFilename;
	private JLabel labelImage;
	private JLabel labelLValue;
	private JLabel labelAValue;
	private JLabel labelHueValue;
	private JLabel labelRgValue;

	public PanelFeatureExtractor() {
		iProcessor = new ImageProcessor();
		initComponents();
		setSize(440, 330);
		setBackground(Color.WHITE);
		setLayout(null);
	}
	
	private void initComponents() {
		labelImage = new JLabel("");
		labelImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		labelImage.setBounds(10, 83, 200, 200);
		add(labelImage);
		
		JLabel lblColorFeatures = new JLabel("Color Features");
		lblColorFeatures.setVerticalAlignment(SwingConstants.BOTTOM);
		lblColorFeatures.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblColorFeatures.setBounds(220, 83, 210, 25);
		add(lblColorFeatures);
		
		labelFilename = new JLabel("<filename>");
		labelFilename.setHorizontalAlignment(SwingConstants.CENTER);
		labelFilename.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		labelFilename.setBounds(10, 293, 160, 14);
		add(labelFilename);
		
		ButtonIcon buttonBrowseInput = new ButtonIcon("browse", "Browse for Input");
		buttonBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseAction();
			}
		});
		buttonBrowseInput.setLocation(180, 288);
		add(buttonBrowseInput);
		
		JLabel lblL = new JLabel("L*");
		lblL.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		lblL.setBounds(241, 119, 46, 20);
		add(lblL);
		
		JLabel lblA = new JLabel("a*");
		lblA.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		lblA.setBounds(243, 150, 46, 20);
		add(lblA);
		
		JLabel lblHue = new JLabel("Hue");
		lblHue.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		lblHue.setBounds(241, 181, 46, 20);
		add(lblHue);
		
		JLabel lblRG = new JLabel("R-G");
		lblRG.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		lblRG.setBounds(243, 212, 46, 20);
		add(lblRG);
		
		ButtonIcon buttonExtractFeatures = new ButtonIcon("extract", "Extract Features from Input");
		buttonExtractFeatures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonExtractFeaturesAction();
			}
		});
		buttonExtractFeatures.setLocation(220, 288);
		add(buttonExtractFeatures);
		
		labelLValue = new JLabel("---");
		labelLValue.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		labelLValue.setBounds(305, 119, 125, 20);
		add(labelLValue);
		
		labelAValue = new JLabel("---");
		labelAValue.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		labelAValue.setBounds(305, 150, 125, 20);
		add(labelAValue);
		
		labelHueValue = new JLabel("---");
		labelHueValue.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		labelHueValue.setBounds(305, 181, 125, 20);
		add(labelHueValue);
		
		labelRgValue = new JLabel("---");
		labelRgValue.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		labelRgValue.setBounds(305, 212, 125, 20);
		add(labelRgValue);
		
		ButtonIcon buttonShowProcess = new ButtonIcon("show", "Show Process");
		buttonShowProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonShowProcessAction();
			}
		});
		buttonShowProcess.setLocation(260, 292);
		add(buttonShowProcess);
		
		JPanel panelBanner = new JPanel();
		panelBanner.setLayout(null);
		panelBanner.setBackground(Color.GRAY);
		panelBanner.setBounds(10, 11, 420, 55);
		add(panelBanner);
		
		JLabel lblFeatureExtraction = new JLabel("Feature Extraction");
		lblFeatureExtraction.setVerticalAlignment(SwingConstants.TOP);
		lblFeatureExtraction.setForeground(Color.WHITE);
		lblFeatureExtraction.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblFeatureExtraction.setBounds(5, 5, 405, 30);
		panelBanner.add(lblFeatureExtraction);
		
		JLabel labelDescription = new JLabel("Select a tomato and extract color features");
		labelDescription.setForeground(Color.WHITE);
		labelDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		labelDescription.setBounds(5, 31, 405, 15);
		panelBanner.add(labelDescription);
		
		chooser = new TomatoXChooser(TomatoXConstants.IMAGE);
	}
	
	private void buttonBrowseAction() {
		File file = null;
		if (chooser.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) {  
			file = chooser.getSelectedFile();
		}
		if (file != null) {
			showInputImage(file);
		}
	}
	
	private void buttonExtractFeaturesAction() {
		if(resized != null) {
			processed = iProcessor.process(resized, 200, 200);
			//format the numbers
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			
			double[] index = FeatureExtractor.computeIndices(processed);
			labelLValue.setText(decimalFormat.format(index[0]) + "");
			labelAValue.setText(decimalFormat.format(index[1]) + "");
			labelHueValue.setText(decimalFormat.format(FeatureExtractor.computeMeanHue(processed)) + "");
			labelRgValue.setText(decimalFormat.format(FeatureExtractor.computeMeanRG(processed)) + "");
		}
	}
	
	private void buttonShowProcessAction() {
		if(processed != null) {
			DialogFeaturesProcess dialog = new DialogFeaturesProcess();
			
			BufferedImage[] images = new BufferedImage[7];
			images[0] = resized;
			images[1] = iProcessor.getBlueChannel();
			images[2] =	iProcessor.getGrayscale();
			images[3] = iProcessor.getBinaryMask();
			images[4] = iProcessor.getSegmented();
			images[5] = iProcessor.getCropped();
			images[6] = processed;
			
			dialog.showProcess(images);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(FrameMain.getInstance());
			dialog.setVisible(true);
		}
	}
	
	private void showInputImage(File inputFile) {
		labelFilename.setText(inputFile.getName());
		try {
			input = ImageIO.read(inputFile);
		} catch (IOException e1) {
			Debugger.printError("IO error in " + this.getClass().getName());
		}
		resized = iProcessor.resizeImage(input, 200, 200);
		labelImage.setIcon(new ImageIcon(resized));
		labelImage.updateUI();
	}
	
}
