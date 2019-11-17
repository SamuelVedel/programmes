package test_ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements KeyListener {
	ArrayList<cube> cubes = new ArrayList<cube>();
	private final int cubeW = 50;
	private final int cubeV = 10;
	
	public Fenetre() {
		this.setTitle("truc");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
		this.setContentPane(new dessiner());
		this.setVisible(true);
		while(true) {
			System.out.println(cubes.size());
			for (int i = 0; i < cubes.size(); i++) {
				cube actCube = (cube) cubes.get(i);
				actCube.truc();
				if (actCube.delet) cubes.remove(i);
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		cubes.add(new cube(0, 0));
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private class cube {
		public int x = 0, y = 0;
		public boolean delet = false;
		
		public cube(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		void truc() {
			y += cubeV;
			if (y > getHeight()) delet = true;
		}
	}
	
	private class dessiner extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			for (int i = 0; i < cubes.size(); i++) {
				cube affCube = (cube) cubes.get(i);
				g.setColor(Color.black);
				g.fillRect(affCube.x, affCube.y, cubeW, cubeW);
			}
		}
	}
}
