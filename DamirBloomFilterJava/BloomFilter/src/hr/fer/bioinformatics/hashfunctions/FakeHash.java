package hr.fer.bioinformatics.hashfunctions;

/**
 * FakeHass class is used to act like a regular hash function. It is used 
 * when there is a need for more hash functions, but there is lack of their implementations
 * so this is used to replace missing hash functions.
 * @author dciganovic
 *
 */
public class FakeHash implements HashFunction{
	private HashFunction hashFunction1;
	private HashFunction hashFunction2;
	private int modifier;
	
	/**
	 * Default FakeHash function with Murmur and Fowler-Noll-Vo hash functions.
	 * NOTE: If more then one FakeHash should be created, use different parameter i.
	 * @param i
	 */
	public FakeHash(int i) {
		this(new MurmurHash(), new FowlerNollVo1AlternateHash(), i);
	}
	

	/**
	 * Fake hash with 2 hashfunctions to help this instance act like a hash function.
	 * NOTE: If more then one FakeHash should be created, use different parameter i.
	 * @param hashFunction1
	 * @param hashFunction2
	 * @param i parameter
	 */
	public FakeHash(HashFunction hashFunction1, HashFunction hashFunction2, int i) {
		if (hashFunction1 == null) {
			throw new IllegalArgumentException("hashFunction1 can't be null!");
		}
		
		if (hashFunction2 == null) {
			throw new IllegalArgumentException("hashFunction2 can't be null!");
		}
		this.hashFunction1 = hashFunction1;
		this.hashFunction2 = hashFunction2;
		this.modifier = i;
	}


	@Override
	public long hash(byte[] byteArrayToHash) {
		return hashFunction1.hash(byteArrayToHash) + hashFunction2.hash(byteArrayToHash) * modifier;
	}
}
