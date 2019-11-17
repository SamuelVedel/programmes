package DVD;

import java.awt.Color;
import java.util.Random;

public class dvd {
	private final int width = 794, height = 571-23;
	private Random r = new Random();
	public int w;
	public int x, y;
	private boolean negatif;
	private int vX, vY;
	public Color c;
	private int t;
	public char forme;
	
	public dvd(int w, int x, int y, int v, int t, char forme) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.negatif = r.nextBoolean();
		if (this.negatif) this.vX = -v;
		else this.vX = v;
		this.negatif = r.nextBoolean();
		if (this.negatif) this.vY = -v;
		else this.vY = v;
		this.t = t;
		couleur();
		this.forme = forme;
	}
	
	public void bouger() {
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
		if (x > width-w) {
			x = width-w;
			vX = -vX;
			couleur();
		}
		if (y > height-w) {
			y = height-w;
			vY = -vY;
			couleur();
		}
	}
	
	private void couleur() {
		c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), t);
	}
}
