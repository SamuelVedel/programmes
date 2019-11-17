package trex;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int width = 794, height = 571;
	private final int v = 10;
	
	private final int solH = 50;
	private final int solW = 1000;
	private final int  solY = height-solH;
	private int sol1X = 0;
	private int sol2X = sol1X+solW;
	
	public void afficher() {
		setTitle("Trex");
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
			sol();
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
	
	private void sol() {
		sol1X -= v;
		sol2X -= v;
		
		if (sol1X+solW < 0) sol1X = sol2X+solW;
		if (sol2X+solW < 0) sol2X = sol1X+solW;
	}
	
	private class dessiner extends JPanel {
		
		protected void paintComponent(Graphics g) {
			try {
				Image fond = ImageIO.read(new File("data/desert.png"));
				g.drawImage(fond, 0, 0, width, height, this);
				
				Image sol = ImageIO.read(new File("data/sol1.png"));
				g.drawImage(sol, sol1X, solY, solW, solH, this);
				g.drawImage(sol, sol2X, solY, solW, solH, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
