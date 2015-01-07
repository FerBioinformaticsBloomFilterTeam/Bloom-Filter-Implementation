#!/usr/bin/python
#-*- coding: utf-8 -*-

import argparse
from bloom_filter import *
from math import log
from math import ceil

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

# every line in the form of: <word> <expected_status>,
# where expected_status is 0 or 1
def test_filter_from_file(filepath, filter, print_successes = False):
    with open(filepath) as f:
        lines = map(lambda x: x.strip("\r\n"), f.readlines())

    # iterate through test lines and conduct each test
    for sequence, expected_status in map(lambda x: (x.split()[0], int(x.split()[1])), lines):
        presence_status = bool(filter.test(sequence))
        
        if presence_status == bool(expected_status):
            if print_successes:
                print "Testing for sequence " + sequence + " is a match"
        else:
            print "Testing for sequence %s does NOT match - Expected %s but got %s" % (sequence, expected_status, presence_status)

parser = argparse.ArgumentParser(description='Initialize and test a bloom filter.')
parser.add_argument('word_size', metavar = 'word_size', type=int,
                   help='Word size')
parser.add_argument('init_filepath', metavar='init_path', type=str,
                   help='Path to the initialization file')
parser.add_argument('test_filepaths', metavar = 'test_paths', type=str,
                   help='Paths to testing files')

args = parser.parse_args()

print "Initializing bloom filter from init file..."
filter = fill_filter_from_file(args.init_filepath, args.word_size, 0.1)
print "DONE!\n-------------"

print "Initializing testing for all test files...\n-------------"
for test_filepath in args.test_filepaths.split(','):
    print "Moving onto next file..."
    test_filter_from_file(test_filepath, filter)
    print "DONE!\n-----------"