package create_world;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class Fenetre extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu fichier = new JMenu("fichier");
	private JMenuItem ouvrir = new JMenuItem("ouvrir");
	private JMenuItem save = new JMenuItem("sauvegarder");
	private JMenuItem quitter = new JMenuItem("quitter");
	
	private JMenu affichage = new JMenu("affichage");
	private JMenu theme = new JMenu("thème");
	private JMenuItem bordeaux = new JMenuItem("bordeaux");
	private JMenuItem blanc = new JMenuItem("blanc");
	private JMenuItem noir = new JMenuItem("noir");
	private JMenuItem bleu = new JMenuItem("bleu");
	private JMenuItem cyan = new JMenuItem("cyan");
	
	private JMenu outils = new JMenu("outils");
	private JMenuItem remplir = new JMenuItem("remplir");
	
	private JTabbedPane onglet = new JTabbedPane();
	
	private createMap [] maps = {
			new createMap("map1.txt"),
			new createMap("map2.txt"),
			new createMap("map3.txt"),
			new createMap("map4.txt"),
			new createMap("map5.txt"),
			new createMap("map6.txt"),
			new createMap("map7.txt"),
			new createMap("map8.txt"),
			new createMap("map9.txt"),
	};
	
	public void afficher() {
		setTitle("create world");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init_jmb();
		setJMenuBar(jmb);
		init_onglet();
		getContentPane().add(onglet);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ouvrir) {
			String dossier = JOptionPane.showInputDialog(null, "quelle map veux tu ouvrir ?", "ouverture", JOptionPane.QUESTION_MESSAGE);
			
			if (dossier != null) {
				for (int i = 0; i < maps.length; i++) {
					maps [i].openMap(dossier);
				}
				for (int i = 0; i < maps.length; i++) {
					maps [i].applicateMap();
				}
			}
			
			repaint();
		}
		
		if (e.getSource() == save) {
			String dossier = JOptionPane.showInputDialog(null, "comment veux tu appeler ta map ?", "enregistrement", JOptionPane.QUESTION_MESSAGE);
			if (dossier != null) {
				File folder = new File(dossier + "/");
				
				boolean correct = true;
				
				for (int i = 0; i < maps.length; i++) {
					try {
						maps [i].setMap();
					} catch (NumberFormatException E) {
						correct = false;
						JOptionPane.showMessageDialog(null, "il semblerais que vous avez mis autre chose qu'un chiffre dans une destination de portail", "ERREUR", JOptionPane.ERROR_MESSAGE);
					}
				}
				if (correct) {
					if (!folder.exists()) {
						folder.mkdir();
					}
					
					for (int i = 0; i < maps.length; i++) {
						maps [i].saveMap(dossier);
					}
				}
			}
		}
		
		if (e.getSource() == quitter) {
			int option = JOptionPane.showConfirmDialog(null, "vouler vous vraiment quitter ?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (option == JOptionPane.OK_OPTION) System.exit(0);
		}
		
		if (e.getSource() == bordeaux) setTheme(new Color(153, 0, 0), Color.white);
		if (e.getSource() == blanc) setTheme(new Color(238, 238, 238), Color.black);
		if (e.getSource() == noir) setTheme(Color.darkGray.darker(), Color.white);
		if (e.getSource() == bleu) setTheme(Color.blue, Color.white);
		if (e.getSource() == cyan) setTheme(Color.cyan, Color.black);
		
		if (e.getSource() == remplir) maps [onglet.getSelectedIndex()].remplissage();
	}
	
	private void setTheme(Color cTheme, Color cText) {
		for (int i = 0; i < maps.length; i++) {
			maps [i].setTheme(cTheme);
			maps [i].setTextColor(cText);
		}
	}
	
	private void init_jmb() {
		jmb.add(fichier);
		jmb.add(affichage);
		jmb.add(outils);
		
		fichier.add(ouvrir);
		fichier.add(save);
		fichier.addSeparator();
		fichier.add(quitter);
		
		ouvrir.addActionListener(this);
		save.addActionListener(this);
		quitter.addActionListener(this);
		
		ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		
		affichage.add(theme);
		
		theme.add(bordeaux);
		theme.add(blanc);
		theme.add(noir);
		theme.add(bleu);
		theme.add(cyan);
		
		bordeaux.addActionListener(this);
		blanc.addActionListener(this);
		noir.addActionListener(this);
		bleu.addActionListener(this);
		cyan.addActionListener(this);
		
		outils.add(remplir);
		
		remplir.addActionListener(this);
		
		remplir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
	}
	
	private void init_onglet() {
		for (int i = 0; i < maps.length; i++) {
			onglet.add("map" + (i+1), maps[i]);
		}
	}
}
