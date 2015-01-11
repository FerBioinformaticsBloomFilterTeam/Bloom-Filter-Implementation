package hr.bio.project.bloomtest;

public class TestWorker {
public static void main(String[] args) {
		
		String fastaAll;
		TestFileReader readFasta = new TestFileReader(args[0]);
		fastaAll = readFasta.getFastaString();
		TestStringProcessor processor = new TestStringProcessor(fastaAll);
		TestBloomFilter bloomsky = new TestBloomFilter(fastaAll.length());
		processor.processFastaString(bloomsky);
		if (bloomsky.testElemInBloom("AGATATGGAACGATTTACTC")) {
			System.out.println("Probably in");
		}
	}
}
