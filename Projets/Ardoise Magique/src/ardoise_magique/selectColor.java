package ardoise_magique;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class selectColor extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public boolean aff = false;
	
	private JSlider c1 = new JSlider();
	private JSlider c2 = new JSlider();
	private JSlider c3 = new JSlider();
	
	public Color c;
	
	public selectColor() {
		this.c1.setMinimum(0);
		this.c1.setMaximum(255);
		this.c1.setValue(0);
		this.c1.setPaintTicks(true);
		this.c1.setPaintLabels(true);
		this.c1.setMinorTickSpacing(10);
		this.c1.setMajorTickSpacing(50);
		
		this.c2.setMinimum(0);
		this.c2.setMaximum(255);
		this.c2.setValue(0);
		this.c2.setPaintTicks(true);
		this.c2.setPaintLabels(true);
		this.c2.setMinorTickSpacing(10);
		this.c2.setMajorTickSpacing(50);
		
		this.c3.setMinimum(0);
		this.c3.setMaximum(255);
		this.c3.setValue(0);
		this.c3.setPaintTicks(true);
		this.c3.setPaintLabels(true);
		this.c3.setMinorTickSpacing(10);
		this.c3.setMajorTickSpacing(50);
		
		this.add(c1);
		this.add(c2);
		this.add(c3);
		
		this.couleur();
	}
	
	void couleur() {
		this.c1.setBackground(new Color(this.c1.getValue(), 0, 0));
		this.c2.setBackground(new Color(0, this.c2.getValue(), 0));
		this.c3.setBackground(new Color(0, 0, this.c3.getValue()));
		this.c = new Color(this.c1.getValue(), this.c2.getValue(), this.c3.getValue());
		this.setBackground(c);
	}
}
