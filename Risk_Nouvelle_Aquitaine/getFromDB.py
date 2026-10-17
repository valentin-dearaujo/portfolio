import matplotlib.pyplot as plt
import numpy as np
import sys
import os
import geopandas as gpd
import pymssql

import pandas as pd 
import ast

# using the login ETD/??? (with SQLServer authentification)n

#psw = input("ETD password: ")


def getCommunesFromDB():
    listCommunes = []
    cnxn = pymssql.connect (server = 'info-mssql-etd', user = 'ETD', password = 'ETD', database = 'EQUIPE4_SAE')
    sql_eleves = "select * from T_Commune"

    df = pd.read_sql(sql_eleves, cnxn)

    listeToConvert = df

    for i in range(len(listeToConvert)):
        listCommunes.append(df["lib_commune"].get(i))
    print(listCommunes)
    return listCommunes
    
def getCodeCommunesFromDB(nomCommune):
    listCodeCommunes = []
    cnxn = pymssql.connect (server = 'info-mssql-etd', user = 'ETD', password = 'ETD', database = 'EQUIPE4_SAE')
    sql_eleves = "select * from T_Commune WHERE lib_commune like '" + nomCommune + "'"

    df = pd.read_sql(sql_eleves, cnxn)
    listeCode = df.head(5)
    print(listeCode)
    print("------------------------------------------")

    for i in range(len(listeCode)):
        listCodeCommunes.append(df["code_commune"].get(i))
    print(listCodeCommunes)
    return listCodeCommunes


def getDepartementsFromDB():
    listDep = []
    cnxn = pymssql.connect (server = 'info-mssql-etd', user = 'ETD', password = 'ETD', database = 'EQUIPE4_SAE')
    sql_eleves = "select * from T_Departement"

    df = pd.read_sql(sql_eleves, cnxn)

    listeToConvert = df

    for i in range(len(listeToConvert)):
        listDep.append(df["lib_departement"].get(i))
    
    stringFinal = ""
    for x in range(len(listDep)):
        stringFinal =  stringFinal + listDep[x] + ";"
    
    stringFinal = stringFinal[:-1]
    stringFinal = stringFinal + "#"

    #print(stringFinal)
    return stringFinal

def getDataTypeFromDB():
    listData = []
    cnxn = pymssql.connect (server = 'info-mssql-etd', user = 'ETD', password = 'ETD', database = 'EQUIPE4_SAE')
    sql_eleves = "select * from T_Type"

    df = pd.read_sql(sql_eleves, cnxn)

    listeToConvert = df

    for i in range(len(listeToConvert)):
        listData.append(df["type"].get(i))
    
    stringFinal = ""
    for x in range(len(listData)):
        stringFinal =  stringFinal + listData[x] + ";"
    
    stringFinal = stringFinal[:-1]

    #print(stringFinal)
    return stringFinal

if __name__ == "__main__":
    #getCommunesFromDB()
    print(getDepartementsFromDB() + getDataTypeFromDB())
    #getDataTypeFromDB()



#//iut.bx/Etudiants/Home/jdurot/Documents/PythonLinkCSharp/venv/Scripts/activate

#cd avant

#python //iut.bx/Etudiants/Home/jdurot/Documents/PythonLinkCSharp/generateImage.py