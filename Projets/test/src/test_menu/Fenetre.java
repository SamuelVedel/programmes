package test_menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Fenetre extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu test1 = new JMenu("fichier");
	private JMenu sousfichier1 = new JMenu("sous fichier");
	private JMenuItem jmi1 = new JMenuItem("exit");
	private JMenuItem jmi2 = new JMenuItem("truc");
	
	public void afficher () {
		
		test1.setMnemonic('A');
		jmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		
		jmi1.addActionListener(this);
		sousfichier1.add(jmi2);
		test1.add(sousfichier1);
		test1.add(jmi1);
		menuBar.add(test1);
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jmi1) System.exit(0);
		
	}
}
