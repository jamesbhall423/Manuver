package james.games.manuver;

import james.games.graphics.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GameFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 81256L;
	private JScrollPane pane;
	private RunPanel panel;
	private GraphicalCoordinator game = new GraphicalCoordinator();
	public GameFrame() {
		boolean gotGoodInput = false;
		pane = new CenteredScrollPane();
		setLocation(200,200);
		while (panel==null) {
			int option = JOptionPane.showConfirmDialog(this,"Yes to load from a saved game, No to load a new game.","Saved Game?",JOptionPane.YES_NO_OPTION);
			if (option==JOptionPane.YES_OPTION||option==JOptionPane.NO_OPTION) loadGame(option==JOptionPane.NO_OPTION);
		}
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("file");
		JMenu game = new JMenu("game");
		JMenuItem nw = new JMenuItem("new");
		JMenuItem save = new JMenuItem("save");
		JMenuItem load = new JMenuItem("load");
		JMenuItem exit = new JMenuItem("exit");
		JMenuItem undo = new JMenuItem("undo");
		JMenuItem endTurn = new JMenuItem("endTurn");
		JMenuItem surrender = new JMenuItem("surrender");
		nw.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		exit.addActionListener(this);
		undo.addActionListener(this);
		endTurn.addActionListener(this);
		surrender.addActionListener(this);
		file.add(nw);
		file.add(save);
		file.add(load);
		file.add(exit);
		game.add(undo);
		game.add(endTurn);
		game.add(surrender);
		bar.add(file);
		bar.add(game);
		setJMenuBar(bar);
		setLayout(new GridLayout(1,1));
		add(pane);
		pack();
		setPreferredSize(new Dimension(400,400));
		setVisible(true);
	}
	public void loadGame(boolean setup) {
		try {
			String file = JOptionPane.showInputDialog(this, "Enter the name of the file");
			if (setup) game.loadSetup(file);
			else game.loadGame(file);
			panel = new RunPanel(game);
			pane.setViewportView(panel);
			setTime();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(this,"Invalid Input");
		}
	}
	private boolean saveGame() {
		try {
			String file = JOptionPane.showInputDialog(this, "Enter the name of the file");
			return game.saveGame(file, new Prompter() {
				public boolean prompt(String prompt) {
					int option = JOptionPane.showConfirmDialog(GameFrame.this, prompt, "Overwrite?", JOptionPane.YES_NO_OPTION);
					return (option==JOptionPane.YES_OPTION);
				}
			});
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(this,"Invalid Input");
		}
		return false;
	}
	private void exit() {
		int option = JOptionPane.showConfirmDialog(this,"Save before exiting?","Save?",JOptionPane.YES_NO_CANCEL_OPTION);
		switch (option) {
			case JOptionPane.YES_OPTION:
			if (!saveGame()) break;
			case JOptionPane.NO_OPTION:
			System.exit(0);
		}
	}
	private void setTime() {
		if (game.kingVictory()==0)setTitle("Manuver "+(game.currentPlayer() ? "King" : "Defender")+": time left = "+game.timeRemaining());
		else setTitle("Manuver "+game.victoryCode(game.victoryStatus())+" at "+(game.currentPlayer() ? "Kg" : "Df")+": tl = "+game.timeRemaining());
	}
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("new")) loadGame(true);
		else if (command.equals("save")) saveGame();
		else if (command.equals("load")) loadGame(false);
		else if (command.equals("exit")) exit();
		else if (command.equals("undo")) panel.undo();
		else if (command.equals("endTurn")) {
			game.endTurn();
			setTime();
			repaint();
		} else if (command.equals("surrender")) {
			game.surrender(game.currentPlayer());
			setTime();
			repaint();
		}
	}
}
