package hashfunctions;

public interface HashFunction {
	/**
	 * Returns hash value of an byte array.
	 * @param byteArrayToHash
	 * @return
	 */
	public long hash(byte[] byteArrayToHash);
}
