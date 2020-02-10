package james.games.manuver;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import static james.tools.util.Strings.NEW_LINE;

/**
 * Represents the manuver game.
 * Many methods barrowed from GameSpecs in the revelation game
 */
public class GameEngine implements Serializable, GameInterface, CreatorInterface {
	private static final int king_dest = 1;
	private static final int def_sur = 2;
	private static final int king_cap = 3;
	private static final int time_end = 4;
	private static final int king_sur = 5;
	private static final long serialVersionUID = 34L;
	private int width;
	private int height;
	private Hex[][] board;
	private Stack<Unit> recentMoves = new Stack<Unit>();
	private int totalTime = 0;
	private int currentTime = 0;
	private int victoryCode = 0;
	private boolean player = true;
	/**
	 * Method GameEngine - creates a new GameEngine
	 *
	 * @param width
	 * @param height
	 */
	public GameEngine(int width, int height) {
		setGame(width,height);
	}
	
	/**
	 * Method GameEngine - creates a new GameEngine from the given file
	 *
	 * @param file
	 * @param savedGame - true if the file represents a running game, false if it represents a setup file.
	 */
	public GameEngine(String file, boolean savedGame) throws IOException {
		if (savedGame) loadGame(file);
		else loadSetup(file);
	}
	
	/**
	 * Method isEast
	 *
	 *
	 * @param p
	 *
	 * @return if this is part of the east face as defined above.
	 *
	 */
	public boolean isEast(Point p) {
		if (!isValid(p)) throw new IllegalArgumentException(p + " is not a valid point.");
		return p.x==width-1;
	}

	/**
	 * Method eastFace
	 *
	 *
	 * @return all the points of the east face.
	 *
	 */
	public Point[] eastFace() {
		Point[] out = new Point[height+(1-(width%2))];
		for (int y = 0; y < out.length; y++) out[y] = new Point(width-1,y);
		return out;
	}

	/**
	 * Method standardConnection
	 *
	 *
	 * @param p1
	 * @param p2
	 *
	 * @return whether p1 is adjacent to p2.
	 *
	 */
	public boolean standardConnection(Point p1, Point p2) {
		if (!isValid(p1)) throw new IllegalArgumentException(p1 + " is not a valid point.");
		if (!isValid(p2)) throw new IllegalArgumentException(p2 + " is not a valid point.");
		int dx = p1.x-p2.x;
		if (dx<-1||dx>1) return false;
		if (dx==0) return Math.abs(p1.y-p2.y)==1;
		if (p1.x%2==1) {
			Point temp = p1;
			p1=p2;
			p2=temp;
		}
		if (p2.y==p1.y||p2.y==p1.y+1) return true;
		else return false;
	}


	/**
	 * Method width
	 *
	 *
	 * @return width
	 *
	 */
	public final int width() {
		return width;
	}

	/**
	 * Method height
	 *
	 *
	 * @return height
	 *
	 */
	public final int height() {
		return height;
	}
	
	/**
	 * Method rowVertical
	 *
	 *
	 * @return true
	 *
	 */
	public boolean rowVertical() {
		return true;
	}	
	
	/**
	 * Method isValid
	 *
	 * @param p
	 *
	 * @return true if p is on the mapboard, false otherwise.
	 *
	 */
	public boolean isValid(Point p) {
		return p.x>=0&&p.x<width&&p.y>=0&&p.y<height+p.x%2;
	}
	
	/**
	 * Method sides
	 *
	 * @param p
	 *
	 * @return the hexes adjacent to p.
	 *
	 */
	public Point[] sides(Point p) {
		return connectedRoads(p,63);
	}
	
	/**
	 * Method victoryCode
	 *
	 * @param code
	 *
	 * @return the name of the code for the victory status.
	 *
	 */
	public final String victoryCode(int code) {
		return victoryCodes()[code];
	}
	
	/**
	 * Method victoryCodes
	 *
	 *
	 * @return {"undetermined","king destination","defending surrender","king capture","time end","king surrender"}
	 *
	 */
	public String[] victoryCodes() {
		String[] out = {"undetermined","king destination","defending surrender","king capture","time end","king surrender"};
		return out;
	}
	
	/**
	 * Method rowLength
	 *
	 * @param index
	 *
	 * @return the length of the given collumn.
	 *
	 */
	public int rowLength(int index) {
		if (index<0||index>=width) throw new IllegalArgumentException("Invalid Index: value = "+index);
		return height+index%2;
	}
	
	/**
	 * Method connectedRoads - method barrowed from GameSpecs. Used internally only
	 *
	 * @param p - the hex the roads emanate from
	 * @param roads - a value representing the connected roads
	 *
	 * @return the points connected by to the given point by the given roads: Sum: 1-North, 2-NorthEast, 4-SouthEast, 8-South, 16-SouthWest, 32-NorthWest
	 *
	 */
	private Point[] connectedRoads(Point p, int roads) {
		if (!isValid(p)) throw new IllegalArgumentException(p + " is not a valid point.");
		if (roads<0||roads>=64) throw new IllegalArgumentException("roads ("+roads+") is not between 0 and 63 inclusive.");
		ArrayList<Point> out = new ArrayList<Point>(6);
		Point next = new Point(p.x,p.y-1);
		if ((roads&1)>0&&isValid(next)) out.add(next);
		next = new Point(p.x,p.y+1);
		if ((roads&8)>0&&isValid(next)) out.add(next);
		boolean southPoint = (p.x%2==0);
		next = new Point(p.x-1,p.y);
		if ((roads&(southPoint?32:16))>0&&isValid(next)) out.add(next);
		next = new Point(p.x+1,p.y);
		if ((roads&(southPoint?2:4))>0&&isValid(next)) out.add(next);
		int mod = (p.x%2==0) ? 1 : -1;
		next = new Point(p.x-1,p.y+mod);
		if ((roads&(southPoint?16:32))>0&&isValid(next)) out.add(next);
		next = new Point(p.x+1,p.y+mod);
		if ((roads&(southPoint?4:2))>0&&isValid(next)) out.add(next);
		return out.toArray(new Point[0]);
	}
	
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
	public void move(int x1, int y1, int x2, int y2) {
		if (victoryCode!=0) throw new IllegalArgumentException("Cannot move after game is over.");
		boolean side = getUnit(x1,y1).side();
		int numHexes = numHexes(x1,y1,x2,y2,side);
		if (numHexes==0) throw new IllegalArgumentException("Unit cannot move from ("+x1+","+y1+") to ("+x2+","+y2+".");
		if (side!=player) throw new IllegalArgumentException("Unit does not belong to the current player.");
		Unit unit = (Unit) getUnit(x1,y1);
		Unit end = (Unit) getUnit(x2,y2);
		unit.moveTo(x2,y2,numHexes,end);
		board[x2][y2].setUnit(unit);
		recentMoves.push(unit);
		if (end!=null&&unit.side()==end.side()) {
			end.moveTo(x1,y1,numHexes(x2,y2,x1,y1,side),unit);
			board[x1][y1].setUnit(end);
		} else board[x1][y1].setUnit(null);
		if (end!=null&&unit.side()!=end.side()&&end.isKing()) victoryCode=king_cap;
		if (unit.isKing()&&isEast(new Point(x2,y2))) victoryCode=king_dest;
	}

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
	public int getTerrain(int x, int y) {
		return board[x][y].getTerrain();
	}
	
	/**
	 * Method undoMove
	 *
	 *
	 * @return the action undone, or null if cannot undo
	 *
	 */
	public MoveAction undoMove() {
		if (recentMoves.empty()||victoryCode>0) return null;
		Unit unit = recentMoves.pop();
		MoveAction action = unit.undoMove();
		Unit other = (Unit) action.lastOccupied();
		board[action.xFrom()][action.yFrom()].setUnit(unit);
		board[action.xTo()][action.yTo()].setUnit(other);
		if (other!=null&&other.side()==unit.side()) other.undoMove();
		return action;
	}

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
	public boolean canMove(int x1, int y1, int x2, int y2, boolean player) {
		if (victoryCode!=0||x1==x2&&y1==y2) return false;
		return numHexes(x1,y1,x2,y2,player)>0;
	}
	//return the number of hexes if the given player can move from (x1,y1) to (x2,y2), 0 otherwise.
	private int numHexes(int x1, int y1, int x2, int y2, boolean player) {
		Unit source = (Unit) getUnit(x1,y1);
		Point last = new Point(x1,y1);
		Point next = new Point(x2,y2);
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(last);
		if (source==null||source.side()!=player||source.hexesLeft()==0) return 0;
		int out = canMove(source,list,next,source.hexesLeft(),1);
		if (out==0) return 0;
		if (!testOther(next,(Unit) getUnit(x2,y2),last,player)) return 0;
		return out;
	}
	
	/**
	 * Method listMoves
	 *
	 *
	 * @param x1
	 * @param y1
	 * @param player
	 *
	 * @return a list of points the player can move from (x1,y1) or null if the player does not have a unit at (x1,y1)
	 *
	 */
	public Point[] listMoves(int x1, int y1, boolean player) {
		if (victoryCode!=0) return null;
		Unit unit = (Unit) getUnit(x1,y1);
		if (unit==null) return null;
		if (unit.side()!=player) return null;
		if (unit.hexesLeft()==0) return new Point[0];
		Point start = new Point(x1,y1);
		ArrayList<Point> list = new ArrayList<Point>();
		ArrayList<Point> source = new ArrayList<Point>();
		source.add(new Point(x1,y1));
		
		listMoves(unit,source,list,unit.hexesLeft());
		Iterator<Point> iterator = list.iterator();
		while (iterator.hasNext()) {
			Point next = iterator.next();
			Unit dest = (Unit) getUnit(next.x,next.y);
			if (!testOther(start,dest,next,player)) iterator.remove();
		}
		return list.toArray(new Point[0]);
	}
	//tests whether the presence of a unit in a destination spot effects the abilty of a unit to move from the source. true=no effect, false=blocked
	private boolean testOther(Point source, Unit dest, Point last, boolean player) {
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(source);
		if (dest!=null&&dest.side()==player&&(dest.hexesLeft()==0||canMove(dest,list,last,dest.hexesLeft(),1)==0)) return false;
		else return true;
	}
	
	/**
	 * Method listMoves - assembles a list of all hexes the given unit can move to
	 *
	 *
	 * @param unit
	 * @param last
	 * @param all - will become a list of all hexes the unit has basic ability to move to.
	 * @param hexesLeft
	 *
	 *
	 */
	private void listMoves(Unit unit, ArrayList<Point> last, ArrayList<Point> all, int hexesLeft) {
		ArrayList<Point> next = new ArrayList<Point>();
		for (int I = 0; I < last.size(); I++) {
			Point p = last.get(I);
			Point[] setP = sides(p);
			for (int i = 0; i < setP.length; i++) {
				Hex toAdd = board[setP[i].x][setP[i].y];
				if (!toAdd.isChecked()&&toAdd.getTerrain()!=2&&(toAdd.getTerrain()==0||unit.canMoveRough())) {
					toAdd.setChecked(true);
					next.add(setP[i]);
					all.add(setP[i]);
				}
			}
		}
		if (hexesLeft>1) listMoves(unit,next,all,hexesLeft-1);
		for (int i = 0; i < next.size(); i++) {
			Point p = next.get(i);
			board[p.x][p.y].setChecked(false);
		}
	}
	
	/**
	 * Method canMove
	 *
	 *
	 * @param unit
	 * @param last
	 * @param dest
	 * @param hexesLeft
	 *
	 * @return whether the given unit has basic ability to move to dest
	 *
	 */
	private int canMove(Unit unit, ArrayList<Point> last, Point dest, int hexesLeft, int totalHexes) {
		int found = 0;
		ArrayList<Point> next = new ArrayList<Point>();
		for (int I = 0; I < last.size()&&(found==0); I++) {
			Point p = last.get(I);
			Point[] setP = sides(p);
			for (int i = 0; i < setP.length; i++) {
				Hex toAdd = board[setP[i].x][setP[i].y];
				if (!toAdd.isChecked()&&toAdd.getTerrain()!=2&&(toAdd.getTerrain()==0||unit.canMoveRough())) {
					toAdd.setChecked(true);
					next.add(setP[i]);
					if (setP[i].equals(dest)) found=totalHexes;
				}
			}
		}
		if (hexesLeft>1&&(found==0)) found=canMove(unit,next,dest,hexesLeft-1,totalHexes+1);
		for (int i = 0; i < next.size(); i++) {
			Point p = next.get(i);
			board[p.x][p.y].setChecked(false);
		}
		return found;
	}
	
	/**
	 * Method getHex
	 *
	 *
	 * @return the hex at (x,y)
	 *
	 */
	private Hex getHex(int x, int y) {
		return board[x][y];
	}

	/**
	 * Method currentPlayer
	 *
	 *
	 * @return the player whose turn it is (true=king player, false = defending player)
	 *
	 */
	public boolean currentPlayer() {
		return player;
	}
	
	/**
	 * Method endTurn - ends the current player's turn
	 *
	 *
	 */
	public void endTurn() {
		if (victoryCode>0) return;
		for (int x = 0; x < board.length; x++) for (int y = 0; y < board[x].length; y++) {
			Unit unit = (Unit) getUnit(x,y);
			if (unit!=null) unit.endTurn();
		}
		recentMoves.clear();
		if (player) {
			currentTime++;
			if (timeRemaining()<=0) victoryCode=time_end;
		}
		player=!player;
	}
	
	/**
	 * Method surrender - have the given player surrender
	 *
	 * @param player
	 */
	public void surrender(boolean player) {
		if (player) victoryCode=king_sur;
		else victoryCode=def_sur;
	}
	
	/**
	 * Method victoryStatus
	 *
	 *
	 * @return the code for the victory status
	 *
	 */
	public int victoryStatus() {
		return victoryCode;
	}
	
	/**
	 * Method kingVictory
	 *
	 *
	 * @return -1: defending victory. 0: open. 1: king player victory.
	 *
	 */
	public int kingVictory() {
		if (victoryCode==0) return 0;
		else if (victoryCode>2) return -1;
		else return 1;
	}

	/**
	 * Method timeRemaining
	 *
	 *
	 * @return the time remaining before the defending player automaticaly wins
	 *
	 */
	public int timeRemaining() {
		return totalTime-currentTime;
	}

	/**
	 * Method saveGame - saves the game to the given file.
	 *
	 *
	 * @param file
	 *
	 * @return true if the save was successful
	 *
	 */
	public boolean saveGame(String file, Prompter user) throws IOException {
		File out = new File("james/games/manuver/saved/"+file+".msav");
		return save(file,out,user);
	}
	/**
	 * Method setUnit - sets the unit at (x,y)
	 *
	 *
	 * @param x
	 * @param y
	 * @param unit - the new unit
	 *
	 */
	public void setUnit(int x, int y, Unit unit) {
		board[x][y].setUnit(unit);
	}

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
	public UnitInterface getUnit(int x, int y) {
		return board[x][y].getUnit();
	}

	/**
	 * Method setGame - sets this to a new empty game with the given width and height.
	 *
	 *
	 * @param width
	 * @param height
	 *
	 */
	public void setGame(int width, int height) {
		this.width=width;
		this.height=height;
		board = new Hex[width][height];
		for (int x = 0; x < board.length; x++) board[x] = new Hex[rowLength(x)];
		for (int x = 0; x < board.length; x++) for (int y = 0; y < board[x].length; y++) board[x][y] = new Hex(x,y);
	}

	/**
	 * Method saveSetup - saves this setup to the given file
	 *
	 *
	 * @param file
	 *
	 * @return true if the save was successful
	 *
	 */
	public boolean saveSetup(String file, Prompter user) throws IOException {
		File out = new File("james/games/manuver/setups/"+file+".mset");
		return save(file,out,user);
	}

	/**
	 * Method loadSetup - loads this from the given setup file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadSetup(String file) throws IOException {
		load(new File("james/games/manuver/setups/"+file+".mset"));
	}	

	/**
	 * Method loadGame - Loads the game in the given saved game file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadGame(String file) throws IOException {
		load(new File("james/games/manuver/saved/"+file+".msav"));
	}	
	/**
	 * Method setTime - sets the automactic victory time by the defending player
	 *
	 *
	 * @param time - the amount of time before an automatic victory by the defending player
	 *
	 */
	public void setTime(int time) {
		totalTime=time;
	}
	private boolean save(String name, File file, Prompter user) throws IOException {
		if (!file.exists()||user.prompt("file \""+name+"\" already exists. Overwite?")) {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
			return true;
		}
		return false;
	}
	private void load(File file) throws IOException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		GameEngine toCopy;
		try {
			toCopy = (GameEngine) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
		width=toCopy.width;
		height=toCopy.height;
		board=toCopy.board;
		recentMoves=toCopy.recentMoves;
		totalTime=toCopy.totalTime;
		currentTime=toCopy.currentTime;
		victoryCode=toCopy.victoryCode;
		player=toCopy.player;
	}
	
	/**
	 * Method setTerrain - sets the terrain at (x,y)
	 *
	 *
	 * @param x
	 * @param y
	 * @param terrain - the new terrain
	 *
	 */
	public void setTerrain(int x, int y, int terrain) {
		board[x][y].setTerrain(terrain);
	}
	
	/**
	 * Method printGame
	 *
	 *
	 * @return a string representation of this game
	 *
	 */
	public String printGame() {
		String out = "";
		for (int y = 0; y <= 2*height; y++) {
			String line = "";
			for (int x = 0; x < board.length; x++) {
				if (y%2==x%2)  line+=Hex.printVoid();
				else line+=board[x][y/2].printHex();
			}
			out+=line+NEW_LINE;
		}
		out+="pl: "+(player ? "king" : "def")+", status: "+victoryCode(victoryCode)+", time left: "+timeRemaining()+NEW_LINE;
		return out;
	}
}
