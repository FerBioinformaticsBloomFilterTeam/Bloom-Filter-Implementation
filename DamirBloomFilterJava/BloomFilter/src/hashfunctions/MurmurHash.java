package hashfunctions;

public class MurmurHash implements HashFunction {
	private int seed;
	
	public MurmurHash() {
		this(0);
	}
	
	public MurmurHash(int seed) {
		this.seed = seed;
	}

	@Override
	public int hash(byte[] byteArrayToHash) {
		// TODO Auto-generated method stub
		return 0;
	}

}
