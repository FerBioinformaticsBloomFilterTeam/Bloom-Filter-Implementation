#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash_funs.h"
#include "bloom_filter.h"

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
    uint32_t i = 0, j = 0;
    uint32_t n;
    char *wrd;
    uint32_t num_pos = 0, num_neg = 0, neg = 0, pos = 0;
    int filter_res;

    if (argc != 4) {
        fprintf(stderr,
            "3 arguments expected: Path to the file with the genome, word size and path to the file with test cases");
        exit(1);
    }

    dna = fopen(argv[1], "r");
    word_size = atoi(argv[2]);
    file_size = get_file_size(dna);
    n = file_size / word_size + (file_size % word_size != 0 ? 1 : 0);
    wrd = calloc(word_size + 1, sizeof(char));

    if (word_size <= 0 || file_size <= 0) {
        fprintf(stderr, "Negative file size or negative word length.");
        fclose(dna);
        exit(2);
    }

    init_opt(0.01, n);
    for (i = 0; i < n; i++) {
        char c;
        j = 0;
        while (j < word_size && (c = getc(dna)) != EOF) {
            wrd[j] = c;
            j++;
        }
        #ifdef DEBUG
        printf("Adding %s\n", wrd);
        #endif // DEBUG
        add(wrd, j);
        memset(wrd, 0, word_size + 1);
    }

    #ifdef DEBUG
    print_filter();
    #endif // DEBUG
    fclose(dna);
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

    printf("Pos results: %u / %u\n", pos, num_pos);
    printf("Neg results: %u / %u\n", neg, num_neg);
    fclose(test_f);
    free(wrd);
    clear();
    return 0;
}
