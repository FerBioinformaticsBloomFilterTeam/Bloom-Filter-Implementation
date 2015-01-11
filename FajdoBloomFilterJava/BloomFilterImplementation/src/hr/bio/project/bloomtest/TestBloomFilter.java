package hr.bio.project.bloomtest;

public class TestBloomFilter {
	private int size;
	private char[] filter;
	private TestHasher hasher;
	private int[] hashes;
	private int k;
	private int m;

	public TestBloomFilter(int size) {
		this.size = size;
		double p = 0.1;
		m = (int) ( Math.ceil(((-Math.log10(p)/Math.log10(2))*this.size)/Math.log(2)));
		filter = new char[m];
		for (int i=0; i<filter.length; i++) {
			filter[i] = 0;
		}
		hasher = new TestHasher();
		k = (int) -Math.log(p);
		hashes = new int[k];
	}
	
	public void addElemToBloom(String fastaPart) {
		int hashFNV, hashMurmur;
		hashFNV = (int) hasher.FNVhash(fastaPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(fastaPart);
		hashMurmur = hashMurmur % (size*4);
		for (int i=0; i<k; i++) {
			hashes[i] = (hashFNV + hashMurmur*i) % m;
			addBloom((char)(hashes[i]%8), hashes[i]/8);
		}
	}
	
	public String testElemInBloom(String testPart) {
		int hashFNV, hashMurmur;
		hashFNV = (int) hasher.FNVhash(testPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(testPart);
		hashMurmur = hashMurmur % (size*4);
		for (int i=0; i<k; i++) {
			hashes[i] = (hashFNV + hashMurmur*i) % m;
		}
		boolean probably_in = true;
		int test = 0;
		for (int i=0; i<k; i++) {
			test = testBloom((char)(hashes[i]%8), hashes[i]/8);
			if (test == 0) {
				probably_in = false;
			}
		}
		if (probably_in == true) {
			return "1";
		} else {
			return "0";
		}
	}
	
	private void addBloom(char c, int index) {
		switch(c) {
			case 0: filter[index] = (char) (filter[index] | 0b10000000);
			case 1: filter[index] = (char) (filter[index] | 0b01000000);
			case 2: filter[index] = (char) (filter[index] | 0b00100000);
			case 3: filter[index] = (char) (filter[index] | 0b00010000);
			case 4: filter[index] = (char) (filter[index] | 0b00001000);
			case 5: filter[index] = (char) (filter[index] | 0b00000100);
			case 6: filter[index] = (char) (filter[index] | 0b00000010);
			case 7: filter[index] = (char) (filter[index] | 0b00000001);
		}
	}
	
	private int testBloom(char c, int index) {
		int tester = 0;
		switch(c) {
			case 0: tester = ((char) (filter[index] & 0b10000000))>>7;
			case 1: tester = ((char) (filter[index] & 0b01000000))>>6;
			case 2: tester = ((char) (filter[index] & 0b00100000))>>5;
			case 3: tester = ((char) (filter[index] & 0b00010000))>>4;
			case 4: tester = ((char) (filter[index] & 0b00001000))>>3;
			case 5: tester = ((char) (filter[index] & 0b00000100))>>2;
			case 6: tester = ((char) (filter[index] & 0b00000010))>>1;
			case 7: tester = (char) (filter[index] & 0b00000001);
		}
		return tester;
	}
}
