import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class LexiconBuilder {

	/**
	 * @param args
	 */
	public static ArrayList<String> opinionWordsToProcess = new ArrayList<String>();
	public static ArrayList<String> opinionWordsAlreadyProcessed = new ArrayList<String>();
	public static ArrayList<wordObject> finalLexicon = new ArrayList<wordObject>();
	public static ArrayList<wordObject> goldWords = new ArrayList<wordObject>();
	public static ArrayList<goldPhrase> goldPhrases = new ArrayList<goldPhrase>();
	public static ArrayList<String> goldFiles = new ArrayList<String>();
	public static ArrayList<String> originalFiles = new ArrayList<String>();
	public static HashMap<String, wordObject> goldDictionary = new HashMap<String, wordObject>();
	public static HashMap<String, goldPhrase> goldPhraseDictionary = new HashMap<String, goldPhrase>();
	public static HashMap<String, wordObject> allWordsDictionary = new HashMap<String, wordObject>();
	public static HashMap<String, wordObject> surroundingSubjectiveWordsDictionary = new HashMap<String, wordObject>();
	
	public static void main(String[] args) 
	{
		int failCount = 0;
		getWordsFromGold();
		

		// Here, we are going to do some extra work. If a word appears too many times in the corpus and is not subjective enough times, we will ax it
		
		refineGold();
		refineGoldPhrases();
		
		
		
		for(String s : goldDictionary.keySet())
			{
			try {
				//System.out.println(getUrlSource("http://www.merriam-webster.com/thesaurus/greedy"));
				getUrlSource("http://www.merriam-webster.com/thesaurus/" + s, goldDictionary.get(s).polarity);
				finalLexicon.add(goldDictionary.get(s));
				
			} catch (IOException e) {
				System.out.println("Couldn't find: " + goldDictionary.get(s).word);
				failCount ++;
			}	
		}
		PrintWriter out;
		try {
			out = new PrintWriter("lexicon.txt");
			for(int i = 0; i < finalLexicon.size(); i ++)
			{
				out.println(finalLexicon.get(i).word + " " + finalLexicon.get(i).polarity);
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		System.out.println("Done");
	}

	 private static String getUrlSource(String url, int polarity) throws IOException {
         URL yahoo = new URL(url);
         URLConnection yc = yahoo.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(
                 yc.getInputStream(), "UTF-8"));
         String inputLine;
         StringBuilder a = new StringBuilder();
         while ((inputLine = in.readLine()) != null)
         {
         a.append(inputLine);
	         if(inputLine.contains("<div><strong>Synonyms</strong>"))
	         {
	        	 // Now we are in the right line--How do we get to just the text that we want?
	        	 String[] splitLine = inputLine.split(">");
	        	 boolean finding = false;
	        	 for(String s : splitLine)
	        	 {
	        		 if(!finding)
	        		 {
		        		 if(s.contains("Syn"))
		        		 {
		        			// We are in the right ball park
		        			 finding = true;
		        		 }
	        		 }
	        		 else
	        		 {
	        			 if(s.contains("<strong"))
	        			 {
	        				 break;
	        			 }
	        			 if(s.contains("dictionary"))
	        			 {
	        				 String[] miniUrl = s.split("/");
	        				 
	        				 String word = miniUrl[miniUrl.length -1];
	        				 
	        				 word = word.substring(0, word.lastIndexOf('"'));
	        				 word = word.replace('+', '-');
	        				 word = word.trim();
	        				 if(word.lastIndexOf('-')== word.length() -1)
	        				 {
	        					 word = word.replace("-", "");
	        				 }
	        				 word = word.toLowerCase();
	        				 wordObject temp = new wordObject(word, polarity);
	        				 if(!finalLexicon.contains(temp) && !word.equals(""))
	        				 {
	        					 if(allWordsDictionary.containsKey(word))
	        					 {
	        						 if(allWordsDictionary.get(word).corpusFreq > 40)
	        						 {
	        							 continue;
	        						 }
	        					 }
	        				 finalLexicon.add(temp);
	        				 //System.out.println(word);
	        				 }
	        			 }
	        		 }
	        	 }
	         }
         }
         in.close();

         return a.toString();
     }
	 
	 
	 public static void getWordsFromGold()
	 {
		 // Build list of golden text documents
		 
		 try
		 {
		 //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("doclist.txt")), true);
		 //File directory = new File("C:\\Users\\Kelly\\workspace\\NLPFinalProject\\testset\\both");
		 
		 File directory = new File("./resources");
			 
		 ArrayList<String> fileList = new ArrayList<String>();
		 
		 if(directory.isDirectory())
		 {
			 String[] files = directory.list();
		 // foreach file in our source directory
			 for(String fileName : files)
			 {
				 if(fileName.contains("gold"))
				 {
					 fileList.add(directory + "/" + fileName);
					 //fileList.add(directory + "\\" + fileName);
					 
					 //System.out.println("resources/" + fileName);
					 
				 }
				 else if(fileName.contains(".txt"))
				 {
					 originalFiles.add(directory + "/" + fileName);
					// originalFiles.add(directory + "\\" + fileName);
					 //System.out.println("resources/" + fileName);
					 //out.println("/home/cjjohnso/Desktop/NLPFinalProject/testset/" + fileName);

					 // out.println("C:\\Users\\Kelly\\workspace\\NLPFinalProject\\testset\\" + fileName);	 
				 }
			 }
		 }
		// out.flush();
		// out.close();

		 goldFiles = fileList;
		 // Foreach document
		 for(int i = 0; i < fileList.size(); i ++)
		 {
			 // Read a line
			    FileReader fr;
				try {
						fr = new FileReader(fileList.get(i));
						Scanner scan = new Scanner(fr);
						while(scan.hasNextLine() != false)
						{
							String line = scan.nextLine();
							
							String[] splitLine = line.split(" ");
							
							
							if(splitLine.length > 6)
							{
								// phrase,
								String polarity = splitLine[2];
								String phrase = "";
								for(int x = 4; x < splitLine.length; x ++)
								{
									phrase += splitLine[x];
									if(x != splitLine.length)
									{
										phrase += " ";
									}
								}
								phrase = phrase.substring(1);
								goldPhrase temp = new goldPhrase(phrase, polarity);
								
								if(!goldPhraseDictionary.keySet().contains(phrase))
								{
								goldPhraseDictionary.put(phrase, temp);
								}
								else
								{
									goldPhraseDictionary.get(phrase).subjFreq ++;
								}
							
							}
							else
							{
								// word,
								String word = splitLine[5];
								if(word.contains("\""))
								{
								word = word.replaceAll("\"", "");
								
								}
								word = word.replaceAll("!", "");
								wordObject temp = new wordObject(word.toLowerCase(), splitLine[2]);
								
								
								if(!goldWords.contains(temp))
									{
									goldWords.add(temp);
									}
								else
								{
									goldWords.get(goldWords.indexOf(temp)).subjFreq ++;
								}
								
								if(temp.word.contains("ly"))
								{
									
									temp.word = temp.word.replace("ly", "");
									temp.subjFreq = 0;
									if(!goldWords.contains(temp))
									{
									goldWords.add(temp);
									}
									else
									{
										goldWords.get(goldWords.indexOf(temp)).subjFreq ++;
									}
								}
								// Simple morphology?
								
								
							}
							
						}
						
						scan.close();
					
					} 
					catch (FileNotFoundException e)
					{
					e.printStackTrace();
					}
			    
			 // Add the opinion phrase/word to our list of words to check.
				
		 }
		 
			for(int i = 0; i < goldWords.size(); i ++)
			{
				goldDictionary.put(goldWords.get(i).word, goldWords.get(i));
			}
		 }
		 catch(Exception e)
		 {
			 
		 }

	 }

	 public static void refineGoldPhrases()
	 {
		 for(String file: originalFiles)
		 {
			 FileReader fr;
				try {
						fr = new FileReader(file);
						Scanner scan = new Scanner(fr);
						
						String line = "";
					while(scan.hasNextLine() != false)
					{
						line = scan.nextLine();
						for(String phrase : goldPhraseDictionary.keySet())
						{
							if(line.contains(phrase))
							{
								goldPhraseDictionary.get(phrase).corpusFreq ++;
							}
						}
					}
					System.out.println("Finished: " + file);
					fr.close();
				}
				catch(Exception e)
				{
					
				}
		 }
		 
		 ArrayList<String> toRemove = new ArrayList<String>();
		 int x = 0;
		 for(String phrase : goldPhraseDictionary.keySet())
		 {
			if(goldPhraseDictionary.get(phrase).corpusFreq/goldPhraseDictionary.get(phrase).subjFreq > 5)
			{
				toRemove.add(phrase);
				System.out.println("Rejected: " + phrase + " with a frequency of: " + goldPhraseDictionary.get(phrase).corpusFreq);
				System.out.println("subjective Freq: " + goldPhraseDictionary.get(phrase).subjFreq);
			}

			x++;
		 }
		 
		 for(String remove: toRemove)
		 {
			 goldPhraseDictionary.remove(remove);
		 }
	 }

	 public static void refineGold()
	 {
		 // count each word in each file.
		 for(String file : originalFiles)
		 {
			 FileReader fr;
				try {
						fr = new FileReader(file);
						Scanner scan = new Scanner(fr);
						
						String line = "";
					while(scan.hasNextLine() != false)
					{
						String[] splitLine = scan.nextLine().split(" ");
						
						for(int i = 0; i < splitLine.length; i ++)
						{
							if(goldDictionary.containsKey(splitLine[i]))
							{
								goldDictionary.get(splitLine[i]).corpusFreq ++;
								
								// Get the surrounding words!
								for(int z = 5; z > 0; z --)
								{
									if(i-z < 0)
									{
										break;
									}
									if(surroundingSubjectiveWordsDictionary.containsKey(splitLine[i - z].toLowerCase().trim()))
									{
										surroundingSubjectiveWordsDictionary.get(splitLine[i-z].toLowerCase().trim()).corpusFreq ++;
									}
									else
									{
										surroundingSubjectiveWordsDictionary.put(splitLine[i -z].toLowerCase().trim(), new wordObject(splitLine[i-z].toLowerCase().trim(), goldDictionary.get(splitLine[i]).polarity));
									}
								}
								
								for(int z = 0; z < 5; z ++)
								{
									if(i + z >= splitLine.length)
									{
										break;
									}
									if(surroundingSubjectiveWordsDictionary.containsKey(splitLine[i + z].toLowerCase().trim()))
									{
										surroundingSubjectiveWordsDictionary.get(splitLine[i+z].toLowerCase().trim()).corpusFreq ++;
									}
									else
									{
										surroundingSubjectiveWordsDictionary.put(splitLine[i +z].toLowerCase().trim(), new wordObject(splitLine[i+z].toLowerCase().trim(), goldDictionary.get(splitLine[i]).polarity));
									}
								}
							}
							if(!allWordsDictionary.containsKey(splitLine[i]))
							{
								wordObject temp = new wordObject(splitLine[i], "unknown");
								allWordsDictionary.put(splitLine[i], temp);
							}
							else
							{
								allWordsDictionary.get(splitLine[i]).corpusFreq ++;
							}
						}
					}
					System.out.println("Finished: " + file);
					fr.close();
				}
				catch(Exception e)
				{
					
				}
		 }
		 ArrayList<String> toRemoveSurrounding = new ArrayList<String>();
		 for(String x: surroundingSubjectiveWordsDictionary.keySet())
		 {
			 System.out.println("Real word: " + x);
			 System.out.println("Word: " + surroundingSubjectiveWordsDictionary.get(x).word);
			 System.out.println("Frequency: " + surroundingSubjectiveWordsDictionary.get(x).corpusFreq);
			 System.out.println("");
			 if(surroundingSubjectiveWordsDictionary.get(x).corpusFreq < 5)
			 {
				 toRemoveSurrounding.add(x);
			 }
		 }
		 for(String remove : toRemoveSurrounding)
		 {
			 surroundingSubjectiveWordsDictionary.remove(remove);
		 }
		 ArrayList<String> toRemove = new ArrayList<String>();
		 for(String x : goldDictionary.keySet())
		 {
			 System.out.println("Word: " + goldDictionary.get(x).word);
			 System.out.println("Frequency: " + goldDictionary.get(x).corpusFreq);
			 System.out.println("Subjective Freq: " + goldDictionary.get(x).subjFreq);
			 boolean keepMe = all.keeper(x, goldDictionary.get(x).corpusFreq, goldDictionary.get(x).subjFreq, .33);
			 System.out.println(keepMe);
			 System.out.println("");
			 
			 if(!keepMe)
			 {
				toRemove.add(x);
			 }
			 
		 }
		 for(String remove : toRemove)
		 {
			 goldDictionary.remove(remove);
		 }
	 }
}

