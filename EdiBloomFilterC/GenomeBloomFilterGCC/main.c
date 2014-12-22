#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash_funs.h"

int main()
{
    char* cont = "crack";
    uint32_t len = strlen(cont);
    uint32_t seed = 2166136261u;
    uint32_t res = murmur_hash(cont, len, seed);
    printf("Seed: %u\nSol: %u\nSol 0x: %x\n", seed, res, res);
    return 0;
}
