import james.tools.test.CommandTester;
//import james.games.manuver.*;
import james.games.graphics.*;
import james.games.manuver.GameFrame;
import james.games.manuver.CreatorFrame;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.awt.Point;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import javax.swing.SwingUtilities;
/**
 * This is a convienience class to test various aspects of the program.
 */
public class Tester {
	
	/**
	 * Method main
	 *
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) throws InvocationTargetException, IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();
			}
		});
		/*HexMap map = new HexMap(7,5,2);
		ArrayList<Object> obj = new ArrayList<Object>();
		ArrayList<Class> clazz = new ArrayList<Class>();
		System.out.println(map.hexAt(0,1).x);
		obj.add(map);
		clazz.add(HexMap.class);
		CommandTester.test(obj,clazz);*/
		
		//Font font = new Font("Lucida Console", Font.PLAIN,8);
		//System.out.println(font);
		
		/*Scanner keyboard = new Scanner(System.in);
		String end;
		do {
			System.out.print("Enter side: ");
			int x = keyboard.nextInt();
			System.out.print("Enter size: ");
			int size = keyboard.nextInt();
			System.out.println("The answer is "+doY(x,size)[0]+" "+doY(x,size)[1]);
			keyboard.nextLine();
			System.out.print("Continue? y/n: ");
			end = keyboard.nextLine();
		} while (!end.equals("n"));*/
		
		/*ArrayList<Object> obj = new ArrayList<Object>();
		ArrayList<Class> clazz = new ArrayList<Class>();
		Prompter prompt = new Prompter() {
			public boolean prompt(String prompt) {
				System.out.println("Prompted: "+prompt);
				return true;
			}
		};
		obj.add(new GameEngine("game1",true));
		clazz.add(GameInterface.class);
		//obj.add(new Unit());
		//clazz.add(Unit.class);
		obj.add(prompt);
		clazz.add(Prompter.class);
		CommandTester.test(obj,clazz);*/
	}
	private static Point doX(int vertex, int size) {
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
	private static Point[] doY(int side, int size) {
		Point[] out = new Point[2];
		for (int i = 0; i < 2; i++) {
			Point next = doX(((side-1+i)%6)+1,size);
			if (side==3&&i==0||side==5&&i==1) out[i] = new Point(next.x,next.y+1);
			else out[i] = next;
		}
		return out;
	}
}
