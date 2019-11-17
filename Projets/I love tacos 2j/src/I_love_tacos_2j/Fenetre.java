package I_love_tacos_2j;

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
	private final int ecart = 20;
	private int t;
	private int maxT = 60*30;
	private int scorej1, scorej2;
	private int textW;
	
	private final int jV = 7;
	
	private final int j1W = 80;
	private final int j1H = j1W*137/138;
	private int j1X = width-j1W-ecart, j1Y = height/2-j1H/2;
	private int j1O = 2;
	private boolean j1Right = false;
	private boolean j1Left = false;
	private boolean j1Up = false;
	private boolean j1Down = false;
	
	private final int j2W = 150;
	private final int j2H = j2W*109/199;;
	private int j2X = ecart, j2Y = height/2-j2H/2;
	private int j2O = 1;
	private boolean j2Right = false;
	private boolean j2Left = false;
	private boolean j2Up = false;
	private boolean j2Down = false;
	
	Random r = new Random();
	private final int tH = 30;
	private final int tW = tH*48/27;
	private int tX = r.nextInt(width-tW);
	private int tY = r.nextInt(height-tH);
	
	public void afficher() {
		setTitle("I love tacos 2 joueurs");
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
			if (t < maxT) {
				t++;
				j1();
				j2();
				tacos();
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
		if (t < maxT) {
			bouger_j1(e.getKeyCode());
			bouger_j2(e.getKeyCode());
		} else if (e.getKeyCode() == 10) restart();
		if (e.getKeyCode() == 27) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arret_bouger_j1(e.getKeyCode());
		arret_bouger_j2(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void j1() {
		if (j1Right) j1X += jV;
		if (j1Left) j1X -= jV;
		if (j1Up) j1Y -= jV;
		if (j1Down) j1Y += jV;
		
		if (j1X < 0) j1X = 0;
		if (j1Y < 0) j1Y = 0;
		if (j1X > width-j1W) j1X = width-j1W;
		if (j1Y > height-j1H) j1Y = height-j1H;
	}
	
	private void bouger_j1(int keyCode) {
		if (keyCode == 39) {
			j1Right = true;
			j1O = 1;
		}
		if (keyCode == 37) {
			j1Left = true;
			j1O = 2;
		}
		if (keyCode == 38) j1Up = true;
		if (keyCode == 40) j1Down = true;
	}
	
	private void arret_bouger_j1(int keyCode) {
		if (keyCode == 39) {
			j1Right = false;
			if (j1Left) j1O = 2;
		}
		if (keyCode == 37) {
			j1Left = false;
			if (j1Right) j1O = 1;
		}
		if (keyCode == 38) j1Up = false;
		if (keyCode == 40) j1Down = false;
	}
	
	private void j2() {
		if (j2Right) j2X += jV;
		if (j2Left) j2X -= jV;
		if (j2Up) j2Y -= jV;
		if (j2Down) j2Y += jV;
		
		if (j2X < 0) j2X = 0;
		if (j2Y < 0) j2Y = 0;
		if (j2X > width-j2W) j2X = width-j2W;
		if (j2Y > height-j2H) j2Y = height-j2H;
	}
	
	private void bouger_j2(int keyCode) {
		if (keyCode == 68) {
			j2Right = true;
			j2O = 1;
		}
		if (keyCode == 81) {
			j2Left = true;
			j2O = 2;
		}
		if (keyCode == 90) j2Up = true;
		if (keyCode == 83) j2Down = true;
	}
	
	private void arret_bouger_j2(int keyCode) {
		if (keyCode == 68) {
			j2Right = false;
			if (j2Left) j2O = 2;
		}
		if (keyCode == 81) {
			j2Left = false;
			if (j2Right) j2O = 1;
		}
		if (keyCode == 90) j2Up = false;
		if (keyCode == 83) j2Down = false;
	}
	
	private void tacos() {
		if (tX+tW > j1X && tX < j1X+j1W
			&& tY+tH > j1Y && tY < j1Y+j1H) {
			tX = r.nextInt(width-tW);
			tY = r.nextInt(height-tH);
			scorej1++;
		}
		
		if (tX+tW > j2X && tX < j2X+j2W
			&& tY+tH > j2Y && tY < j2Y+j2H) {
			tX = r.nextInt(width-tW);
			tY = r.nextInt(height-tH);
			scorej2++;
		}
	}
	
	private void restart() {
		j1X = width-j1W-ecart;
		j1Y = height/2-j1H/2;
		j1O = 2;
		
		j2X = ecart;
		j2Y = height/2-j2H/2;
		j2O= 1;
		
		tX = r.nextInt(width-tW);
		tY = r.nextInt(height-tH);
		
		t = 0;
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			try {
				Image fond = ImageIO.read(new File("data/fond.gif"));
				
				Image j1 = ImageIO.read(new File("data/poulpe.png"));
				Image j1R = ImageIO.read(new File("data/poulpe2.png"));
				
				Image j2 = ImageIO.read(new File("data/elephant.png"));
				Image j2R = ImageIO.read(new File("data/elephant2.png"));
				
				Image tacos = ImageIO.read(new File("data/tacos.png"));
				
				g.drawImage(fond, 0, 0, width, height, this);
				
				if (t < maxT) {
					g.drawImage(tacos, tX, tY, tW, tH, this);
					
					if (j1O == 1) g.drawImage(j1, j1X, j1Y, j1W, j1H, this);
					else g.drawImage(j1R, j1X, j1Y, j1W, j1H, this);
					
					if (j2O == 1) g.drawImage(j2, j2X, j2Y, j2W, j2H, this);
					else g.drawImage(j2R, j2X, j2Y, j2W, j2H, this);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (t < maxT) {
				textW = 40;
				g.setColor(Color.white);
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				g.drawString("score poulpe : " + scorej1, 20, textW);
				g.drawString("score elephant : " + scorej2, 20, textW*2);
			} else {
				textW = 80;
				g.setColor(Color.red);
				g.setFont(new Font("ARIAL", Font.PLAIN, textW));
				if (scorej1 > scorej2) g.drawString("poulpe WIN", 20, textW);
				else if (scorej1 < scorej2) g.drawString("elephant WIN", 20, textW);
				else g.drawString("egalité", 20, textW);
			}
		}
	}
}
