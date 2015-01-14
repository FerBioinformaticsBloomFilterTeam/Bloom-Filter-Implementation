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
            // Set path to test_cases.
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

            // Check if path is correct.
            if (Directory.Exists(testDirectory))
            {
                // Basic info about tests.
                Console.WriteLine("Test Directory:\n\t" + testDirectory);
                Console.WriteLine("Test files:");

                // Get all tests.
                string[] fileEntries = Directory.GetFiles(testDirectory);

                // Stopwatch for time measurement
                Stopwatch sw = new Stopwatch();

                // Bloom filter reference.
                Filter bloomFilter;

                foreach (string filePath in fileEntries)
                {
                    // Get info about file.
                    string folderName = Path.GetDirectoryName(filePath);
                    string fileName = Path.GetFileName(filePath);
                    string[] fileType = fileName.Split('_');

                    // *added* files are ones that are used for insertions.
                    if (fileType[1] == "added")
                    {
                        Console.WriteLine();
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("\t" + fileName);
                        Console.ResetColor();

                        // Error rates we test.
                        double[] errorRates = new double[3] { 0.5, 0.05, 0.20 };
                        foreach (double errorRate in errorRates)
                        {
                            using (StreamReader srAdd = new StreamReader(filePath))
                            {
                                #region Insertion into bloom filter.
                                Console.WriteLine("\t\t========================================");
                                // Start timer.
                                sw.Reset();
                                sw.Start();
                                string line;

                                // Get number of items that will be added.
                                int number = Convert.ToInt32(srAdd.ReadLine());
                                Console.ForegroundColor = ConsoleColor.Green;
                                Console.WriteLine("\t\tAdding " + number.ToString("N0") + " items to filter.");
                                Console.WriteLine("\t\tUsing false positive probability: " + errorRate + ".");
                                Console.ResetColor();

                                // Call garbage collector so that we can measure memory each time "cleanly".
                                bloomFilter = null;
                                GC.Collect();
                                long startBytes = GC.GetTotalMemory(true);

                                // Create and initialize filter.
                                bloomFilter = new Filter(number, errorRate);
                                while ((line = srAdd.ReadLine()) != null)
                                {
                                    // Add to filter.
                                    bloomFilter.Add(Encoding.ASCII.GetBytes(line));
                                }

                                // Stop measuring time after all is added.
                                sw.Stop();

                                // Measure memory that Filter take up.
                                long stopBytes = GC.GetTotalMemory(true);

                                // Print statistics.
                                Console.WriteLine("\t\tAdded from file into filter in time: " + sw.ElapsedMilliseconds + "ms [" + sw.ElapsedTicks + " ticks].");
                                Console.WriteLine("\t\tNumber of hash functions: " + bloomFilter.K);
                                Console.WriteLine("\t\tNumber of bits in array: " + bloomFilter.Bits.Count + " [" + (bloomFilter.Bits.Count / 8).ToString("N0") + "B]");
                                Console.WriteLine("\t\tMemory Garbage Collector allocated: " + (stopBytes - startBytes).ToString("N0") + "B.");
                                #endregion

                                #region Test set membership.
                                Console.ForegroundColor = ConsoleColor.Green;
                                Console.WriteLine("\n\t\tTesting membership...");
                                Console.ResetColor();

                                // Get test file which contains words that will be tested and 100% accurate set membership that is pre-defined.
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
                                    // Start timer.
                                    sw.Reset();
                                    sw.Start();
                                    int numFalsePositives = 0;
                                    int numFalseNegatives = 0;
                                    int totalNumChecked = 0;

                                    while ((line = srTest.ReadLine()) != null)
                                    {
                                        var split = line.Split('\t');

                                        // Get string that will be tested.
                                        string testString = split[0];

                                        // Get 100% correct membership.
                                        bool exists = split[1] == "0" ? false : true;

                                        // Test
                                        bool inFilter = bloomFilter.InFilter(Encoding.ASCII.GetBytes(testString));

                                        // Compare and check for false positives. False negatives should be impossible but show them anyway.
                                        if (exists == false && inFilter == true)
                                        {
                                            numFalsePositives++;
                                        }
                                        if (exists == true && inFilter == false)
                                        {
                                            numFalseNegatives++;
                                        }
                                        totalNumChecked++;
                                    }

                                    // Stop timer.
                                    sw.Stop();

                                    // Get memory usage.
                                    //GC.Collect();
                                    stopBytes = GC.GetTotalMemory(true);

                                    // Print out statistics.
                                    Console.WriteLine("\t\tChecked membership of " + totalNumChecked + " elements.");
                                    Console.WriteLine("\t\tChecked in time: " + sw.ElapsedMilliseconds + "ms [" + sw.ElapsedTicks + " ticks].");
                                    Console.WriteLine("\t\tNumber of false positives: " + numFalsePositives.ToString("N0") + " [" + (double)numFalsePositives / number + "~" + errorRate + "]");
                                    Console.WriteLine("\t\tNumber of false negatives: " + numFalseNegatives.ToString("N0") + " [impossible]");
                                    Console.WriteLine("\t\tMemory Garbage Collector allocated: " + (stopBytes - startBytes).ToString("N0") + "B.");
                                }
                                #endregion
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
