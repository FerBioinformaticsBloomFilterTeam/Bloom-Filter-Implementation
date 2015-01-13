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

        /// <summary>
        /// Initializes a new instance of the <see cref="BloomFilter.Filter"/> class.
        /// </summary>
        /// <param name="n">Expected number of elements that will be inserted.</param>
        /// <param name="p">Probability of false positives.</param>
        public Filter(int n, double p)
        {
            InitFilter(n, p, 0, 0);
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="BloomFilter.Filter"/> class.
        /// </summary>
        /// <param name="n">Expected number of elements that will be inserted.</param>
        /// <param name="p">Probability of false positives.</param>
        /// <param name="m">BitArray length.</param>
        /// <param name="k">Number of hash functions.</param>
        public Filter(int n, double p, int m = 0, int k = 0)
        {
            InitFilter(n, p, m, k);
        }

        /// <summary>
        /// Initializes the filter.
        /// </summary>
        /// <param name="n">Expected number of elements that will be inserted.</param>
        /// <param name="p">Probability of false positives.</param>
        /// <param name="m">BitArray length.</param>
        /// <param name="k">Number of hash functions.</param>
        private void InitFilter(int n, double p, int m = 0, int k = 0)
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

        /// <summary>
        /// Add the string to filter.
        /// </summary>
        /// <param name="value">String to be inserted.</param>
        public void Add(string value)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(value);
            Add(bytes);
        }

        /// <summary>
        /// Adds bytes to filter.
        /// </summary>
        /// <param name="value">Bytes to be inserted.</param>
        public void Add(byte[] value)
        {
            uint primaryHash = HashMurmur.ComputeHash(value).ToUint32();
            uint secondaryHash = HashFNV.ComputeHash(value).ToUint32();

            // Set bits for each hash.
            for (int i = 0; i < K; i++)
            {
                int hash = CombineHash(primaryHash, secondaryHash, i);
                this.Bits[hash] = true;
            }
        }

        /// <summary>
        /// Checks if string is in filter.
        /// </summary>
        /// <returns><c>true</c>, if string is in filter, <c>false</c> otherwise.</returns>
        /// <param name="value">String to be checked.</param>
        public bool InFilter(string value)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(value);
            return InFilter(bytes);
        }

        /// <summary>
        /// Checks if bytes are in filter.
        /// </summary>
        /// <returns><c>true</c>, if bytes are in filter, <c>false</c> otherwise.</returns>
        /// <param name="value">Value.</param>
        public bool InFilter(byte[] value)
        {
            uint primaryHash = HashMurmur.ComputeHash(value).ToUint32();
            uint secondaryHash = HashFNV.ComputeHash(value).ToUint32();

            // Check bits for each hash.
            for (int i = 0; i < K; i++)
            {
                int hash = CombineHash(primaryHash, secondaryHash, i);
                if (this.Bits[hash] == false)
                {
                    // If at least one is false, it isn't in filter.
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// Uses 2 hash values to calculate other values.
        /// </summary>
        /// <returns>Calculated hash.</returns>
        /// <param name="primaryHash">Primary hash.</param>
        /// <param name="secondaryHash">Secondary hash.</param>
        /// <param name="iteration">Iteration.</param>
        private int CombineHash(uint primaryHash, uint secondaryHash, int iteration)
        {
            return Convert.ToInt32((primaryHash + (iteration * secondaryHash)) % M);
        }

        /// <summary>
        /// Finds optimum BitArray length.
        /// </summary>
        /// <returns>BitArray length.</returns>
        private int optimumM()
        {
            // Formula: m = - n * ln(p) / ln(2)^2
            return Convert.ToInt32(Math.Ceiling(-(double)N * Math.Log(P) / Math.Pow(Math.Log(2), 2)));
        }

        /// <summary>
        /// Finds optumum number of hash functions.
        /// </summary>
        /// <returns>Optimum number of hash functions.</returns>
        private int optimumK()
        {
            // Formula: k = m/n * ln(2)
            return Convert.ToInt32(Math.Ceiling((double)M / (double)N * Math.Log(2)));
        }
    }
}

