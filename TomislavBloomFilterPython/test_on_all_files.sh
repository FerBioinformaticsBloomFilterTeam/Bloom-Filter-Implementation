#!/bin/bash
cd src
echo "About to conduct test on all files from test_cases folder..."

echo "Testing for tolerated false positive percentage = 0.1"
python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.1
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.1
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.1
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.1
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.1 

echo "Testing for tolerated false positive percentage = 0.2"
python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.2
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.2
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.2
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.2
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.2 

echo "Testing for tolerated false positive percentage = 0.4"
python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.4
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.4
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.4
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.4
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.4

echo "Testing for tolerated false positive percentage = 0.5"
python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.5
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.5
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.5
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.5
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.5 