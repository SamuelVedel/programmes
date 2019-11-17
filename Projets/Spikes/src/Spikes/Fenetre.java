package Spikes;

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

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int width = 394, height = 571;
	private int t;
	private boolean test;
	private boolean end = false;
	private Random r = new Random();
	private int score;
	private final int g = 1;
	
	private int bordureX = 30, bordureY = 50;
	private Color c1 = Color.darkGray;
	private Color c2 = Color.lightGray;
	
	private final int moiW = 50, moiH = 110*moiW/145;
	private int moiX, moiY;
	private final int moiVX = 6;
	private int moiVY = 0;
	private int vSaut = -10;
	private boolean saut = false;
	private boolean sautClavier = true;
	private char moiD = 'R';
	private boolean saut_image = false;
	
	private ArrayList<pic> pics = new ArrayList<pic>();
	private final int init_picN = 2;
	private int picN = init_picN;
	private int picW = 25, picH = 35;
	private boolean ajout_nb_pic = true;
	
	private ArrayList<traine> traines = new ArrayList<traine>();
	
	public void afficher() {
		setTitle("Spikes");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	public void action() {
		init_moi();
		while(true) {
			if (!end) {
				t++;
				for (int i = 0; i < pics.size(); i++) {
					pics.get(i).toucher();
				}
				
				moi();
				for (int i = 0; i < traines.size(); i++) {
					traine t = (traine) traines.get(i);
					t.truc();
					if (t.remove) traines.remove(i);
				}
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
		if (!end) bouger_moi(e.getKeyCode());
		if (e.getKeyCode() == 27) System.exit(0);
		if (end && e.getKeyCode() == 10) restart();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		arret_bouger_moi(e.getKeyCode());
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void init_moi() {
		moiX = width/2-moiW/2;
		moiY = height/2-moiW/2;
	}
	
	private void moi() {
		if (moiD == 'R') moiX += moiVX;
		else moiX -= moiVX;
		moiY += moiVY;
		moiVY += g;
		
		if (saut) {
			moiVY = vSaut;
			saut = false;
		}
		
		if (moiVY < 0 && t%3 == 0) traines.add(new traine(moiX+moiW/2, moiY+moiH/2));
		
		if (moiX > width-bordureX-moiW) {
			moiX = width-bordureX-moiW;
			moiD = 'L';
			score++;
			if (picN < 7) nb_pic();
			picL();
			couleur();
		}
		
		if (moiX < bordureX) {
			moiX = bordureX;
			moiD = 'R';
			score++;
			if (picN < 7) nb_pic();
			picR();
			couleur();
		}
		
		if (moiY < bordureY) end = true;
		if (moiY > height-bordureY-moiH) end = true;
	}
	
	private void bouger_moi(int keyCode) {
		if (keyCode == 74 || keyCode == 32) {
			if (sautClavier) {
				saut = true;
				sautClavier = false;
				saut_image = true;
			}
		}
	}
	
	private void arret_bouger_moi(int keyCode) {
		if (keyCode == 74 || keyCode == 32) {
			sautClavier = true;
			saut_image = false;
		}
	}
	
	private class traine {
		private int x, y;
		private int w = 20;
		private int v = 1;
		public boolean remove = false;
		
		public traine(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void truc() {
			w -= v;
			if (w <= 0) remove = true;
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.orange);
			g.fillOval(x-w/2, y-w/2, w, w);
		}
	}

	
	private class pic {
		public int x, y;
		private char cote;
		
		public pic(char cote) {
			this.cote = cote;
			if (this.cote == 'R') x = width-bordureX-picW;
			else x = bordureX;
			
			y = r.nextInt(height-bordureY-picH-picW - bordureY+picW)+bordureY+picW;
			
			test = true;
			while (test) {
				test = false;
				for (int i = 0; i < pics.size(); i++) {
					pic p = (pic) pics.get(i);
					if (y + picH > p.y && y < p.y + picW && y != p.y) {
						y = r.nextInt(height-bordureY-picH-picW - bordureY+picW)+bordureY+picW;
						test = true;
					}
				}
			}
		}
		
		public void toucher() {
			if (cote == 'R' && x+picW-moiVX < moiX+moiW
				&& y+picW > moiY && y < moiY+moiH) {
				end = true;
			}
			if (cote == 'L' && x+moiVX > moiX
				&& y+picW > moiY && y < moiY+moiH) {
				end = true;
			}
		}
		
		public void draw(Graphics g) {
			if (cote == 'R') {
				int xp[] = {x, x+picW, x+picW};
				int yp[] = {y+picH/2, y, y+picH};
				g.fillPolygon(xp, yp, 3);
			} else {
				int xp[] = {x, x+picW, x};
				int yp[] = {y, y+picH/2, y+picH};
				g.fillPolygon(xp, yp, 3);
			}
		}
	}
	
	private void nb_pic() {
		if (score % 5 == 0) {
			if (ajout_nb_pic) {
				picN++;
				ajout_nb_pic = false;
			}
		} else ajout_nb_pic = true;
	}
	
	private void picR() {
		pics.removeAll(pics);
		
		for (int i = 0; i < picN; i++) {
			pics.add(new pic('R'));
		}
	}
	
	private void picL() {
		pics.removeAll(pics);
		
		for (int i = 0; i < picN; i++) {
			pics.add(new pic('L'));
		}
	}
	
	private void draw_bordure(Graphics g) {
		g.setColor(c1);
		g.fillRect(0, 0, width, bordureY);
		g.fillRect(0, height-bordureY, width, bordureY);
		g.fillRect(0, bordureY, bordureX, height-2*bordureY);
		g.fillRect(width-bordureX, bordureY, bordureX, height-2*bordureY);
		
		int ecartPic = 10;
		for (int i = 0; i*picH+i*ecartPic+ecartPic < width-bordureX; i++) {
			int x[] = {bordureX+i*picH+i*ecartPic+ecartPic,
					bordureX+i*picH+i*ecartPic+ecartPic+picH,
					bordureX+i*picH+i*ecartPic+ecartPic+picH/2};
			int y1[] = {bordureY, bordureY, bordureY+picW};
			g.fillPolygon(x, y1, 3);
			
			int y2[] =  {height-bordureY, height-bordureY, height-bordureY-picW};
			g.fillPolygon(x, y2, 3);
		}
	}
	
	private void restart() {
		moiX = width/2-moiW/2;
		moiY = height/2-moiH/2;
		moiVY = 0;
		moiD = 'R';
		
		traines.removeAll(traines);
		
		pics.removeAll(pics);
		picN = init_picN;
		
		c1 = Color.darkGray;
		c2 = Color.lightGray;
		
		score = 0;
		end = false;
	}
	
	private void couleur() {
		switch (score) {
		case 5 :
			c2 = new Color(0, 255, 255, 50);
			break;
		case 10 :
			c2 = new Color(255, 175, 175, 50);
			break;
		case 15 :
			c2 = new Color(0, 255, 0, 50);
			break;
		case 20 :
			c2 = new Color(255, 0, 255, 50);
			break;
		case 25 :
			c1 = Color.white;
			c2 = Color.darkGray;
			break;
		case 30 :
			c1 = Color.cyan;
			c2 = Color.blue;
			break;
		case 35 :
			c1 = Color.green;
			c2 = Color.green.darker().darker();
			break;
		case 40 :
			c1 = Color.blue;
			c2 = Color.blue.darker().darker();
		}
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private int textW;
		
		protected void paintComponent(Graphics g) {;;
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			g.setColor(c2);
			g.fillRect(0, 0, width, height);
			
			if (!end) {
				
				for (int i = 0; i < traines.size(); i++) {
					traines.get(i).draw(g);
				}
				try {
					if (saut_image) {
						Image moi = ImageIO.read(new File("data/bird1" + moiD + ".png"));
						g.drawImage(moi, moiX, moiY, moiW, moiH, this);
					} else {
						Image moi = ImageIO.read(new File("data/bird2" + moiD + ".png"));
						g.drawImage(moi, moiX, moiY, moiW, moiH, this);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			draw_bordure(g);
			
			if (!end) {
				for (int i = 0; i < pics.size(); i++) {
					pics.get(i).draw(g);
				}
			}
			
			textW = 40;
			g.setFont(new Font("ARIAL", Font.BOLD, textW));
			g.setColor(Color.white);
			g.drawString("" + score, 20, textW);
			g.setColor(c2);
			g.drawString("" + score, 20, textW);
		}
	}
}
