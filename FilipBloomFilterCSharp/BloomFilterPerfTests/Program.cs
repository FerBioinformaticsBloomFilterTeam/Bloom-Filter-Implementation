using System;
using System.IO;
using System.Text;
using BloomFilter;
using System.Diagnostics;

namespace BloomFilterPerfTests
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            string testDirectory = "";
            if (args.Length == 0)
            {
                Console.WriteLine("Please send test cases directory as parameter.");
                Console.WriteLine("Trying to use default dir...");
                testDirectory = "/home/manager/FerBioinformatika/Bloom-Filter-Implementation/test_cases";
            }
            else if (args.Length == 1)
            {
                testDirectory = args[0];
            }

            if (Directory.Exists(testDirectory))
            {
                Console.WriteLine("Test Directory:\n\t" + testDirectory);
                Console.WriteLine("Test files:");
                string[] fileEntries = Directory.GetFiles(testDirectory);
                Stopwatch sw = new Stopwatch();
                Filter bloomFilter;

                foreach (string filePath in fileEntries)
                {
                    string folderName = Path.GetDirectoryName(filePath);
                    string fileName = Path.GetFileName(filePath);
                    string[] fileType = fileName.Split('_');
                    if (fileType[1] == "added")
                    {
                        Console.WriteLine();
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("\t" + fileName);
                        Console.ResetColor();

                        double[] errorRates = new double[4] { 0.05, 0.10, 0.15, 0.20 };
                        foreach (double errorRate in errorRates)
                        {
                            using (StreamReader srAdd = new StreamReader(filePath))
                            {
                                // Add to filter.
                                Console.WriteLine("\t\t========================================");
                                sw.Reset();
                                sw.Start();
                                string line;
                                int number = Convert.ToInt32(srAdd.ReadLine());
                                Console.ForegroundColor = ConsoleColor.Green;
                                Console.WriteLine("\t\tAdding " + number.ToString("N0") + " items to filter.");
                                Console.WriteLine("\t\tUsing false positive probability: " + errorRate + ".");
                                Console.ResetColor();
                                bloomFilter = null;
                                GC.Collect();
                                long startBytes = GC.GetTotalMemory(true);
                                bloomFilter = new Filter(number, errorRate);
                                while ((line = srAdd.ReadLine()) != null)
                                {
                                    bloomFilter.Add(Encoding.ASCII.GetBytes(line));
                                }
                                sw.Stop();
                                long stopBytes = GC.GetTotalMemory(true);
                                Console.WriteLine("\t\tAdded from file into filter in time: " + sw.ElapsedMilliseconds + "ms [" + sw.ElapsedTicks + " ticks].");
                                Console.WriteLine("\t\tNumber of hash functions: " + bloomFilter.K);
                                Console.WriteLine("\t\tNumber of bits in array: " + bloomFilter.Bits.Count + " [" + (bloomFilter.Bits.Count / 8).ToString("N0") + "B]");
                                Console.WriteLine("\t\tMemory Garbage Collector allocated: " + (stopBytes - startBytes).ToString("N0") + "B.");

                                // Test set membership.
                                // words_added_10_000.txt
                                // words_test_10_000.txt
                                Console.ForegroundColor = ConsoleColor.Green;
                                Console.WriteLine("\n\t\tTesting membership...");
                                Console.ResetColor();
                                string testFile = fileType[0] + "_test_";
                                string numFormated = "";
                                for (int i = 2; i < fileType.Length; i++)
                                {
                                    numFormated += fileType[i];
                                    if (i < fileType.Length - 1)
                                        numFormated += "_";
                                }
                                string testPath = folderName + "/" + testFile + numFormated;

                                using (StreamReader srTest = new StreamReader(testPath))
                                {
                                    sw.Reset();
                                    sw.Start();
                                    int numFalsePositives = 0;
                                    int totalNumChecked = 0;

                                    while ((line = srTest.ReadLine()) != null)
                                    {
                                        var split = line.Split('\t');
                                        string testString = split[0];
                                        bool exists = split[1] == "0" ? false : true;

                                        bool inFilter = bloomFilter.InFilter(Encoding.ASCII.GetBytes(testString));

                                        if (exists == false && inFilter == true)
                                        {
                                            numFalsePositives++;
                                        }
                                        totalNumChecked++;
                                    }
                                    sw.Stop();
                                    GC.Collect();
                                    stopBytes = GC.GetTotalMemory(true);

                                    Console.WriteLine("\t\tChecked membership of " + totalNumChecked + " elements.");
                                    Console.WriteLine("\t\tChecked in time: " + sw.ElapsedMilliseconds + "ms [" + sw.ElapsedTicks + " ticks].");
                                    Console.WriteLine("\t\tNumber of false positives: " + numFalsePositives.ToString("N0") + " [" + (double)numFalsePositives / number + "~" + errorRate + "]");
                                    Console.WriteLine("\t\tMemory Garbage Collector allocated: " + (stopBytes - startBytes).ToString("N0") + "B.");
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                Console.WriteLine("Invalid dir.");
            }
        }
    }
}
