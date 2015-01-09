package hashfunctions;

public class FakeHash implements HashFunction{
	private HashFunction hashFunction1;
	private HashFunction hashFunction2;
	
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
		// TODO Auto-generated constructor stub
	}


	@Override
	public long hash(byte[] byteArrayToHash) {
		// TODO Auto-generated method stub
		return 0L;
	}
}
