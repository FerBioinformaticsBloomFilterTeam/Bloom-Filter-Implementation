package hr.bio.project.bloomtest;

import java.util.ArrayList;
import java.util.List;

public class TestWorker {
public static void main(String[] args) {
		
		int memorija = 0;
		String fastaAll;
		int falsePositives = 0;
		
		// reading files
		List<String> testLista = new ArrayList<String>();
		TestFileReader readFasta = new TestFileReader(args[0], args[1]);
		long tm0 = System.currentTimeMillis();
		fastaAll = readFasta.getFastaString();
		long tm1 = System.currentTimeMillis();
		testLista = readFasta.getTestList();
		long tm2 = System.currentTimeMillis();
		
		// initializing the bloom filter
		TestStringProcessor processor = new TestStringProcessor(fastaAll);
		TestBloomFilter bloomsky = new TestBloomFilter(fastaAll.length(), args[2]);
		processor.processFastaString(bloomsky);
		long tm3 = System.currentTimeMillis();
		
		// testing if the sequences are in the filter
		for (int i=0; i<testLista.size()/20; i++) {
			String[] dva = new String[2];
			dva = testLista.get(i).split("\t");
			if ((bloomsky.testElemInBloom(dva[0]).equals("1"))&&(dva[1].equals("0"))) {
				falsePositives++;
			}
		}
		long tm4 = System.currentTimeMillis();
		memorija = bloomsky.getMemory();
		
		// Prints the statistics
		System.out.print("Number of false positives: ");
		System.out.println(falsePositives);
		System.out.print("Bloom filter initialization time: ");
		System.out.print((tm1 - tm0) + (tm3 - tm2));
		System.out.println(" ms");
		System.out.print("Bloom filter testing time: ");
		System.out.print((tm2 - tm1) + (tm4 - tm3));
		System.out.println(" ms");
		System.out.print("Bloom filter memory usage: ");
		System.out.print(memorija);
		System.out.println(" B");
	}
}
