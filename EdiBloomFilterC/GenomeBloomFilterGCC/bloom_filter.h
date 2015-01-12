#include <inttypes.h>

/*Takes the expected false positive rate and the set size and then initializes 
  the bloom filter calculating optimal values for k and m.*/
void init_opt(float p, uint32_t n);

/*Takes the expected false positive rate, the set size and the k parameter and
  then initializes the bloom filter calculating optimal values for m.*/
void init(float p, uint32_t n, uint8_t k);

/*Tests whether the object obj of len bytes belongs to the filter. If it doesn't
  0 is returned, 1 otherwise.*/
int test(void* obj, unsigned int len);

/*Adds the object obj of len bytes to the filter.*/
void add(void* obj, unsigned int len);

/*Frees the allocated memory used by this filter.*/
void clear(void);

/*A function for printing the contents of the filer in a hexadecimal format.*/
void print_filter(void);
