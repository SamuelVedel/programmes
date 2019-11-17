package DVD;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class slider extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JSlider js = new JSlider();
	private int min, max;
	public int value;
	private int minorTS, majorTS;
	public boolean aff = false;
	
	public slider(int min, int max, int value, int minorTS, int majorTS) {
		this.min = min;
		this.max = max;
		this.value = value;
		this.minorTS = minorTS;
		this.majorTS = majorTS;
		
		init_js();
	}
	
	private void init_js() {
		js.setMinimum(min);
		js.setMaximum(max);
		js.setValue(value);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setMinorTickSpacing(minorTS);
		js.setMajorTickSpacing(majorTS);
		add(js);
	}
	
	public int getValue() {
		return js.getValue();
	}
}
