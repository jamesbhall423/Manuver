package james.games.manuver;

import java.awt.Point;
import java.io.IOException;
/**
 * Represents the manuver game.
 * Many methods barrowed from GameSpecs in the revelation game
 */
public interface GameInterface {
	public static final int TYPE_EMPTY = 0;
	public static final int TYPE_FOREST = 1;
	public static final int TYPE_MOUNTAIN = 2;
	
	/**
	 * Method isEast
	 *
	 *
	 * @param p
	 *
	 * @return the point is on the east face as defined by the specifications above.
	 *
	 */
	public boolean isEast(Point p);

	/**
	 * Method eastFace
	 *
	 *
	 * @return all the points on the east face.
	 *
	 */
	public Point[] eastFace();

	/**
	 * Method standardConnection
	 *
	 *
	 * @param p1
	 * @param p2
	 *
	 * @return whether p1 and p2 are adjacent
	 *
	 */
	public boolean standardConnection(Point p1, Point p2);

	/**
	 * Method width
	 *
	 *
	 * @return the width of the map
	 *
	 */
	public int width();

	/**
	 * Method height
	 *
	 *
	 * @return the height of the map
	 *
	 */
	public int height();
	
	/**
	 * Method rowVertical
	 *
	 *
	 * @return true if the rows run up and down, false if they run left and right.
	 *
	 */
	public boolean rowVertical();	
	
	/**
	 * Method isValid
	 *
	 * @param p
	 *
	 * @return whether the given point is on the mapboard.
	 *
	 */
	public boolean isValid(Point p);
	
	/**
	 * Method sides
	 *
	 * @param p
	 *
	 * @return the hexes adjacent to p.
	 *
	 */
	public Point[] sides(Point p);
	
	/**
	 * Method victoryCode
	 *
	 * @param code
	 *
	 * @return the name of the given victory code.
	 *
	 */
	public String victoryCode(int code);
	
	/**
	 * Method victoryCodes
	 *
	 *
	 * @return a list of all victory codes.
	 *
	 */
	public String[] victoryCodes();
	
	/**
	 * Method rowLength
	 *
	 * @param index
	 *
	 * @return the length of the given row or collumn, as given by the method rowsVertical.
	 *
	 */
	public int rowLength(int index);
	
	/**
	 * Method move - moves the unit at (x1,y1) to (x2,y2)
	 *
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 *
	 */
	public void move(int x1, int y1, int x2, int y2);

	/**
	 * Method getTerrain
	 *
	 *
	 * @param x
	 * @param y
	 *
	 * @return the terrain at (x,y)
	 *
	 */
	public int getTerrain(int x, int y);

	/**
	 * Method getUnit
	 *
	 *
	 * @param x
	 * @param y
	 *
	 * @return the unit at (x,y)
	 *
	 */
	public UnitInterface getUnit(int x, int y);

	/**
	 * Method canMove
	 *
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param player
	 *
	 * @return whether the given player can move a unit from (x1,y1) to (x2,y2)
	 *
	 */
	public boolean canMove(int x1, int y1, int x2, int y2, boolean player);
	
	/**
	 * Method undoMove
	 *
	 *
	 * @return the action undone, or null if cannot undo
	 *
	 */
	public MoveAction undoMove();

	/**
	 * Method currentPlayer
	 *
	 *
	 * @return the player whose turn it is (true=king player, false = defending player)
	 *
	 */
	public boolean currentPlayer();
	
	/**
	 * Method endTurn - ends the current player's turn
	 *
	 *
	 */
	public void endTurn();
	
	/**
	 * Method surrender - have the given player surrender
	 *
	 * @param player
	 */
	public void surrender(boolean player);
	
	/**
	 * Method victoryStatus
	 *
	 *
	 * @return the code for the victory status
	 *
	 */
	public int victoryStatus();
	
	/**
	 * Method kingVictory
	 *
	 *
	 * @return -1: defending victory. 0: open. 1: king player victory.
	 *
	 */
	public int kingVictory();

	/**
	 * Method timeRemaining
	 *
	 *
	 * @return the time remaining before the defending player automaticaly wins
	 *
	 */
	public int timeRemaining();	

	/**
	 * Method saveGame - saves the game to the given file.
	 *
	 *
	 * @param file
	 * @param user - for prompts on whether to overwrite
	 *
	 * @return true if the save was successful
	 *
	 */
	public boolean saveGame(String file, Prompter user) throws IOException;
	
	/**
	 * Method printGame
	 *
	 *
	 * @return a string representation of this game
	 *
	 */
	public String printGame();	
}
