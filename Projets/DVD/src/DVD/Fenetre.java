package DVD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements MouseListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<dvd> dvds = new ArrayList<dvd>();
	private static ArrayList<point> points = new ArrayList<point>();
	
	private int w = 25;
	private int v = 5;
	private int t = 255;
	private char Forme = 'r';
	
	private boolean Pause = false;
	
	private Color cFond = Color.black;
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu fichier = new JMenu("fichier");
	private JMenuItem pause = new JMenuItem("pause/restart");
	private JMenuItem reset = new JMenuItem("reset");
	private JMenuItem quitter = new JMenuItem("quitter");
	
	private JMenu trait = new JMenu("trait");
	private JMenu forme = new JMenu("forme");
	private JMenuItem rond = new JMenuItem("rond");
	private JMenuItem carre = new JMenuItem("carré");
	
	private JMenuItem taille = new JMenuItem("taille");
	private JMenuItem vitesse = new JMenuItem("vitesse");
	private JMenuItem transparence = new JMenuItem("transparence");
	
	private JMenu fond = new JMenu("fond");
	private JMenu couleurFond = new JMenu("couleur");
	private JMenuItem fondBlanc = new JMenuItem("blanc");
	private JMenuItem fondNoir = new JMenuItem("noir");
	private JMenuItem fondBleu = new JMenuItem("bleu");
	private JMenuItem fondCyan = new JMenuItem("cyan");
	private JMenuItem fondRouge = new JMenuItem("rouge");
	private JMenuItem fondVert = new JMenuItem("vert");
	private JMenuItem fondPlus = new JMenuItem("plus");
	
	private slider sTaille = new slider(5, 50, w, 5, 10);
	private slider sVitesse = new slider(2, 15, v, 2, 5);
	private slider sTransparence = new slider(0, 255, t, 10, 50);
	
	private selectColor sCFond = new selectColor();
	
	public void afficher() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		addMouseListener(this);
		getContentPane().add(new dessiner());
		
		init_jmb();
		setJMenuBar(jmb);
		setVisible(true);
	}
	
	public void action() {
		while(true) {
			if (!Pause) {
				for (int i = 0; i < dvds.size(); i++) {
					dvd d = (dvd) dvds.get(i);
					d.bouger();
					points.add(new point(d.x, d.y, d.w, d.c, d.forme));
				}
				
				if (sTaille.aff)
					w = sTaille.getValue();
				if (sVitesse.aff)
					v = sVitesse.getValue();
				if (sTransparence.aff)
					t = sTransparence.getValue();
				if (sCFond.aff) {
					sCFond.couleur();
					cFond = sCFond.getColor();
				}
			}
			
			repaint();
			try {
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dvds.add(new dvd(w, e.getX()-w/2, e.getY()-w/2-jmb.getHeight(), v, t, Forme));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pause) Pause = !Pause;
		if (e.getSource() == reset) {
			int option = JOptionPane.showConfirmDialog(null, "vouler vous vraiment reset ?", "",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (option == JOptionPane.OK_OPTION) {
				dvds.removeAll(dvds);
				points.removeAll(points);
			}
		}
		if (e.getSource() == quitter) {
			int option = JOptionPane.showConfirmDialog(null, "vouler vous vraiment quitter ?", "",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (option == JOptionPane.OK_OPTION) System.exit(0);
		}
		
		if (e.getSource() == rond) Forme = 'r';
		if (e.getSource() == carre) Forme = 'c';
		if (e.getSource() == taille) {
			if (!sTaille.aff) {
				getContentPane().remove(sVitesse);
				sVitesse.aff = false;
				getContentPane().remove(sTransparence);
				sTransparence.aff = false;
				getContentPane().remove(sCFond);
				sCFond.aff = false;
				getContentPane().add(sTaille, BorderLayout.SOUTH);
			} else {
				getContentPane().remove(sTaille);
			}
			sTaille.aff = !sTaille.aff;
			setVisible(true);
		}
		if (e.getSource() == vitesse) {
			if (!sVitesse.aff) {
				getContentPane().remove(sTaille);
				sTaille.aff = false;
				getContentPane().remove(sTransparence);
				sTransparence.aff = false;
				getContentPane().remove(sCFond);
				sCFond.aff = false;
				getContentPane().add(sVitesse, BorderLayout.SOUTH);
			} else {
				getContentPane().remove(sVitesse);
			}
			sVitesse.aff = !sVitesse.aff;
			setVisible(true);
		}
		if (e.getSource() == transparence) {
			if (!sTransparence.aff) {
				getContentPane().remove(sTaille);
				sTaille.aff = false;
				getContentPane().remove(sVitesse);
				sVitesse.aff = false;
				getContentPane().remove(sCFond);
				sCFond.aff = false;
				getContentPane().add(sTransparence, BorderLayout.SOUTH);
			} else {
				getContentPane().remove(sTransparence);
			}
			sTransparence.aff = !sTransparence.aff;
			setVisible(true);
		}
		
		if (e.getSource() == fondBlanc) couleur_fond(Color.white);
		if (e.getSource() == fondNoir) couleur_fond(Color.black);
		if (e.getSource() == fondBleu) couleur_fond(Color.blue);
		if (e.getSource() == fondCyan) couleur_fond(Color.cyan);
		if (e.getSource() == fondRouge) couleur_fond(Color.red);
		if (e.getSource() == fondVert) couleur_fond(Color.green);
		if (e.getSource() == fondPlus) {
			if (!sCFond.aff) {
				getContentPane().remove(sTaille);
				sTaille.aff = false;
				getContentPane().remove(sVitesse);
				sVitesse.aff = false;
				getContentPane().remove(sTransparence);
				sTransparence.aff = false;
				getContentPane().add(sCFond, BorderLayout.SOUTH);
			} else {
				getContentPane().remove(sCFond);
			}
			sCFond.aff = !sCFond.aff;
			setVisible(true);
		}
	}
	
	private void couleur_fond(Color c) {
		getContentPane().remove(sCFond);
		sCFond.aff = false;
		setVisible(true);
		cFond = c;
	}
	
	private void init_jmb() {
		jmb.add(fichier);
		jmb.add(trait);
		jmb.add(fond);
		
		fichier.add(pause);
		fichier.add(reset);
		fichier.addSeparator();
		fichier.add(quitter);
		
		pause.addActionListener(this);
		reset.addActionListener(this);
		quitter.addActionListener(this);
		
		trait.add(forme);
		trait.add(taille);
		trait.add(vitesse);
		trait.add(transparence);
		
		forme.add(rond);
		forme.add(carre);
		
		rond.addActionListener(this);
		carre.addActionListener(this);
		
		taille.addActionListener(this);
		vitesse.addActionListener(this);
		transparence.addActionListener(this);
		
		fond.add(couleurFond);
		
		couleurFond.add(fondBlanc);
		couleurFond.add(fondNoir);
		couleurFond.add(fondBleu);
		couleurFond.add(fondCyan);
		couleurFond.add(fondRouge);
		couleurFond.add(fondVert);
		couleurFond.add(fondPlus);
		
		fondBlanc.addActionListener(this);
		fondNoir.addActionListener(this);
		fondBleu.addActionListener(this);
		fondCyan.addActionListener(this);
		fondRouge.addActionListener(this);
		fondVert.addActionListener(this);
		fondPlus.addActionListener(this);
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			g.setColor(cFond);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			for (int i = 0; i < points.size(); i++) {
				points.get(i).draw(g);
			}
		}
	}
}
