package ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ui.FrameClassificationResult;

@SuppressWarnings("serial")
public class TableResult extends JTable {
	
	private FrameClassificationResult frameClassificationResult;
	private TableColumn column;

	public TableResult(FrameClassificationResult frameClassificationResults, Object[][] tableData, String[] columnNames) {
		super(tableData, columnNames);
		this.frameClassificationResult = frameClassificationResults;
		setConfig();
	}
	
	private void setConfig() {
		setAutoCreateRowSorter(true);
		setShowGrid(true);
		setFillsViewportHeight(true);
		setBackground(Color.WHITE);
		setFont(new Font("Segoe UI", Font.PLAIN, 15));
		setCellSelectionEnabled(true);
		getTableHeader().setFont(new Font("Consolas", Font.PLAIN, 15));
		
		//set the width of the first column
		column = this.getColumnModel().getColumn(0);
		column.setPreferredWidth(1);
		
		//add a listener to the table
		//this part of code is responsible for viewing the tomato image
		ListSelectionModel selectionModel = getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lsE) {
		        int[] selectedRow = getSelectedRows();
		        int[] selectedColumns = getSelectedColumns();

		        String selectData = (String) getValueAt(
		        								selectedRow[selectedRow.length - 1], 
		        								selectedColumns[selectedColumns.length - 1]
		        							);
		        
		        //check if the selected column corresponds to the imagename column
		        if (selectedColumns[selectedColumns.length - 1] == 1) {
			        DialogImageViewer dialog = new DialogImageViewer(
			        											frameClassificationResult, 
			        											frameClassificationResult.getFilename(selectData)
			        										);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
		        }
			}
		});
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	//responsible for setting the color of row
	@Override
	public Component prepareRenderer(TableCellRenderer tableCellRenderer, int row, int col) {
		Component component = super.prepareRenderer(tableCellRenderer, row, col);
		component.setBackground(null);
		component.setForeground(Color.BLACK);
		
		String actualClassification = (String) getValueAt(row, 2);
		String tomatoXClassification = (String) getValueAt(row, 3);
		
		if (!(actualClassification.equals(tomatoXClassification))) {
			component.setBackground(Color.RED);
			component.setForeground(Color.WHITE);
		}
		
		return component;
	}
	
}
