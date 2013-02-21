#Author: Maggie Laidlaw 
#Python program to find all Sundays that are the first 
#of the month between 1901 and 2000.
def loopYears():
	notLeap = [3,0,3,2,3,2,3,3,2,3,2,3]
	leap = [3,1,3,2,3,2,3,3,2,3,2,3]
	day = 2 #Su-0,M-1,...,S-6
	year = 1901
	count = 0
	for x in range (1901,2000):
		if x%100 != 0 && x%4 == 0:
			for y in leap:
				day+=y
		elif x%100 == 0 && x%400 == 0:
			for y in leap:
				day+=y
		else
			for y in notLeap:
				day+=y
		if day == 7:
			count++
			day = 0
	print count