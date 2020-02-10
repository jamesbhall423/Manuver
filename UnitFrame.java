package james.games.manuver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import james.games.graphics.MultiTextField;

public class UnitFrame extends Frame {
	private static final long serialVersionUID = 153L;
	private static final String[] varNames = {"Is king (y/n)","King player (y/n)","Can Move Rough (y/n)","Speed"};
	private MultiTextField field;
	/**
	 * Method UnitFrame
	 *
	 *
	 * @param unit
	 *
	 */
	public UnitFrame(final Unit unit, final CreatorFrame creator) {
		String[] initialVals = new String[4];
		initialVals[0] = unit.isKing() ? "y" : "n";
		initialVals[1] = unit.side() ? "y" : "n";
		initialVals[2] = unit.canMoveRough() ? "y" : "n";
		initialVals[3] = ""+unit.speed();
		field = new MultiTextField(varNames,initialVals);
		setLayout(new GridLayout(1,1));
		setMinimumSize(new Dimension(150,100));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] values = field.getValues();
				try {
					unit.setKing(values[0].toLowerCase().equals("y"));
					unit.setSide(values[1].toLowerCase().equals("y"));
					unit.setRough(values[2].toLowerCase().equals("y"));
					unit.setSpeed(Integer.parseInt(values[3]));
					setVisible(false);
					creator.setEnabled(true);
				} catch (RuntimeException ex) {
					JOptionPane.showMessageDialog(UnitFrame.this, "Invalid Values");
				}
			}
		});
		add(field);
		pack();
		setVisible(true);
	}	
}
