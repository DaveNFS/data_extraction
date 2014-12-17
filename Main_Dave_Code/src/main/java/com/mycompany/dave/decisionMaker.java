
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class finalProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	
		
		String outputDir = args[1];
		String docListFile = args[0];
		
		ArrayList<String> inputFiles = new ArrayList<String>();
		
		System.out.println("Document List File: " + docListFile);
		
		String lexiconFile = "lexicon.txt";
		
		  lexicon.countWordsInLex(lexiconFile);
		  lexicon.readLexicon(lexiconFile);
		  //lexicon.printLexicon();
		  
		    FileReader fr;
			try 
			{
					fr = new FileReader((String) docListFile);
					Scanner scan = new Scanner(fr);
					while(scan.hasNextLine() != false)
					{
						inputFiles.add(scan.nextLine());
					}
					fr.close();
			}
			catch(Exception e)
			{
				
			}
		  
			
			//LexiconBuilder.getWordsFromGold("/home/cjjohnso/Desktop/NLPFinalProject/testset/");
			LexiconBuilder.getWordsFromGold();
			LexiconBuilder.refineGold();
			LexiconBuilder.refineGoldPhrases();
			
		  // Foreach file in our
			try
			{
				PrintWriter out = new PrintWriter("sysfiles.txt");
				
				
			System.out.println("Ready to begin!");
				
			
			for(String file : inputFiles)
			{
		  
				System.out.println("Processing: " + file );
				reading.create_array(file);
				//reading.print_read();			
				pos.doPOS2(file);
				//reading.print_tags();
				
				//sentenceExplorer.explore(reading.sents, reading.tags, file);
				
				opinionFinder.getOpinions(reading.sents, outputDir, file);
				
				String temp = new File(file).getName();
				temp = temp.replace(".txt", ".sys");
				out.println(outputDir + temp);
				out.flush();
			}
				//sentenceExplorer.showTagSet();
				out.close();
			}
			catch(Exception e)
			{
				System.out.println("HELP!");
			}
			

	}
}

