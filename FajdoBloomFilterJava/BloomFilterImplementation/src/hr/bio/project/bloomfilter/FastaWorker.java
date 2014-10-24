package hr.bio.project.bloomfilter;

public class FastaWorker {

	public static void main(String[] args) {
		
		String fastaAll;
		FastaFileReader readFasta = new FastaFileReader(args[0]);
		fastaAll = readFasta.getFastaString();
		FastaStringProcessor processor = new FastaStringProcessor(fastaAll);
		processor.processFastaString();
		
	} 

}
