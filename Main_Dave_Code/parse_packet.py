#!/usr/bin/python

import xml.etree.cElementTree as ET

packet_file = 'one.xml'

tree = ET.ElementTree(file=packet_file)
root = tree.getroot()


#for x in tree.iterfind('staff/salary'):
#    print x.tag, x.attrib, x.text

for elem in tree.iter():
    print elem.tag, elem.attrib, elem.text