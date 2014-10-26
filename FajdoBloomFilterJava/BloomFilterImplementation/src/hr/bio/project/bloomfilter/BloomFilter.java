package hr.bio.project.bloomfilter;


public class BloomFilter {
	private int size;
	private int[] filter;
	private Hasher hasher;

	public BloomFilter(int size) {
		this.size = size;
		filter = new int[size];
		for (int i=0; i<size; i++) {
			filter[i] = 0;
		}
		hasher = new Hasher();
	}
	
	public void addElemToBloom(String fastaPart) {
		int hashFNV, hashMurmur;
		hashFNV = (int) hasher.FNVhash(fastaPart);
		hashFNV = hashFNV % (size*4);
		hashMurmur = (int) hasher.murmurHash(fastaPart);
		hashMurmur = hashMurmur % (size*4);
	}
	
}
