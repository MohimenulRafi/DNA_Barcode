# Importing library
import os

# Getting all the fasta files from the current directory
files = [fas for fas in os.listdir('.') if fas.endswith(".fasta")]

#The path where the converted files will be saved; Change it according to the needs
pathName="E:/DNA Barcode Project/New Approach/Fasta Sequence To Single Line (All Files)/N50000/"
os.makedirs(os.path.dirname(pathName))
for file in files:
    fileReader=open(file, 'r')
    lines=fileReader.readlines()
    name,ext = os.path.splitext(fileReader.name)


    fileWriter=open(pathName+name+"_SingleLined"+".fas", "a")

    sequence=""
    for line in lines:
        if line[0]=='>':
            if sequence!="":
                fileWriter.write(sequence)
                fileWriter.write('\n')
                sequence=""
            fileWriter.write(line)
        else:
            line=line.strip('\n')
            sequence=sequence+line
    fileWriter.write(sequence)

    fileReader.close()
    fileWriter.close()
