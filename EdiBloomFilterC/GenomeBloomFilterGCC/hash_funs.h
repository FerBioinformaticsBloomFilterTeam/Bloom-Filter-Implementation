#include <inttypes.h>

//seed for fnv = 2166136261u
uint32_t fnv_hash(void* bytes, unsigned int len, uint32_t seed);
uint32_t murmur_hash(void* bytes, unsigned int len, uint32_t seed);

void fnv_set_prime(uint32_t prime);
