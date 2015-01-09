#!/usr/bin/python
#-*- coding: utf-8 -*-

from bloom_filter import *
from math import log
from math import ceil

"""Returns a bloom_filter with optimal storage parameters for initialization file,
filled with the words from the initialization file """
def fill_filter_from_file(filepath, max_error_prob):
    with open(filepath) as f:
        words = map(lambda x: x.strip("\r\n"), f.readlines()[1:])

    # calculate optimal parameters for the bloom filter
    optimal_hash_num = int(ceil(log(1 / max_error_prob)))
    optimal_filter_len = int(ceil(len(words) * log(1 / max_error_prob) / (log(2) ** 2)))

    filter = bloom_filter(optimal_filter_len, optimal_hash_num)

    # fill the filter with the words from the init file
    for word in words:
        filter.add(word)

    return filter