#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>

using namespace std;

// source: http://stackoverflow.com/questions/4239993/determining-endianness-at-compile-time
bool isLittleEndian()
{
	short int number = 0x1;
	char *numPtr = (char*)&number;
	return (numPtr[0] == 1);
}

extern "C" uint FnvHash(const char * word, int length) {
	uint32_t fnv_prime = 16777619;
	uint32_t hash = 2166136261;

	uint8_t currByte;

	for (int i = 0; i < length; i++) {
		currByte = (uint8_t) *(word + i);

		// XOR last byte of hash with current byte and OR it with the rest
		hash = (hash & 0xFFFFFF00) | ((hash & 255) ^ currByte);
		hash *= fnv_prime;
	}

	return hash;
}

extern "C" uint32_t MurmurHash(const char * word, int length, uint32_t seed) {
	uint32_t c1 = 0xcc9e2d51;
	uint32_t c2 = 0x1b873593;
	uint32_t r1 = 15;
	uint32_t r2 = 13;
	uint32_t m = 5;
	uint32_t n = 0xe6546b64;

	uint32_t hash = seed;
	uint32_t currChunk;

	// iterate for every 32 bits
	int limit = length / 4;
	const uint32_t * chunkArray = (const uint32_t *)word;
	for (int i = 0; i < limit; i++) {
		currChunk = chunkArray[i];

		currChunk *= c1;
		currChunk = (currChunk << r1) | (currChunk >> (32-r1));
		currChunk *= c2;

		hash ^= currChunk;
		hash = ((hash << r2) | (hash >> (32 - r2))) * m + n;
	}

	// if there are leftover bytes
	if (4 * limit != length) {
		uint32_t leftovers = 0;
		for (int i = limit; i < length; i++) {
			leftovers = leftovers | *(word + i);
		}

		// switch order of bytes if big endian
		if (!isLittleEndian()) {
			leftovers = __builtin_bswap32(leftovers);
		}

		leftovers *= c1;
		leftovers = (leftovers << r1) | (leftovers >> (32 - r1));
		leftovers *= c2;

		hash = hash ^ leftovers;
	}

	hash ^= length;
	hash ^= (hash >> 16);
	hash *= 0x85ebca6b;
	hash ^= (hash >> 13);
	hash *= 0xc2b2ae35;
	hash ^= (hash >> 16);

	return hash;
}