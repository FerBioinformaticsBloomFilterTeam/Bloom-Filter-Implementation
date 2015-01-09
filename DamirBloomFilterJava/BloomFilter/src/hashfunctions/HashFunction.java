package hashfunctions;

public interface HashFunction {
	/**
	 * Returns hash value of an byte array.
	 * @param byteArrayToHash
	 * @return
	 */
	public int hash(byte[] byteArrayToHash);
}
