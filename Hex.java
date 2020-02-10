package james.games.manuver;

import java.io.Serializable;
/**
 * This class represents a Hexagon in gamespace. This is defined by a location, a terrain, and a unit.
 */
public class Hex implements Serializable {
	private static final long serialVersionUID = 16L;
	private boolean checked=false;
	private int terrain = 0;
	private Unit unit = null;
	private int x;
	private int y;
	/**
	 * Method isChecked
	 *
	 *
	 * @return the current value of checked.
	 *
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Method setChecked
	 *
	 *
	 * @param checked - checked is a special value for proccesing purposes. It should be false except when it is used inside a method.
	 *
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * Method getTerrain
	 *
	 *
	 * @return terrain
	 *
	 */
	public int getTerrain() {
		return terrain;
	}

	/**
	 * Method setTerrain
	 *
	 *
	 * @param terrain - must be 0-empty, 1-forest, 2-mountain.
	 *
	 */
	public void setTerrain(int terrain) {
		if (terrain<0||terrain>2) throw new IllegalArgumentException("Invalid value for terrain. Value = "+terrain+".");
		this.terrain=terrain;
	}

	/**
	 * Method setUnit
	 *
	 *
	 * @param unit - the new unit, or null to remove any existing unit.
	 *
	 */
	public void setUnit(Unit unit) {
		if (unit==null) this.unit=null;
		else {
			this.unit=unit.copy();
			this.unit.setX(x);
			this.unit.setY(y);
		}
	}

	/**
	 * Method getUnit
	 *
	 *
	 * @return the unit at this hexagon, or null if there is no unit.
	 *
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * Method getX
	 *
	 *
	 * @return x
	 *
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Method getY
	 *
	 *
	 * @return y
	 *
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method Hex - builds a hex at (x,y)
	 *
	 *
	 * @param x
	 * @param y
	 *
	 */
	public Hex(int x, int y) {
		this.x=x;
		this.y=y;
	}
	/**
	 * Method printHex
	 *
	 *
	 * @return a string representation of this hex.
	 *
	 */
	public String printHex() {
		String terr = null;
		switch (terrain) {
			case 0:
			terr="E";
			break;
			case 1:
			terr="F";
			break;
			case 2:
			terr="M";
			break;
		}
		return "("+x+","+y+")"+terr+(unit==null ? Unit.printNull() : unit.printUnit());
	}
	/**
	 * Method printVoid
	 *
	 *
	 * @return a string representation of a null hex.
	 *
	 */
	public static String printVoid() {
		return "------"+"------";
	}
}
