#!/bin/bash
cd src
echo "Compiling hash library..."
g++ tomoHashes.cpp -o tomohashes.so -shared -Wl,-soname,tomohashes -fPIC
echo "Installation complete."
echo "Run /src/testing.py <init_file> <test_file> <false_positive_percentage> to test.
Run /src/short_example.py to see the bloom filter at work with a small example set of sequences.
To use the bloom filter in another project, simply import the modules you wish."
