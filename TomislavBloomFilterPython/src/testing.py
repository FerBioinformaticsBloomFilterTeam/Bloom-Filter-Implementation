#!/usr/bin/env python

from bloom_filter_factory import fill_filter_from_file
import argparse
import time
import resource

# every line in the form of: <word> <expected_status>,
# where expected_status is 0 or 1
def test_filter_from_file(filepath, filter, print_successes = False, print_failures = True):
    # filter claims it contains the string but it doesn't
    false_positives = 0

    # filter claims it does not contain the string but it does
    # this should never happen, here for debugging purposes
    false_negatives = 0

    with open(filepath) as f:

        # iterate through test lines and conduct each test
        for line in f:
            sequence, expected_status = line.strip("\r\n").split()
            expected_status = bool(int(expected_status))

            presence_status = filter.test(sequence)
            
            # check if result matches what we expected
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

def init_and_test_on_single_file(init_path, test_path, max_tolerance_perc):
    init_start = time.time()
    filter = fill_filter_from_file(init_path, max_tolerance_perc)
    init_end = time.time()

    mem_usage = resource.getrusage(resource.RUSAGE_SELF).ru_maxrss
    print "Memory usage: %s MB" % (mem_usage / 1024)

    test_start = time.time()
    false_positives, false_negatives = test_filter_from_file(test_path, filter, False, False)
    test_end = time.time()

    print "False positives: " + str(false_positives) \
            + "\nFalse negatives: " + str(false_negatives)

    return (init_end - init_start, test_end - test_start)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Initialize and test a bloom filter.')
    parser.add_argument('init_path', metavar='init_path', type=str,
                       help='Path to the initialization file')
    parser.add_argument('test_path', metavar = 'test_path', type=str,
                       help='Path to testing file')
    parser.add_argument('max_tolerance_perc', metavar = 'max_tolerance_perc', type=float,
                       help='Maximum tolerated false positive percentage')

    args = parser.parse_args()

    ##############

    #print "Initializing and testing bloom filter..."
    print "False positive rate: %s percent" % (args.max_tolerance_perc * 100)

    init_time, test_time = init_and_test_on_single_file(args.init_path, args.test_path, args.max_tolerance_perc)

    print "Init duration: %s seconds\nTest duration: %s seconds\nTotal duration: %s seconds." \
            % (init_time, test_time, init_time + test_time)
