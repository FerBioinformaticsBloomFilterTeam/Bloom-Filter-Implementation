using System;
using System.Text;
using BloomFilter;
using System.Collections;

namespace BloomFilterApp
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            string bigFASTAPath = "/home/manager/FerBioinformatika/Bloom-Filter-Implementation/FajdoBloomFilterJava/BloomFilterImplementation/eschericia.fa";
            string smallFASTAPath = "eschericia-part.fa";
            Console.WriteLine("Loading file");
            FASTAReader fasta = new FASTAReader(bigFASTAPath);
            fasta.ReadFASTA();
            fasta.SplitIntoWords();
            Console.WriteLine("Loaded file");
            Console.WriteLine("Adding words of length 20 to filter");
            Filter bloomFilter = new Filter(fasta.Words.Count, 0.1);
            foreach (string word in fasta.Words)
            {
                bloomFilter.Add(Encoding.ASCII.GetBytes(word));
            }
            Console.WriteLine("Added " + fasta.Words.Count + " words");
        }

        public static void PrintValues( IEnumerable myList, int myWidth )  {
            Console.WriteLine("Bit Array Values:");
            int i = myWidth;
            foreach ( Object obj in myList ) {
                if ( i <= 0 )  {
                    i = myWidth;
                    Console.WriteLine();
                }
                i--;
                Console.Write( "{0,8}", obj );
            }
            Console.WriteLine();
        }
    }
}
