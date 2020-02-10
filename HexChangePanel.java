package james.games.graphics.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import james.games.graphics.Hexagon;
import james.games.graphics.MultiTextField;
import james.games.graphics.Painter;

public class HexChangePanel extends Frame {
	private Hexagon hex;
	private HexPanel creator;
	private MultiTextField fields;
	private static final String[] names = {"fill","border","char col","char val","X (y/n)"};
	private static final String[] initialValues = {"","","","",""};
	/**
	 * Method HexChangePanel
	 *
	 *
	 * @param hex
	 *
	 */
	public HexChangePanel(Hexagon hx,HexPanel create) {
		hex=hx;
		creator = create;
		setLayout(new GridLayout(1,1));
		setMinimumSize(new Dimension(150,100));
		fields = new MultiTextField(names,initialValues);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(HexChangePanel.this,"save?","save?",JOptionPane.YES_NO_CANCEL_OPTION);
				switch (option) {
					case JOptionPane.YES_OPTION:
					String[] values = fields.getValues();
					hex.clear();
					hex.setBackground(color(Integer.parseInt(values[0])));
					hex.setBorder(color(Integer.parseInt(values[1])));
					hex.setTextColor(color(Integer.parseInt(values[2])));
					if (!values[3].equals("")) hex.setText(values[3]);
					if (values[4].equals("y")) hex.addPainter(new Painter() {
						public void paint(Graphics g) {
							Point a1 = hex.verticyAt(1);
							Point a2 = hex.verticyAt(4);
							Point b1 = hex.verticyAt(2);
							Point b2 = hex.verticyAt(5);
							g.setColor(Color.orange);
							g.drawLine(a1.x,a1.y,a2.x,a2.y);
							g.drawLine(b1.x,b1.y,b2.x,b2.y);
						}
					});
					Point top = hex.getPosition();
					int size = hex.getSize();
					creator.repaint(top.x,top.y,size,size);
					case JOptionPane.NO_OPTION:
					HexChangePanel.this.setVisible(false);
				}
			}
		});
		add(fields);
		pack();
		setVisible(true);
	}
	private Color color(int value) {
		switch (value) {
			case 1:
			return Color.white;
			case 2:
			return Color.red;
			case 3:
			return Color.green;
			case 4:
			return Color.blue;
			case 5:
			return Color.cyan;
			case 6:
			return Color.magenta;
			case 7:
			return Color.yellow;
			case 8:
			return Color.black;
			case 9:
			return Color.gray;
			default:
			throw new RuntimeException("Invalid Color value");
		}
	}	
}
