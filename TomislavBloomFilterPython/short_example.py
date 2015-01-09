from bloom_filter import *
	
print "Creating bloom filter with the following parameters:"
print "		Bit vector length = 50"
print "		Number of different hashes = 4"

filter = bloom_filter(50, 4)

print "Filter successfully created\n============="

print "Entering sequence: 'Tomislav'"
filter.add("Tomislav")
print "Entering sequence: 'Marko'"
filter.add("Marko")
print "Entering sequence: 'Stipe'"
filter.add("Stipe")
print "Entering sequence: 'Marija'"
filter.add("Marija")
print "Entering sequence: 'Karlo'"
filter.add("Karlo")
print "Entering sequence: 'Petra'"
filter.add("Petra")
print "Entering sequence: 'Ivana'"
filter.add("Ivana")
print "Entering sequence: 'Filip'"
filter.add("Filip")
print "Entering sequence: 'Thor'"
filter.add("Thor")
print "Entering sequence: 'Grom'"
filter.add("Grom")
print "Entering sequence: 'Slartibartfast'"
filter.add("Slartibartfast")
print "Entering sequence: 'Kramp'"
filter.add("Kramp")
print "Entering sequence: 'Livada'"
filter.add("Livada")

print "Finished adding elements\n-----------"

print "Testing for sequence: 'Tomislav'"
elemPresent = filter.test("Tomislav")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Hrvoje'"
elemPresent = filter.test("Hrvoje")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Ivana'"
elemPresent = filter.test("Ivana")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'GitakTV'"
elemPresent = filter.test("GitakTV")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Grom'"
elemPresent = filter.test("Grom")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Livada'"
elemPresent = filter.test("Livada")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Petra'"
elemPresent = filter.test("Petra")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Matej'"
elemPresent = filter.test("Matej")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Simun'"
elemPresent = filter.test("Simun")
print "Element present => " + str(elemPresent)
print ""

print "Testing for sequence: 'Mate'"
elemPresent = filter.test("Mate")
print "Element present => " + str(elemPresent)