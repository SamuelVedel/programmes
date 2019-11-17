package fr.SamuelVedel.play_world_cheat;

public class Main {

	public static void main(String[] args) {
		jeux j = new jeux();
		j.init_map();
		j.afficher();
		j.action();
	}

}
