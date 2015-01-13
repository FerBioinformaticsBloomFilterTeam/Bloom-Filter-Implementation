using System;
using System.Text;
using System.Security.Cryptography;
using BloomFilter;
using NUnit.Framework;

namespace BloomFilterUnitTests
{
    [TestFixture]
    public class BloomFilterUnitTests
    {
        [Test]
        public void MurmurTest()
        {
            Murmur m = new Murmur();
            string value = "TGGGTTAACATTATTATGCG";
            byte[] data = m.ComputeHash(Encoding.ASCII.GetBytes(value));
            Assert.AreEqual(877866957U, data.ToUint32());

            value = "ACGAGTCCACTTCATCAAGA";
            data = m.ComputeHash(Encoding.ASCII.GetBytes(value));
            Assert.AreEqual(2678757309U, data.ToUint32());
        }

        [Test]
        public void FNVTest()
        {
            FNV f = new FNV();
            string value = "TGGGTTAACATTATTATGCG";
            byte[] data = f.ComputeHash(Encoding.ASCII.GetBytes(value));
            Assert.AreEqual(0x3CAC78EDU, data.ToUint32());

            value = "ACGAGTCCACTTCATCAAGA";
            data = f.ComputeHash(Encoding.ASCII.GetBytes(value));
            Assert.AreEqual(0xB2B0F087U, data.ToUint32());
        }
    }
}

