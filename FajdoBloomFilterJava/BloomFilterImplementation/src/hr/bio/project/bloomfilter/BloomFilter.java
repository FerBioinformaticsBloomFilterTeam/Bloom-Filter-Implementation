package hr.bio.project.bloomfilter;

public class BloomFilter {
	private int size;
	private String[] filter;

	public BloomFilter(int size) {
		this.size = size;
		filter = new String[size];
		Hasher hasher = new Hasher();
	}
	
	public void addElemToBloom(String fastaPart) {
		
	}
	
}
