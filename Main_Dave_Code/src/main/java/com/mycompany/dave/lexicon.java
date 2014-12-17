import java.io.BufferedReader;
import java.io.FileReader;

public class lexicon {
	public static int lexiconCount=0;
	public static String lexicon[][];
	
	
	
	// just counting the number of words in the lexicon for array indexes
	public static void countWordsInLex(String lexicon_file_name){
		try{
			BufferedReader br = new BufferedReader(new FileReader(lexicon_file_name));
			String line=null;
			while((line=br.readLine())!=null) lexiconCount++;
			
		}catch(Exception e){e.printStackTrace();}
		
		lexicon=new String[lexiconCount][2];
	}
	
	
	
	
	
	// read the lexicon file into an array 
	public static void readLexicon(String lexicon_file_name){
		try{
			BufferedReader br= new BufferedReader(new FileReader(lexicon_file_name));
			
			
			
			String line=null;
			int line_var=0;
			while((line=br.readLine())!=null){
				String parts[]= line.split("\\s");
		
				
           lexicon[line_var][0]=parts[0];
           lexicon[line_var][1]=parts[1];	    
				 line_var++;
			}
		
	} catch(Exception e){e.printStackTrace();}
		}
	
	
	
	
	// searches the lexicon for a word and returns whether positive or negative 
	public static String checkAndReturn(String input){
		
		boolean present =false; 
		for(int i=0; i<lexiconCount; i++){
			if(lexicon[i][0].equalsIgnoreCase(input))
			{
				present= true; 
			return lexicon[i][1];
			}
			
		}
		return null; 
	}
	
	
	// just prints out the lexicon array for debugging purposes 
	public static void printLexicon(){
		for(int i=0; i<lexiconCount; i++){
			for(int j=0; j<2; j++){
				System.out.print(lexicon[i][j]+"   ");
			}
		System.out.println();
		}
	}
	
	
	
	
//this is the class closing braces 
}
