package hr.fer.bioinformatics.bloomfilter;

import hashfunctions.FakeHash;
import hashfunctions.FowlerNollVo1Hash;
import hashfunctions.HashFunction;
import hashfunctions.MurmurHash;

import java.util.Arrays;
import java.util.BitSet;

public class BloomFilter {
    private HashFunction[] hashFunctions;
    private BitSet bitArray;
    
    /**
     * Creates BloomFilter with default Murmur and Fowler-Noll-Vo hash functions
     * @param size
     * @param k
     */
    public BloomFilter(int size, int k) {
    	this(size, k, new HashFunction[]{new MurmurHash(), new FowlerNollVo1Hash()});    	
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
    	if (sizeOfBloomFilter > 0) {
			throw new IllegalArgumentException("Size can't be null");
		}
    	
    	if (numberOfHashFunctions <= 0) {
			throw new IllegalArgumentException("Number of hash functions can't be 0 or less");
		}
    	
    	if (numberOfHashFunctions > 1 && hashfunctions.length < 2) {
			throw new NullPointerException("Number of hash functions should be at least two");
		}
    	
		bitArray = new BitSet(sizeOfBloomFilter);
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

}
