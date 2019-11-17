package test_JScrollPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel jp = new JPanel();
	
	private jb buttons [] = {new jb(), new jb()};
	
	private ActionListener al = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttons[0]) System.out.println("yes");
			
		}
	};
	
	public void afficher() {
		setSize(200, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		init_jp();
		setContentPane(jp);
		setVisible(true);
	}
	
	private void init_jp() {
//		jp.setLayout(new GridLayout(2, 10));
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(al);
			jp.add(buttons[i]);
		}
	}
	
	private class jb extends JButton {
		int i = 12;
		
		jb() {
			setPreferredSize(new Dimension(50, 50));
		}
		jb(String image, String text) {
			this.setIcon(new ImageIcon(image));
			setPreferredSize(new Dimension(50, 50));
			JLabel jb = new JLabel(text);
			jb.setForeground(Color.white);
			this.add(jb);
//			this.addActionListener(al);
		}
	}
}