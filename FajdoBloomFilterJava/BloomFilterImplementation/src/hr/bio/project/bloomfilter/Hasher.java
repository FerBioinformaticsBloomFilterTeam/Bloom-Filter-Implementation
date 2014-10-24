package hr.bio.project.bloomfilter;

public class Hasher {
	
	private long FNV_prime;
	private long offset_basis;
	
	public Hasher() {
		String FNV_prime_string="16777619";
		FNV_prime = Long.parseUnsignedLong(FNV_prime_string);
		String FNV_offset_string="2166136261";
		offset_basis = Long.parseUnsignedLong(FNV_offset_string);
	}
	
	public long FNVhash(String fastaPiece) {
		long hash;
		char[] hashFasta = fastaPiece.toCharArray();
		
		hash = offset_basis;
		for (char c : hashFasta) {
			hash = hash*FNV_prime;
			hash = hash^c;
		}
			
		return hash;
	}
	
}
