package utilities;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterJPEGImage extends FileFilter {

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
	        return true;
	    }
		
		String extension = ExtensionHelper.getExtension(file);
		if (extension != null) {
			if (extension.equals(ExtensionHelper.jpg) || extension.equals(ExtensionHelper.jpeg)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "JPEG Images";
	}

}
