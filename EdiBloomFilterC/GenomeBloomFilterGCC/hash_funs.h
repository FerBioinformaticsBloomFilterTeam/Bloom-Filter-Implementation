#include <inttypes.h>

//seed for fnv = 2166136261u
/*Calculates the 32-bit version of the FNV hash.*/
uint32_t fnv_hash(void* bytes, unsigned int len, uint32_t seed);
/*Calculates the 32-bit version of the murmur hash.*/
uint32_t murmur_hash(void* bytes, unsigned int len, uint32_t seed);

void fnv_set_prime(uint32_t prime);
