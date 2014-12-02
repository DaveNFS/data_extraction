#!/usr/bin/python

class File:
    file_count = 0
    
    def __init__(self, file_name):
        self.file_name = file_name
        self.file_id = File.file_count
        File.file_count += 1
        self.numbered_entities = {}
        
        
    def print_file_metadata(self):
        print self.file_name, self.file_id
        
        