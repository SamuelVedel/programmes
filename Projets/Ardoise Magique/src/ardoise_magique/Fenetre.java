package ardoise_magique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

public class Fenetre extends JFrame implements MouseMotionListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean mousePressed = false;
	private int pmouseX, pmouseY;
	
	private int size = 5;
	private Color c = Color.black;
	private Color fondC = Color.white;
	
	private ArrayList<point> points = new ArrayList<point>();
	private int nb_points[] = new int[50];
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu fichier = new JMenu("fichier");
	private JMenuItem retour = new JMenuItem("retour");
	private JMenuItem effacer = new JMenuItem("effacer");
	private JMenuItem quitter = new JMenuItem("quitter");
	
	private JMenu trait = new JMenu("trait");
	private JMenu couleur = new JMenu("couleur");
	private JMenuItem blanc = new JMenuItem("blanc");
	private JMenuItem noir = new JMenuItem("noir");
	private JMenuItem bleu = new JMenuItem("bleu");
	private JMenuItem cyan = new JMenuItem("cyan");
	private JMenuItem rouge = new JMenuItem("rouge");
	private JMenuItem vert = new JMenuItem("vert");
	private JMenuItem plus = new JMenuItem("plus");
	
	private JMenuItem taille = new JMenuItem("taille");
	
	private JMenu fond = new JMenu("fond");
	private JMenu couleurFond = new JMenu("couleur");
	private JMenuItem fondBlanc = new JMenuItem("blanc");
	private JMenuItem fondNoir = new JMenuItem("noir");
	private JMenuItem fondBleu = new JMenuItem("bleu");
	private JMenuItem fondCyan = new JMenuItem("cyan");
	private JMenuItem fondRouge = new JMenuItem("rouge");
	private JMenuItem fondVert = new JMenuItem("vert");
	private JMenuItem fondPlus = new JMenuItem("plus");
	
	private JPanel jp = new JPanel();
	private JSlider js = new JSlider();
	private boolean aff_jp = false;
	
	private selectColor cTrait = new selectColor();
	private selectColor cFond = new selectColor();
	
	public void afficher() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new dessiner(this), BorderLayout.CENTER);
		
		init_JMenuBar();
		setJMenuBar(jmb);
		setVisible(true);
		
		init_js();
	}
	
	public void action() {
		System.out.println(jmb.getHeight());
		
		for (int i = 1; i < nb_points.length; i++) {
			nb_points[i] = -1;
		}
		
		while(true) {
			if (cTrait.aff) cTrait.couleur();
			if (cFond.aff) cFond.couleur();
			repaint();
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException E) {
				E.printStackTrace();
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!mousePressed) {
			change_nb_points();
			mousePressed = true;
		}
		
		size = js.getValue();
		
		if (pmouseX == 0 && pmouseY == 0) {
			pmouseX = e.getX();
			pmouseY = e.getY();
		}
		points.add(new point(e.getX(), e.getY(), pmouseX, pmouseY, size, c));
		pmouseX = e.getX();
		pmouseY = e.getY();
		repaint();
		try {
			Thread.sleep(1000/60);
		} catch (InterruptedException E) {
			E.printStackTrace();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePressed = false;
		pmouseX = 0;
		pmouseY = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == retour) {
			fleche_betise();
		}
		
		if (e.getSource() == effacer) {
			int option = JOptionPane.showConfirmDialog(null,
			"vouler vous vraiment effacer ?", "",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (option == JOptionPane.OK_OPTION) {
				points.removeAll(points);
				repaint();
				
				for (int i = 1; i < nb_points.length; i++) {
					nb_points[i] = -1;
				}
			}
		}
		if (e.getSource() == quitter) {
			int option = JOptionPane.showConfirmDialog(null,
			"vouler vous vraiment quitter ?", "",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (option == JOptionPane.OK_OPTION) System.exit(0);
		}
		
		if (e.getSource() == blanc) couleur_trait(Color.white);
		if (e.getSource() == noir) couleur_trait(Color.black);
		if (e.getSource() == bleu) couleur_trait(Color.blue);
		if (e.getSource() == cyan) couleur_trait(Color.cyan);
		if (e.getSource() == rouge) couleur_trait(Color.red);
		if (e.getSource() == vert) couleur_trait(Color.green);
		if (e.getSource() == plus) {
			if (!cTrait.aff) {
				getContentPane().remove(jp);
				aff_jp = false;
				getContentPane().remove(cFond);
				cFond.aff = false;
				getContentPane().add(cTrait, BorderLayout.SOUTH);
			} else getContentPane().remove(cTrait);
			
			setVisible(true);
			repaint();
			cTrait.aff = !cTrait.aff;
		}
		
		if (e.getSource() == taille) {
			if (!aff_jp)  {
				getContentPane().remove(cTrait);
				cTrait.aff = false;
				getContentPane().remove(cFond);
				cFond.aff = false;
				getContentPane().add(jp, BorderLayout.SOUTH);
			} else getContentPane().remove(jp);
			
			setVisible(true);
			aff_jp = !aff_jp;
		}
		
		if (e.getSource() == fondBlanc) couleur_fond(Color.white);
		if (e.getSource() == fondNoir) couleur_fond(Color.black);
		if (e.getSource() == fondBleu) couleur_fond(Color.blue);
		if (e.getSource() == fondCyan) couleur_fond(Color.cyan);
		if (e.getSource() == fondRouge) couleur_fond(Color.red);
		if (e.getSource() == fondVert) couleur_fond(Color.green);
		if (e.getSource() == fondPlus) {
			if (!cFond.aff) {
				getContentPane().remove(jp);
				aff_jp = false;
				getContentPane().remove(cTrait);
				cTrait.aff = false;
				getContentPane().add(cFond, BorderLayout.SOUTH);
			} else getContentPane().remove(cFond);
			
			setVisible(true);
			cFond.aff = !cFond.aff;
		}
	}
	
	void couleur_trait(Color C) {
		c = C;
		cTrait.aff = false;
		getContentPane().remove(cTrait);
		setVisible(true);
	}
	
	void couleur_fond(Color C) {
		fondC = C;
		cFond.aff = false;
		getContentPane().remove(cFond);
		setVisible(true);
	}
	
	private void init_JMenuBar() {
		jmb.add(fichier);
		jmb.add(trait);
		jmb.add(fond);
		
		fichier.add(retour);
		fichier.add(effacer);
		fichier.addSeparator();
		fichier.add(quitter);
		
		retour.addActionListener(this);
		effacer.addActionListener(this);
		quitter.addActionListener(this);
		
		retour.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		
		trait.add(couleur);
		trait.add(taille);
		
		couleur.add(blanc);
		couleur.add(noir);
		couleur.add(bleu);
		couleur.add(cyan);
		couleur.add(rouge);
		couleur.add(vert);
		couleur.add(plus);
		
		blanc.addActionListener(this);
		noir.addActionListener(this);
		bleu.addActionListener(this);
		cyan.addActionListener(this);
		rouge.addActionListener(this);
		vert.addActionListener(this);
		plus.addActionListener(this);
		
		blanc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
		noir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		bleu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
		cyan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		rouge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		vert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		
		taille.addActionListener(this);
		
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
	
	private void init_js() {
		js.setMinimum(0);
		js.setMaximum(20);
		js.setValue(size);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setMinorTickSpacing(5);
		js.setMajorTickSpacing(10);
		jp.add(js);
	}
	
	private void change_nb_points() {
		if (nb_points[nb_points.length-1] == -1) {
			for (int i = nb_points.length-1; nb_points[i] == -1; i--) {
				if ((nb_points[i-1] != -1) && points.size() != 0) {
					nb_points[i] = points.size();
				}
			}
		} else {
			for (int i = 0; i < nb_points.length-1; i++) {
				nb_points[i] = nb_points[i+1];
			}
			nb_points[nb_points.length-1] = points.size();
		}
	}
	
	private void fleche_betise() {
		if (nb_points[nb_points.length-1] == -1) {
			int supr = 0;
			for (int I = nb_points.length-2; nb_points[I+1] == -1; I--) {
				if (nb_points[I] != -1) {
					
					for (int i = points.size()-1; i > nb_points[I]-1; i--) {
						points.remove(i);
					}
					
					supr = I;
				}
			}
			if (supr != 0) nb_points[supr] = -1;
			
		} else {
			for (int i = points.size()-1; i > nb_points[nb_points.length-1]; i--) {
				points.remove(i);
			}
			nb_points[nb_points.length-1] = -1;
		}
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		public dessiner(MouseMotionListener mml) {
			addMouseMotionListener(mml);
		}
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			if (cTrait.aff) c = cTrait.c;
			if (cFond.aff) fondC = cFond.c;
			
			g.setColor(fondC);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			for (int i = 0; i < points.size(); i++) {
				points.get(i).draw(g2d);
			}
		}
	}
}
