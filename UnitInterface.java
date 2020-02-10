package james.games.manuver;

import java.util.ArrayList;
/**
 * Represents a unit in the manuver game.
 * It is impossible to change the values of units through this interface.
 */
public interface UnitInterface {
	
	/**
	 * Method recentMoves
	 *
	 *
	 * @return the moves the unit has made in the last turn
	 *
	 */
	public ArrayList<MoveAction> recentMoves();

	/**
	 * Method getX
	 *
	 *
	 * @return the x coordinate of the unit
	 *
	 */
	public int getX();

	/**
	 * Method getY
	 *
	 *
	 * @return the y coordinate of the unit
	 *
	 */
	public int getY();

	/**
	 * Method canMoveRough
	 *
	 *
	 * @return whether the unit can move in rough (forest) terrain
	 *
	 */
	public boolean canMoveRough();

	/**
	 * Method speed
	 *
	 *
	 * @return the number of hexes the unit can move in a turn
	 *
	 */
	public int speed();

	/**
	 * Method side
	 *
	 *
	 * @return which player the unit belongs to.
	 *
	 */
	public boolean side();

	/**
	 * Method isKing
	 *
	 *
	 * @return whether the unit is a king unit.
	 *
	 */
	public boolean isKing();
	
	/**
	 * Method hexesLeft
	 *
	 *
	 * @return the hexes the unit has remaining to go during the current turn.
	 *
	 */
	public int hexesLeft();
	
	/**
	 * Method printUnit
	 *
	 *
	 * @return a string representation of this unit
	 *
	 */
	public String printUnit();	
}
