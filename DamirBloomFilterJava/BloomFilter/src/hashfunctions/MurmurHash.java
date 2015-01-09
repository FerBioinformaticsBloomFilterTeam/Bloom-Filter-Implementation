package hashfunctions;

import java.nio.ByteBuffer;

/**
 * Murmur hash function implementation
 * @author dciganovic
 *
 */
public class MurmurHash implements HashFunction {
	private int seed;
	public static final int C1 = 0xcc9e2d51;
	public static final int C2 = 0x1b873593;
	public static final int R1 = 15;
	public static final int R2 = 13;
	public static final int M = 5;
	public static final int N = 0xe6546b64;
	
	/**
	 * Default MurmurHash constructor with seed = 0
	 */
	public MurmurHash() {
		this(0);
	}
	
	/**
	 * MurmurHash with seed set to argument seed.
	 * @param seed
	 */
	public MurmurHash(int seed) {
		this.seed = seed;
	}

	@Override
	public long hash(byte[] byteArrayToHash) {
		int length = byteArrayToHash.length;
		
		if (length == 0) {
			throw new IllegalArgumentException("Byte array is empty!");
		}
		
		int numberOfBlocks = (int)(length / 4);
		int position = -4;
		ByteBuffer buffer = ByteBuffer.wrap(byteArrayToHash);
		int k = 0;
		int hashValue = seed;
		for (int index = 0; index < numberOfBlocks; index++) {
			position = index *4;
			
			k = buffer.getInt(position);
			k *= C1;
			k = (k << R1) | (k >> (32-R1));
			k *= C2;
			
			hashValue ^=  k;
			hashValue = (hashValue << R2) | (hashValue >> (32-R2));
	        hashValue = hashValue * M + N;
		}
		
		position += 4;
		int diff = length - position;
		int remainingBytes = 0;
		if (diff == 3) {
			remainingBytes = byteArrayToHash[position] << 16 + byteArrayToHash[position + 1] << 8 + byteArrayToHash[position + 2];
		} else if (diff == 2) {
			remainingBytes = byteArrayToHash[position] << 8 + byteArrayToHash[position + 1];
		}else if (diff == 1) {
			remainingBytes = byteArrayToHash[position];
		}
		
		hashValue |= remainingBytes;
		
		hashValue = hashValue | (hashValue >> 16);
	    hashValue = hashValue * 0x85ebca6b;
	    hashValue = hashValue | (hashValue >> 13);
	    hashValue = hashValue * 0xc2b2ae35;
	    hashValue = hashValue | (hashValue >> 16);
		
		return (long)hashValue;
	}

}
