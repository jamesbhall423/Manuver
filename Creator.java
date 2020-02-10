
import james.games.manuver.CreatorFrame;
import javax.swing.SwingUtilities;
public class Creator {
	
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
				new CreatorFrame();
			}
		});
	}	
}
