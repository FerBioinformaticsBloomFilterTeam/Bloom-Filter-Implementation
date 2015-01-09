package hr.fer.bioinformatics.bloomfilter;

import java.util.BitSet;

public class BloomFilterBitSet extends BitSet {
	private int filterSize = 0;
	
	private static final long serialVersionUID = 1L;

	/**
     * Creates a bit set whose initial size is large enough to explicitly
     * represent bits with indices in the range {@code 0} through
     * {@code nbits-1}. All bits are initially {@code false}.
     *
     * @param  nbits the initial size of the bit set
     * @throws NegativeArraySizeException if the specified initial size
     *         is negative
     */
	public BloomFilterBitSet(int nbits) {
		super(nbits);
		filterSize = nbits;
	}
	
	
	/**
     * Returns the number of bits of space actually in use by this
     * {@code BitSet} to represent bit values.
     * The maximum element in the set is the size - 1st element.
     *
     * @return the number of bits currently in this bit set
     */
	@Override
    public int size() {
        return filterSize;
    }
}
