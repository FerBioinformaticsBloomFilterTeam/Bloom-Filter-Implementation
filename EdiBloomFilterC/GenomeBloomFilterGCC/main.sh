gcc -O3 -w -o main main.c hash_funs.c bloom_filter.c -lm

./main filter_words.txt not_filter_words.txt 