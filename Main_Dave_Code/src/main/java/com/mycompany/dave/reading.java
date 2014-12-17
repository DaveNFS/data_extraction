import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class reading {
	public static String sents[][]=new String[1000][1000];
	public static String tags[][]=new String[1000][1000];
	public static int number_of_sentences=0;
	
public static void create_array(String file_name ) {
		
	sents = new String[1000][1000];
	tags = new String[1000][1000];
	
		try{
		FileReader fr= new FileReader(file_name);
		
		
		
		String line=null;
		Scanner scan = new Scanner(fr);
		int line_var=0;
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			if(line == null || line.equals("\t") || line.trim().equals(""))
			{
				continue;
			}
			number_of_sentences= number_of_sentences+1;
			line = line.trim();
			String parts[]= line.split("\\s");
			int j=0;
			
			for(String part:parts){
				
				int word_length=part.length();
				if(word_length < 1)
				{
					continue;
				}
				if(part.charAt(word_length-1)=='.') part=part.replace('.', ' '); 
				if(part.charAt(word_length-1)==',') part=part.replace(',', ' ');
				
				sents[line_var][j] = part;
				j++;  
				
			}
			
			line_var++;
		}
		fr.close();
	
		
		}catch(Exception e){System.err.println(e.getStackTrace());}
		
	}



public static void print_read(){
	
	for(int i=0; i<1000; i++){
		for(int j=0; j<1000; j++){
			if(sents[i][j]!=null) System.out.print(sents[i][j]+" ");
		}
	if(sents[i][0]!=null)System.out.println();
	
	}
}


public static void print_tags(){
	
	for(int i=0; i<1000; i++){
		for(int j=0; j<1000; j++){
			if(tags[i][j]!=null) System.out.print(tags[i][j]+" ");
		}
	if(tags[i][0]!=null)System.out.println();
	
	}
}

// this is the class closing braces
}
