	 public class wordObject
	 {
		 public int polarity;
		 public String word;
		 public int subjFreq;
		 public int corpusFreq;
		 public wordObject(String word, String polarity)
		 {
			 this.subjFreq = 1;
			 this.corpusFreq = 0;
			 
			 this.word = word;
			 if(polarity.contains("positive"))
			 {
				 this.polarity = 1;
			 }
			 else
			 {
				 this.polarity = 0;
			 }
		 }
		 
		 public wordObject(String word, int polarity)
		 {
			 this.word = word;
			 this.polarity = polarity;
		 }
	 }