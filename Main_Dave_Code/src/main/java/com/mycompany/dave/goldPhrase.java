import java.util.ArrayList;

	 public class goldPhrase
	 {
		 public int polarity;
		 public String phrase;
		 public int subjFreq;
		 public int corpusFreq;
	 	 public ArrayList<String> words= new ArrayList<String>();
		 public goldPhrase(String phrase, String polarity)
		 {
			 this.subjFreq = 1;
			 this.phrase = phrase;
			 if(polarity.contains("positive"))
			 {
				 this.polarity = 1;
			 }
			 else
			 {
				 this.polarity = 0;
			 }
			 
	 		 String word[]= phrase.split(" ");
	 		 for(int i=0; i<word.length; i++)
	 		 {	 
	 			 words.add(word[i]);
	 		 }
		 }
	 }
	 
