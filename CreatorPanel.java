package james.games.manuver;

import james.games.graphics.*;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import static javax.swing.SwingConstants.VERTICAL;
import static javax.swing.SwingConstants.HORIZONTAL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_PLUS;
import static java.awt.event.KeyEvent.VK_MINUS;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;

public class CreatorPanel extends GamePanel {
	private static final long serialVersionUID = 9128435L;
	private Unit unit = null;
	private int terrain = 0;
	public CreatorPanel(GraphicalCoordinator mp) {
		super(mp);
	}
	public void doClick(Point position,Point hex,int button) {
		if (button==1) map.setTerrain(hex.x,hex.y,terrain);
		else map.setUnit(hex.x,hex.y,unit);
		repaint(position.x-size,position.y-size,size*2,size*2);
	}
	public void setUnit(Unit unit) {
		this.unit=unit;
	}
	public void setTerrain(int terrain) {
		this.terrain=terrain;
	}
}

