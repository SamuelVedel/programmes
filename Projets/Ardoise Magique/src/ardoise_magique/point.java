package ardoise_magique;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class point {
	private int x1, y1;
	private int x2, y2;
	private int size;
	private Color c;
	
	public point(int x1,int y1, int x2, int y2, int size, Color c) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.size = size;
		this.c = c;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(size));
		g2d.drawLine(x1, y1, x2, y2);
	}
}
