#include <stdio.h>
#include "hash_funs.h"
#include <math.h>
#include <stdlib.h>

uint8_t *filter = 0;
uint32_t filter_bit_len = 0;
uint32_t filter_byte_len = 0;
uint32_t (*hashes[2])(void*, unsigned int, uint32_t);
uint16_t k = 0;

void init(float p, uint32_t n, uint16_t k1) {
    hashes[0] = fnv_hash;
    hashes[1] = murmur_hash;
    k = k1;
    filter_bit_len = (uint32_t) ceilf(- n * logf(p) / (powf(logf(2), 2.0f)));
    printf("Bit len: %u", filter_bit_len);
    filter_byte_len = filter_bit_len / 8 + (filter_bit_len % 8 != 0 ? 1 : 0);
    filter = (uint8_t *) calloc(filter_byte_len, 1);
}

uint32_t* get_indices(void* obj, unsigned int len) {
    uint32_t *indices = (uint32_t *) calloc(k, sizeof(uint32_t));
    uint32_t i = 0;
    uint32_t fst_hash = hashes[0](obj, len, 2166136261u);
    uint32_t snd_hash = hashes[1](obj, len, 2166136261u);
    for(i = 0; i < k; i++) {
        indices[i] = (fst_hash + i * snd_hash) % filter_bit_len;
    }
    return indices;
}

int get_bit(uint32_t index) {
    uint32_t byte_ind, bit_ind;
    if (index < 0 || index >= filter_bit_len)
        return -1;
    byte_ind = index >> 3;
    bit_ind = index & 7;
    return (filter[byte_ind] >> bit_ind) & 1u;
}

int test(void* obj, unsigned int len) {
    uint32_t *indices = get_indices(obj, len);
    uint16_t i;
    int res = 1;
    for (i = 0; i < k; i++) {
        int bit = get_bit(indices[i]);
        if (bit == 0) {
            res = 0;
            break;
        } else if (bit == -1) {
            printf("error in get_bit");
            exit(2);
        }
    }
    free(indices);
    return res;
}

void set_bit(uint32_t index) {
    uint32_t byte_ind, bit_ind;
    if (index < 0 || index >= filter_bit_len) {
        printf("error in set_bit");
        exit(2);
    }
    byte_ind = index >> 3;
    bit_ind = index & 7;
    filter[byte_ind] |= 1u << bit_ind;
}

void add(void* obj, unsigned int len) {
    uint32_t *indices = get_indices(obj, len);
    uint16_t i;
    for (i = 0; i < k; i++) {
        set_bit(indices[i]);
    }
    free(indices);
}

void clear(void) {
    free(filter);
    filter_bit_len = 0;
    filter_byte_len = 0;
    k = 0;
}
