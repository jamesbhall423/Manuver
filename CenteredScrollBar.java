package james.games.graphics;

import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.Scrollable;
/**
 * This class represents a scrollbar that is focused on the center of the view rather than on the upper-left corner.
 *   I could not get this class to fully work.
 */
 @Deprecated
public class CenteredScrollBar extends JScrollBar {
	private static final long serialVersionUID = 8234L;

	/**
	 * Method CenteredScrollBar
	 *
	 *
	 * @param topBottom - true if the orientation runs vertically, false if it runs horozontally.
	 * @param scrollable - the scrollable this object will be scrolling, or null for no scrollable.
	 *
	 */
	public CenteredScrollBar(boolean topBottom) {
		if (topBottom) setOrientation(SwingConstants.VERTICAL);
		else setOrientation(SwingConstants.HORIZONTAL);
	}
	/**
	 * Method setValues - sets the values for the scrollBar.
	 *
	 *
	 * @param newValue
	 * @param newExtent
	 * @param newMin
	 * @param newMax
	 *
	 */
	@Override
	public void setValues(int newValue, int newExtent, int newMin, int newMax) {
		double newVal = newValue;
		if ((newExtent!=getVisibleAmount()||newMin!=getMinimum()||newMax!=getMaximum())&&newExtent<=newMax-newMin) {
			double begOrigExtent = getValue();
			double endOrigExtent = begOrigExtent+getVisibleAmount();
			double beginOriginal = getMinimum();
			double endOriginal = getMaximum();
			double center = ((begOrigExtent+endOrigExtent)/2-beginOriginal)/(endOriginal-beginOriginal);
			double newCenter = center*(newMax-newMin)+newMin;
			newVal = newCenter - (newExtent/2);
		}
		
		super.setValues((int)newVal,newExtent,newMin,newMax);
	}
}
