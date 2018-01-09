package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class SolutionReader {
	
	private static int input_hidden_separator, hidden_output_separator;
	
	public static double[] read(File file) {
		double[] solution = new double[FileHelper.countFiles(file) - 2];
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String str;	
			int ctr = 0, separtorCount = 0;
			
			while ((str = bufferedReader.readLine()) != null) {
				if (str.equals("-")) {
					if (separtorCount == 0)
						input_hidden_separator = ctr;
					else
						hidden_output_separator = ctr;
					
					separtorCount++;
					continue;
				}
				
				solution[ctr++] = Double.parseDouble(str);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file found.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Cannot convert to network weights.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		} catch (IOException e) {
			return null;
		} finally {
            try {
                if (bufferedReader != null) {
                	bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
		return solution;
	}
	
	public static int getInputHiddenSeparator() {
		return input_hidden_separator;
	}
	
	public static int getHiddenOutputSeparator() {
		return hidden_output_separator;
	}
	
}
