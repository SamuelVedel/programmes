package fr.SamuelVedel.testEnum;

public enum truc {
	salut(1),
	cest(2),
	cool(3);
	
	int voila;
	
	truc(int voila) {
		this.voila = voila;
	}
}
