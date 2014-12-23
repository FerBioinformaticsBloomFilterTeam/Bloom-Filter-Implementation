#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash_funs.h"
#include "bloom_filter.h"

int main()
{
    char* cont = "crack";
    uint32_t len = strlen(cont);
    uint32_t seed = 2166136261u;
    uint32_t res = murmur_hash(cont, len, seed);
    uint32_t i = 0;
    uint32_t n = 23;
    printf("Seed: %u\nSol: %u\nSol 0x: %x\n", seed, res, res);
    init(0.01, n, 2);
    while (i < n) {
        char a[30];
        int res;
        scanf("%s", a);
        res = test(a, strlen(a));
        if (res == 0) {
            printf("Not in set\n");
        } else if (res == 1) {
            printf("Probably in set\n");
        } else {
            printf("error\n");
        }
        add(a, strlen(a));
        i++;
    }
    clear();
    return 0;
}
