using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace BloomFilterApp
{
    public class FASTAReader
    {
        public string FilePath;
        public string FastaWhole;
        public List<string> Words;
        public int WordSize;

        /// <summary>
        /// Initializes a new instance of the <see cref="BloomFilterApp.FASTAReader"/> class.
        /// </summary>
        /// <param name="filePath">FASTA file path.</param>
        /// <param name="wordSize">Word size.</param>
        public FASTAReader(string filePath, int wordSize = 20)
        {
            FilePath = filePath;
            WordSize = wordSize;
            Words = new List<string>();
        }

        /// <summary>
        /// Loads FASTA into a string.
        /// </summary>
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

        /// <summary>
        /// Splits FASTA string into words and puts them in a list.
        /// </summary>
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

