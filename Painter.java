package james.games.graphics;

import java.awt.Graphics;
import java.io.Serializable;
public interface Painter extends Serializable {
	
	/**
	 * Method paint
	 *
	 *
	 * @param g
	 *
	 */
	public void paint(Graphics g);	
}
