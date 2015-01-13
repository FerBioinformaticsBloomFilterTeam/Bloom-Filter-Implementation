using System;
using System.Text;
using BloomFilter;
using System.Collections;
using System.Collections.Generic;

namespace BloomFilterApp
{
    class MainClass
    {
        private static Dictionary<string, string> Arguments;
        private static string FastaPath;
        private static double ErrorRate;
        private static int WordSize;
        private static Filter bloomFilter;
        public static void Main(string[] args)
        {
            /*string bigFASTAPath = "/home/manager/FerBioinformatika/Bloom-Filter-Implementation/FajdoBloomFilterJava/BloomFilterImplementation/eschericia.fa";
            string smallFASTAPath = "eschericia-part.fa";*/

            Arguments = new Dictionary<string, string>();

            foreach (string argument in args)
            {
                string[] splitted = argument.Split('=');

                if (splitted.Length == 2)
                {
                    Arguments[splitted[0]] = splitted[1];
                }
            }

            FastaPath = getArgument("fasta");
            if (FastaPath == null)
                Console.WriteLine("No FASTA file selected.");
            else
                Console.WriteLine("Selected FASTA file: " + FastaPath);

            double tempErrorRate;
            if (double.TryParse(getArgument("errorRate"), out tempErrorRate))
            {
                if (tempErrorRate <= 0 || tempErrorRate >= 1)
                {
                    Console.WriteLine("Invalid error rate range. Has to be in interval <0,1>.");
                    Console.WriteLine("Using default error rate value of 0.05.");
                    ErrorRate = 0.05;
                }
                else
                {
                    ErrorRate = tempErrorRate;
                }
            }
            else
            {
                Console.WriteLine("Error rate not specified.");
                Console.WriteLine("Using default value of 0.05.");
                ErrorRate = 0.05;
            }

            int tempWordSize;
            if (int.TryParse(getArgument("wordSize"), out tempWordSize))
            {
                if (tempWordSize <= 0)
                {
                    Console.WriteLine("Invalid word size. Has to be > 0.");
                    Console.WriteLine("Using default word size value of 20.");
                    WordSize = 20;
                }
                else
                {
                    WordSize = tempWordSize;
                }
            }
            else
            {
                Console.WriteLine("Word size not specified.");
                Console.WriteLine("Using default word size value of 20.");
                WordSize = 20;
            }

            if (FastaPath != null)
            {
                // Load FASTA
                FASTAReader fastaReader = new FASTAReader(FastaPath, WordSize);
                fastaReader.ReadFASTA();
                fastaReader.SplitIntoWords();

                bloomFilter = new Filter(fastaReader.Words.Count, ErrorRate);

                foreach (string word in fastaReader.Words)
                {
                    bloomFilter.Add(word);
                }

                testMembershipLoop();
            }
            else
            {
                // Add elements manually
                bool validNumElements = false;
                int numElements;
                while (!validNumElements)
                {
                    Console.WriteLine("How many elements would you like to add? ");
                    string numElementsString = Console.ReadLine();
                    if (int.TryParse(numElementsString, out numElements))
                    {
                        if (numElements <= 0)
                        {
                            Console.WriteLine("Must be > 0.");
                        }
                        else
                        {
                            validNumElements = true;
                        }
                    }
                    else
                    {
                        Console.WriteLine("Invalid input.");
                    }
                    if (!validNumElements)
                    {
                        Console.WriteLine("Please enter again.");
                    }
                }
            }
        }

        public static void testMembershipLoop()
        {
            Console.WriteLine("You can now test if item is in filter.");
            while (true)
            {
                Console.WriteLine("Please enter item to be checked: ");
                string toCheck = Console.ReadLine();
                bool inFilter = bloomFilter.InFilter(toCheck);
                if (inFilter)
                {
                    Console.WriteLine(toCheck + " probably is in filter (" + ((1 - ErrorRate) * 100) + "%).");
                }
                else
                {
                    Console.WriteLine(toCheck + " is not in filter.");
                }
            }
        }

        public static string getArgument(string argName)
        {
            if (Arguments.ContainsKey(argName))
                return Arguments[argName];
            else
                return null;
        }

        public static void PrintFilterValues( IEnumerable myList, int myWidth )  {
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
