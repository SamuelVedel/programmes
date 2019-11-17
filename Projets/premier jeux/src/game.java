import java.util.Random;
import java.util.Scanner;

public class game {

	public static void main(String[] args) {
		
		while(true) {
			Random r = new Random();
			Scanner scan = new Scanner(System.in);
			int max = 0;
			int justprix = 0;
			int prix = 0;
			int score = 0;
			
			while (max == 0) {
				System.out.println("entrer la dificulté");
				max = scan.nextInt();
			}
			
			justprix = r.nextInt(max);
			prix = max;
			
			System.out.println("entre des nombre entre 0 et " + max);
			while(justprix != prix) {
				prix = scan.nextInt();
				if (prix > justprix) System.out.println("moins");
				if (prix < justprix) System.out.println("plus");
				score++;
			}
			
			System.out.println("bravo!!! ct : " + justprix);
			System.out.println("ton score est de : " + score);
			System.out.println();
			
			max = 0;
			score = 0;
		}
	}

}
