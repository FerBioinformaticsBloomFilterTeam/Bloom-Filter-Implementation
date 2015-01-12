#!/usr/bin/python
#-*- coding: utf-8 -*-
import sys

# http://www.isthe.com/chongo/tech/comp/fnv/index.html#FNV-param
def fnv_hash(some_string):
    fnv_prime = 16777619
    hash = 2166136261

    for byte in bytearray(some_string):
        hash = hash ^ byte
        hash *= fnv_prime

    return hash

def murmur_hash(some_string, seed = 16777619):
    c1 = 0xcc9e2d51
    c2 = 0x1b873593
    r1 = 15
    r2 = 13
    m = 5
    n = 0xe6546b64

    hash = seed

    ind = 0
    some_string = bytearray(some_string)
    while ind+4 <= len(some_string):
        string_chunk = some_string[ind:ind+4]
        
        # put these 4 together into an integer
        chunk = (string_chunk[0] << 24) | \
                (string_chunk[1] << 16) | \
                (string_chunk[2] << 8)  | \
                string_chunk[3]

        chunk *= c1
        chunk = (chunk << r1) | (chunk >> (32-r1))
        chunk *= c2

        hash = hash ^ chunk
        hash = (hash << r2) | (hash >> (32-r2))
        hash = hash * m + n

        ind += 4

    # if there are any leftovers
    if ind < len(some_string):
        
        leftover_str = some_string[ind:]

        # swap endian of leftover bytes if this is a big endian machine
        if sys.byteorder != 'little':
            pass

        shiftnum = 24
        leftover = leftover_str[0] << shiftnum
        for piece in leftover_str:
            shiftnum -= 8
            leftover = leftover | (piece << shiftnum)

        leftover *= c1
        leftover = (leftover << r1) | (leftover >> (32-r1))
        leftover *= c2

        hash = hash ^ leftover

    hash = hash ^ len(some_string)

    hash = hash ^ (hash >> 16)
    hash = hash * 0x85ebca6b
    hash = hash ^ (hash >> 13)
    hash = hash * 0xc2b2ae35
    hash = hash ^ (hash >> 16)

    return hash

        