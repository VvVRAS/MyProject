package Licenta;

import java.util.List;

public class LanguageDetector {
	
	private FileProcessor fileProcessor;
	// stocheaza cuvintele in romana intr-un array list
	private List<String> romanianWords;
	
	public LanguageDetector() {
		this.fileProcessor = new FileProcessor();
		this.romanianWords = fileProcessor.getWords();
	}


	private int countRomanianWordsInText(String text) {
		text = text.toUpperCase();
		
		//Transformarea cuvintelor in romana intr-o lista de cuvinte
		String[] words = text.split(" ");
		// matches counts the number of English words in the text
		int matches = 0;
		//numararea cuvintelor in romana
		for(String s : words)
			if(romanianWords.contains(s))
				matches++;
		
		return matches;
	}
	
	public boolean isRomanian(String text) {
		// Numarul de cuvinte in romana
		int matches = countRomanianWordsInText(text);
		
		if( (float) matches / text.split(" ").length * 100 >= 60){
			return true;
		} else {		
			return false;
		}
	}
}
