package pong_2j;

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
	private int textW = 0;
	private boolean end = false;
	private int win = 0;
	
	private final int moiW = 25, moiH = 100;
	private final int moiV = 7;
	private final int ecart = 10;
	
	private int score_gauche;
	private final int moiGX = ecart;
	private int moiGY = height/2-moiH/2;
	private boolean downG = false, upG = false;
	
	private int score_droite;
	private final int moiDX = width-moiW-ecart;
	private int moiDY = height/2-moiH/2;
	private boolean downD = false, upD = false;
	
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
				moiG();
				moiD();
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
		if (!end) bouger_moiG(e.getKeyCode());
		if (!end) bouger_moiD(e.getKeyCode());
		if (e.getKeyCode() == 27) System.exit(0);
		if (end && e.getKeyCode() == 10) restart();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arret_bouger_moiG(e.getKeyCode());
		arret_bouger_moiD(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void moiG() {
		if (downG) moiGY += moiV;
		if (upG) moiGY -= moiV;
		
		if (moiGY < moiW) moiGY = moiW;
		if (moiGY > height-moiW-moiH) moiGY = height-moiW-moiH;
	}
	
	private void bouger_moiG(int keyCode) {
		if (keyCode == 83) downG = true;
		if (keyCode == 90) upG = true;
	}
	
	private void arret_bouger_moiG(int keyCode) {
		if (keyCode == 83) downG = false;
		if (keyCode == 90) upG = false;
	}
	
	private void moiD() {
		if (downD) moiDY += moiV;
		if (upD) moiDY -= moiV;
		
		if (moiDY < moiW) moiDY = moiW;
		if (moiDY > height-moiW-moiH) moiDY = height-moiW-moiH;
	}
	
	private void bouger_moiD(int keyCode) {
		if (keyCode == 40) downD = true;
		if (keyCode == 38) upD = true;
	}
	
	private void arret_bouger_moiD(int keyCode) {
		if (keyCode == 40) downD = false;
		if (keyCode == 38) upD = false;
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
		
		if (balleY < moiW) {
			balleY = moiW;
			balleVY = -balleVY;
		}
		
		if (balleY > height-moiW-balleW) {
			balleY = height-moiW-balleW;
			balleVY = -balleVY;
		}
		
		if (balleX+balleW > moiGX+moiW && balleX < moiGX+moiW
			&& balleY+balleW > moiGY && balleY < moiGY+moiH) {
			balleX = moiGX+moiW;
			balleVX = -balleVX;
		}
		
		if (balleX+balleW > moiGX && balleX < moiGX+moiW
			&& balleY+balleW > moiGY && balleY < moiGY) {
			balleY = moiGY-balleW;
			balleVY = -balleVY;
		}
		
		if (balleX+balleW > moiGX && balleX < moiGX+moiW
			&& balleY+balleW > moiGY+moiH && balleY < moiGY+moiH) {
			balleY = moiGY+moiH;
			balleVY = -balleVY;
		}
		
		if (balleX+balleW > moiDX && balleX < moiDX
				&& balleY+balleW > moiDY && balleY < moiDY+moiH) {
				balleX = moiDX-balleW;
				balleVX = -balleVX;
			}
			
			if (balleX+balleW > moiDX && balleX < moiDX+moiW
				&& balleY+balleW > moiDY && balleY < moiDY) {
				balleY = moiDY-balleW;
				balleVY = -balleVY;
			}
			
			if (balleX+balleW > moiDX && balleX < moiDX+moiW
				&& balleY+balleW > moiDY+moiH && balleY < moiDY+moiH) {
				balleY = moiDY+moiH;
				balleVY = -balleVY;
			}
		
		if (balleX < -balleW) {
			end = true;
			win = 2;
			score_droite++;
		}
		if (balleX > width) {
			end = true;
			win = 1;
			score_gauche++;
		}
	}
	
	private void restart() {
		moiGY = height/2-moiH/2;
		init_balle();
		
		end = false;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		protected void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			
			g.setColor(Color.white);
			g.drawLine(width/2, 0, width/2, height);
			g.fillRect(0, 0, width, moiW);
			g.fillRect(0, height-moiW, width, moiW);
			
			if (!end) {
				g.fillRect(moiGX, moiGY, moiW, moiH);
				g.fillRect(moiDX, moiDY, moiW, moiH);
				
				g.fillOval(balleX, balleY, balleW, balleW);
				
			} else {
				textW = 90;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				if (win == 1) g.drawString("Left WIN", 20, textW+20);
				else g.drawString("Right WIN", 20, textW+20);
			}
			textW = 20;
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, textW));
			g.drawString("" + score_gauche, 20, textW);
			g.drawString("" + score_droite, width/2+20, textW);
		}
	}
}