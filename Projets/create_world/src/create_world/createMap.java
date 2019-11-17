package create_world;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class createMap extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private String fichier;
	
	private int numeroSelect;
	private String imageSelect = "Herbe.png";
	
	private int map [] [] = new int [19] [10];
	
	private int w = 50;
	private int grilleW = 10*w;
	
	private ArrayList<cube> cubes = new ArrayList<cube>();
	
	private JPanel jpButton = new JPanel();
	private button buttons[] = {
			new button(0, "Herbe.png"),
			new button(1, "Eau.png"),
			new button(2, "Lave.png"),
			new button(3, "Pierre.png"),
			new button(4, "Pierre.png"),
			new button(5, "Sable.png"),
			new button(6, "Vie.png"),
			new button(7, "Glace.png"),
			new button(8, "Glace.png"),
			new button(9, "BaseScie.png"),
			new button(10, "Planche.png"),
			new button(11, "Planche.png"),
			new button(12, "BaseLaser.png"),
			new button(13, "BaseFleche.png"),
			new button(-1, "Portail.png"),
			new button(-2, "Portail.png"),
			new button(-3, "Portail.png"),
			new button(-4, "Portail.png"),
			new button(-5, "Portail.png"),
			new button(-6, "Portail.png"),
			new button(-7, "Portail.png"),
			new button(-8, "Portail.png"),
			new button(-9, "Portail.png")
	};
	
	private JPanel jpCoorTele = new JPanel();
	private coorTele coorTeles [] = {
			new coorTele("portail 1"),
			new coorTele("portail 2"),
			new coorTele("portail 3"),
			new coorTele("portail 4"),
			new coorTele("portail 5"),
			new coorTele("portail 6"),
			new coorTele("portail 7"),
			new coorTele("portail 8"),
			new coorTele("portail 9")
	};
	
	public createMap(String fichier) {
		this.fichier = fichier;
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		add(new dessiner(), BorderLayout.CENTER);
		init_cubes();
		
		init_jpButton();
		add(jpButton, BorderLayout.EAST);
		
		init_jpCoorTele();
		add(jpCoorTele, BorderLayout.WEST);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void openMap(String dossier) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dossier + "/" + fichier), "UTF-8"));
			String line = reader.readLine();
			int lineR = 0;
			
			while (line != null) {
				String s [] = line.split(" ");
				
				int i = 0;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR][i] = Integer.parseInt(s[i]);
				i++;
				map[lineR] [i] = Integer.parseInt(s [i]);
				
				line = reader.readLine();
				lineR++;
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTex(int numero) {
		String image = "inconnu.png";
		for (int i = 0; i < buttons.length; i++) {
			if (buttons [i].getNumero() == numero) {
				image = buttons [i].getImage();
			}
		}
		return image;
	}
	
	public void applicateMap() {
		for (int iY = 0; iY < map.length; iY++) {
			for (int iX = 0; iX < 10; iX++) {
				if (iY < 10) {
					cubes.get(iX+10*iY).setImage(getTex(map [iY] [iX]));
					cubes.get(iX+10*iY).setNumero(map [iY] [iX]);
				}
			}
			if (iY >= 10) {
				coorTeles [iY-10].setText("" + map [iY] [0], "" + map [iY] [1]);
			}
		}
	}
	
	public void setMap() {
		for (int iY = 0; iY < map.length; iY++) {
			for (int iX = 0; iX < 10; iX++) {
				if (iY < 10) {
					map [iY] [iX] = cubes.get(iX+10*iY).getNumero();
				}
			}
			
			if (iY >= 10) {
				map [iY] [0] = Integer.parseInt(coorTeles [iY-10].x.getText());
				map [iY] [1] = Integer.parseInt(coorTeles [iY-10].y.getText());
			}
		}
	}
	
	public void saveMap(String dossier) {
		try {
			File file = new File(dossier + "/" + fichier);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter writer = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(writer);
			for (int iY = 0; iY < map.length; iY++) {
				for (int iX = 0; iX < 10; iX++) {
					bw.write("" + map [iY] [iX]);
					if (iX < 9) {
						bw.write(" ");
					}
				}
				bw.newLine();
			}
			bw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTheme(Color c) {
		jpButton.setBackground(c);
		jpCoorTele.setBackground(c);
		for (int i = 0; i < coorTeles.length; i++) {
			coorTeles [i].setColor(c);
			
		}
		repaint();
	}
	
	public void remplissage() {
		for (int i = 0; i < cubes.size(); i++) {
			cube c = (cube) cubes.get(i);
			c.setImage(imageSelect);
			c.setNumero(numeroSelect);
		}
		repaint();
	}
	
	public void setTextColor(Color c) {
		for (int i = 0; i < coorTeles.length; i++) {
			coorTeles [i].setTextColor(c);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < cubes.size(); i++) {
			cubes.get(i).toucher(e.getX()-getWidth()/2+grilleW/2, e.getY()-getHeight()/2+grilleW/2);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		for (int i = 0; i < cubes.size(); i++) {
			cubes.get(i).toucher(e.getX()-getWidth()/2+grilleW/2, e.getY()-getHeight()/2+grilleW/2);
		}
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < buttons.length; i++) {
			if (e.getSource() == buttons[i]) {
				numeroSelect = buttons[i].getNumero();
				imageSelect = buttons[i].getImage();
			}
		}
		repaint();
	}
	
	class dessiner extends JPanel {
		
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.cyan);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			
			g2d.translate(getWidth() / 2 - grilleW / 2, getHeight() / 2 - grilleW / 2);
			g2d.setColor(Color.black);
			
			for (int i = 0; i < cubes.size(); i++) {
				cube c = (cube) cubes.get(i);
				try {
					Image img = ImageIO.read(new File("data/" + c.getImage()));
					g2d.drawImage(img, c.getX(), c.getY(), w, w, this);
					
					if (c.getNumero() == 4 || c.getNumero() == 8 || c.getNumero() == 11) {
						g2d.setColor(new Color(0, 0, 0, 200));
						g2d.fillRect(c.getX(), c.getY(), w, w);
					}
					if (c.getNumero() < 0) {
						g2d.setColor(Color.white);
						g2d.setFont(new Font("Arial", Font.BOLD, 15));
						g2d.drawString("" + (-c.getNumero()), c.getX()+20, c.getY()+30);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			g2d.setColor(Color.black);
			for (int i = 0; i < 11; i++) {
				g2d.drawLine(i * w, 0, i * w, grilleW);
			}
			for (int i = 0; i < 11; i++) {
				g2d.drawLine(0, i * w, grilleW, i * w);
			}
		}
	}
	
	private class cube {
		private int x, y;
		private int numero = 0;
		private String image = "Herbe.png";
		
		public cube(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void toucher(int mX, int mY) {
			if (mX > x && mX < x+w && mY > y && mY < y+w) {
				numero = numeroSelect;
				image = imageSelect;
			}
		}
		
		public void setImage(String image) {
			this.image = image;
		}
		
		public void setNumero(int numero) {
			this.numero = numero;
		}
		
		public String getImage() {
			return image;
		}
		
		public int getNumero() {
			return numero;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	private void init_cubes() {
		for (int iY = 0; iY < grilleW; iY += w) {
			for (int iX = 0; iX < grilleW; iX += w) {
				cubes.add(new cube(iX, iY));
			}
		}
	}
	
	private void init_jpButton() {
		jpButton.setPreferredSize(new Dimension(150, getHeight()));
		jpButton.setBackground(new Color(153, 0, 0));
		for (int i = 0; i < buttons.length; i++) {
			buttons [i].addActionListener(this);
			jpButton.add(buttons[i]);
		}
	}
	
	private class button extends JButton {
		
		private static final long serialVersionUID = 1L;
		
		private int numero;
		private String image;
		private boolean mur = false;
		
		public button(int numero, String image) {
			this.numero = numero;
			this.image = image;
			this.init();
		}
		
		private void init() {
			setPreferredSize(new Dimension(50, 50));
			
			if (numero < 0) {
				JLabel jb = new JLabel("" + (-numero));
				jb.setForeground(Color.white);
				jb.setFont(new Font("ARIAL", Font.BOLD, 15));
				add(jb);
			}
			if (numero == 4 || numero == 8 || numero == 11) {
				mur = true;
			}
		}
		
		public int getNumero() {
			return numero;
		}
		
		public String getImage() {
			return image;
		}
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			try {
				Image img = ImageIO.read(new File("data/" + image));
				g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (mur) {
				g2d.setColor(new Color(0, 0, 0, 200));
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
			
			if (numeroSelect == numero) {
				g2d.setColor(Color.red);
				g2d.setStroke(new BasicStroke(6));
				g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setStroke(new BasicStroke(1));
			}
		}
	}
	
	private void init_jpCoorTele() {
		jpCoorTele.setPreferredSize(new Dimension(150, getHeight()));
		jpCoorTele.setBackground(new Color(153, 0, 0));
		for (int i = 0; i < coorTeles.length; i++) {
			jpCoorTele.add(coorTeles [i]);
		}
	}
	
	private class coorTele extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private JLabel jL = new JLabel();
		public JTextField x = new JTextField("0");
		public JTextField y = new JTextField("0");
		
		public coorTele(String text) {
			this.jL.setText(text);
			this.setBackground(new Color(153, 0, 0));
			this.jL.setForeground(Color.white);
			this.x.setPreferredSize(new Dimension(20, 20));
			this.y.setPreferredSize(new Dimension(20, 20));
			this.add(this.jL);
			this.add(this.x);
			this.add(this.y);
		}
		
		public void setText(String x, String y) {
			this.x.setText(x);
			this.y.setText(y);
		}
		
		public void setColor(Color c) {
			setBackground(c);
		}
		
		public void setTextColor(Color c) {
			jL.setForeground(c);
		}
	}
	
}
