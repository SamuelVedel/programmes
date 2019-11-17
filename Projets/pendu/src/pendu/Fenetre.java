package pendu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean test;
	private String mode = "debut";
	private String erreur = "";
	private int nb_erreur;
	private ArrayList<mots> MOTS = new ArrayList<mots>();
	
	private JPanel jp = new JPanel();
	private JPanel text = new JPanel();
	private JTextField jtf = new JTextField();
	private JButton jb = new JButton("Ok");
	private JButton restart = new JButton("restart");
	
	private int imageW = 435, imageH = 315;
	private int imageX = 10, imageY = 100;
	
	public void afficher() {
		setTitle("pendu");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public void action() {
		def_jtf();
		def_jb();
		def_text();
		def_jp();
		def_restart();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) Ok();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) Ok();
		if (e.getSource() == restart) restart();
	}
	
	private void def_jp() {
		jp.setBackground(Color.white);
		jp.setLayout(new BorderLayout());
		if (mode == "debut") {
			jtf.setText("");
			jp.removeAll();
			jp.add(text, BorderLayout.NORTH);
			jtf.requestFocus();
		} else if (mode == "jeux") {
			jtf.setText("");
			jp.removeAll();
			jp.add(new dessiner(), BorderLayout.CENTER);
			jp.add(text, BorderLayout.SOUTH);
			jtf.requestFocus();
		} else if (mode == "win" || mode == "perdu") {
			jp.removeAll();
			jp.add(new dessiner(), BorderLayout.CENTER);
			jp.add(restart, BorderLayout.SOUTH);
		}
		setContentPane(jp);
	}
	
	private void def_text() {
		text.add(jtf);
		text.add(jb);
	}
	
	private void def_jtf() {
		jtf.setFont(new Font("ARIAL", Font.BOLD, 15));
		jtf.setPreferredSize(new Dimension(150,30));
		jtf.addKeyListener(this);
	}
	
	private void def_jb() {
		jb.addActionListener(this);
		jb.setBackground(Color.white.darker());
		jb.setFocusable(false);
	}
	
	private void def_restart() {
		restart.addActionListener(this);
		restart.setBackground(Color.white.darker());
	}
	
	private void restart() {
		mode = "debut";
		
		nb_erreur = 0;
		erreur = "";
		
		MOTS.removeAll(MOTS);
		
		def_jp();
	}
	
	private void Ok() {
		if (mode == "debut") {
			MOTS.add(new mots());
			
			mode = "jeux";
			def_jp();
		} else if (mode == "jeux"){
			devine();
		}
	}
	
	private void end() {
		mots m = (mots) MOTS.get(0);
		
		test = false;
		for (int i = 0; i < m.trouver.length; i++) {
			if (m.trouver[i] == false) {
				test = true;
			}
		}
		if (test == false) {
			mode = "win";
			def_jp();
		}
		
		if (nb_erreur == 12) {
			mode = "perdu";
			def_jp();
		}
	}
	
	private void devine() {
		mots m = (mots) MOTS.get(0);
		
		test = false;
		
		for (int i = 0; i < m.mots.length; i++) {
			
			if (jtf.getText().charAt(0) == m.mots[i].charAt(0)) {
				m.trouver[i] = true;
				test = true;
			}
		}
		
		if (!test) {
			erreur += " " + jtf.getText().charAt(0);
			nb_erreur++;
		}
		
		jtf.setText("");
		
		end();
		
		repaint();
	}
	
	private class mots {
		String mots[] = jtf.getText().split("");
		Boolean trouver[] = new Boolean[mots.length];
		
		public mots() {
			for (int i = 0; i < trouver.length; i++) {
				if (mots[i].charAt(0) != ' ') trouver[i] = false;
				else trouver[i] = true;
			}
		}
	}
	
	private class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		int textW;
		
		String s;
		private mots m = (mots) MOTS.get(0);
		
		protected void paintComponent(Graphics g) {
			if (mode == "perdu") {
				for (int i = 0; i < m.trouver.length; i++) {
					m.trouver[i] = true;
				}
			}
			
			s = "";
			for (int i = 0; i < m.mots.length; i++) {
				if (m.trouver[i]) s += m.mots[i];
				else s += "-";
			}
			
			textW = 30;
			g.setFont(new Font("ARIAL", Font.BOLD, textW));
			g.setColor(Color.black);
			g.drawString(s, 20, textW);
			
			g.drawString("" + erreur, 20, 3*textW);
			
			if (nb_erreur > 0) {
				try {
					Image pendu = ImageIO.read(new File("data/pendu" + nb_erreur + ".png"));
					g.drawImage(pendu, imageX, imageY, imageW, imageH, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (mode == "win") {
				textW = 150;
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.drawString("bien ouej", 20, 2*textW);
			}
			
			if (mode == "perdu") {
				textW = 300;
				g.setFont(new Font("ARIAL", Font.BOLD, textW));
				g.drawString("t nul", 20, textW);
			}
		}
	}

	
}
