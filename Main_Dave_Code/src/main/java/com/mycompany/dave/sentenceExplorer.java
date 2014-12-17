import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class sentenceExplorer
{

	public static Map<String, tagRule> tagRuleMap = new HashMap<String, tagRule>();
	
	public static void showTagSet()
	{
		try
		{
			PrintWriter out = new PrintWriter("POSRules.txt");
			
		for(String x: tagRuleMap.keySet())
		{
			if(tagRuleMap.get(x).Subjectivefrequency > 10)
			{
			System.out.println( tagRuleMap.get(x).phrase);
			System.out.println( "Subj Freq " + tagRuleMap.get(x).Subjectivefrequency);
			System.out.println( "NonSubj Freq " + tagRuleMap.get(x).NotSubjectivefrequency);
			System.out.println("");
			out.println(tagRuleMap.get(x).phrase);
			}
		}
		out.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public static void check(String[][] sentences, String[][] tags, ArrayList<String> goldenPhrases)
	{
		try
		{
		// foreach sentence
		String[] curSentence;
		String[] curTags;
		for(int i = 0; i < sentences.length; i ++)
		{
			curSentence = sentences[i];
			if(curSentence[0] == null)
				continue;
			curTags = tags[i];
			
			for(int j = 0; j < curSentence.length; j ++)
			{
				//System.out.println("J = " + j + " I = " + i);
				//System.out.println("curSentence[j] " + curSentence[j]);
				if(curSentence[j] ==null)
				{
					break;
				}
				boolean tagMatch = false;
				String myPhrase = "";
				String myTags = "";
				for(String goldenPhrase : goldenPhrases)
				{
					myTags = "";
					myPhrase = "";
					
					int phraseLength = goldenPhrase.split(" ").length;
					if(curSentence[j + phraseLength] == null)
					{
						continue;
					}
					else
					{
						
						
						for(int z = j; z < phraseLength + j; z ++)
						{
							myPhrase += curSentence[z] + " ";
							myTags += curTags[z] + " ";
						}
						if(myPhrase.trim().toLowerCase().equals(goldenPhrase.toLowerCase()))
						{
							// We have a hit!!!!
							
							tagRule temp = new tagRule(myPhrase, 0,0);
							for(int z = j; z < phraseLength + j; z ++)
							{
								//System.out.print(curSentence[z] + " ");
							}
							System.out.print("\n");
							for(int z = j; z < phraseLength + j; z ++)
							{
								//System.out.print(curTags[z] + " ");
							}
							//System.out.print("\n");
							
							if(tagRuleMap.containsKey(myTags))
							{
								tagRuleMap.get(myTags).Subjectivefrequency ++;
							}
							else
							{
							tagRule tempRule = new tagRule(myTags, 1, 0);
							tagRuleMap.put(myTags, tempRule);
							//System.out.println("Trying to put: " + myTags);
	
							}
							continue;
						}
						if(tagMatch == false)
						{
							if(tagRuleMap.containsKey(myTags))
							{
								tagRuleMap.get(myTags).NotSubjectivefrequency ++;
							}
							else
							{
							tagRule temp = new tagRule(myTags, 0, 1);
							tagRuleMap.put(myTags, temp);
							//System.out.println("Trying to put: " + myTags);
							}
						}
					}
				}

			}
		}
		System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static ArrayList<String> getGoldenPhrases(String fileName)
	{
		ArrayList<String> goldenPhrases = new ArrayList<String>();
		
		try{
		FileReader fr= new FileReader(fileName);
		
		String line=null;
		Scanner scan = new Scanner(fr);
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			String[] splitLine = line.split(" ");
			String newPhrase = "";
			
			for(int pos = 5; pos < splitLine.length; pos ++)
			{
				newPhrase += splitLine[pos] + " ";
			}
			goldenPhrases.add(newPhrase.trim());
		}
		}
		catch(Exception e)
		{
			// oops
		}
		
		return goldenPhrases;
	}

	public static void explore(String[][] sentences, String[][] tags, String fileName)
	{
		// Create the right filename
		fileName = fileName.replace(".txt", ".gold");
		ArrayList<String> goldenPhrases = getGoldenPhrases(fileName);
		check(sentences, tags, goldenPhrases);
		
	}
}
