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

public class CreatorFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 81256L;
	private JScrollPane pane;
	private CreatorPanel panel;
	private GraphicalCoordinator creator = new GraphicalCoordinator();
	private Unit unitMem = null;
	public CreatorFrame() {
		boolean gotGoodInput = false;
		pane = new CenteredScrollPane();
		setLocation(200,200);
		while (panel==null) {
			int option = JOptionPane.showConfirmDialog(this,"Load from file?","Load?",JOptionPane.YES_NO_OPTION);
			if (option==JOptionPane.YES_OPTION) loadSetup();
			else if (option==JOptionPane.NO_OPTION) newInstance();
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
		JMenu edit = new JMenu("edit");
		JMenuItem nw = new JMenuItem("new");
		JMenuItem save = new JMenuItem("save");
		JMenuItem load = new JMenuItem("load");
		JMenuItem exit = new JMenuItem("exit");
		JMenuItem time = new JMenuItem("time");
		JMenuItem terrain = new JMenuItem("terrain");
		JMenuItem unit = new JMenuItem("unit");
		nw.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		exit.addActionListener(this);
		time.addActionListener(this);
		terrain.addActionListener(this);
		unit.addActionListener(this);
		file.add(nw);
		file.add(save);
		file.add(load);
		file.add(exit);
		edit.add(time);
		edit.add(terrain);
		edit.add(unit);
		bar.add(file);
		bar.add(edit);
		setJMenuBar(bar);
		setTime();
		setLayout(new GridLayout(1,1));
		add(pane);
		pack();
		setPreferredSize(new Dimension(400,400));
		setVisible(true);
	}
	public void loadSetup() {
		try {
			String file = JOptionPane.showInputDialog(this, "Enter the name of the file");
			creator.loadSetup(file);
			panel = new CreatorPanel(creator);
			pane.setViewportView(panel);
			setTime();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(this,"Invalid Input");
		}
	}
	public void newInstance() {
		try {
			int width = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the width of the board"));
			int height = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the height of the board"));
			creator.setGame(width,height);
			panel = new CreatorPanel(creator);
			pane.setViewportView(panel);
			setTime();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(this,"Invalid Input");
		}
	}
	private boolean saveSetup() {
		try {
			String file = JOptionPane.showInputDialog(this, "Enter the name of the file");
			return creator.saveSetup(file, new Prompter() {
				public boolean prompt(String prompt) {
					int option = JOptionPane.showConfirmDialog(CreatorFrame.this, prompt, "Overwrite?", JOptionPane.YES_NO_OPTION);
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
			if (!saveSetup()) break;
			case JOptionPane.NO_OPTION:
			System.exit(0);
		}
	}
	private void setTime() {
		setTitle("Manuver Creator: time = "+creator.timeRemaining());
	}
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("new")) newInstance();
		else if (command.equals("save")) saveSetup();
		else if (command.equals("load")) loadSetup();
		else if (command.equals("exit")) exit();
		else if (command.equals("time")) {
			try {
				creator.setTime(Integer.parseInt(JOptionPane.showInputDialog(this,"Enter the time before automatic defensive victory.")));
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(this,"Invalid Input");
			}
			setTime();
		} else if (command.equals("terrain")) {
			try {
				int terrain = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter the terrain type: 0 = Empty, 1 = Forest, 2 = Mountain"));
				if (terrain<0||terrain>2) throw new RuntimeException();
				panel.setTerrain(terrain);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(this,"Invalid Input");
			}
		} else if (command.equals("unit")) {
			if (JOptionPane.showConfirmDialog(this,"Yes to add units, No to remove units.","Add units?",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				if (unitMem==null) unitMem=new Unit();
				new UnitFrame(unitMem,this);
				panel.setUnit(unitMem);
				setEnabled(false);
			} else panel.setUnit(null);
		}
	}
}
