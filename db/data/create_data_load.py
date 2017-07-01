#!/usr/bin/python3
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument('inputfile')
parser.add_argument('-o', '--outputfile')
parser.add_argument('-t', '--type')
args = parser.parse_args()

person_list = []
ou_list = []
pos_list = []
if args.type == 'sql':
    pass
else:
    print("Unknown output file type: {}.".format(args.type))
    sys.exit()

with open(args.inputfile, 'r') as inputfile:
    ingredientset = set(inputfile.readlines())

    with open(args.outputfile, 'w') as outputfile:
        for i in ingredientset:
            insert_statement = "insert into ingredient (id, created_timestamp, updated_timestamp, ingredient) values(nextval('ingredient_id_seq'), current_timestamp, current_timestamp, '{}');\n".format(i.strip())
            outputfile.write(insert_statement)
