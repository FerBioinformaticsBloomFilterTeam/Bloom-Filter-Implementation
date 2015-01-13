Bloom-Filter-Implementation
===========================

Implementation of bloom filter data structure as a part of bioinformatics course on Faculty of Electrical Engineering and Computing, University of Zagreb
Course web page: https://www.fer.unizg.hr/predmet/bio

There are five different implementations of bloom filter (http://en.wikipedia.org/wiki/Bloom_filter) in repository, in C, C#, Python and two implementations in Java. Folders are named like _Name_BloomFilter_ProgramingLanguageUsedForImplementation_.

Application installation and startup


a) DamirBloomFilterJava
	Installation:
		
		1. Download repository
		2. Download and install java 8 using step by step guide on http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/ , don't skip step 3
		3a. Open terminal
		4a. Go to folder DamirBloomFilterJava\Application
		5a. To start application write command: java -jar BloomFilterStructureShowcase.jar
		3b. Double click file DamirBloomFilterJava\Application\BloomFilterStructureShowcase.jar

b) EdiBloomFilterC

c) FajdoBloomFilterJava

d) FilipBloomFilterCSharp

e) TomislavBloomFilterPython
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