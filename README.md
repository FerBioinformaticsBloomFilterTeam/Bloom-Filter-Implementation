Bloom-Filter-Implementation
===========================

### Description:

Implementation of bloom filter data structure as a part of bioinformatics course on Faculty of Electrical Engineering and Computing, University of Zagreb
Course web page: https://www.fer.unizg.hr/predmet/bio

There are five different implementations of bloom filter (http://en.wikipedia.org/wiki/Bloom_filter) in repository, in C, C#, Python and two implementations in Java. Folders are named like _Name_BloomFilter_ProgramingLanguageUsedForImplementation_.

### Application installation and startup:


**a) DamirBloomFilterJava**

	Installation and startup:
		1. Download repository
		2. Download and install java 8 using step by step guide on http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/
		3a. Open terminal
		4a. Go to folder DamirBloomFilterJava\Application
		5a. To start application write command: java -jar BloomFilterStructureShowcase.jar

**b) EdiBloomFilterC**

**c) FajdoBloomFilterJava**
	
	Installation and startup:
		1. Download repository from GitHub
		2. Download and install java 8 using step by step guide on http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/
		3a. Open terminal
		4a. Go to folder FajdoBloomFilterJava
		5a. To start application write command: java -jar ShowcaseFilterApp.jar

**d) FilipBloomFilterCSharp**

	Installation:
		1. Download repository
		2. If on Linux or Mac, install Mono by following [the instructions](http://www.mono-project.com/docs/getting-started/install/)
			2.1. Extract FilipBloomFilterCSharp/FilipBloomFilterCSharp-Linux.tar.gz
			2.2. Go to extracted directory containing BloomFilterApp.exe
			2.3. Run the application from terminal using "mono BloomFilterApp.exe" (without quotation marks)
		3. If on Windows
			3.1. Unzip FilipBloomFilterCSharp/FilipBloomFilterCSharp-Windows.zip
			3.2. Go to unzipped folder containing BloomFilterApp.exe
			3.3. Run by clicking on BloomFilterApp.exe or from commandline using "BloomFilterApp.exe" (withou quotation marks)
		4. Regardless of OS, application can receive some optional parameters when running from terminal/cmd:
			fasta => path to FASTA file that will be loaded when app is started
			wordSize => if loading FASTA file, it will be split in word sizes of this length; default is 20
			errorRate => probability of false positives when checking if item is in bloom filter
			Example usage form terminal:
			$ mono BloomFilterApp.exe fasta=/home/manager/Desktop/eschericia-part.fa wordSize=10 errorRate=0.01

	Testing performance:
		Open project BloomFilterPerfTests and run it from IDE (Visual Studio, Mono,...) or run binaries.
		Make sure path to {repository_clone}/test_cases is correct - you can send it as one any only parameter when running the app or change default path in code.
		When run, statistics like time of execution of different parts and memory usage will be shown.
	
	Usage in other projects:
		You will also be provided with BloomFilter.dll file which can be used in other projects.
		Class of interest is Filter in namespace BloomFilter and its methods:
		public Filter(int n, double p) => creates new Filter object that expects n items to be added and false positive probability of p
		public void Add(string value) => gets ASCII bytes of string and adds it to filter
		public void Add(byte[] value) => adds bytes to filter
		public bool InFilter(string value) => returns whether or not string is in filter
		public bool InFilter(byte[] value) => returns whether or not bytes are in filter

**e) TomislavBloomFilterPython**

    Installation:
        1. Download repository
        2. Run script TomislavBloomFilterPython/install.sh to install
            => This will compile tomoHashes.cpp into a library which is wrapped by the bloom_filter

    Testing:
        1. To run a test on all test case files from the test_cases folder, simply run TomislavBloomFilterPython/test_on_all_files.sh
        2. To run a test on any init and test file of your choosing, run testing.py like so:
               testing.py <init_file_path> <test_file_path> <false_positive_percentage>

    Usage in other projects:
        To use this implementation in other projects, you don't need anything but install.sh, tomoHashes.cpp, bloom_filter.py and bloom_filter_factory.py
        Simply compile the cpp with install.sh, and then import bloom_filter and bloom_filter_factory into your project.

        The rest of the scripts here are for testing/demonstration purposes.
