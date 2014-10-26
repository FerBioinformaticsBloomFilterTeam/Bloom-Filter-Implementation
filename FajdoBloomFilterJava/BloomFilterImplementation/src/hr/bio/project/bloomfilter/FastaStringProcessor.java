package hr.bio.project.bloomfilter;


public class FastaStringProcessor {
	private String fastaString;
	private int fastaSize;

	public FastaStringProcessor(String fastaString) {
		this.fastaString = fastaString;
		this.fastaSize = fastaString.length();
	}
	
	public void processFastaString(BloomFilter bloomey) {
		
		int fastaSizeCopy = fastaSize;
		int number=0;
		String fastaCut;
		
		while (fastaSizeCopy>0) {
			if (fastaSizeCopy<20) {
				fastaCut = fastaString.substring(0+number*20, fastaSize);
			} else {
				fastaCut = fastaString.substring(0+number*20, 20+number*20);
			}
			number++;
			fastaSizeCopy=fastaSizeCopy-20;
			//System.out.println(fastaCut);
			bloomey.addElemToBloom(fastaCut);
		}
	}
	
}
