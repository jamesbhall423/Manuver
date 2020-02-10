package james.games.manuver;

/**
 * This class allows requests to be forwarded onto the user (or not), even though the primary code does not know what format to display the request.
 */
public interface Prompter {
	
	/**
	 * Method prompt
	 *
	 *
	 * @param prompt - a message to be displayed to the user
	 *
	 * @return the user's input
	 *
	 */
	public boolean prompt(String prompt);	
}
