#!/usr/bin/python
#-*- coding: utf-8 -*-

from hashes import *
import ctypes
import os

class bloom_filter(object):
    """
    Bloom filter for identification of the presence of a string.
    
    Methods:
     -  add(some_string) adds a string.
    
     -  test(some_string) returns false if the string is definitely not present,
        or true if it might be(false positives possible, though not often)
    """
    def get_murmur_hash(self, word):
        return ctypes.c_uint32(self.hash_lib.MurmurHash(word, len(word), 16777619)).value

    def get_fnv_hash(self, word):
        return ctypes.c_uint32(self.hash_lib.FnvHash(word, len(word))).value

    # constructor
    def __init__(self, vector_len, num_of_hashes):
        self.currDir = os.getcwd()
        self.hash_lib = ctypes.CDLL('/home/manager/bloom_filter/tomohashes.so')
        self.vector_len = vector_len
        self.num_of_hashes = num_of_hashes

        # initialize hash presence bit vector to 0
        self.hashes_set = set()

    # adds a string into the filter
    def add(self, some_string):
        calced_hashes = self.mass_hash(some_string, self.num_of_hashes, self.vector_len)
        
        for hash in calced_hashes:
            self.hashes_set.add(hash)

    # returns true if there's a match(possible false positives)
    def test(self, some_string):
        calced_hashes = self.mass_hash(some_string, self.num_of_hashes, self.vector_len)

        for hash in calced_hashes:
            # if any of the bits is not set, the item is definitely not inside
            if hash not in self.hashes_set:
                return False

        # item is probably inside
        return True

    # calculates hashnum different hashes of some_string
    # in range of 0 to bucketnum-1
    def mass_hash(self, some_string, hashnum, bucketnum):
        length = len(some_string)

        # inspiration: http://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
        fnv = ctypes.c_uint32(self.hash_lib.FnvHash(some_string, length)).value
        murmur = ctypes.c_uint32(self.hash_lib.MurmurHash(some_string, length, 16777619)).value
            
        return [(fnv + murmur * i) % bucketnum for i in range(0, hashnum)]
