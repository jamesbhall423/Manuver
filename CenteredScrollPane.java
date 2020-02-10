package james.games.graphics;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.awt.Component;
/**
 * This class represents a scrollpane that is focused on the center of the view rather than on the upper-left corner.
 */
public class CenteredScrollPane extends JScrollPane {
	private class CenteredScrollPaneUI extends BasicScrollPaneUI {
		private boolean recursive=false;
		@Override
		public void syncScrollPaneWithViewport() {
			if (recursive) return;
			recursive=true;
			double Hper = ((CenterScrollBar) horizontalScrollBar).getPercent();
			double Vper = ((CenterScrollBar) verticalScrollBar).getPercent();
			recursive=false;
			super.syncScrollPaneWithViewport();
			recursive=true;
			((CenterScrollBar) horizontalScrollBar).setPercent(Hper);
			((CenterScrollBar) verticalScrollBar).setPercent(Vper);
			recursive=false;
		}
	}
	private class CenterScrollBar extends ScrollBar {
		public double getPercent() {
			return ((getValue()+getVisibleAmount()/2.0)-getMinimum())/(getMaximum()-getMinimum()+0.000001);
		}
		public void setPercent(double percent) {
			double center = percent*(getMaximum()-getMinimum())+getMinimum();
			setValue((int)Math.round(center-(getVisibleAmount()/2.0)));
		}
		/**
		 * Method CenterScrollBar
		 *
		 *
		 * @param orientation - the orientation of the scrollBar
		 *
		 */
		public CenterScrollBar(int orientation) {
			super(orientation);
		}
	}
	/**
	 * Method CenteredScrollPane
	 *
	 *
	 */
	public CenteredScrollPane() {
		super();
		setScrollBars();
		setUI(new CenteredScrollPaneUI());
	}

	/**
	 * Method CenteredScrollPane
	 *
	 *
	 * @param view
	 *
	 */
	public CenteredScrollPane(Component view) {
		super(view);
		setScrollBars();
	}

	/**
	 * Method CenteredScrollPane
	 *
	 *
	 * @param view
	 * @param vsbPolicy
	 * @param hsbPolicy
	 *
	 */
	public CenteredScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view,vsbPolicy,hsbPolicy);
		setScrollBars();
	}

	/**
	 * Method CenteredScrollPane
	 *
	 *
	 * @param vsbPolicy
	 * @param hsbPolicy
	 *
	 */
	public CenteredScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy,hsbPolicy);
		setScrollBars();
	}

	/**
	 * Method setScrollBars
	 *
	 *
	 */
	private void setScrollBars() {
		setVerticalScrollBar(new CenterScrollBar(SwingConstants.VERTICAL));
		setHorizontalScrollBar(new CenterScrollBar(SwingConstants.HORIZONTAL));
	}	
}
