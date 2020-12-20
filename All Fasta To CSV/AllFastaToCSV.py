# Importing library
import os

# Getting all the fasta files from the current directory
files = [fas for fas in os.listdir('.') if fas.endswith(".fas")]

#The path where the converted files will be saved; Change it according to the needs
pathName="E:/DNA Barcode Project/New Approach/All Fasta To CSV/N50000/" 
os.makedirs(os.path.dirname(pathName))
# Main loop for reading and writing files
for file in files:
    fileReader=open(file, 'r')
    lines=fileReader.readlines()
    numberOfAttributes=len(lines[1])-1 #To cancel the newline from the count
    name,ext = os.path.splitext(fileReader.name)

    fileWriter=open(pathName+name+".csv", "a")
    for i in range(numberOfAttributes):
        attr="X"+str(i)
        fileWriter.write(attr)
        fileWriter.write(",")
    fileWriter.write("Class")
    fileWriter.write('\n')

    fileReader.close()

    fileReader2=open(file, "r")
    speciesFlag=True
    for line in fileReader2:
        if speciesFlag is True:
            segments=line.split('|')
            speciesName=segments[1]
            speciesFlag=False
        else:
            for i in range(len(line)-1):
                fileWriter.write(line[i])
                fileWriter.write(",")
            fileWriter.write(speciesName)
            fileWriter.write('\n')
            speciesFlag=True

    fileReader2.close()
    fileWriter.close()
