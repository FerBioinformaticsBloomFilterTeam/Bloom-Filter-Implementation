using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace BloomFilterUnitTests
{
    public class FASTAReader
    {
        public string FilePath;
        public string FastaWhole;
        public List<string> Words;
        public int WordSize;

        public FASTAReader(string filePath, int wordSize = 20)
        {
            FilePath = filePath;
            WordSize = wordSize;
            Words = new List<string>();
        }

        public void ReadFASTA()
        {
            using (StreamReader sr = new StreamReader(FilePath))
            {
                StringBuilder sb = new StringBuilder();
                FastaWhole = "";
                string line;
                while ((line = sr.ReadLine()) != null) 
                {
                    if (line[0] != '>')
                        sb.Append(line);
                }

                FastaWhole = sb.ToString();
            }
        }

        public void SplitIntoWords()
        {
            for (int i = 0; i < FastaWhole.Length; i += WordSize)
            {
                if (FastaWhole.Length - i < WordSize)
                {
                    Words.Add(FastaWhole.Substring(i, FastaWhole.Length - i));
                }
                else
                {
                    Words.Add(FastaWhole.Substring(i, WordSize));
                }
            }

            FastaWhole = "";
        }
    }
}

