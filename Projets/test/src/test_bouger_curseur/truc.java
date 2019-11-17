package test_bouger_curseur;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class truc {
	
	public void bouger() {
		delay(1000/2);
		keyPressed(18);
		delay(1000/6);
		keyPressed(27);
		delay(1000/6);
		keyRelased(27);
		keyRelased(18);
		
		bouger_curseur(650, 620);
		delay(1000/2);
		
		mousePressed();
		delay(1000/6);
		mouseRelased();
		
		delay(2000);
		bouger_curseur(650, 350);
		delay(1000/2);
		
		mousePressed();
		delay(1000/6);
		mouseRelased();
		delay(1000/6);
		mousePressed();
		delay(1000/6);
		mouseRelased();
		
		delay(1000/2);
		keyPressed(17);
		delay(1000/6);
		keyPressed(67);
		delay(1000/6);
		keyRelased(67);
		keyRelased(17);
		
		delay(1000/2);
		keyPressed(18);
		delay(1000/6);
		keyPressed(27);
		delay(1000/6);
		keyRelased(27);
		keyRelased(18);
	}
	
	private void bouger_curseur(int x, int y) {
		try {
			Robot r = new Robot();
			r.mouseMove(x, y);
		} catch (Exception e) {
			System.out.println("aïe");
		}
	}
	
	private void mousePressed() {
		try {
			Robot r = new Robot();
			r.mousePress(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			System.out.println("aïe");
		}
	}
	
	private void mouseRelased() {
		try {
			Robot r = new Robot();
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			System.out.println("aïe");
		}
	}
	
	private void keyPressed(int k) {
		try {
			Robot r = new Robot();
			r.keyPress(k);
		} catch (Exception e) {
			System.out.println("aïe");
		}
	}
	
	private void keyRelased(int k) {
		try {
			Robot r = new Robot();
			r.keyRelease(k);;
		} catch (Exception e) {
			System.out.println("aïe");
		}
	}
	
	private void delay(int d) {
		try {
			Thread.sleep(d);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
