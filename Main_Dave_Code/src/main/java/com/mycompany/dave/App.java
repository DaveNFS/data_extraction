package com.mycompany.dave;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class App {
	
public static void doStuff() throws IOException 
{
    POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
    PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
    POSTaggerME tagger = new POSTaggerME(model);

    String input = "";
    ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

    perfMon.start();
    String line;
    while ((line = lineStream.read()) != null)
    {

        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
        System.out.println(sample.toString());

        perfMon.incrementCounter();
    }
    perfMon.stopAndPrintFinalResult();
}


	public void posTag(String fileName) throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		
		String line = null; 
		while((line = br.readLine()) != null)
		{
			System.out.println(line);
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("outputFile.txt")));
	}
	

}
