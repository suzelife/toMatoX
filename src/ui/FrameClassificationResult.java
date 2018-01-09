package ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ui.components.TableResult;

import dataset.Data;
import elm.Result;

@SuppressWarnings("serial")
public class FrameClassificationResult extends JFrame {

	private JPanel contentPane;
	private TableResult table;
	private String[] columnName = new String[4];
	private Object[][] tableData;
	
	private Data data;
	private Result result;

	public FrameClassificationResult(Data data, Result result) {
		this.data = data;
		this.result = result;
		
		setTitle("Results");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 400);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().createImage("images/icon.png"));
		
		initComponents();
	}
	
	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//initialize column names
		columnName[0] = "#";
		columnName[1] = "Name";
		columnName[2] = "Actual Classification";
		columnName[3] = "TomatoX Classification";
		
		//initialize row data
		tableData = new Object[result.size()][4];
		for (int i = 0; i < result.size(); i++) {
			tableData[i][0] = new Integer(i + 1);
			tableData[i][1] = getImageName(data.getFilename(i));
			tableData[i][2] = getTomatoStage(result.getExpected(i));
			tableData[i][3] = getTomatoStage(result.getActual(i));
		}
		
		table = new TableResult(this, tableData, columnName);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	private String getImageName(String fileName) {
		File file = new File(fileName);
		return file.getName();
	}
	
	public String getFilename(String imageName) {
		return data.getFilename(imageName);
	}
	
	private String getTomatoStage(int numericalRepresentation) {
		String tomatoStage = null;
		
		switch (numericalRepresentation) {
			case 0:
				tomatoStage = "GREEN";
				break;
			case 1:
				tomatoStage = "BREAKER";
				break;
			case 2:
				tomatoStage = "TURNING";
				break;
			case 3:
				tomatoStage = "PINK";
				break;
			case 4:
				tomatoStage = "LIGHT RED";
				break;
			case 5:
				tomatoStage = "RED";
				break;
			default:
				break;
		}
		
		return tomatoStage;
	}

}
