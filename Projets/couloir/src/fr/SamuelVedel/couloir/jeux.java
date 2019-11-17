package fr.SamuelVedel.couloir;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class jeux implements KeyListener{
	private JFrame jf = new JFrame();
	
	private Random r = new Random();
	
	private final int grilleSize= 15;
	private final int cubeW = 25;
	private final int scieW = 24;
	private final int scieV = 2;
	
	private final int moiW = 17, moiH = moiW;
	private int moiX = cubeW*(grilleSize/2+1)+cubeW/2-moiW/2;
	private int moiY = cubeW*(grilleSize/2+1)+cubeW/2-moiH/2;
	private int moiO = 0;
	private final int moiV = 5;
	private boolean right;
	private boolean down;
	private boolean left;
	private boolean up;
	
	private ArrayList<cube> cubes = new ArrayList<>();
	
	private String phase = "game";
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) System.exit(0);
		bougerMoi(e.getKeyCode());
		if (phase == "end" && e.getKeyCode() == 10) restart();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arretBougerMoi(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void open_jf() {
		jf.setTitle("couloir");
		jf.setSize(800, 600);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().add(new Dessiner());
		jf.addKeyListener(this);
		jf.setVisible(true);
	}
	
	public void action() {
		init_cubes();
		
		while(true) {
			if (phase == "game") {
				moi();
				
				ajout_cube();
				supr_cube();
			}
			
			for (int i = 0; i < cubes.size(); i++) {
				cubes.get(i).truc();
			}
			
			jf.repaint();
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moi() {
		if (moiO == 0) {
			if (right) moiO = 1;
			else if (down) moiO = 2;
			else if (left) moiO = 3;
			else if (up) moiO = 4;
		}
		
		if (moiO == 1) moiX += moiV;
		else if (moiO == 2) moiY += moiV;
		else if (moiO == 3) moiX -= moiV;
		else if (moiO == 4) moiY -= moiV;
		
		if (moiX < cubeW) moiX = cubeW+cubeW/2-moiW/2;
		else if (moiX+moiW > (grilleSize+1)*cubeW) moiX = grilleSize*cubeW+cubeW/2-moiW/2;
		else if (moiY+moiH > (grilleSize+1)*cubeW) moiY = grilleSize*cubeW+cubeW/2-moiH/2;
		else if (moiY < (grilleSize/2+1)*cubeW) {
			moiY = (grilleSize/2+1)*cubeW+cubeW/2-moiH/2;
			
			for (int i = 0; i < cubes.size(); i++) {
				cubes.get(i).y += moiV;
				cubes.get(i).y2 += moiV;
			}
		} 
		
		cube c = (cube) cubes.get(0);
		if ((moiX-cubeW/2+moiW/2)%cubeW == 0 && (moiY-cubeW/2+moiH/2)%cubeW == 0 && c.y%cubeW == 0) {
			moiO = 0;
		}
		
	}
	
	void bougerMoi(int keyCode) {
		switch (keyCode) {
		case 39 :
			right = true;
			down = false;
			left = false;
			up = false;
			break;
		case 40 :
			right = false;
			down = true;
			left = false;
			up = false;
			break;
		case 37 :
			right = false;
			down = false;
			left = true;
			up = false;
			break;
		case 38 :
			right = false;
			down = false;
			left = false;
			up = true;
			break;
		}
	}
	
	private void arretBougerMoi(int keyCode) {
		if (keyCode == 39) right = false;
		if (keyCode == 40) down = false;
		if (keyCode == 37) left = false;
		if (keyCode == 38) up = false;
	}
	
	private void draw_moi(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(moiX, moiY, moiW, moiH);
	}
	
	public void init_cubes() {
		cubes.removeAll(cubes);
		
		for (int iX = 0; iX < grilleSize; iX++) {
			for (int iY = grilleSize-1; iY >= 0; iY--) {
				newCube(cubeW+cubeW*iX, cubeW+cubeW*iY, Ecube.sol);
			}
		}
	}
	
	public void ajout_cube() {
		cube c = (cube) cubes.get(cubes.size()-1);
		if (c.y+cubeW > cubeW*2) {
			Ecube ajouts [] = new Ecube[grilleSize];
			for (int i = 0; i < ajouts.length; i++) {
				ajouts [i] = Ecube.sol;
			}
			
			int vide = r.nextInt(6);
			for (int i = 0; i < vide; i++) {
				ajouts [r.nextInt(ajouts.length)] = Ecube.vide;
			}
			
			int scie = r.nextInt(3);
			for (int i = 0; i < scie; i++) {
				ajouts [r.nextInt(ajouts.length)] = Ecube.scie;
			}
			
			for (int i = 0; i < ajouts.length; i++) {
				newCube(cubeW+i*cubeW, cubes.get(cubes.size()-1-i).y-cubeW, ajouts [i]);
			}
		}
	}
	
	public void supr_cube() {
		for (int i = cubes.size()-1; i >= 0; i--) {
			cube c = (cube) cubes.get(i);
			if (c.y >= (grilleSize+1)*cubeW) {
				cubes.remove(i);
			}
		}
	}
	
	private void newCube(int x, int y, Ecube ec) {
		if (ec.getTexture() != null) cubes.add(new cube(x, y, ec.getCapacite(), ec.getTexture()));
		else cubes.add(new cube(x, y, ec.getCapacite(), ec.getColor()));
	}
	
	private class cube {
		private int x, y;
		private int x2, y2;
		private int vX, vY;
		private String capacite;
		private Color c;
		private String tex;
		
		public cube(int x, int y, String capacite, Color c) {
			this.x = x;
			this.y = y;
			this.capacite = capacite;
			this.c = c;
			
			if (capacite == "scie") {
				x2 = x-scieW;
				y2 = y-scieW;
			}
		}
		
		public cube(int x, int y, String capacite, String tex) {
			this.x = x;
			this.y = y;
			this.capacite = capacite;
			this.tex = tex;
			
			if (capacite == "scie") {
				x2 = x-scieW;
				y2 = y-scieW;
			}
		}
		
		public void truc() {
			if (moiX == x+cubeW/2-moiW/2 && moiY == y+cubeW/2-moiH/2) { //sur
				if (capacite == "vide") {
					phase = "end";
				}
			}
			
			if (capacite == "scie") {
				x2 += vX;
				y2 += vY;
				
				if (x2 == x-scieW && y2 == y-scieW) {
					vX = 0;
					vY = scieV;
				} else if (x2 == x-scieW && y2 == y+cubeW+1) {
					vX = scieV;
					vY = 0;
				} else if (x2 == x+cubeW+1 && y2 == y+cubeW+1) {
					vX = 0;
					vY = -scieV;
				} else if (x2 == x+cubeW+1 && y2 == y-scieW) {
					vX = -scieV;
					vY = 0;
				}
				
				if (x2+scieW > moiX && x2 < moiX+moiW && y2+scieW > moiY && y2 < moiY+moiH) { // touché scie
					phase = "end";
				}
			}
		}
		
		public void draw(Graphics g) {
			if (c != null) {
				if (y < (grilleSize+1)*cubeW) {
					g.setColor(c);
					g.fillRect(x, y, cubeW, cubeW);
					g.setColor(Color.black);
					g.drawRect(x, y, cubeW-1, cubeW-1);
				}
			} else if (tex != null) {
				try {
					Image img = ImageIO.read(new File(tex));
					g.drawImage(img, x, y, cubeW, cubeW, new Dessiner());
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.setColor(Color.black);
				g.drawRect(x, y, cubeW-1, cubeW-1);
			}
		}
	}
	
	private void restart() {
		moiX = (grilleSize/2+1)*cubeW+cubeW/2-moiW/2;
		moiY = (grilleSize/2+1)*cubeW+cubeW/2-moiH/2;
		
		init_cubes();
		
		phase = "game";
	}
	
	private class Dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		int textW;
		
		protected void paintComponent(Graphics g) {
			g.setColor(Color.darkGray.darker());
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.translate(getWidth()/2-(grilleSize+2)*cubeW/2, getHeight()/2-(grilleSize+2)*cubeW/2);
			
			for (int i = 0; i < cubes.size(); i++) {
				cubes.get(i).draw(g);
			}
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				if (c.capacite == "scie" && c.y >= cubeW && c.y2 < (grilleSize+1)*cubeW) {
					Image imgscie;
					try {
						imgscie = ImageIO.read(new File("data/Scie.png"));
						g.drawImage(imgscie, c.x2, c.y2, scieW, scieW, new Dessiner());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (phase == "game") draw_moi(g);
			
			g.setColor(new Color(102, 0, 153));
			g.fillRect(0, 0, (grilleSize+2)*cubeW, cubeW);
			g.fillRect(0, (grilleSize+1)*cubeW, (grilleSize+2)*cubeW, cubeW);
			g.fillRect(0, cubeW, cubeW, grilleSize*cubeW);
			g.fillRect((grilleSize+1)*cubeW, cubeW, cubeW, grilleSize*cubeW);
			
			g.translate(-(getWidth()/2-(grilleSize+2)*cubeW/2), -(getHeight()/2-(grilleSize+2)*cubeW/2));
			
			if (phase == "end") {
				textW = this.getWidth()*200/1366;
				g.setColor(Color.white);
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.drawString("GAME OVER", 30, textW);
			}
		}
	}
}
