package utilities;

import java.io.File;

public class ExtensionHelper {

	public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String data = "data";
    public final static String tx = "tx";

    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
}
