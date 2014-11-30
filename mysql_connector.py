#!/usr/bin/python

import mysql.connector

cnx = mysql.connector.connect(host="localhost",user="root",password="root",database="dave" )

cursor = cnx.cursor()

query = ("SELECT * FROM word_count;")

cursor.execute(query)

results = cursor.fetchall()
for x in results:
    id = x[0]
    word = x[1]
    count = x[2]
    print id, word, count




cursor.close()
cnx.close()