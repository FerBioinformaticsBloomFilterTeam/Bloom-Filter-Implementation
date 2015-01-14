gcc -O3 -w -o test test_main.c hash_funs.c bloom_filter.c -lm 

for p in 0.05 0.10 0.15 0.20
do
	echo "Testing 100 with $p"
	/usr/bin/time -f "Memory usage: %M kilobytes" ./test ../words_added_100.txt 20 ../words_test_100.txt $p
	echo "###################################"
	echo
	
	echo "Testing 1_000 with $p"
	/usr/bin/time -f "Memory usage: %M kilobytes" ./test ../words_added_1000.txt 20 ../words_test_1000.txt $p
	echo "###################################"
	echo
	
	echo "Testing 10_000 with $p"
	/usr/bin/time -f "Memory usage: %M kilobytes" ./test ../words_added_10_000.txt 20 ../words_test_10_000.txt $p
	echo "###################################"
	echo
	
	echo "Testing 100_000 with $p"
	/usr/bin/time -f "Memory usage: %M kilobytes" ./test ../words_added_100_000.txt 20 ../words_test_100_000.txt $p
	echo "###################################"
	echo

	echo "Testing 1000_000 with $p"
	/usr/bin/time -f "Memory usage: %M kilobytes" ./test ../words_added_1000_000.txt 20 ../words_test_1000_000.txt $p
	echo "###################################"
	echo
done