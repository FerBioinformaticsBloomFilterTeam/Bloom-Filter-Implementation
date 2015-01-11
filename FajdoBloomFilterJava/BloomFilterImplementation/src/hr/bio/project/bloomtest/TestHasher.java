package hr.bio.project.bloomtest;

public class TestHasher {
	private long FNV_prime;
	private long offset_basis;
	private long c1, c2, r1, r2, m, n;
	private long seed;
	
	public TestHasher() {
		String FNV_prime_string="16777619";
		FNV_prime = Long.parseUnsignedLong(FNV_prime_string);
		String FNV_offset_string="2166136261";
		offset_basis = Long.parseUnsignedLong(FNV_offset_string);
		c1 = 0xcc9e2d51;
		c2 = 0x1b873593;
		n = 0xe6546b64;
		r1 = 15;
		r2 = 13;
		m = 5;
		seed = 16777619;
	}
	
	public long FNVhash(String fastaPiece) {
		long hash;
		char[] hashFasta = fastaPiece.toCharArray();
		
		hash = offset_basis;
		for (char c : hashFasta) {
			hash = (hash*FNV_prime) % Integer.MAX_VALUE;
			hash = hash^c;
		}
		

		return hash;
	}
	
	public long murmurHash(String fastaPiece) {
		long hash;
		int i;
		String k;
		
		hash = seed;
		for (i=0; i<(Math.ceil(fastaPiece.length()/4)); i++) {
			if (fastaPiece.length()<(4*(i+1))) {
				k = fastaPiece.substring(0 + 4*i, fastaPiece.length());
			} else {
				k = fastaPiece.substring(0 + 4*i, 4*(i+1));
			}
			long l = 0;
			char[] c;
			c = k.toCharArray();
			if (k.length() == 4) {
				l = (c[0]*(int)Math.pow(2,24) + c[1]*(int)Math.pow(2, 16) + c[2]*(int)Math.pow(2, 8) + c[3]) % Integer.MAX_VALUE;
				l = l*c1  % Integer.MAX_VALUE;
				l = ((l << r1) | (l >> (32 - r1)))  % Integer.MAX_VALUE;
				l = l*c2  % Integer.MAX_VALUE;
				hash = (hash^l)  % Integer.MAX_VALUE;
				hash = ((hash << r2) | (hash >> (32 - r2)))  % Integer.MAX_VALUE;
				hash = (hash*m + n)  % Integer.MAX_VALUE;
			} else {
				if (k.length() == 3) {
					l = (c[0]*(int)Math.pow(2, 16) + c[1]*(int)Math.pow(2, 8) + c[2]) % Integer.MAX_VALUE;
				}
				if (k.length() == 2) {
					l = (c[0]*(int)Math.pow(2, 8) + c[1]) % Integer.MAX_VALUE;
				}
				if (k.length() == 1) {
					l = (c[0]) % Integer.MAX_VALUE;
				}
				l = l*c1  % Integer.MAX_VALUE;
				l = ((l << r1) | (l >> (32 - r1)))  % Integer.MAX_VALUE;
				l = l*c2  % Integer.MAX_VALUE;
				hash = (hash^l) % Integer.MAX_VALUE;
			}
		}
		hash = (hash^fastaPiece.length())  % Integer.MAX_VALUE;
		hash = hash^(hash >> 16)  % Integer.MAX_VALUE;
		hash = hash*0x85ebca6b  % Integer.MAX_VALUE;
		hash = (hash^(hash >> 13)) % Integer.MAX_VALUE;
		hash = hash*0xc2b2ae35 % Integer.MAX_VALUE;
		hash = (hash^(hash >> 16)) % Integer.MAX_VALUE;
		return hash;
	}
	
	public long jenkinsHash(String fastaPiece) {
		long hash = 0;
		int i;
		char[] c;
		c = fastaPiece.toCharArray();
		
		for (i = 0; i < fastaPiece.length(); i++) {
			hash = (hash + c[i]) % Integer.MAX_VALUE;
			hash = (hash + (hash << 10)) % Integer.MAX_VALUE;
			hash = (hash^(hash >> 6)) % Integer.MAX_VALUE;
		}
		hash = (hash + (hash << 3)) % Integer.MAX_VALUE;
		hash = (hash^(hash >> 11)) % Integer.MAX_VALUE;
		hash = (hash + (hash << 15)) % Integer.MAX_VALUE;
		return hash;
	}
}
