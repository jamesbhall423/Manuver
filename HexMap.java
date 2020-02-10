package james.games.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
/**
 *  This class represents a tiled sheet of hexagons. These tiles run up and down, starting at height length and alternating between height length and height+1 length.
 */
public class HexMap implements Serializable {
	private static final long serialVersionUID = 1631L;
	private Hexagon[][] map;
	private int size;
	private Color outside = Color.gray;
	/**
	 * Method HexMap
	 *
	 *
	 * @param width - the number collumns of hexes
	 * @param height - the length of the leftmost collumn
	 * @param initialSize - the size of each hexagon
	 *
	 */
	public HexMap(int width, int height, int initialSize) {
		map = new Hexagon[width][height];
		size = initialSize;
		for (int i = 1; i < width; i+=2) map[i] = new Hexagon[height+1];
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) {
			map[x][y] = new Hexagon(x,y);
			map[x][y].setBackground(Color.white);
			map[x][y].setBorder(Color.black);
			map[x][y].setSize(initialSize);
			map[x][y].setPosition(positionAt(x,y));
		}
		
	}
	/**
	 * Method postionAt
	 *
	 *
	 * @param x - the collumn number of the hexagon
	 * @param y - the row number of the hexagon
	 *
	 * @return the position (upper-left-corner) of the hexagon at (x,y)
	 *
	 */
	public Point positionAt(int x, int y) {
		int xout = x*((3*size+2)/4);
		int yout = y*size+(((x+1)%2)*size)/2;
		return new Point(xout,yout);
	}

	/**
	 * Method hexAt
	 *
	 *
	 * @param x - the collumn number of the hexagon
	 * @param y - the row number of the hexagon
	 *
	 * @return the hexagon at (x,y)
	 *
	 */
	public Hexagon hexAt(int x, int y) {
		return map[x][y];
	}

	/**
	 * Method hexAt
	 *
	 *
	 * @param p
	 * @param includeEdge - if true, edges will be considered part of the hexagon.
	 *
	 * @return the hexagon that contains p, or null if no hexagon contains p.
	 *
	 */
	public Hexagon hexAt(Point p, boolean includeEdge) {
		int container = 1;
		if (includeEdge) container = 0;
		int[] hexNum = hexNum(p);
		Point base = positionAt(hexNum[0],hexNum[1]);
		Point relative = sub(p,base);
		if (hexNum[0]<0||hexNum[1]<0) return null;
		if (hexNum[0]<map.length&&hexNum[1]<map[hexNum[0]].length) if (map[hexNum[0]][hexNum[1]].contains(relative)>=container) return map[hexNum[0]][hexNum[1]];
		if (hexNum[0]-1<0||hexNum[0]-1>=map.length) return null;
		int newY = hexNum[1];
		if (relative.y>size/2) {
			if (hexNum[0]%2==0) newY++;
		} else if (hexNum[0]%2==1) newY--;
		if (newY<0||newY>=map[hexNum[0]-1].length) return null;
		base = positionAt(hexNum[0]-1,newY);
		relative = sub(p,base);
		if (map[hexNum[0]-1][newY].contains(relative)>=container) return map[hexNum[0]-1][newY];
		else return null;
	}
	private int[] hexNum(Point p) {
		int xdiv = (3*size+2)/4;
		int x = p.x/xdiv;
		
		int ynum = p.y-((x+1)%2)*(size/2);
		int y = ynum / size;
		int[] out = {x,y};
		return out;
	}
	private Point sub(Point real, Point base) {
		return new Point(real.x-base.x,real.y-base.y);
	}

	/**
	 * Method mapAt
	 *
	 *
	 * @param p
	 * @param width
	 * @param height
	 * @param initialSize - the size of each hexagon
	 *
	 * @return a HexMap cornered at p with the given number of rows and collumns and the given initial size. This is not guaranteed to be done precisely.
	 *
	 */
	public HexMap mapAt(Point p, int width, int height, int initialSize) {
		int[] corner = hexNum(p);
		if (corner[0]%2==1) corner[0]--;
		if (corner[0]+width>map.length) width = map.length-corner[0];
		if (corner[1]+height>map[0].length) height = map[0].length-corner[1];
		HexMap out = new HexMap(width,height,initialSize);
		for (int x = 0; x < out.map.length; x++) for (int y = 0; y < out.map[x].length; y++) out.map[x][y].loadValues(map[x+corner[0]][y+corner[1]]);
		return out;
	}	
	
	/**
	 * Method paint
	 *
	 *
	 * @param g
	 *
	 */
	public void paint(Graphics g) {
		Rectangle boundries = g.getClipBounds();
		g.setColor(outside);
		g.fillRect(boundries.x,boundries.y,boundries.width,boundries.height);
		int x1 = boundries.x;
		int y1 = boundries.y;
		int x2 = x1+boundries.width;
		int y2 = y1+boundries.height;
		int[] start = hexNum(new Point(x1-size,y1-size));
		if (start[0]%2==1) start[1]--;
		int[] end = hexNum(new Point(x2,y2));
		if (start[0]<0) start[0]=0;
		if (start[1]<0) start[1]=0;
		if (end[0]%2==0) end[1]++;
		if (end[0]>=map.length) end[0] = map.length-1;
		if (end[1]>map[0].length) end[1] = map[0].length;
		for (int x = start[0]; x <= end[0]; x++) for (int y = start[1]; y <= end[1]; y++) if (isValid(x,y)) {
			Point pos = map[x][y].getPosition();
			map[x][y].paint(g.create(pos.x,pos.y,size,size));
		}
	}
	
	/**
	 * Method setSize
	 *
	 *
	 * @param size - the size of each hexagon
	 *
	 *
	 */
	public void setSize(int size) {
		this.size=size;
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) map[x][y].setSize(size);
	}
	/**
	 * Method getSize
	 *
	 *
	 * @return size the size of each hexagon
	 *
	 */
	public int getSize() {
		return size;
	}
	/**
	 * Method setOutsideColor - sets the color for the area in which there are no hexagons.
	 *
	 *
	 * @param color
	 *
	 *
	 */
	public void setOutsideColor(Color color) {
		outside=color;
	}
	/**
	 * Method getOutsideColor
	 *
	 *
	 * @return the color for the area in which there are no hexagons.
	 *
	 */
	public Color getOutsideColor() {
		return outside;
	}
	/**
	 * Method getDimensions
	 *
	 *
	 * @return the dimensions of the map.
	 *
	 */
	public Dimension getDimensions() {
		return new Dimension(map.length*((3*size+2)/4)+size/4,(map[0].length+1)*size);
	}
	/**
	 * Method isValid
	 *
	 * @param x - the collumn number of the hexagon
	 * @param y - the row number of the hexagon
	 *
	 * @return whether the hexagon at (x,y) is on the map.
	 *
	 */
	public boolean isValid(int x, int y) {
		return x>=0&&y>=0&&x<map.length&&y<map[x].length;
	}
	/**
	 * Method zoomIn - increases the size of the map
	 *
	 *
	 * @return whether the operation was successful.
	 *
	 */
	public boolean zoomIn() {
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) if (!map[x][y].zoomIn()) return false;
		size = map[0][0].getSize();
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) map[x][y].setPosition(positionAt(x,y));
		return true;
	}

	/**
	 * Method zoomOut - decreases the size of the map
	 *
	 *
	 * @return whether the operation was successful.
	 *
	 */
	public boolean zoomOut() {
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) if (!map[x][y].zoomOut()) return false;
		size = map[0][0].getSize();
		for (int x = 0; x < map.length; x++) for (int y = 0; y < map[x].length; y++) map[x][y].setPosition(positionAt(x,y));
		return true;
	}
}
