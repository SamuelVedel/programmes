package I_love_tacos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int width = 794, height = 571;
	private int textW;
	private int score = 0;
	private int score_enemie = 0;
	private int t = 0;
	private int maxT = 1800;
	
	private final int moiW = 80;
	private final int moiH = moiW*137/138;
	private int moiX = width/2-moiW/2, moiY = height/2-moiH/2;
	private int moiO = 1;
	private final int moiV = 7;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	
	Random r = new Random();
	private final int tH = 30;
	private final int tW = tH*48/27;
	private int tX = r.nextInt(width-tW);
	private int tY = r.nextInt(height-tH);
	
	mechant plongeur = new mechant(1);
	mechant jean = new mechant(2);
	
	public void afficher() {
		setTitle("I love tacos");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	public void action() {
		while(true) {
			t++;
			if (t < maxT) {
				moi();
				tacos();
				plongeur.bouger();
				plongeur.manger();
				jean.bouger();
				jean.manger();
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
	public void keyPressed(KeyEvent e) {
		if (t < maxT) bougerMoi(e.getKeyCode());
		else if (e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 27) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arretBougerMoi(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void moi() {
		if (right) moiX += moiV;
		if (left) moiX -= moiV;
		if (up) moiY -= moiV;
		if (down) moiY += moiV;
		
		if (moiX < 0) moiX = 0;
		if (moiY < 0) moiY = 0;
		if (moiX > width-moiW) moiX = width-moiW;
		if (moiY > height-moiH) moiY = height-moiH;
	}
	
	private void bougerMoi(int keyCode) {
		if (keyCode == 39) {
			right = true;
			moiO = 1;
		}
		if (keyCode == 37) {
			left = true;
			moiO = 2;
		}
		if (keyCode == 38) up = true;
		if (keyCode == 40) down = true;
	}
	
	private void arretBougerMoi (int keyCode) {
		if (keyCode == 39) {
			right = false;
			if (left) moiO = 2;
		}
		if (keyCode == 37) {
			left = false;
			if (right) moiO = 1;
		}
		if (keyCode == 38) up = false;
		if (keyCode == 40) down = false;
	}
	
	private void tacos() {
		if (tX+tW > moiX && tX < moiX+moiW
			&& tY+tH > moiY && tY < moiY+moiH) {
			tX = r.nextInt(width-tW);
			tY = r.nextInt(height-tH);
			score++;
		}
	}
	
	private class mechant {
		public int w = 0;
		public int h = 0;
		public int x = 0, y = 0;
		public int o = 0;
		private final int maxTV = 60;
		private final int minTV = 10;
		private int TV = 1;
		private boolean negatifVX = false;
		private boolean negatifVY = false;
		public final int maxV = 13;;
		public final int minV = 8;
		private int vX = 0, vY = 0;
		public int n = 0;
		
		public mechant(int n) {
			this.n = n;
			if (this.n == 1) {
				this.w = 150;
				this.h = this.w*46/146;
			} else {
				this.h = 100;
				this.w = this.h*45/104;
			}
		}
		
		void bouger() {
			x += vX;
			y += vY;
			
			if (t%TV == 0) {
				TV = r.nextInt(maxTV-minTV)+minTV;
				
				negatifVX = r.nextBoolean();
				if (negatifVX) vX = r.nextInt(-minV+maxV)-maxV;
				else vX = r.nextInt(maxV-minV)+minV;
				
				negatifVY = r.nextBoolean();
				if (negatifVY) vY = r.nextInt(-minV+maxV)-maxV;
				else vY = r.nextInt(maxV-minV)+minV;
			}
			
			if (x < 0) {
				x = 0;
				vX = -vX;
			}
			if (y < 0) {
				y = 0;
				vY = -vY;
			}
			if (x > width-w) {
				x = width-w;
				vX = -vX;
			}
			if (y > height-h) {
				y = height-h;
				vY = -vY;
			}
			
			if (vX > 0) o = 1;
			else if (vX < 0) o = 2;
		}
		
		public void manger() {
			if (x+w > tX && x < tX+tW
				&& y+h > tY && y < tY+tH) {
				tX = r.nextInt(width-tW);
				tY = r.nextInt(height-tH);
				score_enemie++;
			}
		}
	}
	
	private void restart() {
		moiX = width/2-moiW/2;
		moiY = height/2-moiH/2;
		
		tX = r.nextInt(width-tW);
		tY = r.nextInt(height-tH);
		
		plongeur.x = 0;
		plongeur.y = 0;
		
		jean.x = 0;
		jean.y = 0;
		
		score = 0;
		score_enemie = 0;
		t = 0;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			if (t < maxT) {
				try {
					Image fond = ImageIO.read(new File("data/fond.gif"));
					
					Image poulpe = ImageIO.read(new File("data/poulpe.png"));
					Image poulpe2 = ImageIO.read(new File("data/poulpe2.png"));
					
					Image tacos = ImageIO.read(new File("data/tacos.png"));
					
					Image p = ImageIO.read(new File("data/plongeur.png"));
					Image p2 = ImageIO.read(new File("data/plongeur2.png"));
					
					Image j = ImageIO.read(new File("data/jeans.png"));
					
					g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
					
					g.drawImage(tacos, tX, tY, tW, tH, this);
					
					if (moiO == 1) g.drawImage(poulpe, moiX, moiY, moiW, moiH, this);
					else g.drawImage(poulpe2, moiX, moiY, moiW, moiH, this);
					
					if (plongeur.o == 1) g.drawImage(p, plongeur.x, plongeur.y, plongeur.w, plongeur.h, this);
					else g.drawImage(p2, plongeur.x, plongeur.y, plongeur.w, plongeur.h, this);
					
					g.drawImage(j, jean.x, jean.y, jean.w, jean.h, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.setColor(Color.white);
				textW = 35;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("score : " + score, 10, textW);
				g.drawString("score énemie : " + score_enemie, 10, textW*2-5);
			} else {
				try {
					Image fond = ImageIO.read(new File("data/fond.gif"));
					g.drawImage(fond, 0, 0, width, height, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				g.setColor(Color.red);
				if (score > score_enemie) {
					textW = 200;
					g.setFont(new Font("ARIAL", Font.PLAIN, textW));
					g.drawString("WIN", 10, textW);
				}
				else if (score < score_enemie) {
					textW = 100;
					g.setFont(new Font("ARIAL", Font.PLAIN, textW));
					g.drawString("GAME OVER", 10, textW);
				}
				else {
					textW = 100;
					g.setFont(new Font("ARIAL", Font.PLAIN, textW));
					g.drawString("EGALITE", 10, textW);
				}
			}
		}
	}
}
