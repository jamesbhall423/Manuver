package james.games.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  This class represents a hexagon.
 * Except for the position methods, the hexagon is considered to be contained in a square whose upper-left point is located at (0,0)
 */
public class Hexagon implements Cloneable, Serializable {
	private static final long serialVersionUID = 152L;
	private ArrayList<Painter> painters = new ArrayList<Painter>();
	private Color background;
	private Color border;
	private Color textColor;
	private String text;
	private Point position;
	private int size;
	public final int x;
	public final int y;
	private int sizeType;
	private static final int[] sizes = {2,4,8,12,20,32,52,84,136}; //The size value determines the width and height of the hexagon. (1/2 value = top and bottom sides, 1/4 = width of side triangles (0 for size 2x2), 3/4 = distance between hex collums (all for size 2x2))
	/**
	 * Method contains
	 *
	 *
	 * @param p
	 *
	 * @return 1 if inside, 0 if edge, -1 if outside
	 *
	 */
	public int contains(Point p) {
		if (p.x<0||p.y<0) return -1;
		if (p.x>=size||p.y>=size) return -1;
		if (size==2) return 0;
		if (p.x>=size/4&&p.x<3*size/4) {
			if (p.y==0||p.y==size-1) return 0;
			else return 1;
		}
		int xval;
		int yval;
		if (p.x<size/2) xval=p.x;
		else xval=size-p.x-1;
		if (p.y<size/2) yval=p.y;
		else yval=(size-p.y-1);
		int sum = 2*xval+yval;
		int half = size/2;
		if (sum<half-1) return -1;
		else if (sum>half) return 1;
		else return 0;
	}

	/**
	 * Method verticyAt
	 *
	 *
	 * @param vertex: 1 (upper-right) through 6 (upper-left)
	 *
	 * @return
	 *
	 */
	public Point verticyAt(int vertex) {
		switch (vertex) {
			case 1:
			return new Point(size/4,0);
			case 2:
			return new Point(3*size/4-1,0);
			case 3:
			return new Point(size-1,size/2-1);
			case 4:
			return new Point(3*size/4-1,size-1);
			case 5:
			return new Point(size/4,size-1);
			case 6:
			return new Point(0,size/2-1);
			default:
			throw new IllegalArgumentException("vertex must fall between 1 and 6 inclusive. For value: "+vertex);
		}
	}

	/**
	 * Method line
	 *
	 *
	 * @param side (1-6) (top through left-top)
	 *
	 * @return a pair of points that represent a line representing the given side.
	 *
	 */
	public Point[] line(int side) {
		Point[] out = new Point[2];
		for (int i = 0; i < 2; i++) {
			Point next = verticyAt(((side-1+i)%6)+1);
			if (side==3&&i==0||side==5&&i==1) out[i] = new Point(next.x,next.y+1);
			else out[i] = next;
		}
		return out;
	}

	/**
	 * Method addPainter - allows the given painter to paint an image on this hexagon.
	 *
	 *
	 * @param painter
	 *
	 */
	public void addPainter(Painter painter) {
		painters.add(painter);
	}

	/**
	 * Method getSize
	 *
	 *
	 * @return
	 *
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Method supportedSizes
	 *
	 *
	 * @return
	 *
	 */
	public static int[] supportedSizes() {
		return sizes.clone();
	}

	/**
	 * Method zoomIn - enlarges the hexagon by one factor
	 *
	 *
	 * @return whether the operation was successful.
	 *
	 */
	public boolean zoomIn() {
		if (sizeType>=sizes.length-1) return false;
		size=sizes[++sizeType];
		return true;
	}

	/**
	 * Method zoomOut - shrinks the hexagon by one factor
	 *
	 *
	 * @return whether the operation was successful.
	 *
	 */
	public boolean zoomOut() {
		if (sizeType==0) return false;
		size=sizes[--sizeType];
		return true;
	}

	/**
	 * Method clear - removes all painters from this hexagon.
	 *
	 *
	 */
	public void clear() {
		painters.clear();
		text=null;
	}

	/**
	 * Method setSizeType - sets the size type, as determined by getSupportedSizes()
	 *
	 *
	 * @param sizeType
	 *
	 */
	public void setSizeType(int sizeType) {
		size = sizes[sizeType];
		this.sizeType=sizeType;
	}

	/**
	 * Method setSize - sets the size of this hexagon. Unsupported sizes will result in exceptions.
	 *
	 *
	 * @param size
	 *
	 */
	public void setSize(int size) {
		int index = Arrays.binarySearch(sizes,size);
		if (index<0) throw new IllegalArgumentException("Invalid value for size. val = "+size);
		sizeType=index;
		this.size=size;
	}

	/**
	 * Method midPoint
	 *
	 *
	 * @param side (1-6)
	 *
	 * @return the midpoint of the given side, starting with the top and working clockwise.
	 *
	 */
	public Point midPoint(int side) {
		Point[] line = line(side);
		return new Point((line[0].x+line[1].x)/2,(line[0].y+line[1].y+1)/2);
	}

	/**
	 * Method center
	 *
	 *
	 * @return the center of this hexagon.
	 *
	 */
	public Point center() {
		return new Point(size/2,size/2);
	}

	/**
	 * Method setText - Sets the text charachter for this hexagon
	 *
	 *
	 * @param text
	 *
	 */
	public void setText(String text) {
		this.text = text;
		if (text!=null&&text.length()>1) throw new IllegalArgumentException("Text must be a string of length 1. For value = \""+text+"\"");
	}
	/**
	 * Method setTextColor
	 *
	 *
	 * @param textColor
	 *
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}


	/**
	 * Method getText
	 *
	 *
	 * @return
	 *
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Method getTextColor
	 *
	 *
	 * @return
	 *
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Method getBackground
	 *
	 *
	 * @return
	 *
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Method getBorder
	 *
	 *
	 * @return the Color that is used for the border.
	 *
	 */
	public Color getBorder() {
		return border;
	}

	/**
	 * Method setBackground
	 *
	 *
	 * @param background
	 *
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * Method setBorder - sets the Color that is used for the border.
	 *
	 *
	 * @param border
	 *
	 */
	public void setBorder(Color border) {
		this.border = border;
	}


	/**
	 * Method setPosition - Sets the position within the hexmap. Users should not call this method directly.
	 *
	 *
	 * @param position
	 *
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Method paint
	 *
	 *
	 * @param g
	 *
	 */
	public void paint(Graphics g) {
		Polygon shape;
		do {
			Point[] line1 = line(1);
			Point[] line2 = line(2);
			Point[] line3 = line(3);
			Point[] line4 = line(4);
			Point[] line5 = line(5);
			Point[] line6 = line(6);
			Point[] points = new Point[8];
			points[0] = line1[1];
			points[1] = line2[1];
			points[2] = line3[0];
			points[3] = line3[1];
			points[4] = line4[1];
			points[5] = line5[1];
			points[6] = line6[0];
			points[7] = line6[1];
			shape = new Polygon();
			for (int i = 0; i < 8; i++) shape.addPoint(points[i].x,points[i].y);
		} while (false);
		g.setColor(background);
		g.fillPolygon(shape);
		if (size>=8) g.setColor(border);
		g.drawPolygon(shape);
		for (int i = 0; i < painters.size(); i++) painters.get(i).paint(g);
		if (text!=null&&size>=12) {
			g.setColor(textColor);
			g.setFont(new Font("Lucida Console", Font.PLAIN,2*size/3));
			Point vertex1 = verticyAt(5);
			Point vertex2 = center();
			int xval = (2*vertex1.x+vertex2.x)/3;
			int yval = (vertex1.y+vertex2.y)/2;
			Point upperLeft = new Point(xval,yval);
			g.drawString(text,upperLeft.x,upperLeft.y);
		}
	}

	/**
	 * Method getPosition
	 *
	 *
	 * @return the position in the hexmap.
	 *
	 */
	public Point getPosition() {
		return position;
	}
	/**
	 * Method toString
	 *
	 *
	 * @return
	 *
	 */
	public String toString() {
		return "Hexagon at ("+x+","+y+").";
	}
	/**
	 * Constructor Hexagon 
	 *
	 * @param x - the collum number of this hexagon
	 * @param y - the row number of this hexagon
	 */
	public Hexagon(int x, int y) {
		this.x=x;
		this.y=y;
	}
	/**
	 * Method clone
	 *
	 *
	 * @return a copy of this object.
	 *
	 */
	public Object clone() {
		try {
			Hexagon out = (Hexagon) (super.clone());
			out.painters = (ArrayList<Painter>) painters.clone();
			return out;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
	/**
	 * Method loadValues - copies all non-spacial values from toCopy
	 *
	 * @param toCopy
	 *
	 */
	public void loadValues(Hexagon toCopy) {
		painters = (ArrayList<Painter>) toCopy.painters.clone();
		background = toCopy.background;
		border = toCopy.border;
		textColor = toCopy.textColor;
		text = toCopy.text;
	}
}
