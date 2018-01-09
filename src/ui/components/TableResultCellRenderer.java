package ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class TableResultCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setBackground(null);
		setForeground(Color.BLACK);
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String actualClassification = (String) table.getValueAt(row, 2);
		String tomatoXClassification = (String) table.getValueAt(row, 3);
		
		if (!(actualClassification.equals(tomatoXClassification))) {
			setBackground(Color.RED);
			setForeground(Color.WHITE);
		}
		
		return this;
	}
	
}
