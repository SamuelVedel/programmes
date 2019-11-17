package chute_de_cubes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 500, height = 600;
	private final int ecart = 100;
	private int score = 0;
	private int textW = 0;
	private boolean fin = false;
	Random r = new Random();
	
	private final int moiW = 25;
	private final int moiP1 = 175-moiW/2;
	private final int moiP2 = 325-moiW/2;
	private int moiX = moiP1, moiY = height-ecart-moiW*2;
	private final int initvTele = -7;
	private int vTele = initvTele;
	private int teleW;
	Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	
	cube cube1 = new cube(1);
	cube cube2 = new cube(2);
	cube cube3 = new cube(3);
	cube cube4 = new cube(4);
	private int t = 0;
	private int tCube = 13;
	private int passage = 1;
	
	public void afficher () {
		setTitle("chute de cubes");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setResizable(false);
		setVisible(true);
		setContentPane(new dessiner());
	}
	
	public void action() {
		while(true) {
			if (!fin) {
				t++;
				tele();
				cube1.truc();
				cube2.truc();
				cube3.truc();
				cube4.truc();
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
		bougerMoi(e.getKeyCode());
		if (e.getKeyCode() == 27) System.exit(0);
		if (e.getKeyCode() == 10 && fin) restart();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	private void bougerMoi(int keyCode) {
		if (keyCode == 74 || keyCode == 32) {
			if (moiX == moiP1) moiX = moiP2;
			else moiX = moiP1;
			vTele = -vTele;
		}
	}
	
	private void tele() {
		teleW += vTele;
		if (teleW < 0) teleW = 0;
		if (teleW > moiW) teleW = moiW;
	}
	
	private class cube {
		public int W = 90;
		public int x = 0, y = height;
		private int v = 20;
		private int p;
		private final int P1 = 175-W/2;
		private final int P2 = 325-W/2;
		private int n;
		private boolean ajout = false;
		
		public cube(int n) {
			this.n = n;
		}
		
		public void truc() {
			y += v;
			
			if (p == 1) x = P1;
			else x = P2;
			
			if (t == tCube && passage == n) {
				p = r.nextInt(2);
				y = -W;
				t = 0;
				if (passage != 4) passage++;
				else passage = 1;
			}
			
			if (x+W > moiX && x < moiX+moiW
				&& y+W > moiY && y < moiY+moiW) {
				fin = true;
			}
			
			if (y > moiY+moiW) {
				if (ajout) score++;
				ajout = false;
			} else ajout = true;
		}
	}
	
	private void restart() {
		score = 0;
		fin = false;
		
		moiX = moiP1;
		vTele = initvTele;
		teleW = 0;
		c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		
		cube1.y = getHeight();
		cube1.ajout = false;
		cube2.y = getHeight();
		cube2.ajout = false;
		cube3.y = getHeight();
		cube3.ajout = false;
		cube4.y = getHeight();
		cube4.ajout = false;
		
		t = 0;
		passage = 1;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			if(!fin) {
				g.setColor(c);
				g.fillRect(moiP1+teleW/2, moiY+teleW/2, moiW-teleW, moiW-teleW);
				g.fillRect(moiP2+moiW/2-teleW/2, moiY+moiW/2-teleW/2, teleW, teleW);
				
				g.setColor(Color.black);
				g.fillRect(cube1.x, cube1.y, cube1.W, cube1.W);
				g.fillRect(cube2.x, cube2.y, cube2.W, cube2.W);
				g.fillRect(cube3.x, cube3.y, cube3.W, cube3.W);
				g.fillRect(cube4.x, cube4.y, cube4.W, cube4.W);
				
				g.drawLine(width/5, ecart, width/5, height-ecart);
				g.drawLine(width/2, ecart, width/2, height-ecart);
				g.drawLine(width/5*4, ecart, width/5*4, height-ecart);
				
				textW = 50;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("" + score, 20, textW);
			} else {
				g.setColor(Color.black);
				textW = 70;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("score : " + score, 20, textW);
			}
		}
	}
}
