import java.awt.EventQueue;

import ui.FrameMain;
import ui.SplashScreen;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Thread(new Runnable() {
						@Override
						public void run() {
							//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							FrameMain frameMain = FrameMain.getInstance();
							
							SplashScreen splashScreen = new SplashScreen();
							splashScreen.setVisible(true);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							splashScreen.dispose();
							frameMain.setVisible(true);
						}
					}).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
