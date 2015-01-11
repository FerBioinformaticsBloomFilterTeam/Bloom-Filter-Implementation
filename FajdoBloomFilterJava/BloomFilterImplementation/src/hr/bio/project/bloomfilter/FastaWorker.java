package hr.bio.project.bloomfilter;

public class FastaWorker {
	
	public static void main(String[] args) {
		
		String fastaAll;
		FastaFileReader readFasta = new FastaFileReader(args[0]);
		fastaAll = readFasta.getFastaString();
		FastaStringProcessor processor = new FastaStringProcessor(fastaAll);
		BloomFilter bloomsky = new BloomFilter(fastaAll.length());
		processor.processFastaString(bloomsky);
		if (bloomsky.testElemInBloom("AGATATGGAACGATTTACTC")) {
			System.out.println("Probably in");
		}
	}


}
