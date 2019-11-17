package string_séparateur;

public class Main {
	
	public static void main(String[] args) {
		String s = "coucou";
		String mots[] = s.split("");
		
		for (int i = 0; i < mots.length; i++) {
			System.out.println(mots[i]);
		}
		
		System.out.println();
		
		mots[1] = "s";
		
		for (int i = 0; i < mots.length; i++) {
			System.out.println(mots[i]);
		}
	}
	
}
