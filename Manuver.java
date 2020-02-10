
import james.games.manuver.GameFrame;
import javax.swing.SwingUtilities;
public class Manuver {
	
	/**
	 * Method main
	 *
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();
			}
		});
	}	
}
