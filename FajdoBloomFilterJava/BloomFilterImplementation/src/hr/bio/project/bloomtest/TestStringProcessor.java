package hr.bio.project.bloomtest;

public class TestStringProcessor {
	private String fastaString;
	private int fastaSize;

	public TestStringProcessor(String fastaString) {
		this.fastaString = fastaString;
		this.fastaSize = fastaString.length();
	}
	
	public void processFastaString(TestBloomFilter bloomey) {
		
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
