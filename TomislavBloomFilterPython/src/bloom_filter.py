#!/usr/bin/env python

import ctypes
import os
import sys
from array import array
from math import ceil

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
        my_dir = os.path.dirname(os.path.realpath(__file__))
        
        # build an object that wraps a custom c++ hash library
        self.hash_lib = ctypes.CDLL(os.path.join(my_dir, 'tomohashes.so'))
        
        self.vector_len = vector_len
        self.num_of_hashes = num_of_hashes

        # initialize hash presence bit vector to 0(empty)
        zero = chr(0)
        self.bits = array('c', [zero,] * int(ceil(self.vector_len / 8.0)))

    # adds a string into the filter
    def add(self, some_string):
        calced_hashes = self._mass_hash(some_string, self.num_of_hashes, self.vector_len)
        
        for hash in calced_hashes:
            self.set_bit_at(hash)

    # returns true if there's a match(possible false positives)
    def test(self, some_string):
        calced_hashes = self._mass_hash(some_string, self.num_of_hashes, self.vector_len)

        for hash in calced_hashes:
            # if any of the bits is not set, the item is definitely not inside
            if not self.is_bit_set(hash):
                return False

        #item is probably inside
        return True

    def is_bit_set(self, index):
        # whole number division(like floor(index/8.0))
        char_index = index / 8

        index_in_char = index % 8
        
        # return index_in_char-th bit of char_index-th char in the bit array
        byte = ord(self.bits[char_index])

        # we need to isolate the bit
        # unset all other bits(BESIDES the one)
        # if it's now 0, the bit was 0, else it was 1
        cancelator = 1 << index_in_char

        return bool(byte & cancelator)

    def set_bit_at(self, index):
        # whole number division(like floor(index/8.0))
        char_index = index / 8

        index_in_char = index %  8
        
        # set index_in_char-th bit of char_index-th char in the bit array
        byte = ord(self.bits[char_index])
        self.bits[char_index] = chr(byte | (1 << index_in_char))

    # calculates hashnum different hashes of some_string while hashing only once
    # constrained to values in a range of 0 to bucketnum-1
    # inspiration: http://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
    def _mass_hash(self, some_string, hashnum, bucketnum):
        length = len(some_string)

        # get fnv and murmur hash directly to save on object overhead
        fnv = ctypes.c_uint32(self.hash_lib.FnvHash(some_string, length)).value
        murmur = ctypes.c_uint32(self.hash_lib.MurmurHash(some_string, length, 16777619)).value
            
        return [(fnv + murmur * i) % bucketnum for i in range(0, hashnum)]

