package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.ejml.data.DenseMatrix64F;

import ui.components.ButtonIcon;
import ui.components.ProgressPane;
import utilities.SolutionWriter;
import utilities.TomatoXChooser;
import utilities.TomatoXConstants;
import dataset.Data;
import dataset.DataReader;
import elm.ELM;

@SuppressWarnings("serial")
public class PanelTrain extends JPanel {

	private ProgressPane progressPane;
	
	private TomatoXChooser chooserData, chooserTx;
	private JCheckBox chckbxRegularization;
	private JSpinner spinnerRegulatizationCoeff;
	
	private ELM elm;
	private Data trainData;
	
	private JTextField textFieldTraindata;
	private JSpinner spinnerHidden;
	private JLabel labelElapsedTime;
	
	private static PanelTrain instance;

	public PanelTrain() {
		initComponents();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setSize(464, 373);
		setLayout(null);
		instance = this;
	}
	
	private void initComponents() {
		JLabel lblTrainingData = new JLabel("Train Data");
		lblTrainingData.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTrainingData.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblTrainingData.setBounds(10, 80, 150, 30);
		add(lblTrainingData);
		
		textFieldTraindata = new JTextField();
		textFieldTraindata.setColumns(10);
		textFieldTraindata.setBounds(170, 80, 242, 25);
		add(textFieldTraindata);
		
		ButtonIcon buttonBrowse = new ButtonIcon("browse", "Browse for Train Data");
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBrowseTrainDataAction();
			}
		});
		buttonBrowse.setLocation(424, 80);
		add(buttonBrowse);
		
		JLabel lblHiddenNodes = new JLabel("Hidden Nodes");
		lblHiddenNodes.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHiddenNodes.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblHiddenNodes.setBounds(10, 121, 150, 30);
		add(lblHiddenNodes);
		
		spinnerHidden = new JSpinner();
		spinnerHidden.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));
		spinnerHidden.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		spinnerHidden.setBounds(170, 122, 100, 25);
		add(spinnerHidden);
		
		chckbxRegularization = new JCheckBox("");
		chckbxRegularization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxRegularizationCoeffAction();
			}
		});
		chckbxRegularization.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		chckbxRegularization.setBounds(10, 167, 21, 25);
		add(chckbxRegularization);
		
		JLabel lblLambda = new JLabel("Lambda");
		lblLambda.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLambda.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblLambda.setBounds(37, 162, 123, 30);
		add(lblLambda);
		
		spinnerRegulatizationCoeff = new JSpinner();
		spinnerRegulatizationCoeff.setModel(new SpinnerNumberModel(new Float(1), null, null, new Float(0)));
		spinnerRegulatizationCoeff.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		spinnerRegulatizationCoeff.setBounds(170, 165, 100, 25);
		add(spinnerRegulatizationCoeff);
		spinnerRegulatizationCoeff.setEnabled(false);
		
		ButtonIcon buttonLoad = new ButtonIcon("load", "Load Train Data");
		buttonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonLoadTrainDataAction();
			}
		});
		buttonLoad.setLocation(21, 296);
		add(buttonLoad);
		
		ButtonIcon buttonTrain = new ButtonIcon("train", "Train using ELM");
		buttonTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonTrainAction();
			}
		});
		buttonTrain.setLocation(61, 296);
		add(buttonTrain);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonSaveAction();
			}
		});
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnSave.setBounds(287, 296, 89, 25);
		//add(btnSave);
		
		ButtonIcon buttonSave = new ButtonIcon("save", "Save as a Classifier");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonSaveAction();
			}
		});
		buttonSave.setLocation(101, 296);
		add(buttonSave);
		
		chooserData = new TomatoXChooser(TomatoXConstants.DATA);
		chooserTx = new TomatoXChooser(TomatoXConstants.TX);
		
		progressPane = new ProgressPane(); 
		progressPane.setSize(420, 30);
		progressPane.setLocation(21, 332);
		this.add(progressPane);
		
		JLabel lblTrainingTimesec = new JLabel("Training Time (sec)");
		lblTrainingTimesec.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTrainingTimesec.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblTrainingTimesec.setBounds(10, 203, 260, 30);
		add(lblTrainingTimesec);
		
		labelElapsedTime = new JLabel("---");
		labelElapsedTime.setVerticalAlignment(SwingConstants.BOTTOM);
		labelElapsedTime.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelElapsedTime.setBounds(284, 203, 170, 30);
		add(labelElapsedTime);
		
		JPanel panelBanner = new JPanel();
		panelBanner.setBackground(Color.GRAY);
		panelBanner.setBounds(10, 11, 444, 55);
		add(panelBanner);
		panelBanner.setLayout(null);
		
		JLabel lblElmTraining = new JLabel("ELM Training");
		lblElmTraining.setVerticalAlignment(SwingConstants.TOP);
		lblElmTraining.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblElmTraining.setForeground(Color.WHITE);
		lblElmTraining.setBounds(5, 5, 429, 30);
		panelBanner.add(lblElmTraining);
		
		JLabel lblDescription = new JLabel("Set the ELM parameters and train the network");
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		lblDescription.setBounds(5, 31, 424, 15);
		panelBanner.add(lblDescription);
	}
	
	private void buttonBrowseTrainDataAction() {
		if (chooserData.showOpenDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) {
			textFieldTraindata.setText(chooserData.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void checkboxRegularizationCoeffAction() {
		spinnerRegulatizationCoeff.setEnabled(chckbxRegularization.isSelected() ? true: false);
	}
	
	private void buttonLoadTrainDataAction() {
		if(!(textFieldTraindata.getText().equals(""))) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					File file = new File("" + textFieldTraindata.getText()); 
					DataReader dl = new DataReader(progressPane, file);
					trainData = dl.read();
				}
				
			}).start();
		}
		else {
			JOptionPane.showMessageDialog(FrameMain.getInstance(), "Please fill in appropriately.");
		}
	}
	
	private void buttonTrainAction() {
		if (trainData != null) {
			doELMTraining(trainData, (int) spinnerHidden.getValue(), chckbxRegularization.isSelected());
		}
	}
	
	private void buttonSaveAction() {
		if(chooserTx.showSaveDialog(FrameMain.getInstance()) == JFileChooser.APPROVE_OPTION) {
		    File file = chooserTx.getSelectedFile();
		    SolutionWriter fileSaver = new SolutionWriter(file);
			fileSaver.saveFile(elm.getWeights(), elm.getInputHiddenSeparator(), elm.getHiddenOutputSeparator());
		}
	}
	
	public void displayTrainingTime(double elapsedTime) {
		labelElapsedTime.setText(elapsedTime + "");
	}
	
	private void doELMTraining(final Data trainingData, final int hiddenNodes, final boolean withRegularization) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				elm = new ELM();
				elm.setProgressPane(progressPane);
				elm.setParent(instance);
				
				if (withRegularization)
					elm.setLambda((float) spinnerRegulatizationCoeff.getValue());
				
				DenseMatrix64F input = new DenseMatrix64F(trainingData.getInputVector());
				DenseMatrix64F output = new DenseMatrix64F(trainingData.getOutputVector());
				elm.train(input, output, hiddenNodes);
			}
		}).start();
	}
	
}
