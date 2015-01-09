package hr.fer.bioinformatics.hashfunctions;
/**
 * Fowler-Noll-Vo hash function algorithm 1 (alternate) implementation
 * @author dciganovic
 *
 */
public class FowlerNollVo1AlternateHash implements HashFunction {
	public static final long FNV_PRIME = 16777619L;
	public static final long FNV_OFFSET_BASIS = 2166136261L;

	@Override
	public long hash(byte[] byteArrayToHash) {
		long hash = FNV_OFFSET_BASIS;
		for (int octetOfData : byteArrayToHash) {
			hash |= octetOfData;
			hash *= FNV_PRIME;
		}

		return hash;
	}

}
