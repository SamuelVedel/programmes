package fr.SamuelVedel.Snake_towers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private String phase = "game";
	private int score;
	private int actMapX = 4, actMapY = 4;
	
	private Random r = new Random();
	
	private niveau world [] [] = new niveau [9] [9];
	private ArrayList<cube> cubes = new ArrayList<>();
	private int niveauSize = 15;
	
	private int cubeW = 25;
	
	private final int yeuxW = 3;
	private final int yeuxEcart = 5;
	
	private final int sW = 19;
	private final int sV = 5;
	
	private final int init_tSX = 7*cubeW+cubeW/2-sW/2;
	private final int init_tSY = 7*cubeW+cubeW/2-sW/2;
	private int tSX = init_tSX, tSY = init_tSY;
	private int tSMapX = actMapX, tSMapY = actMapY;
	private int tS_oldX = 0, tS_oldY = 0;
	private int tS_oldMapX, tS_oldMapY;
	public int d = 0;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	private boolean up = false;
	private final Color cSnake = Color.blue;
	
	private ArrayList<cS> CS = new ArrayList<cS>();
	
	private boolean pause = false;
	private final int pauseW = 100, pauseH = 305;
	private final int pauseEcart = 50;
	private final int pauseX = 0;
	private final int pauseY = 0;
	private final int pauseA = pauseW;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) System.exit(0);
		if (phase == "game") bouger_tS(e.getKeyCode());
		if (phase == "end" && e.getKeyCode() == 10) restart();
		if (phase == "game" && e.getKeyCode() == 80) pause = !pause;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void afficher() {
		setTitle("Snake Towers");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new dessiner());
		addKeyListener(this);
		setVisible(true);
	}
	
	public void action() {
		init_world();
		openNiveau(world [actMapY] [actMapX]);
		
		grandir();
		while(true) {
//			for (int iT = 0; iT < 5; iT++) {
				if (phase == "game" && !pause) {
					tete_serpent();
					for (int i = 0; i < CS.size(); i++) {
						cS c = (cS) CS.get(i);
						c.truc();
					}
					for (int i = cubes.size()-1; i >= 0; i--) {
						if (i < cubes.size()) cubes.get(i).truc(i);
					}
					
					world [actMapY] [actMapX].open();
				}
				if (pause && phase != "game") pause = false;
				repaint();
				try {
					Thread.sleep(1000/25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//			}
//			try {
//				Thread.sleep(1000/15);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	private void openNiveau(niveau n) {
		n.createNiveau();
	}
	
	private int [] [] copieIntMatrice(int matrice [] [], int lenX) {
		int copieMatrice [] [] = new int [matrice.length] [lenX];
		
		for (int iY = 0; iY < matrice.length; iY++) {
			for (int iX = 0; iX < lenX; iX++) {
				copieMatrice [iY] [iX] = matrice [iY] [iX];
			}
		}
		
		return copieMatrice;
	}
	
	private void init_world() {
		ArrayList<EMap> maps = new ArrayList<>();
		
		maps.add(EMap.DEPART);
		maps.add(EMap.map1);
		maps.add(EMap.map2);
		maps.add(EMap.map3);
		maps.add(EMap.map4);
		maps.add(EMap.map5);
		maps.add(EMap.map6);
		maps.add(EMap.map7);
		maps.add(EMap.map8);
		maps.add(EMap.map9);
		maps.add(EMap.map10);
		maps.add(EMap.map11);
		maps.add(EMap.map12);
		maps.add(EMap.map13);
		maps.add(EMap.map14);
		maps.add(EMap.map15);
		maps.add(EMap.map16);
		maps.add(EMap.map17);
		maps.add(EMap.map18);
		maps.add(EMap.map19);
		maps.add(EMap.map20);
		maps.add(EMap.map21);
		maps.add(EMap.map22);
		maps.add(EMap.map23);
		maps.add(EMap.map23);
		maps.add(EMap.map24);
		maps.add(EMap.map25);
		
		for (int iY = 0; iY < world.length; iY++) {
			for (int iX = 0; iX < world.length; iX++) {
				if (iX == 4 && iY == 4) world [iY] [iX] = new niveau(maps.get(0).getMap1());
				else {
					int oMap = r.nextInt(5-1)+1;
					switch (oMap) {
					case 1 :
						world [iY] [iX] = new niveau(copieIntMatrice(maps.get(r.nextInt(maps.size()-1)+1).getMap1(), 15));
						break;
					case 2 :
						world [iY] [iX] = new niveau(copieIntMatrice(maps.get(r.nextInt(maps.size()-1)+1).getMap2(), 15));
						break;
					case 3 :
						world [iY] [iX] = new niveau(copieIntMatrice(maps.get(r.nextInt(maps.size()-1)+1).getMap3(), 15));
						break;
					case 4 :
						world [iY] [iX] = new niveau(copieIntMatrice(maps.get(r.nextInt(maps.size()-1)+1).getMap4(), 15));
						break;
					}
				}
			}
		}
	}
	
	private class niveau {
		public int map [] [] = new int [niveauSize] [niveauSize];
		
		private boolean openR = false;;
		private boolean openD = false;
		private boolean openL = false;
		private boolean openU = false;
		
		public niveau (int map [] []) {
			this.map = map;
		}
		
		public void createNiveau() {
			cubes.removeAll(cubes);
			
			int portailW = 11, portailH = 21;
			int murH = 33;
			int pommeW = 17, pommeH = 17;
			
			Color portailC = new Color(153, 102, 0);
			Color murC = Color.red;
			Color fissureC = new Color(153, 153, 153);
			
			for (int iY = 0; iY < map.length; iY++) {
				for (int iX = 0; iX < map.length; iX++) {
					
					Color c;
					if ((iX+iY)%2 == 0) c = Color.decode("#06bcc1");
					else c = Color.decode("#06bcc1").darker();
					
					switch (map [iY] [iX]) {
					case 0 :
						break;
					case 1 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						break;
					case 2 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, portailW, portailH, portailC, 'R'));
						break;
					case 3 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, portailW, portailH, portailC, 'D'));
						break;
					case 4 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, portailW, portailH, portailC, 'L'));
						break;
					case 5 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, portailW, portailH, portailC, 'U'));
						break;
					case 6 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, murH, murC, 'M'));
						break;
					case 7 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, 'M'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, murH, fissureC, 'F'));
						break;
					case 8 :
						cubes.add(new cube(cubeW*iX, cubeW*iY, cubeW, cubeW, c, 'N'));
						cubes.add(new cube(cubeW*iX, cubeW*iY, pommeW, pommeH, Color.yellow, 'P'));
						break;
					}
				}
			}
			niveau n;
			if (actMapX != world.length-1) n = (niveau) world [actMapY] [actMapX+1];
			else n = (niveau) world [actMapY] [0];
			if (n.openL) openR = true;
			
			if (actMapY != world.length-1) n = (niveau) world [actMapY+1] [actMapX];
			else n = (niveau) world [0] [actMapX];
			if (n.openU) openD = true;
			
			if (actMapX != 0) n = (niveau) world [actMapY] [actMapX-1];
			else n = (niveau) world [actMapY] [world.length-1];
			if (n.openR) openL = true;
			
			if (actMapY != 0) n = (niveau) world [actMapY-1] [actMapX];
			else n = (niveau) world [world.length-1] [actMapX];
			if (n.openD) openU = true;
		}
		
		private void open() {
			boolean test = false;
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.capacite == 'P') test = true;
			}
			if (!test) {
				openR = true;
				openD = true;
				openL = true;
				openU = true;
			}
		}
	}
	
	private class cube {
		private int x, y;
		private int w, h;
		private Color c;
		private char capacite;
		
		public cube(int x, int y, char capacite) {
			this.x = x;
			this.y = y;
			this.capacite = capacite;
		}
		
		public cube(int x, int y, int w, int h, Color c, char capacite) {
			this(x, y, capacite);
			this.w = w;
			this.h = h;
			this.c = c;
		}
		
		void truc(int index) {
			niveau actN = (niveau) world [actMapY] [actMapX];
			if (x+cubeW > tSX && x < tSX+sW && y+cubeW > tSY && y < tSY+sW) { // touché
				if (capacite == 'M') death();
				if (capacite == 'F') death();
				
				if ((capacite == 'R') && (!actN.openR)
						|| (capacite == 'D') && (!actN.openD)
						|| (capacite == 'L') && (!actN.openL)
						|| (capacite == 'U') && (!actN.openU)) {
					death();
				}
			}
			
			if (tSX+sW/2-cubeW/2 == x && tSY+sW/2-cubeW/2 == y) { // sur
				if (capacite == 'P') {
					score++;
					grandir();
					world [actMapY] [actMapX].map [y/cubeW] [x/cubeW] = 1;
					cubes.remove(index);
				}
				
				if ((capacite == 'R') && actN.openR) {
					if (actMapX != world.length-1) actMapX++;
					else actMapX = 0;
					openNiveau(world [actMapY] [actMapX]);
					tSX = cubeW/2-sW/2+sV;
					tS_oldX = cubeW/2-sW/2;
				}
				
				if ((capacite == 'D') && actN.openD) {
					if (actMapY != world.length-1) actMapY++;
					else actMapY = 0;
					openNiveau(world [actMapY] [actMapX]);
					tSY = cubeW/2-sW/2+sV;
					tS_oldY = cubeW/2-sW/2;
				}
				
				if ((capacite == 'L') && actN.openL) {
					if (actMapX != 0) actMapX--;
					else actMapX = world.length-1;
					openNiveau(world [actMapY] [actMapX]);
					tSX = 14*cubeW+cubeW/2-sW/2-sV;
					tS_oldX = 14*cubeW+cubeW/2-sW/2;
				}
				
				if ((capacite == 'U') && actN.openU) {
					if (actMapY != 0) actMapY--;
					else actMapY = world.length-1;
					openNiveau(world [actMapY] [actMapX]);
					tSY = 14*cubeW+cubeW/2-sW/2-sV;
					tS_oldY = 14*cubeW+cubeW/2-sW/2;
				}
			}
		}
		
		public void draw(Graphics g) {
			niveau actN = (niveau) world [actMapY] [actMapX];
			
			if (capacite == 'N') {
				g.setColor(c);
				g.fillRect(x, y, w, h);
			}
			else if (capacite == 'M' || capacite == 'F') {
				g.setColor(c.darker());
				g.fillRect(x, y-h+cubeW, w, h);
				g.setColor(c);
				g.fillRect(x, y+cubeW-h+cubeW, w, h-cubeW);
			}
			else if ((capacite == 'R') && (!actN.openR)
					|| (capacite == 'D') && (!actN.openD)
					|| (capacite == 'L') && (!actN.openL)
					|| (capacite == 'U') && (!actN.openU)) {
				g.setColor(c.darker());
				g.fillRect(x+cubeW/2-w/2, y+cubeW/2-w/2-h+w, w, h);
				g.setColor(c);
				g.fillRect(x+cubeW/2-w/2, y+cubeW/2-w/2-h+w, w, w);
			}
			else if (capacite == 'P') {
				g.setColor(c);
				g.fillOval(x+cubeW/2-w/2, y+cubeW/2-h/2, w, h);
			}
		}
	}
	
	private void tete_serpent() {
		if (d == 1) tSX += sV;
		if (d == 2) tSY += sV;
		if (d == 3) tSX -= sV;
		if (d == 4) tSY -= sV;
		tSMapX = actMapX;
		tSMapY = actMapY;
		
		tS_oldX = tSX;
		tS_oldY = tSY;
		
		tS_oldMapX = tSMapX;
		tS_oldMapY = tSMapY;
		
		if ((tSX-cubeW/2+sW/2)%cubeW == 0 && (tSY-cubeW/2+sW/2)%cubeW == 0) {
			if (right && d != 3) d = 1;
			if (down && d != 4) d = 2;
			if (left && d != 1) d = 3;
			if (up && d != 2) d = 4;
		}
	}
	
	private void bouger_tS(int keyCode) {
		if (keyCode == 39) {
			right = true;
			down = false;
			left = false;
			up = false;
		}
		if (keyCode == 40) {
			right = false;
			down = true;
			left = false;
			up = false;
		}
		if (keyCode == 37) {
			right = false;
			down = false;
			left = true;
			up = false;
		}
		if (keyCode == 38) {
			right = false;
			down = false;
			left = false;
			up = true;
		}
	}
	
	private void draw_tete_serpent(Graphics g) {
		g.setColor(cSnake);
		g.fillOval(tSX, tSY, sW, sW);
		
		g.setColor(Color.white);
		switch(d) {
			case 1 :
				g.fillRect(tSX+sW-yeuxEcart-yeuxW/2, tSY+yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				g.fillRect(tSX+sW-yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				break;
			case 2 :
				g.fillRect(tSX+yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				g.fillRect(tSX+sW-yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				break;
			case 3 :
				g.fillRect(tSX+yeuxEcart-yeuxW/2, tSY+yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				g.fillRect(tSX+yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				break;
			case 4 :
				g.fillRect(tSX+yeuxEcart-yeuxW/2, tSY+yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				g.fillRect(tSX+sW-yeuxEcart-yeuxW/2, tSY+yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				break;
			default :
				g.fillRect(tSX+yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				g.fillRect(tSX+sW-yeuxEcart-yeuxW/2, tSY+sW-yeuxEcart-yeuxW/2, yeuxW, yeuxW);
				break;
		}
	}
	
	private class cS {
		public int x = 0, y = 0;
		private int mapX = -1, mapY = -1;
		private int oldX = 0, oldY = 0;
		private int oldMapX, oldMapY;
		private int n = 0;
		
		public cS(int x, int y, int n) {
			this.x = x;
			this.y = y;
			this.n = n;
		}
		
		public void truc() {
			oldX = x;
			oldY = y;
			oldMapX = mapX;
			oldMapY = mapY;
			if (n == 1) {
				x = tS_oldX;
				y = tS_oldY;
				mapX = tS_oldMapX;
				mapY = tS_oldMapY;
			} else {
				cS place_cS = (cS) CS.get(n-2);
				x = place_cS.oldX;
				y = place_cS.oldY;
				mapX = place_cS.oldMapX;
				mapY = place_cS.oldMapY;
			}
			
			if (n > 10 && mapX == actMapX && mapY == actMapY) {
				if (x+cubeW > tSX && x < tSX+cubeW
					&& y+cubeW > tSY && y < tSY+cubeW) {
					if (phase != "end") death();
				}
			}
		}
		
		public void draw(Graphics g) {
			g.setColor(cSnake);
			g.fillOval(x, y, sW, sW);
		}
	}
	
	private void grandir() {
		for (int i = 0; i < 5; i++) {
			if (CS.size() == 0) CS.add(new cS(tS_oldX, tS_oldY, 1));
			else {
				cS addcS = (cS) CS.get(CS.size()-1);
				CS.add(new cS(addcS.oldX, addcS.oldY, CS.size()+1));
			}
		}
	}
	
	private void death() {
		phase = "end";
	}
	
	private void restart() {
		actMapX = 4;
		actMapY = 4;
		init_world();
		openNiveau(world [actMapY] [actMapX]);
		tSX = init_tSX;
		tSY = init_tSY;
		d = 0;
		right = false;
		down = false;
		left = false;
		up = false;
		CS.removeAll(CS);
		grandir();
		phase = "game";
		score = 0;
	}
	
	private void draw_pause(Graphics g, int width, int height) {
		int textW;
		g.translate(width/2-(pauseW*2+pauseEcart)/2, height/2-pauseH/2);
		g.setColor(Color.white);
		g.fillRoundRect(pauseX, pauseY, pauseW, pauseH, pauseA, pauseA);
		g.fillRoundRect(pauseX+pauseW+pauseEcart, pauseY, pauseW, pauseH, pauseA, pauseA);
		textW = 30;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		g.translate(-(width/2-(pauseW*2+pauseEcart)/2), -(height/2-pauseH/2));
		g.drawString("press 'p' for play", 20, 50+textW);
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		int textW;
		
		protected void paintComponent(Graphics g) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.translate(getWidth()/2-niveauSize*cubeW/2, getHeight()/2-niveauSize*cubeW/2);
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.capacite != 'M' || c.capacite != 'F') {
					if (c.c != null) c.draw(g);
				}
			}
			if (phase == "game") {
				for (int i = 0; i < CS.size(); i++) {
					cS c = (cS) CS.get(i);
					if ((c.mapX == actMapX) && (c.mapY == actMapY)) c.draw(g);
				}
				draw_tete_serpent(g);
				
				for (int i = 0; i < cubes.size(); i++) {
					cube c = (cube) cubes.get(i);
					if (c.capacite == 'M' || c.capacite == 'F') {
						if (c.c != null) c.draw(g);
					}
				}
				
				g.translate(-(getWidth()/2-niveauSize*cubeW/2), -(getHeight()/2-niveauSize*cubeW/2));
				textW = 50;
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.setColor(Color.white);
				g.drawString("score : " + score, 20, textW);
				
				if (pause) {
					draw_pause(g, getWidth(), getHeight());
				}
			} else if (phase == "end") {
				g.translate(-(getWidth()/2-niveauSize*cubeW/2), -(getHeight()/2-niveauSize*cubeW/2));
				textW = 100;
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.setColor(Color.white);
				g.drawString("GAME OVER ", 20, textW);
				textW = 80;
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.drawString("score : " + score, 20, textW*2+10);
			}
		}
	}
}