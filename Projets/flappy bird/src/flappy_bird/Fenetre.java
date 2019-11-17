package flappy_bird;

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
	
	private final int width = 794, height = 571;
	private boolean end = false;
	private int score = 0;
	private int t = 0;
	Random r = new Random();
	private boolean j = true;
	private boolean space = true;
	private int textW = 0;
	
	private final int moiW = 80;
	private final int moiH = moiW*168/237;
	private int moiX = 100,  moiY = height/2-moiH/2;
	private int moiV = 0;
	private int g = 1;
	
	ArrayList<tuyau> tuyaus = new ArrayList<tuyau>();
	private int Ttuyau = 60;
	private final int tuyauW = moiW, tuyauH = height;
	private final int ecart = 150;
	private final int minY = 10+ecart, maxY = height-10;
	private final int tuyauV = 7;
	
	private boolean pause = false;
	private final int pauseW = 100, pauseH = 305;
	private final int pauseEcart = 50;
	private final int pauseX = width/2-pauseW*2/2-pauseEcart/2;
	private final int pauseY = height/2-pauseH/2;
	private final int pauseA = 50;
	
	public void afficher() {
		setTitle("I love tacos");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
		
		while(true) {
			if (!end && !pause) {
				t++;
				if (t%Ttuyau == 0) tuyaus.add(new tuyau());
				moi();
				for (int i = 0; i < tuyaus.size(); i++) {
					tuyau actT = (tuyau) tuyaus.get(i);
					actT.truc();
					if (actT.delet) tuyaus.remove(i);
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
		bouger_moi(e.getKeyCode());
		if (e.getKeyCode() == 27) System.exit(0);
		if (end && e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 80) pause = !pause;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 74) j = true;
		if (e.getKeyCode() == 32) space = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void moi() {
		moiV += g;
		moiY += moiV;
		
		if (moiY < 0) moiY = 0;
		if (moiY > height-moiH) end = true;
	}
	
	private void bouger_moi(int keyCode) {
		if (keyCode == 74 && j || keyCode == 32 && space) moiV = -10;
		if (keyCode == 74) j = false;
		if (keyCode == 32) space = false;
	}
	
	private class tuyau {
		public int x = width;
		public int y1 = 0, y2 = 0;
		public boolean delet = false;
		private boolean ajout = true;
		
		public tuyau() {
			y1 = r.nextInt(maxY-minY)+minY;
			y2 = y1-ecart-tuyauH;
		}
		
		public void truc() {
			x -= tuyauV;
			if (x < -tuyauW) delet = true;
			
			if (x+tuyauW < moiX) {
				if (ajout) score++;
				ajout = false;
			} else ajout = true;
			
			if (x+tuyauW > moiX && x < moiX+moiW
				&& y1+tuyauH > moiY && y1 < moiY+moiH) {
				end = true;
			}
			
			if (x+tuyauW > moiX && x < moiX+moiW
				&& y2+tuyauH > moiY && y2 < moiY+moiH) {
				end = true;
			}
		}
	}
	
	private void restart() {
		moiY = height/2-moiH/2;
		moiV = 0;
		
		tuyaus.removeAll(tuyaus);
		
		score = 0;
		t = 0;
		end = false;
	}
	
	class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			try {
				Image fond = ImageIO.read(new File("data/blue sky.png"));
				
				Image moi = ImageIO.read(new File("data/flappy.png"));
				
				g.drawImage(fond, 0, 0, width+20, height+5, this);
				
				if (!end) g.drawImage(moi, moiX, moiY, moiW, moiH, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (!end) {
				g.setColor(Color.green);
				for (int i = 0; i < tuyaus.size(); i++) {
					tuyau affT = (tuyau) tuyaus.get(i);
					g.fillRect(affT.x, affT.y1, tuyauW, tuyauH);
					g.fillRect(affT.x, affT.y2, tuyauW, tuyauH);
				}
				g.setColor(Color.white);
				textW = 50;
			} else {
				g.setColor(Color.white);
				textW  = 30;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("press ENTER for restart", 20, 100+textW);
				textW = 100;
			}
			g.setFont(new Font("ARIAL", Font.PLAIN, textW));
			g.drawString("score : " + score, 20, textW);
			
			if (pause && end) pause = false;
			if (pause) {
				g.setColor(Color.white);
				g.fillRoundRect(pauseX, pauseY, pauseW, pauseH, pauseA, pauseA);
				g.fillRoundRect(pauseX+pauseW+pauseEcart, pauseY, pauseW, pauseH, pauseA, pauseA);
				textW = 30;
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("press 'p' for play", 20, 50+textW);
			}
		}
	}
}
