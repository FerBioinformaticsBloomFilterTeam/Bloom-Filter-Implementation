package hr.bio.project.bloomfilter;

public class BloomFilter {
	private int size;
	private String[] filter;
	private Hasher hasher;

	public BloomFilter(int size) {
		this.size = size;
		filter = new String[size];
		hasher = new Hasher();
	}
	
	public void addElemToBloom(String fastaPart) {
		long hash1, hash2, hash3;
		hash1 = hasher.FNVhash(fastaPart) % size;
		
	}
	
}
