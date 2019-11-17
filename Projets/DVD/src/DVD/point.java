package DVD;

import java.awt.Color;
import java.awt.Graphics;

public class point {
	private int x, y;
	private int w;
	private Color c;
	private char forme;
	
	public point(int x, int y, int w, Color c, char forme) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.c = c;
		this.forme = forme;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		switch (forme) {
		case 'r' :
			g.fillOval(x, y, w, w);
			break;
		case 'c' :
			g.fillRect(x, y, w, w);
			break;
		}
	}
}
