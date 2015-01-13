#!/bin/bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $DIR
cd src
echo "About to conduct test on all files from test_cases folder..."
echo ""

python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.05
echo "--------------------"
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.05
echo "--------------------"
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.05
echo "--------------------"
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.05
echo "--------------------"
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.05
echo "--------------------"

python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.1
echo "--------------------"
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.1
echo "--------------------"
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.1
echo "--------------------"
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.1
echo "--------------------"
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.1 
echo "--------------------"

python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.15
echo "--------------------"
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.15
echo "--------------------"
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.15
echo "--------------------"
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.15
echo "--------------------"
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.15
echo "--------------------"

python testing.py ../../test_cases/words_added_100.txt ../../test_cases/words_test_100.txt 0.2
echo "--------------------"
python testing.py ../../test_cases/words_added_1000.txt ../../test_cases/words_test_1000.txt 0.2
echo "--------------------"
python testing.py ../../test_cases/words_added_10_000.txt ../../test_cases/words_test_10_000.txt 0.2
echo "--------------------"
python testing.py ../../test_cases/words_added_100_000.txt ../../test_cases/words_test_100_000.txt 0.2
echo "--------------------"
python testing.py ../../test_cases/words_added_1000_000.txt ../../test_cases/words_test_1000_000.txt 0.2 
