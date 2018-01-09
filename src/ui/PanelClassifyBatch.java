package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ui.components.ButtonIcon;
import ui.components.ProgressPane;
import utilities.OutputLayerHelper;
import utilities.SolutionReader;
import utilities.TomatoXChooser;
import utilities.TomatoXConstants;
import dataset.Data;
import dataset.DataReader;
import elm.Classifier;
import elm.Result;

@SuppressWarnings("serial")
public class PanelClassifyBatch extends JPanel {

	private Classifier classifier;
	private ProgressPane progressPane;
	
	private TomatoXChooser chooserTx, chooserData;
	
	private double[] solution = null;
	private Data data;
	private Result result;
	
	private JTextField textFieldClassifier;
	private JTextField textFieldData;
	private JLabel labelCorrectValue;
	private JLabel labelIncorrectValue;
	private JLabel labelPercent;

	public PanelClassifyBatch() {
		initComponents();
		setBackground(Color.WHITE);
		setSize(440, 330);
		setLayout(null);
	}
	
	private void initComponents() {
		JLabel lblClassifier = new JLabel("Classifier");
		lblClassifier.setVerticalAlignment(SwingConstants.BOTTOM);
		lblClassifier.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblClassifier.setBounds(10, 113, 120, 25);
		add(lblClassifier);
		
		JLabel lblTestData = new JLabel("Data");
		lblTestData.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTestData.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblTestData.setBounds(10, 77, 120, 25);
		add(lblTestData);
		
		textFieldClassifier = new JTextField();
		textFieldClassifier.setColumns(10);
		textFieldClassifier.setBounds(140, 113, 250, 25);
		add(textFieldClassifier);
		
		textFieldData = new JTextField();
		textFieldData.setColumns(10);
		textFieldData.setBounds(140, 77, 250, 25);
		add(textFieldData);
		
		ButtonIcon buttonBrowseClassifier = new ButtonIcon("browse", "Browse for Classifier");
		buttonBrowseClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseClassifierAction();
			}
		});
		buttonBrowseClassifier.setLocation(400, 113);
		add(buttonBrowseClassifier);
		
		ButtonIcon buttonBrowseData = new ButtonIcon("browse", "Browse for Test Data");
		buttonBrowseData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseTestDataAction();
			}
		});
		buttonBrowseData.setLocation(400, 77);
		add(buttonBrowseData);
		
		JLabel lblNoOfCorrect = new JLabel("Correct");
		lblNoOfCorrect.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNoOfCorrect.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblNoOfCorrect.setBounds(10, 149, 120, 25);
		add(lblNoOfCorrect);
		
		JLabel lblNoOfIncorrect = new JLabel("Incorrect");
		lblNoOfIncorrect.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNoOfIncorrect.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblNoOfIncorrect.setBounds(10, 185, 120, 25);
		add(lblNoOfIncorrect);
		
		labelCorrectValue = new JLabel("---");
		labelCorrectValue.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		labelCorrectValue.setBounds(142, 149, 80, 25);
		add(labelCorrectValue);
		
		labelIncorrectValue = new JLabel("---");
		labelIncorrectValue.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		labelIncorrectValue.setBounds(142, 185, 80, 25);
		add(labelIncorrectValue);
		
		labelPercent = new JLabel("---");
		labelPercent.setHorizontalAlignment(SwingConstants.CENTER);
		labelPercent.setFont(new Font("Segoe UI Symbol", Font.BOLD, 30));
		labelPercent.setBounds(204, 149, 120, 61);
		add(labelPercent);
		
		ButtonIcon buttonLoad = new ButtonIcon("load", "Load Test Data");
		buttonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonLoadAction();
			}
		});
		buttonLoad.setLocation(10, 253);
		add(buttonLoad);
		
		ButtonIcon buttonClassify = new ButtonIcon("classify", "Classify Test Data");
		buttonClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClassifyAction();
			}
		});
		buttonClassify.setLocation(50, 253);
		add(buttonClassify);
		
		progressPane = new ProgressPane(); 
		progressPane.setSize(420, 30);
		progressPane.setLocation(10, 289);
		this.add(progressPane);
		
		JPanel panelBanner = new JPanel();
		panelBanner.setLayout(null);
		panelBanner.setBackground(Color.GRAY);
		panelBanner.setBounds(10, 11, 420, 55);
		add(panelBanner);
		
		JLabel lblBatchClassification = new JLabel("Batch Classification");
		lblBatchClassification.setVerticalAlignment(SwingConstants.TOP);
		lblBatchClassification.setForeground(Color.WHITE);
		lblBatchClassification.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblBatchClassification.setBounds(5, 5, 405, 30);
		panelBanner.add(lblBatchClassification);
		
		JLabel labelDescription = new JLabel("Select a classifier and classify a set of tomatoes");
		labelDescription.setForeground(Color.WHITE);
		labelDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		labelDescription.setBounds(5, 31, 405, 15);
		panelBanner.add(labelDescription);
		
		ButtonIcon buttonView = new ButtonIcon("view", "View Classification Result");
		buttonView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				buttonViewAction();
			}
		});
		buttonView.setLocation(90, 253);
		add(buttonView);
		
		chooserTx = new TomatoXChooser(TomatoXConstants.TX);
		chooserData = new TomatoXChooser(TomatoXConstants.DATA);
	}
	
	private void buttonBrowseClassifierAction() {
		if (chooserTx.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) { 
			textFieldClassifier.setText(chooserTx.getSelectedFile()+"");
			solution = SolutionReader.read(chooserTx.getSelectedFile());
		}
	}
	
	private void buttonBrowseTestDataAction() {
		if (chooserData.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) {
			textFieldData.setText(chooserData.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void buttonLoadAction() {
		if(!textFieldData.getText().equals("")) {
			new Thread( new Runnable() {
				@Override
				public void run() {
					File file = new File("" + textFieldData.getText()); 
					DataReader dl = new DataReader(progressPane, file);
					data = dl.read();
				}
				
			}).start();
		}
		else {
			JOptionPane.showMessageDialog(FrameMain.getInstance(), "Please fill in appropriately.");
		}
	}
	
	private void buttonViewAction() {
		if (data != null && result != null) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						FrameClassificationResult frame = new FrameClassificationResult(data, result);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	private void buttonClassifyAction() {
		if(solution != null && data != null) {
			classifyData();
		}
		else {
			JOptionPane.showMessageDialog(FrameMain.getInstance(), "Please fill in appropriately.");
		}
	}
	
	private void classifyData() {
		classifier = new Classifier(
						solution, 
						SolutionReader.getHiddenOutputSeparator() - SolutionReader.getInputHiddenSeparator() 
					);
		result = classifier.testBatch(data.getInputVector(), OutputLayerHelper.normalize(data.getOutputVector()));
		
		float acc = result.getAccuracy(); 
		if(acc > 90 )
			labelPercent.setForeground(new Color(102, 255, 0));
		else
			labelPercent.setForeground(new Color(255, 51, 51));
		
		DecimalFormat df = new DecimalFormat("#.##");
		labelPercent.setText(df.format((double)acc) + " %");
		
		labelCorrectValue.setText(result.getScore() + "");
		labelIncorrectValue.setText(result.size() - result.getScore() + "");
	}
	
}
