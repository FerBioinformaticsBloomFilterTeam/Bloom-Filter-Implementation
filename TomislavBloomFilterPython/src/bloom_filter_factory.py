#!/usr/bin/env python

from bloom_filter import *
from math import log
from math import ceil

"""Returns a bloom_filter with optimal storage parameters for initialization file,
filled with the words from the initialization file """
def fill_filter_from_file(filepath, max_error_prob, suppres_prints = False):
    with open(filepath) as f:
        word_num = int(f.readline().strip("\r\n"))

        if (not suppres_prints):
            print "Number of words: " + str(word_num)

        # calculate optimal parameters for the bloom filter
        optimal_hash_num = int(ceil(log(1 / max_error_prob)))
        optimal_filter_len = int(ceil(word_num * log(1 / max_error_prob) / (log(2) ** 2)))

        if (not suppres_prints):
            print "Number of hashes: %s\nFilter length: %s" \
                    % (optimal_hash_num, optimal_filter_len) 

        filter = bloom_filter(optimal_filter_len, optimal_hash_num)

        # fill the filter with the words from the init file
        for line in f:
            filter.add(line.strip("\r\n"))

        return filter