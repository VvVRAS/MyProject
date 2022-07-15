package Licenta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

	private List<String> words;
	
	public FileProcessor() {
		this.words = new ArrayList<>();  // arraylist ca un db
		getData();						 
	}

	private void getData() {
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(new File("romanian_dic.txt"));
			bufferedReader = new BufferedReader(fileReader);
			
			String line = "";
			
			while((line=bufferedReader.readLine())!=null) {  	//Citeste liniile din fisier line-by-line pana ajunge la finalul fisierului
				words.add(line.toUpperCase());					// converteste literele din string in litere mari
			}
			
			fileReader.close();
			bufferedReader.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public List<String> getWords() {
		return this.words;
	}
}
