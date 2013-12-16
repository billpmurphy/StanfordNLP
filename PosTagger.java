import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosTagger {
	static MaxentTagger tagger;
		
	/**
	 * Run the Stanford Part-of_speech tagger on a single line, and print to the console
	 * @param args[0] = the line of text to read
	 */
	public static void main(String[] args){
	   
		//Initialize tagger
		try {
			tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 

        //Tag
        String tagged = tagger.tagString(args[0]);
		System.out.println(tagged);
	}
}