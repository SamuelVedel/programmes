package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private int score = 0;
	private int textW = 0;
	private boolean end = false;
	private BestScores bs = new BestScores(5);
	
	private boolean pause = false;
	private final int pauseW = 100, pauseH = 305;
	private final int pauseEcart = 50;
	private final int pauseX = 0;
	private final int pauseY = 0;
	private final int pauseA = 50;
	
	private final int W = 25;
	private final int nWCase = 15;
	private final int grilleX = 0, grilleY = 0;
	
	private final int yeuxW = 3;
	private final int yeuxEcart = 5;
	
	private boolean moche = false;
	private final int sW = 19;
	private final int sV = 5;
	
	private final int init_tSX = grilleX+2*W+W/2-sW/2;
	private final int init_tSY = grilleY+7*W+W/2-sW/2;
	private int tSX = init_tSX, tSY = init_tSY;
	private int tS_oldX = 0, tS_oldY = 0;
	public static int d = 1;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	private boolean up = false;
	
	ArrayList<cS> CS = new ArrayList<cS>();
	
	Random r = new Random();
	private final int max_fW = 17;
	private final int v_fW = 2;
	private int fW;
	private int fX = 0, fY = 0;
	
	void afficher() {
		setTitle("Snake");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		setContentPane(new dessiner());
	}
	
	public void action() {
		grandir();
		bouger_fruit();
		init_bs();
		while(true) {
			if (!end && !pause) {
				for (int i = 0; i < CS.size(); i++) {
					cS actcS = (cS) CS.get(i);
					actcS.truc();
				}
				tete_serpent();
				fruit();
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
		if (!end && !pause) bouger_tS(e.getKeyCode());
		if (e.getKeyCode() == 80) pause = !pause;
		if (end && e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 68) moche = !moche;
		
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
	
	private void init_bs() {
		try {
			BufferedReader readerNames = new BufferedReader(new InputStreamReader(new FileInputStream("BestScores/names.txt"), "UTF-8"));
			String lineNames = readerNames.readLine();
			
			BufferedReader readerScores = new BufferedReader(new InputStreamReader(new FileInputStream("BestScores/scores.txt"), "UTF-8"));
			String lineScores = readerScores.readLine();
			
			while (lineNames != null) {
				bs.add(lineNames, Integer.parseInt(lineScores));
				
				lineNames = readerNames.readLine();
				lineScores = readerScores.readLine();
			}
			
			readerNames.close();
			readerScores.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save_bs() {
		try {
			File folder = new File("BestScores/");
			if (!folder.exists()) {
				folder.mkdir();
			}
			
			File names = new File("BestScores/names.txt");
			if (!names.exists()) {
				names.createNewFile();
			}
			
			FileWriter writerName = new FileWriter(names);
			BufferedWriter bwName = new BufferedWriter(writerName);
			for (int i = 0; i < bs.size(); i++) {
				if (bs.getName(i) != null) {
					bwName.write(bs.getName(i));
					bwName.newLine();
				}
			}
			
			bwName.close();
			writerName.close();
			
			File scores = new File("BestScores/scores.txt");
			if (!scores.exists()) {
				scores.createNewFile();
			}
			
			FileWriter writerScore = new FileWriter(scores);
			BufferedWriter bwScore = new BufferedWriter(writerScore);
			for (int i = 0; i < bs.size(); i++) {
				if (bs.getName(i) != null) {
					bwScore.write("" + bs.getScore(i));
					bwScore.newLine();
				}
			}
			
			bwScore.close();
			writerScore.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void draw_grille(Graphics g) {
		for (int iX = grilleX; iX < grilleX+nWCase*W; iX += W) {
			for (int iY = grilleY; iY < grilleY+nWCase*W; iY += W) {
				if ((iX/W+iY/W)%2 == 0) g.setColor(Color.green);
				else g.setColor(Color.green.darker());
				g.fillRect(iX, iY, W, W);
			}
		}
	}
	
	private void tete_serpent() {
		if (d == 1) tSX += sV;
		if (d == 2) tSY += sV;
		if (d == 3) tSX -= sV;
		if (d == 4) tSY -= sV;
		
		tS_oldX = tSX;
		tS_oldY = tSY;
		
		if ((tSX-grilleX-W/2+sW/2)%W == 0 && (tSY-grilleY-W/2+sW/2)%W == 0) {
			if (right && d != 3) d = 1;
			if (down && d != 4) d = 2;
			if (left && d != 1) d = 3;
			if (up && d != 2) d = 4;
		}
		
		if (tSX == fX && tSY == fY) {
			grandir();
			bouger_fruit();
			score++;
		}
		
		if (tSX < grilleX || tSX+W > grilleX+nWCase*W+W-sW
			|| tSY < grilleY || tSY+W > grilleY+nWCase*W+W-sW) {
			death();
			
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
		if (moche) g.setColor(new Color(0, 0, 255/2));
		else g.setColor(Color.blue);
		g.fillOval(tSX, tSY, sW, sW);
		
		g.setColor(Color.white);
		switch(d) {
			case 1 :
				g.setColor(Color.white);
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
		}
	}
	
	private class cS {
		public int x = 0, y = 0;
		private int oldX = 0, oldY = 0;
		private int n = 0;
		
		public cS(int x, int y, int n) {
			this.x = x;
			this.y = y;
			this.n = n;
		}
		
		public void truc() {
			oldX = x;
			oldY = y;
			if (n == 1) {
				x = tS_oldX;
				y = tS_oldY;
			} else {
				cS place_cS = (cS) CS.get(n-2);
				x = place_cS.oldX;
				y = place_cS.oldY;
			}
			
			if (n > 10) {
				if (x+W > tSX && x < tSX+W
					&& y+W > tSY && y < tSY+W) {
					if (!end) death();
				}
				
				while (x+W > fX && x < fX+W
					&& y+W > fY && y < fY+W) {
					bouger_fruit();
				}
			}
		}
		
		public void draw(Graphics g) {
			if(moche) g.setColor(new Color(n*(255/CS.size()), 0, 255/2));
			else g.setColor(Color.blue);
			g.fillOval(x, y, sW, sW);
		}
	}
	
	void grandir() {
		for (int i = 0; i < 5; i++) {
			if (CS.size() == 0) CS.add(new cS(tS_oldX, tS_oldY, 1));
			else {
				cS addcS = (cS) CS.get(CS.size()-1);
				CS.add(new cS(addcS.oldX, addcS.oldY, CS.size()+1));
			}
		}
	}
	
	void fruit() {
		if (fW < max_fW) fW += v_fW;
	}
	
	void bouger_fruit() {
		fX = grilleX+W*r.nextInt(nWCase)+W/2-sW/2;
		fY = grilleY+W*r.nextInt(nWCase)+W/2-sW/2;
		fW = 1;
	}
	
	private void draw_fruit(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(fX+sW/2-fW/2, fY+sW/2-fW/2, fW, fW);
	}
	
	private void draw_score(Graphics g) {
		g.setColor(Color.white);
		textW = 50;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		g.drawString("score : " + score, 20, textW);
	}
	
	private void draw_pause(Graphics g) {
		g.setColor(Color.white);
		g.fillRoundRect(pauseX, pauseY, pauseW, pauseH, pauseA, pauseA);
		g.fillRoundRect(pauseX+pauseW+pauseEcart, pauseY, pauseW, pauseH, pauseA, pauseA);
		textW = 30;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		g.drawString("press 'p' for play", 20, 50+textW);
	}
	
	private void draw_end(Graphics g) {
		g.setColor(Color.white);
		textW  = 30;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		g.drawString("press ENTER for restart", 20, 90+textW);
		textW = 90;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		g.drawString("score : " + score, 20, textW);
		
		g.translate(this.getWidth()-400, 0);
		textW = 30;
		g.setFont(new Font("ARIAL", Font.PLAIN, textW));
		int y = 0;
		for (int i = 0; i < bs.size(); i++) {
			if (bs.getName(i) != null) {
				g.drawString(bs.getName(i) + " : " + bs.getScore(i), 20, textW*(y+1));
				y++;
			}
		}
		
		g.translate(-(this.getWidth()-400), 0);
	}
	
	void death() {
		end = true;
		repaint();
		if (score > bs.getScore(bs.size()-1)) {
			String s = JOptionPane.showInputDialog(null, "quelle est ton nom", "saving score", JOptionPane.QUESTION_MESSAGE);
			if (s != null) bs.add(s, score);
		}
		save_bs();
	}
	
	void restart() {
		tSX = init_tSX;
		tSY = init_tSY;
		tS_oldX = 0;
		tS_oldY = 0;
		
		d = 1;
		right = false;
		down = false;
		left = false;
		up = false;
		
		CS.removeAll(CS);
		grandir();
		
		bouger_fruit();
		
		score = 0;
		end = false;
	}
	
	class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.translate(this.getWidth()/2-nWCase*W/2, this.getHeight()/2-nWCase*W/2);
			
			draw_grille(g);
			
			if (!end) {
				draw_fruit(g);
				
				for (int i = CS.size()-1; i >= 0; i--) {
					cS affcS = (cS) CS.get(i);
					if (affcS.x != 0 && affcS.y != 0) affcS.draw(g);
				}
				draw_tete_serpent(g);
				
				g.translate(-(this.getWidth()/2-nWCase*W/2), -(this.getHeight()/2-nWCase*W/2));
				
				draw_score(g);
			} else {
				g.translate(-(this.getWidth()/2-nWCase*W/2), -(this.getHeight()/2-nWCase*W/2));
				draw_end(g);
			}
			
			if (pause && end) pause = false;
			if (pause) {
				g.translate(this.getWidth()/2-(pauseW*2+pauseEcart)/2, this.getHeight()/2-pauseH/2);
				draw_pause(g);
				g.translate(-(this.getWidth()/2-(pauseW*2+pauseEcart)/2), -(this.getHeight()/2-pauseH/2));
			}
		}
	}
}
