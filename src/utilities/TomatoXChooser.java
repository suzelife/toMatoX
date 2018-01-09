package utilities;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class TomatoXChooser extends JFileChooser {

	public TomatoXChooser(byte type) {
		super("resources");
		
		switch (type) {
		case TomatoXConstants.DIRECTORY:
			setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			break;
		case TomatoXConstants.IMAGE:
			setAcceptAllFileFilterUsed(false);
			addChoosableFileFilter(new FilterJPEGImage());
			break;
		case TomatoXConstants.DATA:
			setAcceptAllFileFilterUsed(false);
			addChoosableFileFilter(new FilterData());
			break;
		case TomatoXConstants.TX:
			setAcceptAllFileFilterUsed(false);
			addChoosableFileFilter(new FilterTx());
			break;
		default:
			break;
		}
	}
	
}
