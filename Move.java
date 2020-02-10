package james.games.manuver;

import java.io.Serializable;
/**
 *  This class represents a move action.
 */
public class Move implements MoveAction, Serializable {
	private static final long serialVersionUID = 15L;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int numHexes;
	private UnitInterface last;
	/**
	 * Method Move - constructs a new Move object.
	 *
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param last - the previous unit at (x2,y2)
	 *
	 */
	public Move(int x1, int y1, int x2, int y2, int numHexes, UnitInterface last) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.last=last;
		this.numHexes=numHexes;
	}
	
	/**
	 * Method xFrom
	 *
	 *
	 * @return the x-coordinate the unit came from
	 *
	 */
	public int xFrom() {
		return x1;
	}

	/**
	 * Method yFrom
	 *
	 *
	 * @return the y-coordinate the unit came from
	 *
	 */
	public int yFrom() {
		return y1;
	}

	/**
	 * Method xTo
	 *
	 *
	 * @return the x-coordinate the unit went to
	 *
	 */
	public int xTo() {
		return x2;
	}

	/**
	 * Method yTo
	 *
	 *
	 * @return the y-coordinate the unit went to
	 *
	 */
	public int yTo() {
		return y2;
	}

	/**
	 * Method lastOccupied
	 *
	 *
	 * @return the unit at (xTo(),yTo()) before this unit moved there.
	 *
	 */
	public UnitInterface lastOccupied() {
		return last;
	}
	
	/**
	 * Method numHexes
	 *
	 *
	 * @return the number of hexes the unit traveled
	 *
	 */
	public int numHexes() {
		return numHexes;
	}
	/**
	 * Method toString
	 *
	 *
	 * @return a string representation of this object
	 *
	 */
	 @Override
	public String toString() {
		return "Start: ("+x1+","+y1+"). End: ("+x2+","+y2+") "+(last==null ? "" : last.toString());
	}
}
