package snake;

import java.util.ArrayList;

public class BestScores {
	private int size;
	private ArrayList<String> names = new ArrayList<>();
	private ArrayList<Integer> scores = new ArrayList<>();
	
	public BestScores(int size) {
		this.size = size;
		for (int i = 0; i < size; i++) {
			names.add(null);
		}
		
		for (int i = 0; i < size; i++) {
			scores.add(0);
		}
	}
	
	public void add(String name, int score) {
		names.add(name);
		scores.add(score);
		
		int min = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) <= scores.get(min)) min = i;
		}
		
		names.remove(min);
		scores.remove(min);
	}
	
	public int size() {
		return size;
	}
	
	public String getName(int index) {
		String getNames [] = new String [size];
		int maxs [] = new int [size];
		int min = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) <= scores.get(min)) min = i;
		}
		for (int i = 0; i < maxs.length; i++) {
			maxs [i] = min;
		}
		boolean test = false;
		
		for (int ign = 0; ign < getNames.length; ign++) {
			for (int iS = 0; iS < scores.size(); iS++) {
				test = false;
				if (scores.get(iS) > scores.get(maxs [ign])) {
					for (int iM = 0; iM < ign; iM++) {
						if (iS == maxs [iM]) test = true;
					}
					if (!test) maxs [ign] = iS;
				}
				
				if (scores.get(iS) == scores.get(maxs [ign])) {
					if (iS < maxs [ign]) {
						for (int iM = 0; iM < ign; iM++) {
							if (iS == maxs [iM]) test = true;
						}
						if (!test) maxs [ign] = iS;
					}
				}
			}
			getNames [ign] = names.get(maxs [ign]);
		}
		
		return getNames [index];
	}
	
	public int getScore(int index) {
		int getScores [] = new int [size];
		int maxs [] = new int [size];
		
		int min = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) <= scores.get(min)) min = i;
		}
		
		for (int i = 0; i < maxs.length; i++) {
			maxs [i] = min;
		}
		boolean test = false;
		
		for (int ign = 0; ign < getScores.length; ign++) {
			for (int iS = 0; iS < scores.size(); iS++) {
				test = false;
				if (scores.get(iS) > scores.get(maxs [ign])) {
					for (int iM = 0; iM < ign; iM++) {
						if (iS == maxs [iM]) test = true;
					}
					if (!test) maxs [ign] = iS;
				}
				
				if (scores.get(iS) == scores.get(maxs [ign])) {
					if (iS < maxs [ign]) {
						for (int iM = 0; iM < ign; iM++) {
							if (iS == maxs [iM]) test = true;
						}
						if (!test) maxs [ign] = iS;
					}
				}
			}
			getScores [ign] = scores.get(maxs [ign]);
		}
		
		return getScores [index];
	}
}
