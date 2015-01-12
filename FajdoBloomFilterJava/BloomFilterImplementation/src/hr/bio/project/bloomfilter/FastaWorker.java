package hr.bio.project.bloomfilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FastaWorker {
	
	public static void main(String[] args) {
		
		// Initializing the filter
		BloomFilter bloomsky = new BloomFilter(100, "0.1");
		System.out.println("Inserting sequence: Adenosine, press enter to continue");
		bloomsky.addElemToBloom("Adenosine");
		pressEnter();
		System.out.println("Inserting sequence: is, press enter to continue");
		bloomsky.addElemToBloom("is");
		pressEnter();
		System.out.println("Inserting sequence: a, press enter to continue");
		bloomsky.addElemToBloom("a");
		pressEnter();
		System.out.println("Inserting sequence: purine, press enter to continue");
		bloomsky.addElemToBloom("purine");
		pressEnter();
		System.out.println("Inserting sequence: base, press enter to continue");
		bloomsky.addElemToBloom("base");
		pressEnter();
		
		// Testing the filter
		System.out.println("Testing sequence: Adenosine");
		if (bloomsky.testElemInBloom("Adenosine")) {
			System.out.println("Sequence is probably in the filter, press enter to continue");
		} else {
			System.out.println("Sequence is not in the filter, press enter to continue");
		}
		pressEnter();
		System.out.println("Testing sequence: purine");
		if (bloomsky.testElemInBloom("purine")) {
			System.out.println("Sequence is probably in the filter, press enter to continue");
		} else {
			System.out.println("Sequence is not in the filter, press enter to continue");
		}
		pressEnter();
		System.out.println("Testing sequence: About to finish");
		if (bloomsky.testElemInBloom("About to finish")) {
			System.out.println("Sequence is probably in the filter, press enter to continue");
		} else {
			System.out.println("Sequence is not in the filter, press enter to continue");
		}
		pressEnter();
		System.out.println("Done");
	}

	private static void pressEnter() {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(System.in));
			input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
