
# this parser is used to parse google files crawled from the internet

import nltk
import re
import io
import os

#------------------------- function defination -----------------------------
# store all article file path into arrays
def pathCatcher(test, out, names):
    for root, dirs, files in os.walk(test):
        for fm in files:
            out.append(str(root)+'\\'+str(fm))
            names.append(str(fm))

# this function remove all non-ascii characters from the string
def remove_non_ascii(text):
    return ''.join(i for i in text if ord(i)<128)


#-------------------------------- main code ------------------------------
filePaths = []
fileNames = []
# define your article files' directory, define your input file directory here
directory = "D:\study\structurePrediction_data\google_finance"
# define your output files' directory, define your output file directory here
outDirectory = "D:\study\structurePrediction_data\\tagged_file"

pathCatcher(directory, filePaths, fileNames)

for i in range(len(filePaths)):
    oneNews = open(filePaths[i], 'r')
    flag = False

    #get article setences
    rawfile = oneNews.readlines()
    filesetence = []
    for i in range(0, len(rawfile)):
        if len(rawfile[i]) > 3:
            flag = True
            filesetence.append(remove_non_ascii(rawfile[i]))

    if flag == False:
        continue

    #define output file path and open it
    outName = fileNames[i].replace('article', 'tagged')
    outFilePath = outDirectory + "\\" + outName
    tagOutput = open(outFilePath, 'w')

    #append word into array
    wordinFile = []
    for i in range(0, len(filesetence)):
        m = re.findall(r'\b\w+\b', filesetence[i])
        wordinFile.append(m)

    tags = []
    for i in range(len(wordinFile)):
        tags = nltk.pos_tag(wordinFile[i])
        for j in range(len(tags)):
            tagOutput.write(str(tags[j]))
        tagOutput.write("\n")
    tagOutput.close()


