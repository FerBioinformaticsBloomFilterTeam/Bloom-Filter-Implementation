package hr.fer.bioinformatics.performancetest;

import hr.fer.bioinformatics.bloomfilter.BloomFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * Test class for BloomFilter implementation performance. To use test bloom filer with this class there are several conditions.
 * First: create a folder where you would put your test cases. Second: create files in that folder with names:
 * words_added_1000_000.txt, words_added_100_000.txt, words_added_10_000.txt, words_added_1000.txt, words_added_1000.txt, words_added_100.txt
 * and files with name words_test_1000_000.txt, words_test_100_000.txt, words_test_10_000.txt, words_test_1000.txt, words_test_100.txt.
 * 
 * Third: words_added files must be structured so that first line is a word count number, and all the other lines are words meant to be inserted, 
 * Example:
 * 3
 * one
 * two
 * three
 * 
 * Fourth: words_test files must be structured so that every it contains word and indication if it has been already inserted in bloom filter,
 * 1 if it is, 0 if it isn't
 * Example:
 * one 1
 * four 0
 * three 1
 * two 1
 * seven 0
 * @author dciganovic
 *
 */
public class PerformanceTest {
	private static final int KB = 1024;
	
	public static void main(String[] args) {
		
		// -- Read arguments and check them
		if (args.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments, first argument is path to test_cases folder CONTENTS (not to the folder itself).\n"
					+ "Second argument is false positive rate\n"
					+ "Test cases folder must contain files named words_added_X and words_test_X, where is text from:"
					+ "1000_000.txt, 100_000.txt, 10_000.txt, 1000.txt, 100.txt");
			
		}
		
		String pathToTestCasesFolder = args[0];
		String[] wordsAddedFiles = new String[]{"words_added_100.txt", "words_added_1000.txt", 
				"words_added_10_000.txt", "words_added_100_000.txt", "words_added_1000_000.txt"};
		
		String[] wordsTestFiles = new String[]{"words_test_100.txt", "words_test_1000.txt", 
				"words_test_10_000.txt", "words_test_100_000.txt", "words_test_1000_000.txt"};
		
		double falsePositiveRate;
		
		try {
			falsePositiveRate = Double.parseDouble(args[1]);
		} catch (Exception e) {
			throw new NumberFormatException("Argument two type must be double");
		}
		
		if (falsePositiveRate > 0.7 || falsePositiveRate < 0.01) {
			throw new IllegalArgumentException("False positive rate must be between 0.01 and 0.7");
		}
		
		// Start testings
		StringBuilder performanceResult = new StringBuilder();
		performanceResult.append("\n---------------\n\n");
		for (int fileIndex = 0; fileIndex < wordsAddedFiles.length; fileIndex++) {
			try {
				
				performanceResult.append("Testing files: ").append(wordsAddedFiles[fileIndex]).append(", ").append(wordsTestFiles[fileIndex]).append("\n");
				BufferedReader brWrodsAdded = new BufferedReader(new FileReader(pathToTestCasesFolder + wordsAddedFiles[fileIndex]));
				BufferedReader brWordsTest = new BufferedReader(new FileReader(pathToTestCasesFolder + wordsTestFiles[fileIndex]));
				
				// Check first file format
				String line;
				int numberOfWords = 0;
				line = brWrodsAdded.readLine();
				if (line != null) {
					try {
						numberOfWords = Integer.parseInt(line);
					} catch (Exception e) {
						System.out.println("First line in file: " + pathToTestCasesFolder + wordsAddedFiles[fileIndex] + " is not a number!");
						performanceResult.append("Testing gone wrong!\n");
						continue;
					}
				}
				
				// Find optimal bloom filter parameters for given number of words and false positive rate
				long startInitTime = System.nanoTime();
				int sizeOfBloomFilter = BloomFilter.getSizeOfBloomFilter(numberOfWords, falsePositiveRate);
				int numberOfHashFunctions = BloomFilter.getNumberOfHashFunctions(sizeOfBloomFilter, numberOfWords);
				
				
				// Initialize bloom filter with words
				BloomFilter bloomFilter = new BloomFilter(sizeOfBloomFilter, numberOfHashFunctions);
				long memoryConsumption = sizeOfBloomFilter / 8 + 200;
				while ((line = brWrodsAdded.readLine()) != null) {
					if (line.isEmpty()) {
						break;
					}
					bloomFilter.addWord(line);
				}
				brWrodsAdded.close();
				long diff = System.nanoTime() - startInitTime;
				performanceResult.append("Time for initialisation: ").append(TimeUnit.MILLISECONDS.convert(diff, TimeUnit.NANOSECONDS)).append(" ms\n");
				
				// Check words in filter
				long startTestingTime = System.nanoTime();
				int numberOfWordsChecked = 0;
				int numberOfWordsCorrectlyClassified = 0;
				String[] wordAndClass;
				boolean result;
				while ((line = brWordsTest.readLine()) != null) {
					if (line.isEmpty()) {
						break;
					}

					wordAndClass = line.split("\t");
					result = bloomFilter.isInFilter(wordAndClass[0]);
					
					if (result && wordAndClass[1].equals("1") || !result && wordAndClass[1].equals("0")) {
						numberOfWordsCorrectlyClassified += 1;
					}
					numberOfWordsChecked += 1;
				}
				brWordsTest.close();
				long diff2 = System.nanoTime() - startTestingTime;
				performanceResult.append("Time for word checking: ").append(TimeUnit.MILLISECONDS.convert(diff2, TimeUnit.NANOSECONDS)).append(" ms\n");
				performanceResult.append("Number of words correctly classified / Number of words checked -> ")
					.append(numberOfWordsCorrectlyClassified).append(" / ").append(numberOfWordsChecked).append("\n");
				performanceResult.append("For ").append(numberOfWords).append(" words in init file, and twice as that in testing file, total time is: ").append(TimeUnit.MILLISECONDS.convert(diff + diff2, TimeUnit.NANOSECONDS)).append(" ms\n")
				.append("Memory Consumption: ").append(memoryConsumption).append(" bytes = ").append(memoryConsumption/KB).append(" kilobytes.").append("\n*******************\n\n");
			    
			} catch (Exception e) {
				System.out.println("One of the files with names: " + wordsAddedFiles[fileIndex] + ", " + wordsTestFiles[fileIndex]
						+ " in folder " + pathToTestCasesFolder + " does not exist, or path is wrong, or wrong file format");
				performanceResult.append("Testing gone wrong!\n");
				continue;
			}
		}
		
		System.out.print(performanceResult.toString());		
	}
	
}
