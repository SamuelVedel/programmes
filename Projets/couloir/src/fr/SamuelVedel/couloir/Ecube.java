package fr.SamuelVedel.couloir;

import java.awt.Color;

public enum Ecube {
	sol("null", Color.gray),
	vide("vide"),
	scie("scie", Color.white.darker());
	
	private String capacite;
	private Color c;
	private String tex;
	
	Ecube(String capacite) {
		this.capacite = capacite;
	}
	
	Ecube(String capacite, Color c) {
		this.capacite = capacite;
		this.c = c;
	}
	
	Ecube(String capacite, String tex) {
		this.capacite = capacite;
		this.tex = tex;
	}
	
	public String getCapacite() {
		return capacite;
	}
	
	public Color getColor() {
		return c;
	}
	
	public String getTexture() {
		return tex;
	}
}
