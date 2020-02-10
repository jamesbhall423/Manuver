package james.games.manuver;

import java.awt.Point;
public class RunPanel extends GamePanel {
	private static final long serialVersionUID = 61725L;
	private Point last;
	private Point lastG;
	public RunPanel(GraphicalCoordinator mp) {
		super(mp);
	}
	public void doClick(Point position,Point hex,int button) {
		if (button==1) {
			map.displayMoves(hex.x,hex.y,map.currentPlayer());
			last=hex;
			lastG=position;
		} else {
			if (last!=null&&map.canMove(last.x,last.y,hex.x,hex.y,map.currentPlayer())) map.move(last.x,last.y,hex.x,hex.y);
			else map.releaseMoves();
			last=null;
		}
		repaint();
	}
	public void undo() {
		MoveAction action = map.undoMove();
		if (action!=null) {
			repaint();
		}
	}
}
