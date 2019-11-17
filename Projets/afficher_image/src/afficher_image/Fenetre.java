package afficher_image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public void afficher() {
		Panneau panel = new Panneau();
		boutton b = new boutton("boutton", "/Users/Samuel/Desktop/maison/truc eclipse/afficher_image/src/afficher_image/data/ocean.png", "/Users/Samuel/Desktop/maison/truc eclipse/afficher_image/src/afficher_image/data/desert.png");
		
		setTitle("truc");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(panel);
		//setLayout(new BorderLayout());
		panel.add(b);
	}
	
	public class Panneau extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		protected void paintComponent(Graphics g) {
			try {
				Image fond = ImageIO.read(new File("/Users/Samuel/Desktop/maison/truc eclipse/afficher_image/src/afficher_image/data/fond.gif"));
				
				g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class boutton extends JButton {
		
		private static final long serialVersionUID = 1L;
		
		public boutton(String txt, String icon, String iconHover) {
			setText(txt);
			//super(txt);
			setForeground(Color.white);
			
			setPreferredSize(new Dimension(100, 50));
			setOpaque(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);
			
			setHorizontalAlignment(SwingConstants.CENTER);
			setHorizontalTextPosition(SwingConstants.CENTER);
			
			setIcon(new ImageIcon(icon));
			setRolloverIcon(new ImageIcon(iconHover));
		}
	}
}
