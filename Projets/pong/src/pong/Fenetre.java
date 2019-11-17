package pong;

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
	
	private final int width = 794, height = 571;
	private int score = 0;
	private int textW = 0;
	private boolean end = false;
	
	private final int moiW = 25, moiH = 100;
	private final int moiX = 10;
	private int moiY = height/2-moiH/2;
	private final int moiV = 7;
	private boolean down = false, up = false;
	
	private final int balleW = 25;
	Random r = new Random();
	private final int init_balleV = 7;
	private boolean negatifV = true;
	private int balleVX = 0;
	private int balleVY = 0;
	private int balleX = 0, balleY = 0;
	
	public void afficher() {
		setTitle("pong");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	public void action() {
		init_balle();
		while(true) {
			if (!end) {
				moi();
				balle();
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
	
	private void moi() {
		if (down) moiY += moiV;
		if (up) moiY -= moiV;
		
		if (moiY < moiW) moiY = moiW;
		if (moiY > height-moiW-moiH) moiY = height-moiW-moiH;
	}
	
	private void bouger_moi(int keyCode) {
		if (keyCode == 40) down = true;
		if (keyCode == 38) up = true;
	}
	
	private void arret_bouger_moi(int keyCode) {
		if (keyCode == 40) down = false;
		if (keyCode == 38) up = false;
	}
	
	private void init_balle() {
		negatifV = r.nextBoolean();
		if (negatifV) balleVX = -init_balleV;
		else balleVX = init_balleV;
		negatifV = r.nextBoolean();
		if (negatifV) balleVY = init_balleV;
		else balleVY = init_balleV;
		balleX = r.nextInt(width/3*2-balleW-width/3)+width/3;
		balleY = r.nextInt(height-balleW);
	}
	
	private void balle() {
		balleX += balleVX;
		balleY += balleVY;
		
		if (balleX > width-moiW-balleW) {
			balleX = width-moiW-balleW;
			balleVX = -balleVX;
		}
		if (balleY < moiW) {
			balleY = moiW;
			balleVY = -balleVY;
		}
		
		if (balleY > height-moiW-balleW) {
			balleY = height-moiW-balleW;
			balleVY = -balleVY;
		}
		
		if (balleX+balleW > moiX+moiW && balleX < moiX+moiW
			&& balleY+balleW > moiY && balleY < moiY+moiH) {
			balleX = moiX+moiW;
			balleVX = -balleVX;
			score++;
		}
		
		if (balleX+balleW > moiX && balleX < moiX+moiW
			&& balleY+balleW > moiY && balleY < moiY) {
			balleY = moiY-balleW;
			balleVY = -balleVY;
		}
		
		if (balleX+balleW > moiX && balleX < moiX+moiW
			&& balleY+balleW > moiY+moiH && balleY < moiY+moiH) {
			balleY = moiY+moiH;
			balleVY = -balleVY;
		}
		
		if (balleX < -balleW) {
			end = true;
		}
	}
	
	private void restart() {
		moiY = height/2-moiH/2;
		init_balle();
		
		end = false;
		score = 0;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		protected void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			
			g.setColor(Color.white);
			g.drawLine(width/2, 0, width/2, height);
			g.fillRect(0, 0, width, moiW);
			g.fillRect(width-moiW, 0, moiW, height);
			g.fillRect(0, height-moiW, width, moiW);
			
			if (!end) {
				g.fillRect(moiX, moiY, moiW, moiH);
				
				g.fillOval(balleX, balleY, balleW, balleW);
				
				g.setColor(Color.black);
				textW = 20;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString(""+score, 20, textW);
			} else {
				g.setColor(Color.white);
				textW = 100;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString(""+score, 20, textW);
			}
		}
	}
}
