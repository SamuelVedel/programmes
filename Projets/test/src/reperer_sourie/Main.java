package reperer_sourie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Main{
	
	sourie s = new sourie();
	
	public static void main(String[] args) {
		
		
	}
	
	private class sourie extends JFrame implements MouseListener {
		
		private static final long serialVersionUID = 1L;

		private sourie() {
			this.addMouseListener(this);
			while(true) {
				System.out.println("yes");
				repaint();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println(e.getX() + " " + e.getY());
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
