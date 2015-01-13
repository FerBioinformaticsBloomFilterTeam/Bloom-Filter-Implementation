using System;
using System.Collections;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;

namespace BloomFilter
{
    public class Filter
    {
        public int N { get; private set; } // Expected number of elements that will be added to the filter
        public int M { get; private set; } // Number of bits in Bit array
        public int K { get; private set; } // Number of hash functions
        public double P { get; private set; } // False positive probability
        public BitArray Bits { get; private set; } // Bit array
        private Murmur HashMurmur;
        private FNV HashFNV;

        public Filter(int n, double p)
        {
            InitFilter(n, p, 0, 0);
        }

        public Filter(int n, double p, int m = 0, int k = 0)
        {
            InitFilter(n, p, m, k);
        }

        public void InitFilter(int n, double p, int m = 0, int k = 0)
        {
            N = n;
            P = p;
            if (m == 0)
                M = optimumM();
            else
                M = m;
            if (k == 0)
                K = optimumK();
            else
                K = k;
            Bits = new BitArray(M);
            HashMurmur = new Murmur();
            HashFNV = new FNV();
        }

        public void Add(string value)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(value);
            Add(bytes);
        }

        public void Add(byte[] value)
        {
            uint primaryHash = HashMurmur.ComputeHash(value).ToUint32();
            uint secondaryHash = HashFNV.ComputeHash(value).ToUint32();
            for (int i = 0; i < K; i++)
            {
                int hash = CombineHash(primaryHash, secondaryHash, i);
                this.Bits[hash] = true;
            }
        }

        public bool InFilter(string value)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(value);
            return InFilter(bytes);
        }

        public bool InFilter(byte[] value)
        {
            uint primaryHash = HashMurmur.ComputeHash(value).ToUint32();
            uint secondaryHash = HashFNV.ComputeHash(value).ToUint32();
            for (int i = 0; i < K; i++)
            {
                int hash = CombineHash(primaryHash, secondaryHash, i);
                if (this.Bits[hash] == false)
                {
                    return false;
                }
            }
            return true;
        }

        private int CombineHash(uint primaryHash, uint secondaryHash, int iteration)
        {
            return Convert.ToInt32((primaryHash + (iteration * secondaryHash)) % M);
        }

        private int optimumM()
        {
            // m = - n * ln(p) / ln(2)^2
            return Convert.ToInt32(Math.Ceiling(-(double)N * Math.Log(P) / Math.Pow(Math.Log(2), 2)));
        }

        private int optimumK()
        {
            // k = m/n * ln(2)
            return Convert.ToInt32(Math.Ceiling((double)M / (double)N * Math.Log(2)));
        }
    }
}

