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

            FastaPath = GetArgument("fasta");
            if (FastaPath == null)
                Console.WriteLine("No FASTA file selected.");
            else
            {
                Console.WriteLine("Selected FASTA file: " + FastaPath);
                int tempWordSize;
                if (int.TryParse(GetArgument("wordSize"), out tempWordSize))
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
            }

            double tempErrorRate;
            if (double.TryParse(GetArgument("errorRate"), out tempErrorRate))
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

            if (FastaPath != null)
            {
                // Load FASTA
                FASTAReader fastaReader = new FASTAReader(FastaPath, WordSize);
                fastaReader.ReadFASTA();
                fastaReader.SplitIntoWords();

                bloomFilter = new Filter(fastaReader.Words.Count, ErrorRate);
                PrintFilterInfo();

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
                int numElements = 0;
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

                bloomFilter = new Filter(numElements, ErrorRate);
                PrintFilterInfo();

                while (numElements > 0)
                {
                    Console.WriteLine("Please enter item you wish to add: ");
                    string toAdd = Console.ReadLine();
                    if (toAdd.Length > 0)
                    {
                        bloomFilter.Add(toAdd);
                        numElements--;
                    }
                    else
                    {
                        Console.WriteLine("Invalid input.");
                    }
                }

                testMembershipLoop();
            }
        }

        public static void testMembershipLoop()
        {
            Console.WriteLine("You can now test if item is in filter.");
            while (true)
            {
                Console.WriteLine("Please enter item to be checked: ");
                string toCheck = Console.ReadLine();
                if (toCheck.Length > 0)
                {
                    bool inFilter = bloomFilter.InFilter(toCheck);
                    if (inFilter)
                    {
                        Console.ForegroundColor = ConsoleColor.Green;
                        Console.WriteLine(toCheck + " probably is in filter (" + ((1 - ErrorRate) * 100) + "%).");
                        Console.ResetColor();
                    }
                    else
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine(toCheck + " is not in filter.");
                        Console.ResetColor();
                    }
                }
                else
                {
                    Console.WriteLine("Invalid input.");
                }
            }
        }

        public static string GetArgument(string argName)
        {
            if (Arguments.ContainsKey(argName))
                return Arguments[argName];
            else
                return null;
        }

        public static void PrintFilterInfo()
        {
            Console.WriteLine("Bloom filter info: ");
            Console.WriteLine("\t- Expected number of elements (N): " + bloomFilter.N);
            Console.WriteLine("\t- Number of hash functions (K): " + bloomFilter.K);
            Console.WriteLine("\t- Lengh of bit array (M): " + bloomFilter.M);
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
