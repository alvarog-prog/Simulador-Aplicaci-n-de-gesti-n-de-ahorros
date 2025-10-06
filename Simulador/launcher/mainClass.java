package launcher;

import view.LoginWindow;
import view.mainWindow;

public class mainClass {
	
	public static void main(String[] args) {
		 javax.swing.SwingUtilities.invokeLater(() -> {
	            new LoginWindow();
	        });
	}

}
