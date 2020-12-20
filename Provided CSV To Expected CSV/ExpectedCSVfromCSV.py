#Name of the file which needs to be converted. If the file is in a different folder, write the full path
dataFileName="corvus_mafft_aligned.csv"
fileReader=open(dataFileName, 'r')

lines=fileReader.readlines()
#numberOfAttributes=len(lines[1])-1 #To cancel the newline from the count
sequence=lines[1].split(',')[5]
numberOfAttributes=len(sequence)-3 #To cancel the newline and two quotations from the count

#Name of the converted file is provided
file=open("FormatedCorvus.csv", "a")
for i in range(numberOfAttributes):
    attr="X"+str(i)
    file.write(attr)
    file.write(",")
file.write("Class")
file.write('\n')

fileReader.close()

fileReader2=open(dataFileName, "r")
firstLineFlag=True
for line in fileReader2:
    if firstLineFlag is True:
        firstLineFlag=False
        continue
    else:
        speciesNameOld=line.split(',')[2]
        speciesName=speciesNameOld.replace(' ','_')
        barcode=line.split(',')[5]
        for i in range(len(barcode)-1):
            if(barcode[i]=='\"'):
                continue
            '''b=line[i]
            if b=='A':
                file.write('1')
            elif b=='T':
                file.write('2')
            elif b=='G':
                file.write('3')
            elif b=='C':
                file.write('4')
            elif b=='N':
                file.write('5')
            else:
                file.write('5')'''
            file.write(barcode[i])
            file.write(",")
        file.write(speciesName)
        file.write('\n')
    
fileReader2.close()
file.close()
