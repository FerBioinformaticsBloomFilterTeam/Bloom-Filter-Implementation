#include "hash_funs.h"
#include <stdio.h>

uint32_t prime = 16777619u;

uint32_t fnv_hash(void* bytes, unsigned int len, uint32_t seed) {
    uint32_t hash = seed;
    const uint8_t* by = (uint8_t*) bytes;
    unsigned int i;
    for (i = 0; i < len; i++) {
        hash *= prime;
        hash ^= by[i];
    }
    return hash;
}

void fnv_set_prime(uint32_t pr) {
    prime = pr;
}

uint32_t murmur_hash(void* bytes, unsigned int len, uint32_t seed) {
    static const uint32_t c1 = 0xcc9e2d51;
    static const uint32_t c2 = 0x1b873593;
    static const uint32_t r1 = 15;
    static const uint32_t r2 = 13;
    static const uint32_t m = 5;
    static const uint32_t n = 0xe6546b64;


    uint32_t hash = seed;
    const uint32_t* by = (uint32_t*) bytes;
    const unsigned int n4_len = len / 4;
    const uint8_t* by8 = &((uint8_t*) bytes)[len & 0xFFFFFFFC];
    const unsigned int n4_rem = len & 3;
    unsigned int i;
    unsigned int k;
    uint32_t remaining = 0;

    for (i = 0; i < n4_len; i++) {
        k = by[i];
        k *= c1;
        k = (k << r1) | (k >> (32 - r1));
        k *= c2;

        hash ^= k;
        hash = (hash << r2) | (hash >> (32 - r2));
        hash = hash * m + n;
    }

    switch (n4_rem) {
    case 3 :
        remaining ^= by8[2] << 16;
    case 2:
        remaining ^= by8[1] << 8;
    case 1:
        remaining ^= by8[0];

        remaining *= c1;
        remaining = (remaining << r1) | (remaining >> (32 - r1));
        remaining *= c2;

        hash ^= remaining;
    }

    hash ^= len;
    hash ^= hash >> 16;
    hash *= 0x85ebca6b;
    hash ^= hash >> 13;
    hash *= 0xc2b2ae35;
    hash ^= hash >> 16;

    return hash;
}
