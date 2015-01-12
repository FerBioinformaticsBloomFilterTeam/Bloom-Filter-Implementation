#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash_funs.h"
#include "bloom_filter.h"
#include <time.h>

long get_file_size(FILE* f) {
    long t1, t2;
    fseek(f, 0L, SEEK_SET);
    t1 = ftell(f);
    fseek(f, 0L, SEEK_END);
    t2 = ftell(f);
    fseek(f, 0L, SEEK_SET);
    return t2 - t1;
}

int main(int argc, char** argv)
{
    FILE *dna;
    FILE *test_f;
    int word_size;
    long file_size;
    uint32_t i = 0;
    uint32_t n;
    char *wrd;
    uint32_t num_pos = 0, num_neg = 0, neg = 0, pos = 0;
    int filter_res;
    struct timeval time1, time2, time3;
    double millis1, millis2;
    double p;

    if (argc != 5) {
        fprintf(stderr,
            "4 arguments expected: Path to the file with the genome,\
            word size, path to the file with test cases and percentage of false positives.");
        exit(1);
    }

    dna = fopen(argv[1], "r");
    word_size = atoi(argv[2]);
    file_size = get_file_size(dna);
    n = file_size / word_size + (file_size % word_size != 0 ? 1 : 0);
    wrd = calloc(word_size + 1, sizeof(char));
    p = atof(argv[4]);

    if (word_size <= 0 || file_size <= 0) {
        fprintf(stderr, "Negative file size or negative word length.");
        fclose(dna);
        exit(2);
    }

    gettimeofday(&time1, 0);
    fscanf(dna, "%d", &n);
    init_opt(p, n);
    for (i = 0; i < n; i++) {
        fscanf(dna, "%s", wrd);
        #ifdef DEBUG
        printf("Adding %s\n", wrd);
        #endif // DEBUG
        add(wrd, word_size);
        memset(wrd, 0, word_size + 1);
    }

    #ifdef DEBUG
    print_filter();
    #endif // DEBUG
    fclose(dna);
    gettimeofday(&time2, 0);
    test_f = fopen(argv[3], "r");
    while (1) {
        int cl;
        int res = fscanf(test_f, "%s %d", wrd, &cl);
        if (res < 0)
            break;
        filter_res = test(wrd, strlen(wrd));
        if (cl == 1) {
            if (filter_res == 1) {
                pos++;
            }
            num_pos++;
        } else if (cl == 0) {
            if (filter_res == 0) {
                neg++;
            }
            num_neg++;
        } else {
            fprintf(stderr, "Error while reading the file.");
            fclose(test_f);
            free(wrd);
            exit(3);
        }
        memset(wrd, 0, word_size + 1);
    }
    gettimeofday(&time3, 0);
    millis1 = ((time2.tv_sec - time1.tv_sec) * 1000000 + (time2.tv_usec - time1.tv_usec)) / 1000.0;
    millis2 = ((time3.tv_sec - time2.tv_sec) * 1000000 + (time3.tv_usec - time2.tv_usec)) / 1000.0;
    printf("Pos results: %u / %u\n", pos, num_pos);
    printf("Neg results: %u / %u\n", neg, num_neg);
    printf("Time required for reading: %lf ms\n", millis1);
    printf("Time required for querying: %lf ms\n", millis2);
    fclose(test_f);
    free(wrd);
    clear();
    return 0;
}
