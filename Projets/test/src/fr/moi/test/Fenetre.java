package fr.moi.test;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public void afficher() {
		setTitle("truc");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setContentPane(new Panneau());
	}
}
