package hr.bio.project.bloomfilter;


public class BloomFilter {
	private int size;
	private char[] filter;
	private Hasher hasher;

	public BloomFilter(int size) {
		this.size = size;
		filter = new char[size/2+1];
		for (int i=0; i<filter.length; i++) {
			filter[i] = 0;
		}
		hasher = new Hasher();
	}
	
	public void addElemToBloom(String fastaPart) {
		int hashFNV, hashMurmur, hashJenkins;
		hashFNV = (int) hasher.FNVhash(fastaPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(fastaPart);
		hashMurmur = hashMurmur % (size*4);
		hashJenkins = (int) hasher.jenkinsHash(fastaPart);
		hashJenkins = hashJenkins % (size*4);
		addBloom((char)(hashFNV%8), hashFNV/8);
		addBloom((char)(hashMurmur%8), hashMurmur/8);
		addBloom((char)(hashJenkins%8), hashJenkins/8);
	}
	
	public boolean testElemInBloom(String fastaPart) {
		int hashFNV, hashMurmur, hashJenkins;
		int[] test = new int[3];
		hashFNV = (int) hasher.FNVhash(fastaPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(fastaPart);
		hashMurmur = hashMurmur % (size*4);
		hashJenkins = (int) hasher.jenkinsHash(fastaPart);
		hashJenkins = hashJenkins % (size*4);
		test[0] = testBloom((char)(hashFNV%8), hashFNV/8);
		test[1] = testBloom((char)(hashMurmur%8), hashMurmur/8);
		test[2] = testBloom((char)(hashJenkins%8), hashJenkins/8);
		if ((test[0] == 1)&&(test[1] == 1)&&(test[2] == 1)) {
			return true;
		} else {
			return false;
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
