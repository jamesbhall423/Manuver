package james.games.manuver;

import james.games.graphics.*;
import java.io.Serializable;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
/**
 * This represents a game engine that also updates a given graphical map.
 */
public class GraphicalCoordinator implements Serializable, GameInterface, CreatorInterface {
	//this class paints an X for possible moves.
	private static class XMarker implements Painter {
		private static final long serialVersionUID = 23458L;
		private Hexagon hex;
		public XMarker(Hexagon hex) {
			this.hex=hex;
		}
		public void paint(Graphics g) {
			Point a1 = hex.verticyAt(1);
			Point a2 = hex.verticyAt(4);
			Point b1 = hex.verticyAt(2);
			Point b2 = hex.verticyAt(5);
			g.setColor(Color.blue);
			g.drawLine(a1.x,a1.y,a2.x,a2.y);
			g.drawLine(b1.x,b1.y,b2.x,b2.y);
		}
	}
	//this class fills a circle for units.
	private static class CircleMarker implements Painter {
		private static final long serialVersionUID = 912735L;
		private Hexagon hex;
		private Color color;
		public CircleMarker(Hexagon hex,Color color) {
			this.hex=hex;
			this.color=color;
		}
		public void paint(Graphics g) {
			int radius = hex.getSize()/3;
			g.setColor(color);
			Point center = hex.center();
			g.fillOval(center.x-radius,center.y-radius,2*radius,2*radius);
		}
	}
	private static final long serialVersionUID = 2972L;
	private GameEngine engine;
	private HexMap map;
	private Point[] possibleMoves;
	/**
	 * Method incSize - increases the size of the graphical map
	 *
	 *
	 */
	public void incSize() {
		if (map.getSize()<75) map.zoomIn();
	}

	/**
	 * Method decSize - decreases the size of the graphical map
	 *
	 *
	 */
	public void decSize() {
		if (map.getSize()>8) map.zoomOut();
	}
	/**
	 * Method paint - paints the map.
	 *
	 * @param g
	 */
	public void paint(Graphics g) {
		map.paint(g);
	}
	/**
	 * Method getSize
	 * 
	 *
	 * @return   the size of each hexagon in the graphical map.
	 */
	public int getSize() {
		return map.getSize();
	}
	/**
	 * Method getDimensions
	 * 
	 *
	 * @return   the dimensions of the graphical map.
	 */
	public Dimension getDimensions() {
		return map.getDimensions();
	}		
	/**
	 * Method hexAt
	 * 
	 * @param p the point in graphical space 
	 *
	 * @return   the point in game space, or null if p does not rest on a hexagon. I.E. x=collumnNumber, y=rowNumber.
	 */
	public Point hexAt(Point p) {
		Hexagon hex = map.hexAt(p,true);
		if (hex==null) return null;
		else return new Point(hex.x,hex.y);
	}
	/**
	 * Method positionAt
	 * 
	 * @param hex the point in game space 
	 *
	 * @return   the point in graphical space.
	 */
	public Point positionAt(Point hex) {
		return map.hexAt(hex.x,hex.y).getPosition();
	}
	
	/**
	 * Method isEast
	 *
	 *
	 * @param p
	 *
	 * @return the point is on the east face as defined by the specifications.
	 *
	 */
	public boolean isEast(Point p) {
		return engine.isEast(p);
	}

	/**
	 * Method eastFace
	 *
	 *
	 * @return all the points on the east face.
	 *
	 */
	public Point[] eastFace() {
		return engine.eastFace();
	}

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
	public boolean standardConnection(Point p1, Point p2) {
		return engine.standardConnection(p1,p2);
	}

	/**
	 * Method width
	 *
	 *
	 * @return the width of the map
	 *
	 */
	public int width() {
		return engine.width();
	}

	/**
	 * Method height
	 *
	 *
	 * @return the height of the map
	 *
	 */
	public int height() {
		return engine.height();
	}
	
	/**
	 * Method rowVertical
	 *
	 *
	 * @return true if the rows run up and down, false if they run left and right.
	 *
	 */
	public boolean rowVertical() {
		return engine.rowVertical();
	}	
	
	/**
	 * Method isValid
	 *
	 * @param p
	 *
	 * @return whether the given point is on the mapboard.
	 *
	 */
	public boolean isValid(Point p) {
		return engine.isValid(p);
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
		return engine.sides(p);
	}
	
	/**
	 * Method victoryCode
	 *
	 * @param code
	 *
	 * @return the name of the given victory code.
	 *
	 */
	public String victoryCode(int code) {
		return engine.victoryCode(code);
	}
	
	/**
	 * Method victoryCodes
	 *
	 *
	 * @return a list of all victory codes.
	 *
	 */
	public String[] victoryCodes() {
		return engine.victoryCodes();
	}
	
	/**
	 * Method rowLength
	 *
	 * @param index
	 *
	 * @return the length of the given row or collumn, as given by the method rowsVertical.
	 *
	 */
	public int rowLength(int index) {
		return engine.rowLength(index);
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
		releaseMoves();
		engine.move(x1,y1,x2,y2);
		updateHex(x1,y1);
		updateHex(x2,y2);
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
		return engine.getTerrain(x,y);
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
		return engine.getUnit(x,y);
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
		return engine.canMove(x1,y1,x2,y2,player);
	}
	
	/**
	 * Method undoMove
	 *
	 *
	 * @return the action undone, or null if cannot undo
	 *
	 */
	public MoveAction undoMove() {
		releaseMoves();
		MoveAction undone = engine.undoMove();
		if (undone!=null) {
			updateHex(undone.xFrom(),undone.yFrom());
			updateHex(undone.xTo(),undone.yTo());
		}
		return undone;
	}

	/**
	 * Method currentPlayer
	 *
	 *
	 * @return the player whose turn it is (true=king player, false = defending player)
	 *
	 */
	public boolean currentPlayer() {
		return engine.currentPlayer();
	}
	
	/**
	 * Method endTurn - ends the current player's turn
	 *
	 *
	 */
	public void endTurn() {
		releaseMoves();
		engine.endTurn();
	}
	
	/**
	 * Method surrender - have the given player surrender
	 *
	 * @param player
	 */
	public void surrender(boolean player) {
		releaseMoves();
		engine.surrender(player);
	}
	
	/**
	 * Method victoryStatus
	 *
	 *
	 * @return the code for the victory status
	 *
	 */
	public int victoryStatus() {
		return engine.victoryStatus();
	}
	
	/**
	 * Method kingVictory
	 *
	 *
	 * @return -1: defending victory. 0: open. 1: king player victory.
	 *
	 */
	public int kingVictory() {
		return engine.kingVictory();
	}

	/**
	 * Method timeRemaining
	 *
	 *
	 * @return the time remaining before the defending player automaticaly wins
	 *
	 */
	public int timeRemaining() {
		return engine.timeRemaining();
	}	

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
	public boolean saveGame(String file, Prompter user) throws IOException {
		return engine.saveGame(file, user);
	}
	
	/**
	 * Method printGame
	 *
	 *
	 * @return a string representation of this game
	 *
	 */
	public String printGame() {
		return engine.printGame();
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
		engine.setUnit(x,y,unit);
		updateHex(x,y);
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
		if (engine==null) engine = new GameEngine(width,height);
		else engine.setGame(width,height);
		map = new HexMap(width,height,32);
		updateMap();
	}

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
	public boolean saveSetup(String file, Prompter user) throws IOException {
		return engine.saveSetup(file,user);
	}

	/**
	 * Method loadSetup - loads this from the given setup file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadSetup(String file) throws IOException {
		if (engine==null) engine = new GameEngine(file,false);
		else engine.loadSetup(file);
		map = new HexMap(width(),height(),32);
		updateMap();
	}	

	/**
	 * Method loadGame - Loads the game in the given saved game file
	 *
	 *
	 * @param file
	 *
	 */
	public void loadGame(String file) throws IOException {
		if (engine==null) engine = new GameEngine(file,true);
		else engine.loadGame(file);
		map = new HexMap(width(),height(),32);
		updateMap();
	}		

	/**
	 * Method setTime - sets the automactic victory time by the defending player
	 *
	 *
	 * @param time - the amount of time before an automatic victory by the defending player
	 *
	 */
	public void setTime(int time) {
		engine.setTime(time);
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
		engine.setTerrain(x,y,terrain);
		updateHex(x,y);
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
		return engine.listMoves(x1,y1,player);
	}
	/**
	 * Method displayMoves - displays a list of points the player can move from (x1,y1) or does nothing if the player does not have a unit at (x1,y1)
	 *
	 *
	 * @param x1
	 * @param y1
	 * @param player
	 * 
	 *
	 */
	public void displayMoves(int x1, int y1, boolean player) {
		releaseMoves();
		UnitInterface unit = engine.getUnit(x1,y1);
		if (unit!=null) {
			player=unit.side();
			possibleMoves = engine.listMoves(x1,y1,player);
			if (possibleMoves!=null) for (int i = 0; i < possibleMoves.length; i++) {
				Hexagon hex = map.hexAt(possibleMoves[i].x,possibleMoves[i].y);
				hex.addPainter(new XMarker(hex));
			}
		}
		
	}
	/**
	 * Method releaseMoves - releases the moves displayed by displayMoves.
	 *
	 * 
	 */
	public void releaseMoves() {
		if (possibleMoves!=null) for (int i = 0; i < possibleMoves.length; i++) updateHex(possibleMoves[i].x,possibleMoves[i].y);
		possibleMoves=null;
	}
	/**
	 * Method updateHex - updates the graphical hexagon at x,y
	 *
	 * 
	 * @param x
	 * @param y
	 */
	public void updateHex(int x, int y) {
		Hexagon hex = map.hexAt(x,y);
		hex.clear();
		Color background;
		switch (engine.getTerrain(x,y)) {
			case TYPE_EMPTY:
			background=Color.yellow;
			break;
			case TYPE_FOREST:
			background=Color.green;
			break;
			case TYPE_MOUNTAIN:
			background=Color.gray;
			break;
			default:
			throw new RuntimeException("Unexpected Terrain Type. value = "+engine.getTerrain(x,y));
		}
		UnitInterface unit = engine.getUnit(x,y);
		if (unit!=null) {
			String text = ""+unit.speed();
			Color textColor = unit.canMoveRough() ? Color.black : Color.white;
			Color unitColor;
			if (unit.isKing()) unitColor=Color.magenta;
			else if (unit.side()) unitColor=Color.red;
			else unitColor=Color.cyan;
			hex.addPainter(new CircleMarker(hex,unitColor));
			hex.setText(text);
			hex.setTextColor(textColor);
		}
		hex.setBackground(background);
		
	}
	/**
	 * Method updateMap - updates the graphical map
	 *
	 * 
	 */
	public void updateMap() {
		for (int x = 0; x < width(); x++) for (int y = 0; y < rowLength(x); y++) updateHex(x,y);
	}
	/**
	 * Creates a new Graphical Coordinator object.
	 */
	public GraphicalCoordinator() {
	}
}
