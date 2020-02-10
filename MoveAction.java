package james.games.manuver;

/**
 * Represent a move from one point to another.
 */
public interface MoveAction {
	
	/**
	 * Method xFrom
	 *
	 *
	 * @return the x-coordinate the unit came from
	 *
	 */
	public int xFrom();

	/**
	 * Method yFrom
	 *
	 *
	 * @return the y-coordinate the unit came from
	 *
	 */
	public int yFrom();

	/**
	 * Method xTo
	 *
	 *
	 * @return the x-coordinate the unit went to
	 *
	 */
	public int xTo();

	/**
	 * Method yTo
	 *
	 *
	 * @return the y-coordinate the unit went to
	 *
	 */
	public int yTo();
	
	/**
	 * Method numHexes
	 *
	 *
	 * @return the number of hexes the unit traveled
	 *
	 */
	public int numHexes();

	/**
	 * Method lastOccupied
	 *
	 *
	 * @return the unit at (xTo(),yTo()) before this unit moved there.
	 *
	 */
	public UnitInterface lastOccupied();	
}
