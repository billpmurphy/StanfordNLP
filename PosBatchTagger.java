import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosBatchTagger {
	static MaxentTagger tagger;
	static BufferedReader br;
	static BufferedWriter bw;
	static ArrayList<String> rows;
		
	/**
	 * Run the Stanford NLP Part-of-Speech tagger on all lines in a file
	 * @param args
	 * args[0] = input file
	 * args[1] = output file
	 */
	public static void main(String[] args){
				
		//Read from file
		try {
			rows = new ArrayList<String>();
			br = new BufferedReader(new FileReader(args[0]));
			String line;
			while ((line = br.readLine()) != null) {
				rows.add(line);
			} br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		//Initialize tagger
		try {
			tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
		
		//Tag
		for(int i = 0; i < rows.size(); i++){
			rows.set(i, tagger.tagString(rows.get(i)));
		}
				
		//Write to file
		try {
			File writeFile = new File(args[1]);
			if (!writeFile.exists()) {
				writeFile.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(writeFile));
			for (String row : rows) {
				bw.write(row + "\n");
			} bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
}

