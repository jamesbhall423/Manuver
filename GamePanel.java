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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
/**
 *  This class represents a panel that interacts with a Graphical Coordinator.
 *
 */
public abstract class GamePanel extends JPanel implements Scrollable {
	private static final long serialVersionUID = 123826L;
	protected GraphicalCoordinator map;
	protected int size;
	protected static final int rightButton=3;
	public GamePanel(GraphicalCoordinator mp) {
		map=mp;
		setSize();
		requestFocusInWindow();
		setForeground(Color.gray);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GamePanel.this.requestFocusInWindow();
				Point hex = map.hexAt(e.getPoint());
				if (hex!=null) doClick(e.getPoint(),hex,e.getButton());
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char val = e.getKeyChar();
				if (val == '+') changeSize(true);
				else if (val == '-') changeSize(false);
			}
		});
	}
	private void setSize() {
		size = map.getSize();
		setPreferredSize(map.getDimensions());
	}
	public Dimension getPreferredScrollableViewportSize() {
		return map.getDimensions();
	}
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == VERTICAL) return visibleRect.height-size;
		else return  visibleRect.width-size;
	}
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == VERTICAL) return size/2;
		else return 3*size/4;
	}
	public void paint(Graphics g) {
		map.paint(g);
	}
	public abstract void doClick(Point position, Point hex,int button);
	public void changeSize(boolean increase) {
		if (increase) map.incSize();
		else map.decSize();
		setSize();
		revalidate();
	}
}
