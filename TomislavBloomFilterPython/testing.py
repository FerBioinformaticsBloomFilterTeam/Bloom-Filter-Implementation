from simulator import fill_filter_from_file
import argparse

# every line in the form of: <word> <expected_status>,
# where expected_status is 0 or 1
def test_filter_from_file(filepath, filter, print_successes = False, print_failures = True):
    # filter claims it contains the string but it doesn't
    false_positives = 0

    # filter claims it does not contain the string but it does
    false_negatives = 0

    with open(filepath) as f:
        lines = map(lambda x: x.strip("\r\n"), f.readlines())

    # iterate through test lines and conduct each test
    for sequence, expected_status in map(lambda x: (x.split()[0], bool(int(x.split()[1]))), lines):
        presence_status = bool(filter.test(sequence))
        
        if presence_status == expected_status:
            if print_successes:
                print "Testing for sequence " + sequence + " is a match"
        else:
            if expected_status:
                false_negatives += 1
            else:
                false_positives += 1
            if print_failures:
                print "Testing for sequence %s does NOT match - Expected %s but got %s" % (sequence, expected_status, presence_status)

    return (false_positives, false_negatives)

def init_and_test_on_single_file(word_size, init_path, test_path, max_tolerance_perc):
    filter = fill_filter_from_file(init_path, word_size, max_tolerance_perc)

    false_positives, false_negatives = test_filter_from_file(test_path, filter, False, False)

    print "Single file test done. False positives = " + str(false_positives) \
            + ", False negatives = " + str(false_negatives)

def init_and_test_on_multiple_files(word_size, init_path, test_path_list, max_tolerance_perc):
    filter = fill_filter_from_file(init_path, word_size, max_tolerance_perc)
    index = 1

    for test_path in test_path_list:
        false_positives, false_negatives = test_filter_from_file(test_path, filter, False, False)

        print "Batch file test - test number " + str(index) + " done. " \
                + "False positives = " + str(false_positives) \
                + ", False negatives = " + str(false_negatives)

        index += 1

    print "Batch test DONE."

def test1(word_size, init_path, test_path_list, max_tolerance_perc):
    print "Initializing testing for all test files...\n-------------"

    init_and_test_on_single_file(word_size, 
        init_path, 
        test_path_list[0], 
        max_tolerance_perc)

    if len(test_path_list) > 1:
        init_and_test_on_multiple_files(
            word_size, 
            init_path, 
            test_path_list, 
            max_tolerance_perc)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Initialize and test a bloom filter.')
    parser.add_argument('word_size', metavar = 'word_size', type=int,
                       help='Word size')
    parser.add_argument('init_path', metavar='init_path', type=str,
                       help='Path to the initialization file')
    parser.add_argument('test_paths', metavar = 'test_paths', type=str,
                       help='Paths to testing files')
    parser.add_argument('max_tolerance_perc', metavar = 'max_tolerance_perc', type=float,
                       help='Maximum tolerated false positive percentage')

    args = parser.parse_args()
    test_path_list = args.test_paths.split(',')

    # no tests to conduct
    if len(test_path_list) == 0:
        exit()

    #################

    test1(args.word_size, args.init_path, args.test_paths.split(','), args.max_tolerance_perc)

    