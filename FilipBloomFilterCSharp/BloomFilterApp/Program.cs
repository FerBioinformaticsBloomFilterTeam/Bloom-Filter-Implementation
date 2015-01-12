using System;
using System.Text;
using BloomFilter;

namespace BloomFilterApp
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            string bigFASTAPath = "/home/manager/FerBioinformatika/Bloom-Filter-Implementation/FajdoBloomFilterJava/BloomFilterImplementation/eschericia.fa";
            string smallFASTAPath = "eschericia-part.fa";
            FASTAReader fasta = new FASTAReader(smallFASTAPath);
            fasta.ReadFASTA();
            fasta.SplitIntoWords();
            foreach (string word in fasta.Words)
            {

            }
        }
    }
}
