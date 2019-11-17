package test_enregister_un_tableau_2d;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static void main(String[] args) {
		int t1 [] [] = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		
		int t2 [] [] = new int [3] [3];
		
		File folder = new File("/Users/Samuel/Desktop/dossiers1/");
		
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		File file = new File("/Users/Samuel/Desktop/dossiers1/truc.txt");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileWriter writer;
			try {
				writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);
				for (int i = 0; i < 3; i++) {
					bw.write("" + t1 [i] [0] + " " + t1 [i] [1] + " " + t1 [i] [2]);
					bw.newLine();
				}
				bw.close();
				writer.close();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/Samuel/Desktop/dossiers1/truc.txt"), "UTF-8"));
				String line = reader.readLine();
				int lineR = 0;
				
				while (line != null) {
					String s [] = line.split(" ");
					t2 [lineR] [0] = Integer.parseInt(s [0]);
					t2 [lineR] [1] = Integer.parseInt(s [1]);
					t2 [lineR] [2] = Integer.parseInt(s [2]);
					
					line = reader.readLine();
					lineR++;
				}
				for (int i = 0; i < 3; i++) {
					System.out.println(t2 [i] [0] + " " + t2 [i] [1] + " " + t2 [i] [2]);
				}
				
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
