package table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jp = new JPanel();
	private JLabel jl = new JLabel();
	private JTextField jtf = new JTextField();
	private JButton jb = new JButton("Ok");
	private Random r = new Random();

	private int n1, n2;
	private int old_n1[] = new int[36];
	private int old_n2[] = new int[36];
	private int nCalc = -1;
	private boolean test;

	public void afficher() {
		setTitle("table de multiplication");
		setSize(400, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		init_n();
		init_jl();
		init_jtf();
		init_jp();
		init_jb();
		getContentPane().setLayout(new GridLayout(1, 2));
		getContentPane().add(jp);
		getContentPane().add(jb);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (Integer.parseInt(jtf.getText()) == n1 * n2) {
				init_n();
				if (jl.getText() != "BRAVO") init_jl();
				jtf.setText("");
				jtf.setForeground(Color.black);
			} else {
				jtf.setForeground(Color.red);
			}
		} catch (NumberFormatException E) {
			JOptionPane.showMessageDialog(null, "il faut rentrer un nombre", "attention", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void init_jp() {
		jp.add(jl);
		jp.add(jtf);
		jp.setBackground(Color.white.darker());
	}

	private void init_jl() {
		jl.setForeground(Color.black);
		jl.setText("" + n1 + " * " + n2 + " = ");
	}

	private void init_jtf() {
		jtf.setPreferredSize(new Dimension(150, 30));
	}

	private void init_jb() {
		jb.addActionListener(this);
		jb.setBackground(Color.gray.darker());
		jb.setForeground(Color.black);
	}

	private void init_n() {
		if (nCalc >= 0) {
			old_n1[nCalc] = n1;
			old_n2[nCalc] = n2;
		}
		nCalc++;
		
		if (old_n1[old_n1.length - 1] == 0) {
			test = true;
			while (test) {
				test = false;
				n1 = r.nextInt(10 - 2) + 2;
				n2 = r.nextInt(10 - 2) + 2;
				for (int i = 0; i < old_n1.length; i++) {
					if (((n1 == old_n1[i]) && (n2 == old_n2[i])) || ((n1 == old_n2[i]) && (n2 == old_n1[i]))) {
						test = true;
					}
				}
			}
		} else {
			getContentPane().remove(jb);
			jp.remove(jtf);
			jp.setBackground(Color.green);
			jl.setText("BRAVO");
			jl.setForeground(Color.green.darker().darker().darker());
			setVisible(true);
		}
	}
}