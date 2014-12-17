
# code use to collect all file path and filename from directory

import os
testout = open("outfile.txt", 'w')
file=[]
def fun(test, out):
    for root, dirs, files in os.walk(test):
        for fm in files:
            #out.append(str(root)+'/'+str(fm))
            out.write(str(root)+'\\'+str(fm))
            out.write("\n")

fun("D:\\study\\structurePrediction_data\\news", testout)

