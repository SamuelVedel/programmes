package tir;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements MouseListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int width = 794, height = 571;
	private int score;
	private final int init_life = 5;
	private int life = init_life;
	private int textW;
	Random r = new Random();
	
	ArrayList<meteor> meteors = new ArrayList<meteor>();
	private final int meteorW = 40;
	private final double g = 0.2;
	private int t;
	private final int mintM = 15, maxtM = 30;
	private int tM = r.nextInt(maxtM-mintM)+mintM;
	
	public void afficher() {
		setTitle("tir");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	public void action() {
		while(true) {
			if (life > 0) {
				t++;
				
				for (int i = 0; i < meteors.size(); i++) {
					meteor m = (meteor) meteors.get(i);
					m.truc();
					if (m.remove) meteors.remove(i);
				}
				
				if (t == tM) {
					meteors.add(new meteor());
					t = 0;
					tM = r.nextInt(maxtM-mintM)+mintM;
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
		if (life > 0) toucher(e.getX(), e.getY());
		System.out.println(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) System.exit(0);
		if (life == 0 && e.getKeyCode() == 10) restart();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void toucher(int x, int y) {
		for (int i = 0; i < meteors.size(); i++) {
			meteor m = (meteor) meteors.get(i);
			if (x > m.x && x < m.x+meteorW
				&& y > m.y-35 && y < m.y+meteorW) {
				m.remove = true;
				score++;
			}
		}
	}
	
	private class meteor {
		public int x, y;
		public double v = 5;
		public boolean remove = false;
		
		public meteor() {
			y = -meteorW;
			x = r.nextInt(width-meteorW);
		}
		
		public void truc() {
			y += v;
			v += g;
			
			if (y > height+60) {
				remove = true;
				life--;
			}
		}
	}
	
	private void restart() {
		meteors.removeAll(meteors);
		t = 0;
		tM = r.nextInt(maxtM-mintM)+mintM;
		
		score = 0;
		life = init_life;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, width, height);
			
			g.setColor(Color.green);
			g.fillRect(0, height-30, width, 30);
			
			if (life > 0) {
				for(int i = 0; i < meteors.size(); i++) {
					meteor m = (meteor) meteors.get(i);
					for (int iC = 255; iC >= 51; iC -= 51) {
						g.setColor(new Color(255, 100+iC/51*31, iC, 255/(iC/51)));
						g.fillRect(m.x, m.y-iC/51*10, meteorW, meteorW);
					}
					g.setColor(new Color(255, 50, 0));
					g.fillRect(m.x, m.y, meteorW, meteorW);
				}
				
				g.setColor(Color.white);
				textW = 50;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("score : " + score, 20, textW);
				g.drawString(+ life + "/" + init_life, 20, textW*2);
			} else {
				g.setColor(Color.white);
				textW = 90;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("score : " + score, 20, textW);
			}
		}
	}
}
