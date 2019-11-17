package fr.moi.test;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panneau extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public void paintComponent(Graphics g) {
		try {
			Image fond = ImageIO.read(new File("poulpe2.png"));
			
			g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
