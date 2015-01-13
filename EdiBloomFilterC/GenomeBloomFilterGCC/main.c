#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash_funs.h"
#include "bloom_filter.h"
#include <time.h>
#include <unistd.h>

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
    FILE *add_file;
    FILE *test_file;
    uint32_t i = 0;
    uint32_t n = 10;
    uint32_t str_len = 0;
    char *wrd; //buffer for reading a word from the file
    char** addedwords; //arrays of words read
    uint32_t num_pos = 0, num_neg = 0, neg = 0, pos = 0;
    int filter_res;
    double p = 0.2;

    if (argc != 3) {
        fprintf(stderr,
            "2 arguments expected: Path to the file with words\
 to add, and path to the file with words to test the filter with.");
        exit(1);
    }

    //adding words to the filter
    add_file = fopen(argv[1], "r");

    if (add_file == NULL) {
        fprintf(stderr, "No input file exists.");
        exit(2);
    }

    wrd = calloc(20, sizeof(char));
    addedwords = calloc(10, sizeof(char*));
    init_opt(p, n);

    printf("\n");
    for (i = 0; i < n; i++) {
        fscanf(add_file, "%s", wrd);
        str_len = strlen(wrd);
        printf("Adding word \"%s\".\n", wrd);
        add(wrd, str_len);
        if (i < 3) {
            printf("\nResulting filter (hex):\n");
            print_filter();
            printf("\n");
            sleep(2);
        }
        addedwords[i] = calloc(str_len + 1, sizeof(char));
        strcpy(addedwords[i], wrd);
        memset(wrd, 0, 20);
        sleep(2);
    }

    printf("\nResulting filter (hex):\n\n");
    print_filter();
    fclose(add_file);

    printf("\nTesting some words that passed through the filter:\n\n");

    for (i = 0; i < 10; i+=3) {
        char* wrd = addedwords[i];
        if (test(wrd, strlen(wrd))) {
            printf("The word \"%s\" has probably \
passed through the filter.\n", wrd);
        } else {
            printf("The word \"%s\" hasn't \
passed through the filter.\n", wrd);
        }
        sleep(2);
    }

    printf("\nNow for some words that didn't pass through the filter:\n\n");
    test_file = fopen(argv[2], "r");
    if (test_file == NULL) {
        fprintf(stderr, "No test file exists.");
        //cleanup
        free(wrd);
        for (i = 0; i < 10; i++) {
            free(addedwords[i]);
        }
        free(addedwords);
        clear();
        exit(2);
    }

    for (i = 0; i < 10; i++) {
        sleep(2);
        fscanf(test_file, "%s", wrd);
        str_len = strlen(wrd);
        if (test(wrd, strlen(wrd))) {
            printf("The word \"%s\" has probably \
passed through the filter.\n", wrd);
        } else {
            printf("The word \"%s\" hasn't \
passed through the filter.\n", wrd);
        }
        memset(wrd, 0, 20);
    }
    fclose(test_file);

    //cleanup
    free(wrd);
    for (i = 0; i < 10; i++) {
        free(addedwords[i]);
    }
    free(addedwords);
    clear();
    return 0;
}
