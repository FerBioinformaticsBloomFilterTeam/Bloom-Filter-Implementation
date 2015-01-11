using System;
using System.Collections;
using System.Collections.Generic;
using System.Security.Cryptography;

namespace BloomFilter
{
    public class BloomFilter
    {

        private int m;
        private int k;
        private BitArray bitArray;
        private uint[] hashes;
        private int wordLength;
        private double errorRate;

        public BloomFilter()
        {

        }

        private void InitFilter()
        {
            hashes = new uint[k];
        }

        private uint ComputeHash(uint firstHash, uint secondHash, uint iteration)
        {
            return (firstHash + (iteration * secondHash)) % Convert.ToUInt32(bitArray.Count);
        }

        private static int optimumK(int capacity, float errorRate)
        {
            return 0;
        }

        private static int optimumM(int capacity, double errorRate)
        {
            return 0;
        }
    }
}

