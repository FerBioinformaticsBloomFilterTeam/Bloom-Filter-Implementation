package hr.fer.bioinformatics.bloomfilter;

import hashfunctions.FakeHash;
import hashfunctions.FowlerNollVo1AlternateHash;
import hashfunctions.HashFunction;
import hashfunctions.MurmurHash;

import java.util.Arrays;

/**
 * Bloom filter implementation
 * @author dciganovic
 *
 */
public class BloomFilter {
    private HashFunction[] hashFunctions;
    private BloomFilterBitSet bitArray;
    
    /**
     * Creates BloomFilter with default Murmur and Fowler-Noll-Vo hash functions
     * @param sizeOfBloomFilter
     * @param numberOfHashFunctions
     */
    public BloomFilter(int sizeOfBloomFilter, int numberOfHashFunctions) {
    	this(sizeOfBloomFilter, numberOfHashFunctions, new HashFunction[]{new MurmurHash(), new FowlerNollVo1AlternateHash()});    	
	}
    
    /**
     * Creates BloomFilter with hash functions passed as constructor argument.
     * The number of hash functions should be at least two (different from each other) if k > 1. 
     * Otherwise, constructor will not be able to properly initialize filter.
     * @param sizeOfBloomFilter of bit array
     * @param numberOfHashFunctions number of hash functions
     * @param hashfunctions array
     */
    public BloomFilter(int sizeOfBloomFilter, int numberOfHashFunctions, HashFunction[] hashfunctions) {
    	if (sizeOfBloomFilter <= 0) {
			throw new IllegalArgumentException("Size can't be zero or less then zero");
		}
    	
    	if (numberOfHashFunctions <= 0) {
			throw new IllegalArgumentException("Number of hash functions can't be 0 or less");
		}
    	
    	if (numberOfHashFunctions > 1 && hashfunctions.length < 2) {
			throw new NullPointerException("Number of hash functions should be at least two");
		}
    	
    	for (int i = 0; i < hashfunctions.length; i++) {
			if (hashfunctions[i] == null) {
				throw new NullPointerException("hash function can't be null. Hash function index: " + i);
			}
		}
    	
		bitArray = new BloomFilterBitSet(sizeOfBloomFilter);
		this.hashFunctions = generateHashFunctions(hashfunctions, numberOfHashFunctions);
	}

    /**
     * Returns array of hash functions with length same as k. If k > hashfunctions.length
     * it will generate new hash functions, if k < hashfunctions.length returns first k 
     * hash functions from hashFunctions array
     * @param hashFunctions array of hash functions
     * @param numberOfHashFunctions 
     * @return array of hash functions with length same as k
     */
	private HashFunction[] generateHashFunctions(
			HashFunction[] hashFunctions, int numberOfHashFunctions) {
		
		if (numberOfHashFunctions == hashFunctions.length) {
			return hashFunctions;
		}
		
		HashFunction[] hashFunctionsNew = new HashFunction[numberOfHashFunctions];
		if (numberOfHashFunctions < hashFunctions.length) {
			return Arrays.copyOfRange(hashFunctions, 0, numberOfHashFunctions);
		}
		
		for (int hashFunctionIndex = 0; hashFunctionIndex < numberOfHashFunctions; hashFunctionIndex++) {
			if (hashFunctionIndex < hashFunctions.length) {
				hashFunctionsNew[hashFunctionIndex] = hashFunctions[hashFunctionIndex];
				continue;
			}
			
			hashFunctionsNew[hashFunctionIndex] =  new FakeHash(hashFunctions[0], hashFunctions[1], hashFunctionIndex);
		}
		
		return hashFunctionsNew;
		
	}
	
	/**
	 * Adds word in a bloom filter, StringCoding.encode(value, 0, value.length);
	 * @param word
	 */
	public void addWord(char[] word) {
		addWord(new String(word));
	}
	
	/**
	 * Adds word in a bloom filter, StringCoding.encode(value, 0, value.length);
	 * @param word
	 */
	public void addWord(String word) {
		if (word == null || word.isEmpty()) {
			return;
		}
		
		byte[] wordInBytes = word.getBytes();
		for (HashFunction hashFunction : hashFunctions) {
			long filterIndex = hashFunction.hash(wordInBytes);
			
			filterIndex = filterIndex % bitArray.size();
			if (filterIndex < 0) {
				filterIndex += bitArray.size();
			}
			
			bitArray.set((int)filterIndex);
		}
	}
	
	/**
	 * Checks if word is in bloom filter, if it returns false, 
	 * word is for sure not added in filer, but if it returns true,
	 * word may not be really added in filer (bloom filter properties)
	 * @param word
	 * @return
	 */
	public boolean isInFilter(char[] word) {
		return isInFilter(new String(word));
	}
	
	/**
	 * Checks if word is in bloom filter, if it returns false, 
	 * word is for sure not added in filer, but if it returns true,
	 * word may not be really added in filer (bloom filter properties)
	 * @param word
	 * @return
	 */
	public boolean isInFilter(String word) {
		byte[] wordInBytes = word.getBytes();
		for (HashFunction hashFunction : hashFunctions) {
			long filterIndex = hashFunction.hash(wordInBytes);
			
			filterIndex = filterIndex % bitArray.size();
			
			if (filterIndex < 0) {
				filterIndex += bitArray.size();
			}
			
			if (bitArray.get((int)filterIndex) == false) {
				return false;
			}
		}
		return true;
	}

}
