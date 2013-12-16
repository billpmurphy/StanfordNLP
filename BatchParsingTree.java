import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class BatchParsingTree {
	static BufferedReader br;
	static BufferedWriter bw;
	static ArrayList<String> rows;
	static Tree[] parsed;
	static Collection[] typedDeps;

	/**
	 * Run the Stanford NLP tree parser on all lines in a file
	 * @param args
	 * args[0] = input file
	 * args[1] = output file
	 */
	public static void main(String[] args) {	
		try {
			//Read from file
			rows = new ArrayList<String>();
			br = new BufferedReader(new FileReader(args[0]));
			String line;
			while ((line = br.readLine()) != null) {
				rows.add(line);
			} br.close();
			
			
			//initialize parser
			LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
			TreebankLanguagePack tlp = new PennTreebankLanguagePack();
			GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
			
			
			parsed = new Tree[rows.size()];
			typedDeps = new Collection[rows.size()];
			for(int i = 0; i < rows.size(); i++){
				parsed[i] = lp.apply(rows.get(i));
				typedDeps[i] = gsf.newGrammaticalStructure(parsed[i]).typedDependenciesCCprocessed(true);
			}
			
			//Write to file
			File writeFile = new File(args[1]);
			if (!writeFile.exists()) {
				writeFile.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(writeFile));
			for (int i = 0; i < rows.size(); i++) {
				bw.write(parsed[i].toString() + "\n" + typedDeps[i].toString() + "\n");
			} bw.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	 
	}
}
