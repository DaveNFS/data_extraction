import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class all {
	
	public static ArrayList<String> valid_tags_seq= new ArrayList<String>();
	public static int id=1; 
	public static ArrayList<String> matched_phrases= new ArrayList<String>();
	public static ArrayList<String> subjective_phrases= new ArrayList<String>();
	public static ArrayList<String> subjective_phrases_value= new ArrayList<String>(); // corresponding value positive/negative for the subjective phrase
	public static ArrayList<String> foundPhraseWords= new ArrayList<String>();
	public static ArrayList<Integer> foundPhraseWordsPolarity= new ArrayList<Integer>();
	public static boolean sentsRepeat[][]= new boolean[1000][1000];
	
	
	
	
	public static void generate_valid_tags(String tagFile){
		try{
		BufferedReader br= new BufferedReader(new FileReader(tagFile));
		
		String line=null;
		int line_var=0;
		while((line=br.readLine())!=null){
			valid_tags_seq.add(line);
			line_var++;
		}
		br.close();
		}catch(Exception e){e.printStackTrace();}
	} 
	public static boolean tag_match(int i, int j){
		
		boolean match=false;
		if(reading.tags[i][j].equals("JJ") | reading.tags[i][j].equals("JJR") | reading.tags[i][j].equals("JJS") | reading.tags[i][j].equals("VB") | reading.tags[i][j].equals("VBD")| reading.tags[i][j].equals("VBG") | reading.tags[i][j].equals("VBP"))
		{ match=true; } 
		
		return match; 
	}



public static String searchLexicon(String word){
	
	for(int i=0; i<lexicon.lexiconCount; i++){
		
		if(word.equalsIgnoreCase(lexicon.lexicon[i][1])) return (id +" "+lexicon.lexicon[i][0]+"  "+lexicon.lexicon[i][0]);
	}
	
	return "not found";
}

// go through the sentences and see if there is a sequence found 
public static void doPhrases(){
	matched_phrases.clear();
	initSentRepeat();
	
	for(String goldPhrase: LexiconBuilder.goldPhraseDictionary.keySet())
	{
		if(all.isPhraseInSentences(goldPhrase))
		{
			all.subjective_phrases.add(goldPhrase);
			
			
			all.subjective_phrases_value.add(Integer.toString(LexiconBuilder.goldPhraseDictionary.get(goldPhrase).polarity));
			
			
		}
	}
	
	for(int sentence = 0; sentence < reading.tags.length; sentence ++){
		
		for(int x = 0; x < reading.sents[sentence].length; x ++)
		{
			if(reading.sents[sentence][x] == null || sentsRepeat[sentence][x])
			{
				continue;
			}
			int surroundingCount = 0;
			boolean surrounding = false;
			try
			{
			for(int z = 1; z < 6; z ++)
			{
				if(z + x > 1000)
				{
					break;
				}
				if(reading.sents[sentence][x+z] == null)
				{
					break;
				}
				if(LexiconBuilder.surroundingSubjectiveWordsDictionary.containsKey(reading.sents[sentence][x + z].toLowerCase().trim()))
				{
					surrounding = true;
					surroundingCount ++;
					
				}
			}
			for(int z = 5; z > 0; z --)
			{
				if(x - z < 0)
				{
					break;
				}
				
				if(reading.sents[sentence][x-z] == null)
				{
					break;
				}
				
				if(LexiconBuilder.surroundingSubjectiveWordsDictionary.containsKey(reading.sents[sentence][x - z].toLowerCase().trim()))
				{
					surrounding = true;
					surroundingCount ++;
				}
			}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			//System.out.println("Surrounding Count: " + surroundingCount);
			if(!surrounding && surroundingCount < 2)
			{
				continue;
			}
			
			for(int i=0; i<valid_tags_seq.size(); i++)
			{
				// now we have reached the phrase sentence 
				
				String[] matchArray = valid_tags_seq.get(i).split(" ");
				String phraseToAdd = "";
				

					
						String[] curSent = Arrays.copyOfRange(reading.tags[sentence], x, 1000);
						String[] curSentWords = Arrays.copyOfRange(reading.sents[sentence], x, 1000);
						int exists = Collections.indexOfSubList(Arrays.asList(curSent), Arrays.asList(matchArray));
						//here modify the boolean values for each matched word  

						if(exists != -1)
						{
						String[] finalPhrase = Arrays.copyOfRange(curSentWords, exists, exists + matchArray.length);
						
						String phrase = "";
						for(int z = 0; z < finalPhrase.length; z ++)
						{
							phrase += finalPhrase[z] + " ";
						}

						if(!matched_phrases.contains(phrase.trim()))
						{
						if(checkIndividualPhraseSubjective(phrase.trim()))
						{
							// WE HAVE A MATCH
							boolean already = false;
							for(int word = x + exists; word < x + exists + matchArray.length; word ++)
							{
							 if(all.sentsRepeat[sentence][word])
							 {
								 already = true;
								 break;
							 }
							}
							if(already)
							{
								continue;
							}
							for(int word = exists + x; word < x + exists + matchArray.length; word ++)
							{
							all.sentsRepeat[sentence][word] = true;	
							}
							matched_phrases.add(phrase.trim());
							x += finalPhrase.length -1;
							
							//System.out.println("GOOD SURROUNDING COUNT: " + surroundingCount);
							break;
						}
						
						}
						}
						else
						{
							continue;
						}
					}
				}
				
			}
		}

public static boolean checkIndividualPhraseSubjective(String phrase)
{
	//System.out.println("Checking subjectivity");
	String[] phraseSplit = phrase.split(" ");
	for(int index = 0; index < phraseSplit.length; index ++)
	{
		if(lexicon.checkAndReturn(phraseSplit[index]) != null)
		{
			return true;
		}
	}
	return false;
}
// this method checks is the phrases pulled out contains any subjective words in them using lexicon
 public static void checkPhraseSubjective(){

	 for(int phrase=0; phrase<matched_phrases.size(); phrase++){
		 
		 String currentPhrase= matched_phrases.get(phrase);
		 String words[]= currentPhrase.split(" ");
		 
		 for(String word: words){
			 String subjectivity= lexicon.checkAndReturn(word);
			 if(subjectivity!=null){
				 subjective_phrases.add(currentPhrase);
				 subjective_phrases_value.add(subjectivity);
				 break;
			 }
		 }
				 
		 
	 }
	 
 }

 
 
 public static void findMatches(){
	 
	  String rule[]={"ADJ", "V", "JJ"};
	 for(int sentence = 0; sentence < reading.tags.length; sentence ++){
		 
		for(int i=0; i<reading.tags.length; i++){
			if(rule[0].equalsIgnoreCase(reading.tags[sentence][i])){
				int startPosition=i;
				int endingPosition=-1;
				boolean completeMatch=false;
				for(int internal=0; internal<rule.length; internal++){
					if(!rule[internal].equalsIgnoreCase(reading.tags[sentence][i+internal])) completeMatch=false;
				    }
				
				if(completeMatch) endingPosition= i+ rule.length;
				
				
			}
		}
	 }
	 
 }
 
 public void printOutput(){
	 for(int i=0; i<subjective_phrases.size(); i++) 
		 System.out.println(subjective_phrases.get(i)+ "  "+subjective_phrases_value.get(i) );
 }


 
 
 public static boolean keeper(String word, int totalCount, int subCount , double probValue ){
		
		boolean myAnswer=false;
		
		ArrayList<String> endingChars= new ArrayList<String>();
		endingChars.add("es");
		endingChars.add("s");
		endingChars.add("ed");
		endingChars.add("ment");
		endingChars.add("tive");
		endingChars.add("ing");
		endingChars.add("ate");
		endingChars.add("y");
		
		String lowerCaseWord = word.toLowerCase();
		
		//check whether it ends with any on the list
		for(int i=0; i<endingChars.size(); i++){
			if(lowerCaseWord.endsWith(endingChars.get(i))){
				myAnswer= true; 
				
			}
		}
		
		if(totalCount == 0)
		{
			return false;
		}
		
		int prob= subCount/totalCount;
		if(prob>probValue )myAnswer=true;
		 
		
		return myAnswer; 
	}
 
  public static void checkCorpusForPhraseWords(goldPhrase obj){
	 
	 for(int sentence = 0; sentence < reading.sents.length; sentence ++){ 
		 String currentSent[]= reading.sents[sentence];
		 for(int word=0; word<currentSent.length; word++){
			 // check to see if goldPhrase words in this line
			 for(int goldWord=0; goldWord<obj.words.size(); goldWord++){
				 
				 String currentWord=obj.words.get(goldWord);
				 Integer currentPloraity= obj.polarity;
				 if(currentWord.equalsIgnoreCase(currentSent[word])){
					 foundPhraseWords.add(currentWord);
					 foundPhraseWordsPolarity.add(currentPloraity);
				 }
			 }
		 }
		 
	 }
	 
 }
  
  
  
  
  // Initialize all the values to false, if repeats then make it true-- this is done in the doPhrases()
  public static void initSentRepeat()
  {
	  for(int i=0; i<1000; i++)
	  {
		  for(int j=0; j<1000; j++)
		  {
			  sentsRepeat[i][j]=false;
		  }
	  }
  }
 
  
  
  public static boolean isPhraseInSentences(String phrase){
	  boolean present=false;
	  
	  String phraseWords[]= phrase.split(" ");
	  for(int s=0; s<reading.sents.length; s++){
		  for(int word=0; word<reading.sents[s].length; word++){
			  
	// first check for the match of the first word in the phrase
	 if(reading.sents[s][word]!=null && reading.sents[s][word].equalsIgnoreCase(phraseWords[0])){
		
		 
		 
	// now that we know that the first word matched we check for every other word in a row
		 boolean soFar= true;    
		 for(int i=0; i<phraseWords.length; i++){
		    	  if(!(phraseWords[i].equalsIgnoreCase(reading.sents[s][word+i]))){
		    		  soFar=false;
		    		  break;
		    	  }
		    	  
		    
		    		  
		      }
		 
		 if(soFar){
			 
		 
			 for(int i=0; i<phraseWords.length; i++)  
				 {
				 sentsRepeat[s][i+word]=true;
				 }
		 }
		 
		 
		 
		 
	
	   present=soFar;
	 }
			  
		  }
		  
	  }
	  
	  
	 return present;  
  }
  
  
  
  public static boolean isPhraseEligilbe(String  phrase){
	  boolean eligible=false; 
	  String phraseWords[]=phrase.split(" ");
	  
	  for(String word:phraseWords){
		  String output= lexicon.checkAndReturn(word);
		  if(output!=null) 
			  {
			  eligible= true;
			  }
		  continue;
	  }
	  
	  
	  return eligible;
  }
  
//this is the class closing  braces 
}