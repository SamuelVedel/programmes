package generateur_de_Vbux;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int option = JOptionPane.showConfirmDialog(null,
		"vouler vous beaucoup de VBux ?", "modificateur de VBux",
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (option != JOptionPane.OK_OPTION) System.exit(0);
		
		String s = JOptionPane.showInputDialog(null, "adresse IMail", "modificateur de VBux", JOptionPane.QUESTION_MESSAGE);
		
		if (s == null) {
			JOptionPane.showMessageDialog(null, "modification de VBux annuler", "", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		s = JOptionPane.showInputDialog(null, "mot de passe", "modificateur de VBux", JOptionPane.QUESTION_MESSAGE);
		
		if (s == null) {
			JOptionPane.showMessageDialog(null, "modification de VBux annuler", "", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		s = JOptionPane.showInputDialog(null, "nombre de VBux", "modificateur de VBux", JOptionPane.QUESTION_MESSAGE);
		
		if (s == null) {
			JOptionPane.showMessageDialog(null, "modification de VBux annuler", "", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		JOptionPane.showMessageDialog(null, "c'est bon vous avez " + s + " VBux", "modificateur de VBux", JOptionPane.INFORMATION_MESSAGE);
	}
}
