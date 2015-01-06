#!/usr/bin/python
#-*- coding: utf-8 -*-

from hashes import *

class bloom_filter(object):
    """
    Bloom filter for identification of the presence of a string.
    
    Methods:
     -  add(some_string) adds a string.
    
     -  test(some_string) returns false if the string is definitely not present,
        or true if it might be(false positives possible, though not often)
    """

    # constructor
    def __init__(self, vector_len, num_of_hashes):
        self.vector_len = vector_len
        self.num_of_hashes = num_of_hashes

        # initialize hash presence bit vector to 0
        self.hashes_vector = list()
        for i in range(0, vector_len):
            self.hashes_vector.append(False)

    # adds a string into the filter
    def add(self, some_string):
        calced_hashes = self.mass_hash(some_string, self.num_of_hashes, self.vector_len)
        
        for hash in calced_hashes:
            self.hashes_vector[hash] = True

    # returns true if there's a match(possible false positives)
    def test(self, some_string):
        calced_hashes = self.mass_hash(some_string, self.num_of_hashes, self.vector_len)

        for hash in calced_hashes:
            # if any of the bits is not set, the item is definitely not inside
            if not self.hashes_vector[hash]:
                return False

        # item is probably inside
        return True

    def mass_hash(self, some_string, hashnum, bucketnum):
        hashes = list()

        # inspiration: http://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
        for i in range(0, hashnum):
            hash = (fnv_hash(some_string) + murmur_hash(some_string) * i) % bucketnum
            hashes.append(hash)

        return hashes