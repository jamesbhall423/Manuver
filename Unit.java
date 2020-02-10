package james.games.manuver;

import java.util.ArrayList;
import java.io.Serializable;
/**
 * This class represents a unit.
 */
public class Unit implements UnitInterface, Cloneable, Serializable {
	private static final long serialVersionUID = 12L;
	private ArrayList<MoveAction> recentMoves = new ArrayList<MoveAction>();
	private int x;
	private int y;
	private boolean isKing=false;
	private boolean side=false;
	private boolean isRough=false;
	private int speed=1;
	private int hexesLeft=1;
	/**
	 * Method recentMoves
	 *
	 *
	 * @return the moves the unit has made in the last turn
	 *
	 */
	public ArrayList<MoveAction> recentMoves() {
		return (ArrayList<MoveAction>) recentMoves.clone();
	}

	/**
	 * Method getX
	 *
	 *
	 * @return the x coordinate of the unit
	 *
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method getY
	 *
	 *
	 * @return the y coordinate of the unit
	 *
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method hexesLeft
	 *
	 *
	 * @return the hexes the unit has remaining to go during the current turn.
	 *
	 */
	public int hexesLeft() {
		return hexesLeft;
	}

	/**
	 * Method canMoveRough
	 *
	 *
	 * @return whether the unit can move in rough (forest) terrain
	 *
	 */
	public boolean canMoveRough() {
		return isRough;
	}

	/**
	 * Method speed
	 *
	 *
	 * @return the number of hexes the unit can move in a turn
	 *
	 */
	public int speed() {
		return speed;
	}

	/**
	 * Method side
	 *
	 *
	 * @return which player the unit belongs to.
	 *
	 */
	public boolean side() {
		return side;
	}

	/**
	 * Method isKing
	 *
	 *
	 * @return whether the unit is a king unit.
	 *
	 */
	public boolean isKing() {
		return isKing;
	}
	
	/**
	 * Method endTurn - ends the turn for the given unit
	 *
	 *
	 */
	public void endTurn() {
		recentMoves.clear();
		hexesLeft=speed();
	}
	
	/**
	 * Method moveTo - Updates the states of this unit based on the given move. Only game engines should call this method.
	 *
	 * @param x2
	 * @param y2
	 * @param numHexes
	 * @param previousUnit - the previous unit at (x2,y2)
	 *
	 *
	 */
	public void moveTo(int x2, int y2, int numHexes, UnitInterface previousUnit) {
		if (numHexes>hexesLeft) throw new IllegalArgumentException("Destination is "+numHexes+" hexes away. Unit can only go "+hexesLeft+" hexes.");
		hexesLeft-=numHexes;
		recentMoves.add(new Move(x,y,x2,y2,numHexes,previousUnit));
		x=x2;
		y=y2;
	}
	
	/**
	 * Method undoMove
	 *
	 *
	 * @return the action undone
	 */
	public MoveAction undoMove() {
		MoveAction last = recentMoves.remove(recentMoves.size()-1);
		hexesLeft+=last.numHexes();
		x=last.xFrom();
		y=last.yFrom();
		return last;
	}
	
	/**
	 * Method setRough - sets the ability of the unit to move through rough terrain
	 *
	 *
	 * @param rough - whether the unit will be able to move through rough terrain
	 *
	 */
	public void setRough(boolean rough) {
		isRough = rough;
	}

	/**
	 * Method setSpeed
	 *
	 *
	 * @param speed - the new speed
	 *
	 */
	public void setSpeed(int speed) {
		if (speed<1) throw new IllegalArgumentException("speed must be greater than or equal to one.");
		this.speed = speed;
		hexesLeft=speed;
	}

	/**
	 * Method setSide
	 *
	 *
	 * @param side - which player the unit belongs to.
	 *
	 */
	public void setSide(boolean side) {
		this.side = side;
	}

	/**
	 * Method setKing
	 *
	 *
	 * @param king - whether the unit is a king.
	 *
	 */
	public void setKing(boolean king) {
		isKing = king;
	}
	
	/**
	 * Method setX
	 *
	 *
	 * @param x - the new x
	 *
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Method setY
	 *
	 *
	 * @param y - the new y
	 *
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Method copy
	 *
	 *
	 * @return an independent copy of this unit
	 *
	 */
	public Unit copy() {
		Unit out;
		try {
			out = (Unit) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
		out.recentMoves = new ArrayList<MoveAction>();
		return out;
	}
	
	/**
	 * Method printUnit
	 *
	 *
	 * @return a string representation of this unit
	 *
	 */
	public String printUnit() {
		String sid;
		if (isKing) sid="K";
		else if (side) sid="k";
		else sid="d";
		String rough = isRough ? "r" : "o";
		String rate = speed+"("+hexesLeft+")";
		return sid+rough+rate;
	}
	/**
	 * Method printNull
	 *
	 *
	 * @return a string representation of a null unit
	 *
	 */
	public static String printNull() {
		return "      ";
	}
	/**
	 * Method stringLength
	 *
	 *
	 * @return the length of a string representation of a unit
	 *
	 */
	public static int stringLength() {
		return 6;
	}
	/**
	 * Method toString
	 *
	 *
	 * @return a string representation of this unit
	 *
	 */
	@Override
	public String toString() {
		return "Unit: "+printUnit();
	}
	
	/**
	 * Method Unit - creates a new unit
	 *
	 *
	 */
	public Unit() {
	}
}
