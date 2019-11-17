package fr.SamuelVedel.play_world_cheat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class jeux extends JFrame implements KeyListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private int t;
	private int tDegat;
	private int textW;
	private String phase = "game";
	
	private final int cubeW = 50;
	private final int nWCase = 10;
	private final int grilleW = cubeW*nWCase;
	private final int grilleX = 0;
	private final int grilleY = 0;
	
	private int map1 [] [] = new int [19] [10];
	private int map2 [] [] = new int [19] [10];
	private int map3 [] [] = new int [19] [10];
	private int map4 [] [] = new int [19] [10];
	private int map5 [] [] = new int [19] [10];
	private int map6 [] [] = new int [19] [10];
	private int map7 [] [] = new int [19] [10];
	private int map8 [] [] = new int [19] [10];
	private int map9 [] [] = new int [19] [10];
	
	private int sciW = 30;
	private int sciV = 4;
	
	private boolean test;
	
	private final int init_vie = 5;
	private int vie = init_vie;
	private boolean degat = false;
	private boolean invincible = false;
	private final int moiH = 40;
	private final int moiW = 34*moiH/45;
	private int moiX;
	private int moiY;
	private int init_moiV = 5;
	private int moiV = init_moiV;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	private boolean up = false;
	
	private int init_gV = 2;
	private int gV = init_gV;
	private boolean g = false;
	private boolean gright = false;
	private boolean gdown = false;
	private boolean gleft = false;
	private boolean gup = false;
	
	private int init_sV = 2;
	private int init_sgV = 1;
	
	private int inter_degat = 30;
	private boolean toucher_lave = false;
	
	private boolean toucher_portail = false;
	
	private ArrayList<cube> cubes = new ArrayList<cube>();
	private ArrayList<accCube> accCubes = new ArrayList<accCube>();
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu jmCheat = new JMenu("cheat");
	private JMenu teleportation = new JMenu("téléportation");
	private JMenuItem jmiMap1 = new JMenuItem("map1");
	private JMenuItem jmiMap2 = new JMenuItem("map2");
	private JMenuItem jmiMap3 = new JMenuItem("map3");
	private JMenuItem jmiMap4 = new JMenuItem("map4");
	private JMenuItem jmiMap5 = new JMenuItem("map5");
	private JMenuItem jmiMap6 = new JMenuItem("map6");
	private JMenuItem jmiMap7 = new JMenuItem("map7");
	private JMenuItem jmiMap8 = new JMenuItem("map8");
	private JMenuItem jmiMap9 = new JMenuItem("map9");
	
	private JMenuItem jmiVie = new JMenuItem("modifier la vie");
	private JMenuItem jmiInvincible = new JMenuItem("invincibilité");
	
	private JMenu vitesse = new JMenu("vitesse");
	private JMenuItem sur_terre = new JMenuItem("sur la terre");
	private JMenuItem sur_sable = new JMenuItem("sur le sable");
	private JMenuItem sur_glace = new JMenuItem("sur la glace");
	private JMenuItem sur_sableGlace = new JMenuItem("sur le sable et la glace");
	
	public int [] [] get_map(String dossier, String file) {
		int map [] [] = new int [19] [10];
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dossier + "/" + file), "UTF-8"));
			String line = reader.readLine();
			int lineR = 0;
			
			while (line != null) {
				String s [] = line.split(" ");
				
				int i = 0;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR] [i] = Integer.parseInt(s [i]);
				
				line = reader.readLine();
				lineR++;
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void init_map() {
		String dossier = JOptionPane.showInputDialog(null, "Quelle map voulez vous générer", "Génétateur de map", JOptionPane.QUESTION_MESSAGE);
		if (dossier == null) {
			System.exit(0);
		}
		
		map1 = get_map(dossier, "map1.txt");
		map2 = get_map(dossier, "map2.txt");
		map3 = get_map(dossier, "map3.txt");
		map4 = get_map(dossier, "map4.txt");
		map5 = get_map(dossier, "map5.txt");
		map6 = get_map(dossier, "map6.txt");
		map7 = get_map(dossier, "map7.txt");
		map8 = get_map(dossier, "map8.txt");
		map9 = get_map(dossier, "map9.txt");
	}
	
	public void init_jmb() {
		jmb.add(jmCheat);
		jmCheat.add(teleportation);
		jmCheat.add(jmiVie);
		jmCheat.add(jmiInvincible);
		jmCheat.add(vitesse);
		
		teleportation.add(jmiMap1);
		teleportation.add(jmiMap2);
		teleportation.add(jmiMap3);
		teleportation.add(jmiMap4);
		teleportation.add(jmiMap5);
		teleportation.add(jmiMap6);
		teleportation.add(jmiMap7);
		teleportation.add(jmiMap8);
		teleportation.add(jmiMap9);
		
		jmiMap1.addActionListener(this);
		jmiMap2.addActionListener(this);
		jmiMap3.addActionListener(this);
		jmiMap4.addActionListener(this);
		jmiMap5.addActionListener(this);
		jmiMap6.addActionListener(this);
		jmiMap7.addActionListener(this);
		jmiMap8.addActionListener(this);
		jmiMap9.addActionListener(this);
		
		jmiVie.addActionListener(this);
		jmiInvincible.addActionListener(this);
		
		vitesse.add(sur_terre);
		vitesse.add(sur_sable);
		vitesse.add(sur_glace);
		vitesse.add(sur_sableGlace);
		
		sur_terre.addActionListener(this);
		sur_sable.addActionListener(this);
		sur_glace.addActionListener(this);
		sur_sableGlace.addActionListener(this);
	}
	
	public void afficher() {
		setTitle("myWorld");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new dessiner());
		addKeyListener(this);
		init_jmb();
		setJMenuBar(jmb);
		setVisible(true);
	}
	
	public void action() {
		restart();
		while(true) {
			t++;
			tDegat++;
			if (phase == "game") moi();
			for (int i = 0; i < cubes.size(); i++) {
				cubes.get(i).truc();
			}
			for (int i = accCubes.size()-1; i >= 0; i--) {
				accCube aC = (accCube) accCubes.get(i);
				aC.truc();
				if (aC.remove) accCubes.remove(i);
			}
			repaint();
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jmiMap1) cheat_teleportation(map1);
		if (e.getSource() == jmiMap2) cheat_teleportation(map2);
		if (e.getSource() == jmiMap3) cheat_teleportation(map3);
		if (e.getSource() == jmiMap4) cheat_teleportation(map4);
		if (e.getSource() == jmiMap5) cheat_teleportation(map5);
		if (e.getSource() == jmiMap6) cheat_teleportation(map6);
		if (e.getSource() == jmiMap7) cheat_teleportation(map7);
		if (e.getSource() == jmiMap8) cheat_teleportation(map8);
		if (e.getSource() == jmiMap9) cheat_teleportation(map9);
		
		if (e.getSource() == jmiVie) {
			vie = Integer.parseInt(JOptionPane.showInputDialog(null, "combien veux tu de vie", "modifier la vie", JOptionPane.QUESTION_MESSAGE));
		}
		
		if (e.getSource() == jmiInvincible) invincible = !invincible;
		
		if (e.getSource() == sur_terre) {
			init_moiV = Integer.parseInt(JOptionPane.showInputDialog(null, "que veux tu comme vitesse", "modifier la vitesse sur la terre", JOptionPane.QUESTION_MESSAGE));
		}
		if (e.getSource() == sur_sable) {
			init_sV = Integer.parseInt(JOptionPane.showInputDialog(null, "que veux tu comme vitesse", "modifier la vitesse sur le sable", JOptionPane.QUESTION_MESSAGE));
		}
		if (e.getSource() == sur_glace) {
			init_gV = Integer.parseInt(JOptionPane.showInputDialog(null, "que veux tu comme vitesse", "modifier la vitesse sur la glace", JOptionPane.QUESTION_MESSAGE));
			gV = init_gV;
		}
		if (e.getSource() == sur_sableGlace) {
			init_sgV = Integer.parseInt(JOptionPane.showInputDialog(null, "que veux tu comme vitesse", "modifier la vitesse sur le sable et glace", JOptionPane.QUESTION_MESSAGE));
		}
	}
	
	private void cheat_teleportation(int map [] []) {
		String coor[] = JOptionPane.showInputDialog(null, "ou veux tu te téléporter", "téléportation", JOptionPane.QUESTION_MESSAGE).split(" ");
		passage_portail(Integer.parseInt(coor [0]), Integer.parseInt(coor [1]), map);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (phase == "game") bouger_moi(e.getKeyCode());
		if (phase == "game over" && e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 27) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arret_bouger_moi(e.getKeyCode());
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void createWorld(int m [] []) {
		t = 0;
		
		cubes.removeAll(cubes);
		accCubes.removeAll(accCubes);
		moiV = init_moiV;
		
		toucher_lave = false;
		
		for(int iY = 0; iY < nWCase; iY++) {
			for(int iX = 0; iX < nWCase; iX++) {
				switch(m [iY] [iX]) {
				case 0 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Herbe.png", 'N'));
					break;
				case 1 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Eau.png", 'B'));
					break;
				case 2 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Lave.png", 'D'));
					break;
				case 3 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Pierre.png", 'N'));
					break;
				case 4 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Pierre.png", 'M'));
					break;
				case 5 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Sable.png", 'S'));
					break;
				case 6 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Vie.png", 'V'));
					break;
				case 7 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Glace.png", 'G'));
					break;
				case 8 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Glace.png", 'M'));
					break;
				case 9 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/BaseScie.png", 'C', grilleX+(iX-1)*cubeW+cubeW/2-sciW/2, grilleY+(iY-1)*cubeW+cubeW/2-sciW/2));
					break;
				case 10 : 
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Planche.png", 'N'));
					break;
				case 11 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Planche.png", 'M'));
					break;
				case 12 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/BaseLaser.png", 'L'));
					break;
				case 13 :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, 'B'));
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/BaseFleche.png", 'F'));
					break;
				default :
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/inconnu.png", 'N'));
					break;
				}
				if (m [iY] [iX] < 0) {
					cubes.remove(cubes.size()-1);
					int i = -m [iY] [iX];
					String s = "" + i;
					char c = s.charAt(0);
					cubes.add(new cube(grilleX+iX*cubeW, grilleY+iY*cubeW, "data/Portail.png", c, m [nWCase-1+i] [0], m [nWCase-1+i] [1]));
				}
			}
		}
	}
	
	private void restart() {
		moiX = grilleX+map1 [10] [0]*cubeW+cubeW/2-moiW/2;
		moiY = grilleY+map1 [10] [1]*cubeW+cubeW/2-moiH/2;
		vie = init_vie;
		createWorld(map1);
		accCubes.removeAll(accCubes);
		t = 0;
		phase = "game";
		gright = false;
		gdown = false;
		gleft = false;
		gup = false;
		degat = false;
	}
	
	private void moi() {
		if (right) moiX += moiV;
		if (down) moiY += moiV;
		if (left) moiX -= moiV;
		if (up) moiY -= moiV;
		
		if (g) {
			if (gright && !right) moiX += gV;
			if (gdown && !down) moiY += gV;
			if (gleft && !left) moiX -= gV;
			if (gup && !up) moiY -= gV;
		}
		
		if (moiX < grilleX) moiX = grilleX;
		if (moiY < grilleY) moiY = grilleY;
		if (moiX > grilleX+grilleW-moiW) moiX = grilleX+grilleW-moiW;
		if (moiY > grilleY+grilleW-moiH) moiY = grilleY+grilleW-moiH;
		
		if (toucher_lave) {
			if (tDegat%inter_degat == 0) {
				vie--;
				degat = true;
			}
		}
		
		if (degat && tDegat%30-10 == 0) degat = false;
		
		if (vie <= 0 && !invincible) phase = "game over";
	}
	
	private void bouger_moi(int keyCode) {
		switch(keyCode) {
		case 39 :
			right = true;
			break;
		case 40 :
			down = true;
			break;
		case 37 :
			left = true;
			break;
		case 38 :
			up = true;
			break;
		}
	}
	
	private void arret_bouger_moi(int keyCode) {
		switch(keyCode) {
		case 39 :
			right = false;
			break;
		case 40 :
			down = false;
			break;
		case 37 :
			left = false;
			break;
		case 38 :
			up = false;
			break;
		}
	}
	
	private void draw_vie(Graphics g) {
		textW = 70;
		if (!invincible) g.setColor(Color.white);
		else g.setColor(Color.red);
		g.setFont(new Font("ARIAL", Font.BOLD, textW));
		g.drawString("" + vie, 20, textW);
	}
	
	private class cube {
		private int x, y;
		private String tex;
		private char capacite;
		private int x2, y2;
		private int vX, vY;
		
		public cube(int x, int y, char capacite) {
			this.x = x;
			this.y = y;
			this.capacite = capacite;
		}
		
		public cube(int x, int y, String tex, char capacite) {
			this(x, y, capacite);
			this.tex = tex;
		}
		
		public cube(int x, int y, String tex, char capacite, int x2, int y2) {
			this(x, y, tex, capacite);
			this.x2 = x2;
			this.y2 = y2;
		}
		
		public void truc() {
			if (capacite == 'B') {
				if (x > moiX && x < moiX+moiW
					&& y+cubeW-moiV-1 > moiY && y+moiV+1 < moiY+moiH) { // choc par la gauche
					moiX = x-moiW;
				}
				
				if (x+cubeW > moiX && x+cubeW < moiX+moiW
					&& y+cubeW-moiV-1 > moiY && y+moiV+1 < moiY+moiH) { // choc par la droite
					moiX = x+cubeW;
				}
				
				if (x+cubeW-moiV-1 > moiX && x+moiV-1 < moiX+moiW
					&& y > moiY && y < moiY+moiH) { // choc par le haut
					moiY = y-moiH;
				}
				
				if (x+cubeW-moiV-1 > moiX && x+moiV-1 < moiX+moiW
					&& y+cubeW > moiY && y+cubeW < moiY+moiH) { //choc par le bas
					moiY = y+cubeW;
				}
			}
			
			if (x+cubeW > moiX && x < moiX+moiW
				&& y+cubeW > moiY && y < moiY+moiH) { // toucher
				
				if (capacite == 'D') {
					if (!toucher_lave) tDegat = inter_degat-1;
					toucher_lave = true;
				}
				
				if (capacite != 'D' && toucher_lave) {
					test('D', 'D');
					if (!test) {
						toucher_lave = false;
					}
				}
				
				if (capacite == 'S') {
					moiV = init_sV;
					gV = init_sgV;
				}
				
				if (capacite != 'S' && moiV != init_moiV) {
					test('S', 'S');
					if (!test) {
						moiV = init_moiV;
						gV = init_gV;
					}
				}
				
				if (capacite == 'V') vie = init_vie;
				
				if (capacite == 'G') {
					g = true;
					
					if (right) {
						gright = true;
						gleft = false;
					}
					if (down) {
						gdown = true;
						gup = false;
					}
					if (left) {
						gleft = true;
						gright = false;
					}
					if (up) {
						gup = true;
						gdown = false;
					}
				}
				
				if (capacite != 'G' && g) {
					test('G', 'G');
					if (!test) {
						g = false;
						gright = false;
						gdown = false;
						gleft = false;
						gup = false;
					}
				}
				
				if (capacite == '1' && !toucher_portail) passage_portail(x2, y2, map1);
				if (capacite == '2' && !toucher_portail) passage_portail(x2, y2, map2);
				if (capacite == '3' && !toucher_portail) passage_portail(x2, y2, map3);
				if (capacite == '4' && !toucher_portail) passage_portail(x2, y2, map4);
				if (capacite == '5' && !toucher_portail) passage_portail(x2, y2, map5);
				if (capacite == '6' && !toucher_portail) passage_portail(x2, y2, map6);
				if (capacite == '7' && !toucher_portail) passage_portail(x2, y2, map7);
				if (capacite == '8' && !toucher_portail) passage_portail(x2, y2, map8);
				if (capacite == '9' && !toucher_portail) passage_portail(x2, y2, map9);
				
				
				if (capacite >= '0' && capacite <= '9') {
					toucher_portail = true;
				}
				
				if (capacite < '0' || capacite > '9' && toucher_portail) {
					test('0', '9');
					if (!test) toucher_portail = false;
				}
			}
			
			if (capacite == 'C') {
				x2 += vX;
				y2 += vY;
				
				if (x2 == x-cubeW/2-sciW/2
					&& y2 == y-cubeW/2-sciW/2) {
					vX = 0;
					vY = sciV;
				}
				
				if (x2 == x-cubeW/2-sciW/2
					&& y2 == y+cubeW+cubeW/2-sciW/2) {
					vX = sciV;
					vY = 0;
				}
				
				if (x2 == x+cubeW+cubeW/2-sciW/2
					&& y2 == y+cubeW+cubeW/2-sciW/2) {
					vX = 0;
					vY = -sciV;
				}
				
				if (x2 == x+cubeW+cubeW/2-sciW/2
					&& y2 == y-cubeW/2-sciW/2) {
					vX = -sciV;
					vY = 0;
				}
				
				if (x2+sciW > moiX && x2 < moiX+moiW
					&& y2+sciW > moiY && y2 < moiY+moiH) {
					vie = 0;
				}
			}
			
			if (t%(30*3) == 0 || t == 1) {
				if (capacite == 'L') {
					int laserW = 6;
					int laserV = 50;
					int laserD = 30*2;
					accCubes.add(new accCube(x+cubeW, y+cubeW/2-laserW/2, 0, laserW, laserV, 0, laserD, 'L', Color.red));
					accCubes.add(new accCube(x+cubeW/2-laserW/2, y+cubeW, laserW, 0, laserV, 1, laserD, 'L', Color.red));
					accCubes.add(new accCube(x, y+cubeW/2-laserW/2, 0, laserW, laserV, 2, laserD, 'L', Color.red));
					accCubes.add(new accCube(x+cubeW/2-laserW/2, y, laserW, 0, laserV, 3, laserD, 'L', Color.red));
				}
			}
			
			if (t%30 == 0 || t == 1) {
				if (capacite == 'F') {
					int flecheW = 35;
					int flecheH = 6;
					int flecheV = 5;
					accCubes.add(new accCube(x+cubeW-flecheV, y+cubeW/2-flecheH/2, flecheW, flecheH, flecheV, 0, 'P', "data/FlecheD.png"));
					accCubes.add(new accCube(x+cubeW/2-flecheH/2, y+cubeW-flecheV, flecheH, flecheW, flecheV, 1, 'P', "data/FlecheB.png"));
					accCubes.add(new accCube(x-flecheW+flecheV, y+cubeW/2-flecheH/2, flecheW, flecheH, flecheV, 2, 'P', "data/FlecheG.png"));
					accCubes.add(new accCube(x+cubeW/2-flecheH/2, y-flecheW+flecheV, flecheH, flecheW, flecheV, 3, 'P', "data/FlecheH.png"));
				}
			}
		}
	}
	
	private void test(char min, char max) {
		test = false;
		for (int i = 0; i < cubes.size(); i++) {
			cube c = (cube) cubes.get(i);
			if (c.capacite >= min && c.capacite <= max) {
				
				if (c.x+cubeW > moiX && c.x < moiX+moiW
					&& c.y+cubeW > moiY && c.y < moiY+moiH) {
					test = true;
				}
				
			}
		}
	}
	
	private class accCube {
		public int x, y;
		public int w, h;
		private int v;
		private int o;
		private int d;
		private char capacite;
		private Color c;
		public String tex;
		private int tC;
		public boolean remove = false;
		private boolean toucherMoi = false;
		private int tToucherMoi;
		
		public accCube(int x, int y, int w, int h, int v, int o, char capacite, Color c) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.v = v;
			this.o = o;
			this.capacite = capacite;
			this.c = c;
			this.tC = t;
		}
		
		public accCube(int x, int y, int w, int h, int v, int o, char capacite, String tex) {
			this(x, y, w, h, v, o, capacite, Color.blue);
			c = null;
			this.tex = tex;
		}
		
		public accCube(int x, int y, int w, int h, int v, int o, int d, char capacite, Color c) {
			this(x, y, w, h, v, o, capacite, c);
			this.d = d;
		}
		
		public void truc() {
			if (d != 0) {
				if (t >= tC+d) {
					remove = true;
				}
			}
			
			if (toucherMoi) tToucherMoi++;
			
			if (capacite == 'L') {
				switch (o) {
				case 0:
					w += v;
					break;
				case 1:
					h += v;
					break;
				case 2:
					x -= v;
					w += v;
					break;
				case 3:
					y -= v;
					h += v;
					break;
				}
				
				if (x < grilleX) reculL();
				if (y < grilleY) reculL();
				if (x+w > grilleX+grilleW) reculL();
				if (y+h > grilleX+grilleW) reculL();
			}
			
			if (capacite == 'P') {
				switch(o) {
				case 0:
					x += v;
					break;
				case 1:
					y += v;
					break;
				case 2:
					x -= v;
					break;
				case 3:
					y -= v;
					break;
				}
				
				if (x < 0) remove = true;
				if (y < 0) remove = true;
				if (x+w > grilleX+grilleW) remove = true;
				if (y+h > grilleY+grilleW) remove = true;
			}
			
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.capacite == 'B') {
					if (x+w > c.x && x < c.x+cubeW && y+h > c.y && y < c.y+cubeW) {
						if (capacite == 'L') reculL();
						else if (capacite == 'P') remove = true;
					}
				}
			}
			
			if (x+w > moiX && x < moiX+moiW && y+h > moiY && y < moiY+moiH) {
				if (capacite == 'L') {
					if (!toucherMoi) {
						vie--;
						degat = true;
						toucherMoi = true;
						tToucherMoi = 1;
					}
					
					if (tToucherMoi%inter_degat == 0) {
						toucherMoi = false;
					}
				}
				if (capacite == 'P') vie = 0;
			} else {
				if (capacite == 'l') toucherMoi = false;
			}
		}
		
		private void reculL() {
			if (capacite == 'L') {
				switch (o) {
				case 0:
					w -= v;
					break;
				case 1:
					h -= v;
					break;
				case 2:
					x += v;
					w -= v;
					break;
				case 3:
					y += v;
					h -= v;
					break;
				}
			}
		}
		
		public void draw(Graphics g) {
			g.setColor(c);
			g.fillRect(x, y, w, h);
		}
	}
	
	private void passage_portail(int x, int y, int map [] []) {
		createWorld(map);
		if (x < 10) moiX = grilleX+x*cubeW+cubeW/2-moiW/2;
		if (y < 10) moiY = grilleY+y*cubeW+cubeW/2-moiH/2;
	}
	
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.translate(this.getWidth()/2-grilleW/2, this.getHeight()/2-grilleW/2);
			
			for(int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.tex != null) {
					try {
						Image texture = ImageIO.read(new File(c.tex));
						g.drawImage(texture, c.x, c.y, cubeW, cubeW, this);
						if (c.capacite == 'M') {
							g.setColor(new Color(0, 0, 0, 200));
							g.fillRect(c.x, c.y, cubeW, cubeW);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.capacite == 'C') {
					try {
						Image scie = ImageIO.read(new File("data/Scie.png"));
						g.drawImage(scie, c.x2, c.y2, sciW, sciW, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (phase == "game") {
				try {
					if (!degat) {
						Image moi = ImageIO.read(new File("data/Perso.png"));
						g.drawImage(moi, moiX, moiY, moiW, moiH, this);
					} else {
						Image moi = ImageIO.read(new File("data/PersoDegats.png"));
						g.drawImage(moi, moiX, moiY, moiW, moiH, this);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			for (int i = 0; i < accCubes.size(); i++) {
				accCube aC = (accCube) accCubes.get(i);
				if (aC.c != null) aC.draw(g);
				if (aC.tex != null) {
					try {
						Image img = ImageIO.read(new File(aC.tex));
						g.drawImage(img, aC.x, aC.y, aC.w, aC.h, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			g.setColor(Color.black);
			g.drawRect(grilleX, grilleY, grilleW, grilleW);
			
			g.translate(-(this.getWidth()/2-grilleW/2), -(this.getHeight()/2-grilleW/2));
			if (phase == "game") draw_vie(g);
			
			if (phase == "game over") {
				textW = this.getWidth()*200/1366;
				g.setColor(Color.white);
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.drawString("GAME OVER", 30, textW);
			}
		}
	}
}
