using System;
using System.Security.Cryptography;

namespace BloomFilter
{
    /// <summary>
    /// Murmur3 hash 32-bit unsigned int implementation.
    /// </summary>
    public class Murmur: HashAlgorithm
    {
        protected const uint C1 = 0xcc9e2d51;
        protected const uint C2 = 0x1b873593;

        private readonly uint _Seed;

        public Murmur(uint seed = 3475832)
        {
            _Seed = seed;
            Reset();
        }

        public override int HashSize { get { return 32; } }

        public uint Seed { get { return _Seed; } }

        protected uint H1 { get; set; }

        protected int Length { get; set; }

        private void Reset()
        {
            H1 = Seed;
            Length = 0;
        }

        public override void Initialize()
        {
            Reset();
        }

        protected override byte[] HashFinal()
        {
            H1 = (H1 ^ (uint)Length).FMix();

            return BitConverter.GetBytes(H1);
        }

        protected override void HashCore(byte[] array, int ibStart, int cbSize)
        {
            Length += cbSize;
            Body(array, ibStart, cbSize);
        }

        private void Body(byte[] data, int start, int length)
        {
            int remainder = length & 3;
            int blocks = length / 4;

            unsafe
            {
                // grab pointer to first byte in array
                fixed (byte* d = &data[start])
                {
                    uint* b = (uint*)d;

                    while (blocks-- > 0)
                        H1 = (((H1 ^ (((*b++ * C1).RotateLeft(15)) * C2)).RotateLeft(13)) * 5) + 0xe6546b64;

                    if (remainder > 0)
                        Tail(d + (length - remainder), remainder);
                }
            }
        }

        unsafe private void Tail(byte* tail, int remainder)
        {
            // create our keys and initialize to 0
            uint k1 = 0;

            // determine how many bytes we have left to work with based on length
            switch (remainder)
            {
                case 3: k1 ^= (uint)tail[2] << 16; goto case 2;
                    case 2: k1 ^= (uint)tail[1] << 8; goto case 1;
                    case 1: k1 ^= tail[0]; break;
            }

            H1 ^= (k1 * C1).RotateLeft(15) * C2;
        }
    }

    /// <summary>
    /// FNV-1a hash 32-bit unsigned int implementation.
    /// </summary>
    public class FNV: HashAlgorithm
    {
        private const uint FNVPrime = unchecked(16777619);
        private const uint FNVOffsetBasis = unchecked(2166136261);
        private uint hash;

        public FNV()
        {
            this.Initialize();
        }

        public override int HashSize { get { return 32; } }

        protected override void HashCore(byte[] array, int ibStart, int cbSize)
        {
            for (var i = ibStart; i < cbSize; i++)
            {
                unchecked
                {
                    this.hash ^= array[i];
                    this.hash *= FNVPrime;
                }
            }
        }

        protected override byte[] HashFinal()
        {
            return BitConverter.GetBytes(this.hash);
        }


        public override void Initialize()
        {
            this.hash = FNVOffsetBasis;
        }
    }
}

