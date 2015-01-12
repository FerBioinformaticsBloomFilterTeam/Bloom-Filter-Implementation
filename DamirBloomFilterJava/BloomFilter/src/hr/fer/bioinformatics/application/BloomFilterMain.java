package hr.fer.bioinformatics.application;

import java.util.Scanner;

import hr.fer.bioinformatics.bloomfilter.BloomFilter;

public class BloomFilterMain {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		System.out.println("\n#######################################################################\n"
				+ "##This application will show bloom filter capabilities and its flaws!##\n"
				+ "#######################################################################\n\n"
				+ "#################################\n"
				+ "##Bloom filter usage, Example 1##\n"
				+ "#################################\n\n"
				+ "--> This example will show main bloom filter flaw.\n"
				+ "    We will initialise bloom filter with size 10 and 3 hash functions\n    (remeber that bloom filter can be used for much bigger scale, this is just for an example)");
	
		System.out.println("\n    Initialising bloom filter with size 10 and 3 hash functions\n");
		BloomFilter bloomFilter = new BloomFilter(10, 3);
		
		System.out.println("****Press enter to continue...");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		
		System.out.println("--> Next, we will add several words in bloom filter.\n"
				+ "    Adding words:\n    'Bloom',\n    'Mouse',\n    'Plane',\n    'Spoon'\n");
		String[] addWords = new String[]{"Bloom","Mouse","Plane", "Spoon"};
		for (String word : addWords) {
			bloomFilter.addWord(word);
		}
		
		System.out.println("--> Words are hashed by each of the hash functions in bloom filter.\n"
				+ "    Every hash function returns a number in range from 0 to size of bloom filter\n"
				+ "    Returned number is position in bloom filter that is set to 1.\n");
		
		System.out.println("****Press enter to continue...");
		keyboard.nextLine();
		
		System.out.println("--> Now, lets check bloom filter for words that we added and some other words.\n"
				+ "    Words:\n    'Bloom',\n    'Mouse',\n    'Plane',\n    'Spoon',\n    'Python',"
				+ "    \n    'Java',\n    'School',\n    'Monkey'\n");
		System.out.println("--> Words will be processed by hash fucntions in the same manner as it was with adding words.\n"
				+ "    Returned number is position in bloom filter that will be checked for value.\n"
				+ "    If every returned number position check results in 1, word is in bloom filter (probably), otherwise it is not(for sure)\n");
		
		System.out.println("****Press enter to check words...");
		keyboard.nextLine();
		
		String[] testWords = new String[]{"Bloom", "Mouse", "Plane", "Spoon", "Python", "Java", "School", "Monkey"};
		boolean found;
		for (String word : testWords) {
			found = bloomFilter.isInFilter(word);
			if (found == true) {
				System.out.println("    Word: '" + word + "' is found in bloom filter");
				continue;
			}
			System.out.println("    Word: '" + word + "' is not found in bloom filter");
		}
		
		System.out.println("\n****Press enter to continue...");
		keyboard.nextLine();
		
		System.out.println("--> As we can see, every word that we added was found in bloom filter.\n"
				+ "    But, we can also see that word 'School' was found, even though it was't added.\n"
				+ "    Bloom filter property is that if it returns 'false', on word check, we know for sure that word is not added in filter.\n"
				+ "    But if it returns 'true' we can not be sure if it was added with 100% certainty, \n"
				+ "    We only know that it is pretty possible that it is added.\n"
				+ "    For more information why is it like this, check Bloom filter in wikipedia.\n");
		
		System.out.println("****Press enter to continue to example 2...\n");
		keyboard.nextLine();
		
		System.out.println("#################################\n"
				+ "##Bloom filter usage, Example 2##\n"
				+ "#################################\n\n"
				+ "--> This example will show bloom filter usage with optimal parameters used.\n"
				+ "    We will initialise bloom filter with optimal parameters\n"
				+ "    We will calculate the bloom filter size and number of hash functions with foreseed number of words that will be added and false positive rate.\n"
				+ "    We will add 4 words and set false positive rate to 2%\n");
		
		System.out.println("****Press enter to continue...");
		keyboard.nextLine();
	
		System.out.println("--> Calculating optimal bloom filter parameters...");
		int sizeOfBloomFilter = BloomFilter.getSizeOfBloomFilter(4, 0.02);
		int numberOfHashFunctions = BloomFilter.getNumberOfHashFunctions(sizeOfBloomFilter, 4);
		
		System.out.println("    Initialising bloom filter with size " + sizeOfBloomFilter + " and " + numberOfHashFunctions + " hash functions.\n");
		bloomFilter = new BloomFilter(sizeOfBloomFilter, numberOfHashFunctions);
		
		System.out.println("****Press enter to continue...");
		keyboard.nextLine();
		
		System.out.println("--> Next, we will again add seme 4 words in bloom filter.\n"
				+ "    Adding words:\n    'Bloom',\n    'Mouse',\n    'Plane',\n    'Spoon'\n");
		for (String word : addWords) {
			bloomFilter.addWord(word);
		}
		
		System.out.println("\n****Press enter to continue...");
		keyboard.nextLine();
		
		System.out.println("--> Now, lets check bloom filter again with same checking words as last time.\n"
				+ "    Words:\n    'Bloom',\n    'Mouse',\n    'Plane',\n    'Spoon',\n"
				+ "    'Python',\n    'Java',\n    'School',\n    'Monkey'\n");

		System.out.println("****Press enter to check words...");
		keyboard.nextLine();
		
		for (String word : testWords) {
			found = bloomFilter.isInFilter(word);
			if (found == true) {
				System.out.println("    Word: '" + word + "' is found in bloom filter");
				continue;
			}
			System.out.println("    Word: '" + word + "' is not found in bloom filter");
		}
		
		System.out.println("\n****Press enter to continue...");
		keyboard.nextLine();
		
		System.out.println("--> As we can see, every word that we added was found in bloom filter.\n"
				+ "    But, this time, there were no other words that were proclaimed found.\n"
				+ "    That is because we initialise bloom filter with optimal parameters and really low false positive probability\n");
		
		System.out.println("****Press enter to exit application...");
		keyboard.nextLine();
	}
	
}
