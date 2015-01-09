#!/usr/bin/python
#-*- coding: utf-8 -*-

import argparse
from bloom_filter import *
from math import log
from math import ceil

"""Returns a bloom_filter with optimal storage parameters for initialization file,
filled with the words from the initialization file """
def fill_filter_from_file(filepath, word_length, max_error_prob):
    with open(filepath) as f:
        contents = ''.join(map(lambda x: x.strip("\r\n"), f.readlines()[1:]))

    # calculate optimal parameters for the bloom filter
    elemnum = len(contents) / word_length

    optimal_hash_num = int(ceil(log(1 / max_error_prob)))
    optimal_filter_len = int(ceil(elemnum * log(1 / max_error_prob) / (log(2) ** 2)))

    filter = bloom_filter(optimal_filter_len, optimal_hash_num)

    # fill the filter with the words from the init file
    ind = 0
    while True:
        word = contents[ind:ind+word_length]
            
        if len(word) < word_length:
            break
        else:
            filter.add(word)

        ind += word_length

    return filter

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Initialize and test a bloom filter.')
    parser.add_argument('word_size', metavar = 'word_size', type=int,
                       help='Word size')
    parser.add_argument('init_filepath', metavar='init_path', type=str,
                       help='Path to the initialization file')
    parser.add_argument('test_filepaths', metavar = 'test_paths', type=str,
                       help='Paths to testing files')

    args = parser.parse_args()

    print "Example bloom filter run..."
    print "Initializing bloom filter from init file..."
    filter = fill_filter_from_file(args.init_filepath, args.word_size, 0.1)
    print "DONE!\n-------------"