package hr.bio.project.bloomtest;

import java.util.ArrayList;
import java.util.List;

public class TestWorker {
public static void main(String[] args) {
		
		long tm0 = System.currentTimeMillis();
		String fastaAll;
		int falsePositives = 0;
		List<String> testLista = new ArrayList<String>();
		TestFileReader readFasta = new TestFileReader(args[0], args[1]);
		fastaAll = readFasta.getFastaString();
		testLista = readFasta.getTestList();
		long tm1 = System.currentTimeMillis();
		TestStringProcessor processor = new TestStringProcessor(fastaAll);
		TestBloomFilter bloomsky = new TestBloomFilter(fastaAll.length());
		processor.processFastaString(bloomsky);
		long tm2 = System.currentTimeMillis();
		
		for (int i=0; i<testLista.size(); i++) {
			String[] dva = new String[2];
			dva = testLista.get(i).split("\t");
			if (bloomsky.testElemInBloom(dva[0]).equals(dva[1])) {
				//System.out.println(bloomsky.testElemInBloom(dva[0]));
				//System.out.println(dva[1]);
			} else {
				falsePositives++;
			}
		}
			System.out.println(falsePositives);
			System.out.println(tm1 - tm0);
			System.out.println(tm2 - tm1);
	}
}
