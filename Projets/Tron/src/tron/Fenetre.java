package tron;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int width = 794, height = 571;
	private final int bordure = 50;
	private boolean end = false;
	
	private final int motoW = 30, motoH = 15;
	private final int motoV = 7;
	
	moto m1 = new moto(39, 40, 37, 38, width/2-motoW/2, height/2-motoH/2, 'r', Color.blue);
	moto m2 = new moto(68, 83, 81, 90, width/2+motoW/2, height/2-motoH/2-motoH, 'l', Color.orange);
	
	ArrayList<trait> traits = new ArrayList<trait>();
	
	public void afficher() {
		setTitle("Tron");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	void action() {
		while (true) {
			if (!end) {
				if (m1.dead || m2.dead) end = true;
				
				m1.avancer();
				m1.dead();
				
				m2.avancer();
				m2.dead();
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
		if (!end) {
			m1.tourner(e.getKeyCode());
			m2.tourner(e.getKeyCode());
		} else if (e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 27) System.exit(0);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private class moto {
		public int init_x, init_y;
		public char init_o;
		
		public int x, y;
		public int oldX, oldY;
		private char o;
		public int kRight, kDown, kLeft, kUp;
		private Color c;
		
		private boolean dead = false;
		int vX, vY;
		int vW, vH;
		
		public moto(int kRight, int kDown, int kLeft, int kUp, int x, int y, char o, Color c) {
			this.kRight = kRight;
			this.kDown = kDown;
			this.kLeft = kLeft;
			this.kUp = kUp;
			this.init_x = x;
			this.x = this.init_x;
			this.init_y = y;
			this.y = this.init_y;
			this.c = c;
			this.init_o = o;
			this.o = this.init_o;
		}
		
		public void avancer() {
			oldX = x;
			oldY = y;
			switch (o) {
			case 'r' :
				x += motoV;
				break;
			case 'd' :
				y += motoV;
				break;
			case 'l' :
				x -= motoV;
				break;
			case 'u' :
				y -= motoV;
				break;
			}
			
			traits.add(new trait(x, y, oldX, oldY, o, c));
		}
		
		public void tourner(int keyCode) {
			if (keyCode == kRight && o != 'l') o = 'r';
			if (keyCode == kDown && o != 'u') o = 'd';
			if (keyCode == kLeft && o != 'r') o = 'l';
			if (keyCode == kUp && o != 'd') o = 'u';
		}
		
		public void dead() {
			switch (o) {
			case 'r' :
				vX = x;
				vY = y-motoH/2;
				vW = motoW;
				vH = motoH;
				break;
			case 'd' :
				vX = x-motoH/2;
				vY = y;
				vW = motoH;
				vH = motoW;
				break;
			case 'l' :
				vX = x-motoW;
				vY = y-motoH/2;
				vW = motoW;
				vH = motoH;
				break;
			case 'u' :
				vX = x-motoH/2;
				vY = y-motoW;
				vW = motoH;
				vH = motoW;
				break;
			}
			
			if (vX < bordure || vY < bordure) dead = true;
			if (vX > width-bordure-vW || vY > height-bordure-vH) dead = true;
			
			for (int i = 0; i < traits.size(); i++) {
				trait t = (trait) traits.get(i);
				switch (t.o) {
				case 'r' :
					if (vX+vW > t.x2 && vX < t.x1
						&& vY+vH > t.y1 && vY < t.y1) {
						dead = true;
					}
					break;
				case 'd' :
					if (vX+vW > t.x1 && vX < t.x1
						&& vY+vH > t.y2 && vY < t.y1) {
						dead = true;
					}
					break;
				case 'l' :
					if (vX+vW > t.x1 && vX < t.x2
						&& vY+vH > t.y1 && vY < t.y1) {
						dead = true;
					}
					break;
				case 'u' :
					if (vX+vW > t.x1 && vX < t.x1
						&& vY+vH > t.y1 && vY < t.y2) {
						dead = true;
					}
					break;
				}
			}
		}
		
		public void draw(Graphics2D g2d) {
			g2d.setColor(c);
			
			switch (o) {
			case 'r' :
				g2d.fillRect(x, y-motoH/2, motoW, motoH);
				break;
			case 'd' :
				g2d.fillRect(x-motoH/2, y, motoH, motoW);
				break;
			case 'l' :
				g2d.fillRect(x-motoW, y-motoH/2, motoW, motoH);
				break;
			case 'u' :
				g2d.fillRect(x-motoH/2, y-motoW, motoH, motoW);
				break;
			}
		}
	}
	
	private class trait {
		public int x1, y1, x2, y2;
		public char o;
		private Color c;
		
		public trait(int x1, int y1, int x2, int y2, char o, Color c) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.o = o;
			this.c = c;
		}
		
		void draw(Graphics2D g2d) {
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(c);
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
	
	private void restart() {
		m1.x = m1.init_x;
		m1.y = m1.init_y;
		m1.o = m1.init_o;
		m1.dead = false;
		
		m2.x = m2.init_x;
		m2.y = m2.init_y;
		m2.o = m2.init_o;
		m2.dead = false;
		
		traits.removeAll(traits);
		
		end = false;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private int textW;
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			try {
				Image fond = ImageIO.read(new File("data/TRON80.png"));
				g2d.drawImage(fond, 0, 0, width, height, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			g2d.setColor(new Color(102, 102, 102, 150));
			g2d.fillRect(bordure, bordure, width-2*bordure, height-2*bordure);
			
			if (!end) {
				for (int i = 0; i < traits.size(); i++) {
					traits.get(i).draw(g2d);
				}
				
				m1.draw(g2d);
				m2.draw(g2d);
			} else {
				textW = 100;
				g2d.setColor(Color.white);
				g2d.setFont(new Font("ARIAL", Font.BOLD, textW));
				if (m2.dead) {
					g2d.setColor(Color.blue);
					g2d.drawString("blue WIN", 20, textW);
				}
				else {
					g2d.setColor(Color.orange);
					g2d.drawString("orange WIN", 20, textW);
				}
			}
		}
	}
}
