package hr.bio.project.bloomfilter;


public class BloomFilter {
	private int size;
	private char[] filter;
	private Hasher hasher;
	private int[] hashes;
	private int k;
	private int m;

	public BloomFilter(int size, String ps) {
		this.size = size/20;
		double p = Double.parseDouble(ps);
		m = (int) ( Math.ceil(((-Math.log10(p)/Math.log10(2))*this.size)/Math.log(2)));
		filter = new char[m];
		for (int i=0; i<filter.length; i++) {
			filter[i] = 0;
		}
		hasher = new Hasher();
		k = (int) Math.ceil(-Math.log(p));
		hashes = new int[k];
		//System.out.println(m);
		//System.out.println(k);
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
	
	public boolean testElemInBloom(String fastaPart) {
		int hashFNV, hashMurmur;
		hashFNV = (int) hasher.FNVhash(fastaPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(fastaPart);
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
			return true;
		} else {
			return false;
		}
	}
	
	private void addBloom(char c, int index) {
		switch(c) {
			case 0: filter[index] = (char) (filter[index] | 0b10000000);
					break;
			case 1: filter[index] = (char) (filter[index] | 0b01000000);
					break;
			case 2: filter[index] = (char) (filter[index] | 0b00100000);
					break;
			case 3: filter[index] = (char) (filter[index] | 0b00010000);
					break;
			case 4: filter[index] = (char) (filter[index] | 0b00001000);
					break;
			case 5: filter[index] = (char) (filter[index] | 0b00000100);
					break;
			case 6: filter[index] = (char) (filter[index] | 0b00000010);
					break;
			case 7: filter[index] = (char) (filter[index] | 0b00000001);
					break;
		}
	}
	
	private int testBloom(char c, int index) {
		int tester = 0;
		switch(c) {
			case 0: tester = ((char) (filter[index] & 0b10000000))>>7;
					break;
			case 1: tester = ((char) (filter[index] & 0b01000000))>>6;
					break;
			case 2: tester = ((char) (filter[index] & 0b00100000))>>5;
					break;
			case 3: tester = ((char) (filter[index] & 0b00010000))>>4;
					break;
			case 4: tester = ((char) (filter[index] & 0b00001000))>>3;
					break;
			case 5: tester = ((char) (filter[index] & 0b00000100))>>2;
					break;
			case 6: tester = ((char) (filter[index] & 0b00000010))>>1;
					break;
			case 7: tester = (char) (filter[index] & 0b00000001);
					break;
		}
		return tester;
	}
	
}
