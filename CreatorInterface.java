package james.games.manuver;

import java.io.IOException;
/**
 * Represents the methods needed to make a manuver game.
 */
public interface CreatorInterface {
	
	/**
	 * Method setUnit - sets the unit at (x,y)
	 *
	 *
	 * @param x
	 * @param y
	 * @param unit - the new unit
	 *
	 */
	public void setUnit(int x, int y, Unit unit);

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
	 * Method setGame - sets this to a new empty game with the given width and height.
	 *
	 *
	 * @param width
	 * @param height
	 *
	 */
	public void setGame(int width, int height);

	/**
	 * Method saveSetup - saves this setup to the given file
	 *
	 *
	 * @param file
	 * @param user - for prompts on whether to overwrite
	 *
	 * @return true if the save was successful
	 *
	 */
	public boolean saveSetup(String file, Prompter user) throws IOException;

	/**
	 * Method loadSetup - loads this from the given setup file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadSetup(String file) throws IOException;	

	/**
	 * Method loadGame - Loads the game in the given saved game file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadGame(String file) throws IOException;

	/**
	 * Method timeRemaining
	 *
	 *
	 * @return the amount of time that has to pass before the defending player automaticaly wins
	 *
	 */
	public int timeRemaining();	

	/**
	 * Method setTime - sets the automactic victory time by the defending player
	 *
	 *
	 * @param time - the amount of time before an automatic victory by the defending player
	 *
	 */
	public void setTime(int time);
	
	/**
	 * Method printGame
	 *
	 *
	 * @return a string representation of this game
	 *
	 */
	public String printGame();
	
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
	 * Method setTerrain - sets the terrain at (x,y)
	 *
	 *
	 * @param x
	 * @param y
	 * @param terrain - the new terrain
	 *
	 */
	public void setTerrain(int x, int y, int terrain);
}
