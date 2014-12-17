

import nltk
import io
import sys
import re

testfile = open("D:\\study\\structurePrediction_data\\news\\aa _331682.txt", "r")
rawfile = testfile.readlines()
filesetence = []

for i in range(len(rawfile)):
    if(len(rawfile[i]) > 2):
        filesetence.append(rawfile[i])


expression = "[^.!?\s][^.!?]*(?:[.!?](?!['\"]?\s|$)[^.!?]*)*[.!?]?['\"]?(?=\s|$)"
m = []
wordinfile = []
longString = ""
for k in range(0, len(filesetence)):
    longString += filesetence[k]
    #wordinfile.append(m)

#longString.replace("\n", " ")
#longString.replace("\s", " ")
