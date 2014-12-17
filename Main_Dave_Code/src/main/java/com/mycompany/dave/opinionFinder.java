import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class opinionFinder
{
	public int id = 0;
	
	
	public static void getOpinions(String[][] sents, String outputDir, String fileName)
	{
		// Cycle through the sentences
		
		all.generate_valid_tags("POSRules.txt");
		
		//reading.print_read();
		
		 all.subjective_phrases.clear();
		 all.subjective_phrases_value.clear();
		
		all.doPhrases();
		
		all.checkPhraseSubjective();
		
		
		
		//System.out.println("I have: " + all.subjective_phrases.size() + " subjective phrases");
		ArrayList<String> resultList = new ArrayList<String>();
		
		
		PrintWriter out;
		try 
		{
			fileName = new File(fileName).getName();
		       fileName = fileName.replace(".txt", ".sys");
			out = new PrintWriter(outputDir + fileName);
			int index = 0;
			for(String result : all.subjective_phrases)
			{	
				if(all.subjective_phrases_value.get(index).toString().equals("0"))
				{
					out.println(all.id + " " + "negative " +  result);
					System.out.println(all.id + " " + "negative " +  result);
				}
				else
				{
					out.println(all.id + " " + "positive " +  result);
					System.out.println(all.id + " " + "negative " +  result);
				}
				
				all.id ++;
			}
			out.flush();
			out.close();
		}
		catch(Exception e)
		{
			System.out.println("SOMETHING BLEW UP " + e.getMessage());
		}
	}
	
}
