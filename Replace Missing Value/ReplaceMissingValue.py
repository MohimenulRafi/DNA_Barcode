import numpy as np

#Name of the file where the missing values will be replaced; If the file is in a different folder, write the full path
dataFileName="corvus_mafft_aligned.csv"
fileReader=open(dataFileName, 'r')

sp={}

lines=fileReader.readlines()
na=len(lines[0].split(','))-1

for i in range(1,len(lines)):
    attributes=lines[i].split(',')
    speciesName=attributes[-1].replace('\n','')
    numberOfAttributes=len(attributes)-1
    
    if speciesName in sp:
        mat=sp[speciesName]
        for j in range(0,numberOfAttributes):
            if attributes[j]=='A':
                mat[0][j]+=1.0
            elif attributes[j]=='T':
                mat[1][j]+=1.0
            elif attributes[j]=='G':
                mat[2][j]+=1.0
            elif attributes[j]=='C':
                mat[3][j]+=1.0
            elif attributes[j]=='N':
                mat[4][j]+=1.0
            else:
                mat[4][j]+=1.0
        sp[speciesName]=mat
    else:
        mat=np.ndarray(shape=(6,numberOfAttributes), dtype=np.longdouble)
        for r in range(0,6):
            for c in range(numberOfAttributes):
                mat[r][c]=0.0

        for j in range(0,numberOfAttributes):
            if attributes[j]=='A':
                mat[0][j]=1.0
            elif attributes[j]=='T':
                mat[1][j]=1.0
            elif attributes[j]=='G':
                mat[2][j]=1.0
            elif attributes[j]=='C':
                mat[3][j]=1.0
            elif attributes[j]=='N':
                mat[4][j]=1.0
            else:
                mat[4][j]=1.0
        sp[speciesName]=mat

for key, value in sp.items():
    for i in range(0,na):
        numberOfBases=[]
        baseNumber=0
        numberOfBases.append(value[0][i])
        numberOfBases.append(value[1][i])
        numberOfBases.append(value[2][i])
        numberOfBases.append(value[3][i])
        
        baseNumber=numberOfBases.index(max(numberOfBases))
        value[5][i]=baseNumber
    sp[key]=value

fileReader.close()

#dataFileName2="corvus_mafft_aligned.csv"
fileReader2=open(dataFileName, 'r')
lines=fileReader2.readlines()

file=open("FormatedCorvus_Replaced.csv", "a")
file.write(lines[0])

for i in range(1,len(lines)):
    attributes=lines[i].split(',')
    speciesName=attributes[-1].replace('\n','')
    numberOfAttributes=len(attributes)-1
    mat=sp[speciesName]

    for j in range(0,numberOfAttributes):
        if(attributes[j]=='-' or attributes[j]=='N'):
            if(mat[5][j]==0):
                file.write('A')
                file.write(",")
            elif(mat[5][j]==1):
                file.write('T')
                file.write(",")
            elif(mat[5][j]==2):
                file.write('G')
                file.write(",")
            elif(mat[5][j]==3):
                file.write('C')
                file.write(",")
        else:
            file.write(attributes[j])
            file.write(",")
    file.write(speciesName)
    file.write('\n')
    
fileReader2.close()
file.close()
