import java.io.*;
import java.util.Scanner;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class pos {
	
	public static void doPOS2(String fileName){
		
		try{
			POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
			POSTaggerME tagger= new POSTaggerME(model);
			
			 FileReader fr= new FileReader(fileName);
			 Scanner scan = new Scanner(fr);
			 
			    String line= null; 
			    int line_var=0;
			    while(scan.hasNextLine())
			    {
			    	line = scan.nextLine();
			    	if(line == null || line.equals("\t") || line.trim().equals(""))
			    	{
			    		continue;
			    	}
			    	 String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			         String[] tags = tagger.tag(whitespaceTokenizerLine);
			         
			         for(int j=0; j< tags.length; j++) reading.tags[line_var][j]= tags[j]; 
			         line_var++;
			    	
			    }
			
			
		}catch(Exception e){e.printStackTrace();}
	}

	
	
	/*
public static void doPOS(){
	try{
	POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
    PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
    POSTaggerME tagger = new POSTaggerME(model);
    perfMon.start();
    
    
    BufferedReader br= new BufferedReader(new FileReader("C:/Users/dave/Desktop/sample_text.txt"));
    String sentence= null; 
    int line_var=0;
    while((sentence=br.readLine())!=null){
    
    
    // String input = "Can anyone help me dig through OpenNLP's horrible documentation?";
   String input=sentence; 
    ObjectStream<String> lineStream =
            new PlainTextByLineStream(new StringReader(input));

    
    String line;
    while ((line = lineStream.read()) != null) {

        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
        String[] tags = tagger.tag(whitespaceTokenizerLine);
        
        for(int j=0; j< tags.length; j++) reading.tags[line_var][j]= tags[j]; 
        line_var++;
        //System.out.print(tags[j]+" ");
        POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
      //  System.out.println(sample.toString());

        perfMon.incrementCounter();
    }
    perfMon.stopAndPrintFinalResult(); 
    
    // end of while loop 
    }
    
    
	}catch (Exception e){e.printStackTrace();}
   
}	
  */
	
	
	
	
	
	
	
	

// this is the class closing braces 
}
