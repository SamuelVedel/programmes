package rebond;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends JFrame implements KeyListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	private boolean jeux = false;
	private JPanel jp = new JPanel();
	private JPanel truc = new JPanel();
	private JTextField jtf = new JTextField("nombre de balles");
	private JButton jb = new JButton("Ok");
	private int t;
	private boolean clavier = true;
	
	private final int width = 794, height = 571;
	private boolean end = false;
	private int trans = 70;
	private Random r = new Random();
	private int textW;
	
	private int moiC1 = r.nextInt(255);
	private int moiC2 = r.nextInt(255);
	private int moiC3 = r.nextInt(255);
	private final int moiW = 25;
	private int moiX = width/2-moiW/2;
	private int moiY = height/2-moiW/2;
	private final int moiV  = 10;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	private boolean up = false;
	
	private ArrayList<balle> balles = new ArrayList<balle>();
	private int nBalle;
	private final int balleW = 25;
	private final int balleV = 7;
	
	public void afficher() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		jtf();
		jb.addActionListener(this);
		addKeyListener(this);
		jtf.addKeyListener(this);
	}
	
	public void def_jp() {
		if (jeux) {
			jp.removeAll();
			jp.add(new dessiner(), BorderLayout.CENTER);
			jtf.setForeground(Color.black);
		}
		else {
			jp.removeAll();
			jp.setBackground(Color.black);
			jp.setLayout(new BorderLayout());
			truc.add(jtf);
			truc.add(jb);
			jp.add(truc, BorderLayout.NORTH);
		}
		setContentPane(jp);
	}
	
	public void action() {
		while(true) {
			
			if (jeux) {
				if (t <= 30) t++;
				if (!end && t > 30) {
					moi();
					for (int i = 0; i < balles.size(); i++) {
						balle b = (balle) balles.get(i);
						b.truc();
					}
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
	public void actionPerformed(ActionEvent arg0) {
		launch();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!end) bouger_moi(e.getKeyCode());
		if (e.getKeyCode() == 27) System.exit(0);
		if (e.getKeyCode() == 10) {
			if (!jeux) {
				if (clavier) launch();
			}
			if (end) restart();
			clavier = false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		arret_bouger_moi(e.getKeyCode());
		if (e.getKeyCode() == 10) clavier = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void jtf() {
		jtf.setFont(new Font("ARIAL", Font.BOLD, 15));
		jtf.setPreferredSize(new Dimension(150,30));
	}
	
	private void moi() {
		if (right) moiX += moiV;
		if (down) moiY += moiV;
		if (left) moiX -= moiV;
		if (up) moiY -= moiV;
		
		if (moiX < 0) moiX = 0;
		if (moiY < 0) moiY = 0;
		if (moiX > width-moiW) moiX = width-moiW;
		if (moiY > height-moiW) moiY = height-moiW;
	}
	
	private void bouger_moi(int keyCode) {
		if (keyCode == 39) {
			if (right == false) moi_couleur();
			right = true;
		}
		if (keyCode == 40) {
			if (down == false) moi_couleur();
			down = true;
		}
		if (keyCode == 37) {
			if (left == false) moi_couleur();
			left = true;
		}
		if (keyCode == 38) {
			if (up == false) moi_couleur();
			up = true;
		}
	}
	
	private void arret_bouger_moi(int keyCode) {
		if (keyCode >= 37 && keyCode <= 40) moi_couleur();
		if (keyCode == 39) right = false;
		if (keyCode == 40) down = false;
		if (keyCode == 37) left = false;
		if (keyCode == 38) up = false;
	}
	
	private void moi_couleur() {
		moiC1 = r.nextInt(255);
		moiC2 = r.nextInt(255);
		moiC3 = r.nextInt(255);
	}
	
	private class balle {
		int x, y;
		boolean negatifVX;
		int vX;
		boolean negatifVY;
		int vY;
		int c1, c2, c3;
		
		public balle() {
			init();
		}
		
		public void init() {
			x = r.nextInt(width-balleW);
			y = r.nextInt(height-balleW);
			while (x+balleW > moiX && x < moiX+moiW
					&& y+balleW > moiY && y < moiY+moiW) {
				x = r.nextInt(width-balleW);
				y = r.nextInt(height-balleW);
			}
			negatifVX = r.nextBoolean();
			if (negatifVX) vX = -balleV;
			else vX = balleV;
			if (negatifVY) vY = -balleV;
			else vY = balleV;
			couleur();
		}
		
		public void truc() {
			x += vX;
			y += vY;
			
			if (x < 0) {
				x = 0;
				vX = -vX;
				couleur();
			}
			if (y < 0) {
				y = 0;
				vY = -vY;
				couleur();
			}
			if (x > width-balleW) {
				x = width-balleW;
				vX = -vX;
				couleur();
			}
			if (y > height-balleW) {
				y = height-balleW;
				vY = -vY;
				couleur();
			}
			
			if (x+balleW > moiX && x < moiX+moiW
				&& y+balleW > moiY && y < moiY+moiW) {
				end = true;
			}
		}
		
		private void couleur() {
			c1 = r.nextInt(255);
			c2 = r.nextInt(255);
			c3 = r.nextInt(255);
		}
	}
	
	private void launch() {
		jeux = true;
		t = 0;
		try {
			nBalle = Integer.parseInt(jtf.getText());
		} catch (NumberFormatException e) {
			jeux = false;
			jtf.setForeground(Color.red);
			JOptionPane.showMessageDialog(null, "Il faut rentrer un nombre surpérieur à 0", "attention", JOptionPane.ERROR_MESSAGE);
		}
		
		if (nBalle < 1 && jtf.getForeground() != Color.red) {
			jeux = false;
			jtf.setForeground(new Color(254, 0, 0));
			JOptionPane.showMessageDialog(null, "Il faut rentrer un nombre surpérieur à 0", "atention", JOptionPane.ERROR_MESSAGE);
		}
		
		balles.removeAll(balles);
		for (int i = 0; i < nBalle; i++) balles.add(new balle());
		requestFocus();
		def_jp();
	}
	
	private void restart() {
		moiX = width/2-moiW/2;
		moiY = height/2-moiW/2;
		moi_couleur();
		
		end = false;
		jeux = false;
		def_jp();
	}
	
	class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		protected void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			
			if (!end) {
				g.setColor(new Color(moiC1, moiC2, moiC3, trans));
				g.fillRect(moiX, moiY, moiW, moiW);
				g.setColor(new Color(moiC1, moiC2, moiC3));
				g.drawRect(moiX, moiY, moiW, moiW);
				
				for (int i = 0; i < balles.size(); i++) {
					balle b = (balle) balles.get(i);
					g.setColor(new Color(b.c1, b.c2, b.c3, trans));
					g.fillOval(b.x, b.y, balleW, balleW);
					g.setColor(new Color(b.c1, b.c2, b.c3));
					g.drawOval(b.x, b.y, balleW, balleW);
				}
			} else {
				textW = 50;
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, textW));
				g.drawString("press ENTER for restart", 20, textW);
			}
		}
	}
}