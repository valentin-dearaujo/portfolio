import matplotlib.pyplot as plt
import numpy as np
import sys
import os
import geopandas as gpd
import pymssql
import pandas as pd
from datetime import datetime, timedelta, date
import matplotlib.dates as mdates

from matplotlib.colors import ListedColormap

import warnings

warnings.filterwarnings('ignore')

cnxn = pymssql.connect (server = 'info-mssql-etd', user = 'ETD', password = 'ETD', database = 'EQUIPE4_SAE')

donneeInexistante = False

my_path = os.path.dirname(os.path.abspath(__file__))

def getCodeDepFromDB(nomDep):
    listCodeCommunes = []
    sql_eleves = "select num_departement from T_Departement WHERE lib_departement like '" + nomDep + "'"

    df = pd.read_sql(sql_eleves, cnxn)
    listeCode = df.head(5)

    for i in range(len(listeCode)):
        listCodeCommunes.append(df["num_departement"].get(i))

    if (df.empty):
        print("données inexistantes 47")  
        quit()
    else:
        return df["num_departement"].get(0)

def daterange(start_date: date, end_date: date):
    days = int((end_date - start_date).days)
    for n in range(days):
        yield start_date + timedelta(n)

def getDataForDataTypeAndDeptFromSql(idDataType, numeroDepartement):
    sql = f"SELECT donnée, date_deb FROM T_Donnees WHERE code_commune LIKE '{numeroDepartement}%' AND id_Type LIKE '{idDataType}'"
    df = pd.read_sql(sql, cnxn)
    df['donnée'] = df['donnée'].astype(float)
    df['date_parsed'] = pd.to_datetime(df['date_deb'], format='%m-%Y')
    df_grouped = df.groupby('date_parsed')['donnée'].mean().reset_index()
    df_grouped = df_grouped.sort_values('date_parsed')
    df_grouped['date_deb'] = df_grouped['date_parsed'].dt.strftime('%m-%Y') 
    return df_grouped[['date_deb', 'donnée']]


def getDataIdFromDataNameFromSql(DataTypeName):
    convertedStr = DataTypeName.replace("-", " ")
    if (DataTypeName.startswith("qualité")):
        convertedStr = "qualité d''air"

    sql_eleves = "SELECT id_Type from T_Type WHERE type LIKE '" + convertedStr + "'"

    df = pd.read_sql(sql_eleves, cnxn)

    if (df.empty):
        print("données inexistantes 84")  
        quit()
    else:
        return df.iloc[0]['id_Type']
      



def getDonneesFromDate(dateDeDebut, dateDeFin, dataType, depCode):

    listDonnees = []
    dfFull = getDataForDataTypeAndDeptFromSql(dataType, depCode)

    dateToCompareDebut = datetime.strptime(dateDeDebut, '%m-%Y')
    dateToCompareFin = datetime.strptime(dateDeFin, '%m-%Y')
    
    for index, row in dfFull.iterrows(): #pb ici
        dateToCompareIter = datetime.strptime(row['date_deb'], '%m-%Y')
        
        if(dateToCompareIter >= dateToCompareDebut and dateToCompareIter <= dateToCompareFin): #fjidsghnujri hgnrjihguirhgnuirhgu
            listDonnees.append(row['donnée'])

    if(len(listDonnees) < 1):
        print("données inexistantes 107")  
        quit()
    else:
        return listDonnees
    

def getDatesFromDate(dateDeDebut, dateDeFin, dataType, depCode):
    listDates = []

    dfFull = getDataForDataTypeAndDeptFromSql(dataType, depCode)
    pd.to_datetime(dfFull['date_deb'], format='%m-%Y')

    print(dfFull)

    dfFull['date_deb_parsed'] = pd.to_datetime(dfFull['date_deb'], format='%m-%Y')
    dfFull.sort_values(by="date_deb_parsed", inplace=True)

    print(dfFull.sort_values(by="date_deb"))

    dateToCompareDebut = datetime.strptime(dateDeDebut, '%m-%Y')

    dateToCompareFin = datetime.strptime(dateDeFin, '%m-%Y')

    for index, row in dfFull.iterrows(): #pb ici

        dateToCompareIter = datetime.strptime(row['date_deb'], '%m-%Y')
        if(dateToCompareIter >= dateToCompareDebut and dateToCompareIter <= dateToCompareFin): #fjidsghnujri hgnrjihguirhgnuirhgu

            listDates.append(row['date_deb'])

    if(len(listDates) < 1):

        print("données inexistantes 107")  

        quit()

    else:
        s1 = pd.Series(listDates)

        s2 = s1.apply(pd.to_datetime, format='%m-%Y').sort_values()
        toReturn = s2.dt.strftime('%m-%Y').tolist()

        listDates.sort()
        return toReturn
    


    
def generateMap():
    sql_eleves = "SELECT * FROM T_Contour_Departement"

    df = pd.read_sql(sql_eleves, cnxn)
    gs = gpd.GeoSeries.from_wkt(df['geometry'])
    gdf = gpd.GeoDataFrame(df, geometry=gs)
    return gdf

def fetchRisques(nom_dep):
    cursor = cnxn.cursor()
    cursor.execute(f"""
        SELECT don.donnée AS type_risque, RIGHT(don.date_deb, 4) AS annee
        FROM T_Donnees don
        JOIN T_Commune c ON don.code_commune = c.code_commune
        JOIN T_Departement d ON c.num_departement = d.num_departement
        WHERE don.id_Type = '1' AND d.lib_departement = '{nom_dep}'
        AND don.date_deb IS NOT NULL
    """)
    df_risk = pd.DataFrame(cursor.fetchall(), columns=["type_risque", "annee"])
    cursor.close()

    df_risk["annee"] = pd.to_numeric(df_risk["annee"], errors="coerce")
    df_risk = df_risk.dropna(subset=["annee"]).astype({"annee": int})

    df_pivot = df_risk.groupby(["annee", "type_risque"]).size().unstack(fill_value=0)
    df_pivot = df_pivot.sort_index()

    return df_pivot

COLORS_RISQUES = {
    "Eboulement et/ou Chute de Blocs":                "#C95000",
    "Inondations et/ou Coulées de Boue":              "#00F7FF",
    "Chocs Mécaniques liés à l'action des Vagues":    "#0026FF",
    "Inondations Remontée Nappe":                     "#08FF08",
    "Tempête":                                        "#707070",
    "Mouvement de Terrain":                           "#FF0000",
    "Grêle":                                          "#639922",
    "Glissement de Terrain":                          "#885A30",
    "Sécheresse":                                     "#FBFF00",
    "Secousse Sismique":                              "#533AB7",
    "Avalanche":                                      "#FF00C8",
}

def generateStreamGraphRisques(nom_dep):
    cursor = cnxn.cursor()
    cursor.execute(f"""
        SELECT don.donnée AS type_risque, don.date_deb
        FROM T_Donnees don
        JOIN T_Commune c ON don.code_commune = c.code_commune
        JOIN T_Departement d ON c.num_departement = d.num_departement
        WHERE don.id_Type = '1' AND d.lib_departement = '{nom_dep}'
        AND don.date_deb IS NOT NULL
    """)
    df_risk = pd.DataFrame(cursor.fetchall(), columns=["type_risque", "date_deb"])
    cursor.close()

    df_risk["annee"] = df_risk["date_deb"].astype(str).str[-4:]
    df_risk["annee"] = pd.to_numeric(df_risk["annee"], errors="coerce")
    df_risk = df_risk.dropna(subset=["annee"]).astype({"annee": int})

    df_pivot = df_risk.groupby(["annee", "type_risque"]).size().unstack(fill_value=0)
    df_pivot = df_pivot.sort_index()

    plt.figure(figsize=(12, 6))
    
    bottom = pd.Series(0, index=df_pivot.index)

    for col in df_pivot.columns:
        vals = df_pivot[col]
        plt.fill_between(df_pivot.index, bottom, bottom + vals,
                         color=COLORS_RISQUES[col], alpha=0.85, label=col)
        bottom = bottom + vals

    plt.title(f"Évolution des risques naturels — {nom_dep}")
    plt.xlabel("Année")
    plt.ylabel("Nombre d'événements")
    plt.legend(loc="upper left", fontsize=7, ncol=2)
    plt.grid(alpha=0.2)
    
    annee_min = df_pivot.index.min()
    annee_max = df_pivot.index.max()
    plt.xticks(range(annee_min, annee_max + 1, 2))

    plt.tight_layout()

################################################################################################################
# REGRESSION LINEAIRE
################################################################################################################
# ne fonctionne pas avec type 1 et 3  


def getTransformTypeIntoNumber():
    cursor = cnxn.cursor()
    cursor.execute("""
        SELECT date_deb, donnée
        FROM T_Donnees 
        WHERE id_Type = '12'
        AND date_deb IS NOT NULL
    """)
    df_air_lib = pd.DataFrame(cursor.fetchall(), columns=["date_deb", "donnée"])
    cursor.close()

    mapping = {"Bon": 1, "Moyen": 2, "Dégradé": 3, "Mauvais": 4, "Extrêmement mauvais": 5}
    df_air_lib["donnée"] = df_air_lib["donnée"].astype(str).str.split(";").str[0].map(mapping).fillna(pd.to_numeric(df_air_lib["donnée"], errors='coerce')).fillna(0)
    
    df_air_lib["annee"] = df_air_lib["date_deb"].astype(str).str[-4:].astype(int)
    
    return df_air_lib[["annee", "donnée"]]


def moyenne(x):
    moy = 0.0
    for i in range(len(x)):
        moy += x[i]
    return moy / len(x)

def variance(x):
    var = 0.0
    moy = moyenne(x)
    for i in range(len(x)):
        var += (x[i] - moy) ** 2
    return var / len(x)

def ecartType(x):
    return variance(x) ** 0.5

def covariance(x, y):
    n = len(x)
    moy_x = moyenne(x)
    moy_y = moyenne(y)
    cov = 0.0
    for i in range(n):
        cov += (x[i] - moy_x) * (y[i] - moy_y)
    return cov / n

def regression(x, y):
    a = covariance(x, y) / variance(x)
    b = moyenne(y) - a * moyenne(x)
    r = covariance(x, y) / (ecartType(x) * ecartType(y))
    return a, b, r

def erreurQuadratique(x, y, a, b): #moindre carré
    erreur = 0.0
    for i in range(len(x)):
        erreur += (y[i] - (a * x[i] + b)) ** 2
    return erreur

def regressionParMorceaux(x, y):
    n = len(x)
    best_cut = None
    best_error = float('inf')

    for cut in range(5, n - 5):
        x1, y1 = x[:cut], y[:cut]
        x2, y2 = x[cut:], y[cut:]

        a1, b1, _ = regression(x1, y1)
        a2, b2, _ = regression(x2, y2)

        erreur_totale = erreurQuadratique(x1, y1, a1, b1) + erreurQuadratique(x2, y2, a2, b2)

        if erreur_totale < best_error:
            best_error = erreur_totale
            best_cut = cut

    return best_cut

def tracerSegment(x, a, b):
    y_pred = []
    for xi in x:
        y_pred.append(a * xi + b)
    return y_pred

def traceAnalyseMorceaux(x, y, titre, xlabel, ylabel):
    cut = regressionParMorceaux(x, y)

    x1, y1 = x[:cut], y[:cut]
    x2, y2 = x[cut:], y[cut:]

    a1, b1, r1 = regression(x1, y1)
    a2, b2, r2 = regression(x2, y2)

    plt.figure(figsize=(12, 6))
    plt.scatter(x, y, color="#008F62", alpha=0.7, label="Données")
    plt.plot(x1, tracerSegment(x1, a1, b1), color="#FF4000", linewidth=2,
             label=f"Segment 1 : y = {a1:.5f}x + {b1:.2f}  (R={r1:.3f})")
    plt.plot(x2, tracerSegment(x2, a2, b2), color="#0026FF", linewidth=2,
             label=f"Segment 2 : y = {a2:.5f}x + {b2:.2f}  (R={r2:.3f})")
    plt.title(titre)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend(fontsize=9)
    plt.grid(alpha=0.3)
    plt.tight_layout()

def createRegression(type_donnee_nom):
    id_type = getDataIdFromDataNameFromSql(type_donnee_nom)

    if str(id_type) == '12':
        df_brut = getTransformTypeIntoNumber()
        df_reg = df_brut.groupby("annee")["donnée"].mean().reset_index()
        df_reg.columns = ["annee", "val_moy"]
    else:
        sql = f"""
            SELECT CAST(RIGHT(date_deb, 4) AS INT) AS annee, AVG(TRY_CAST(donnée AS FLOAT)) AS val_moy
            FROM T_Donnees
            WHERE id_Type = '{id_type}'
            AND date_deb IS NOT NULL
            AND TRY_CAST(donnée AS FLOAT) IS NOT NULL
            GROUP BY RIGHT(date_deb, 4)
            ORDER BY annee
        """
        df_reg = pd.read_sql(sql, cnxn).dropna()

    x = df_reg["annee"].tolist()
    y = df_reg["val_moy"].tolist()

    type_label = type_donnee_nom.replace("-", " ")

    traceAnalyseMorceaux(x, y,
                         titre=f"Régression linéaire par morceaux - {type_label} - Nouvelle-Aquitaine",
                         xlabel="Année",
                         ylabel=type_label)

    traceAnalyseMorceaux(x, y,
                         titre=f"Régression linéaire par morceaux - {type_label} - Nouvelle-Aquitaine",
                         xlabel="Année",
                         ylabel=type_label)
    
################################################################################################################
# For graphics methodes
################################################################################################################

def fetch_by_commune(cursor, id_type, col_name, date):
    cursor.execute(f"""
        SELECT c.code_commune, AVG(CAST(don.donnée AS FLOAT)) AS valeur
        FROM T_Donnees don
        JOIN T_Commune c ON don.code_commune = c.code_commune
        WHERE don.id_Type = '{id_type}'
        AND don.date_deb = '{date}'
        GROUP BY c.code_commune
        ORDER BY c.code_commune
    """)
    return pd.DataFrame(cursor.fetchall(), columns=["code_commune", col_name])
  
################################################################################################################
# Parallele graphic
################################################################################################################
def fetchParallelCoord(date):
    cursor = cnxn.cursor()
    df_temp  = fetch_by_commune(cursor, 4, "temp", date)
    df_preci = fetch_by_commune(cursor, 2, "pluie", date)
    df_wind  = fetch_by_commune(cursor, 7, "vent", date)
    cursor.close()

    cursor = cnxn.cursor()
    cursor.execute(f"""
        SELECT c.code_commune, don.donnée
        FROM T_Donnees don
        JOIN T_Commune c ON don.code_commune = c.code_commune
        WHERE don.id_Type = '12'
        AND don.date_deb = '{date}'
    """)
    df_air_lib = pd.DataFrame(cursor.fetchall(), columns=["code_commune", "donnée"])
    cursor.close()

    mapping = {"Bon": 1, "Moyen": 2, "Dégradé": 3, "Mauvais": 4, "Extrêmement mauvais": 5}
    df_air_lib["valeur"] = df_air_lib["donnée"].str.split(";").str[0].map(mapping).fillna(0)
    df_air = df_air_lib.groupby("code_commune")["valeur"].mean().reset_index()
    df_air.columns = ["code_commune", "air"]

    cursor = cnxn.cursor()
    cursor.execute("SELECT code_commune, lib_commune, num_departement FROM T_Commune")
    df_communes = pd.DataFrame(cursor.fetchall(), columns=["code_commune", "lib_commune", "num_departement"])
    cursor.close()

    df = (df_communes
          .merge(df_temp,  on="code_commune", how="left")
          .merge(df_preci, on="code_commune", how="left")
          .merge(df_wind,  on="code_commune", how="left")
          .merge(df_air,   on="code_commune", how="left"))

    return df

def generateParallelCoord(date):
    COLORS = {
        "16": "#378ADD", "17": "#0400FF", "19": "#FF00FF", "23": "#FF9500",
        "24": "#7F77DD", "33": "#008F62", "40": "#FF4000", "47": "#00FF62",
        "64": "#D4537E", "79": "#FF0000", "86": "#00E1FF", "87": "#000000FF"
    }

    cols = ["temp", "pluie", "vent", "air"]
    labels = ["TempMax. (°C) Moyenne", "Précip. (mm) Moyenne", "Vent (km/h) Moyen", "Qualité air Moyenne"]

    df = fetchParallelCoord(date)

    df_norm = df.copy()
    df_norm[cols] = (df[cols] - df[cols].min()) / (df[cols].max() - df[cols].min())

    plt.figure(figsize=(12, 6))

    for _, row in df_norm.iterrows():
        vals = row[cols]
        indices = [i for i, v in enumerate(vals) if pd.notna(v)]
        values = [v for v in vals if pd.notna(v)]
        if len(indices) >= 2:
            plt.plot(indices, values, color=COLORS[str(row["num_departement"])], alpha=0.4, linewidth=1.0)

    for i, col in enumerate(cols):
        plt.axvline(i, color="black", linewidth=0.5, alpha=0.3)

        min_val = df[col].min()
        max_val = df[col].max()

        plt.text(i, -0.03, f"{min_val:.1f}", ha='center', va='top', fontsize=9)
        plt.text(i, 1.03, f"{max_val:.1f}", ha='center', va='bottom', fontsize=9)
     

    for dep, color in COLORS.items():
        plt.plot([], [], color=color, linewidth=2, label=f"Dép. {dep}")

    plt.xticks(range(len(cols)), labels)
    plt.legend(loc="upper right", fontsize=8, ncol=2)
    plt.title(f"Données météo par commune — Nouvelle-Aquitaine ({date})")

    plt.yticks([]) #cache les trucs verticales a gauche
    plt.box(False) #desac la box de plot
    plt.tight_layout() #evite les erreurs dans le cadrage

################################################################################################################
# HISTOGRAMMMME
################################################################################################################
def getDfForEventCount(nameDep):
    cursor = cnxn.cursor()
    cursor.execute(f"""
        SELECT donnée, count(d.donnée)as 'nbdonnée' FROM T_Departement dep
        join T_Commune c ON c.num_departement = dep.num_departement
        join T_Donnees d ON d.code_commune = c.code_commune
        join T_Type t On t.id_Type = d.id_Type
        WHERE t.id_Type = 1 and lib_departement = '{nameDep}'
        GROUP By donnée
    """)
    df_event_count = pd.DataFrame(cursor.fetchall(), columns=["lib_event", "nb_event"])
    cursor.close()
    return(df_event_count)

################################################################################################################
# Diagramme en barre pour la qualité d'air
################################################################################################################

def createHistoEventForDep(dep_name):

    import matplotlib.pyplot as plt
    import textwrap

    df_event = getDfForEventCount('Gironde')

    labels_sautes = [textwrap.fill(str(label), width=15) for label in df_event["lib_event"]]

    plt.figure(figsize=(8, 5))
    plt.bar(labels_sautes, df_event["nb_event"], color='skyblue', edgecolor='black', linewidth=0.5)

    plt.xticks(rotation=60)
    plt.ylabel("Nombre de catastrophes")
    plt.title("Noms des catastrophes - Gironde")

    plt.tight_layout()

def  makeDataframeForMap(dataType, dateUnique):
    dataframeToFill = generateMap()

    dfNew = pd.DataFrame()
    listValues = []

    for index, row in dataframeToFill.iterrows():
        sql_requeteAllDep = "SELECT code_commune, id_Type, donnée from T_Donnees WHERE id_Type LIKE '" + str(dataType) + "' AND date_deb LIKE '" + str(dateUnique) + "' AND code_commune LIKE '" + row['num_departement'] + "%'"
        df = pd.read_sql(sql_requeteAllDep, cnxn)

        df['donnée'] = df['donnée'].astype(float)
        df = df.loc[df.groupby('id_Type')['donnée'].idxmax()]
        listValues.append(df)

    dataframeFinal = pd.concat(listValues, ignore_index=True)

    dataframeFinal['num_dep_temp'] = dataframeFinal['code_commune'].astype(str).str[:2]

    dataframeToFill['num_departement'] = dataframeToFill['num_departement'].astype(str)
        
    ResultatCartographie = pd.merge(
            dataframeToFill, 
            dataframeFinal[['num_dep_temp', 'code_commune', 'donnée']], 
            left_on='num_departement', 
            right_on='num_dep_temp', 
            how='left'
        )
    
    ResultatCartographie = ResultatCartographie.drop(columns=['num_dep_temp'])

    return ResultatCartographie

def generateGraph(gridTrueOrFalse, dateDebut, dateFin, depNameUn, depNameDeux, dataTypeNameUn, dataTypeNameDeux, filename):

    stringLocOne = "emptyList"
    stringLocTwo = "emptyList"
    stringAnneeNumbOne = "emptyList"
    stringAnneeNumbTwo = "emptyList"

    if(dateDebut == "None" and dateFin == "None" and depNameDeux == "None" and dataTypeNameUn == "None" and dataTypeNameDeux == "None"):
        generateStreamGraphRisques(depNameUn)
        plt.savefig(f'{filename}.png', transparent=True)
        return
    if( depNameUn == "None"  and dateFin == "None" and depNameDeux == "None" and dataTypeNameUn == "None" and dataTypeNameDeux == "None"):
        generateParallelCoord(dateDebut)
        plt.savefig(f'{filename}.png', transparent=True)


        return
    if (depNameUn == "None"  and dateFin == "None" and depNameDeux == "None" and dateDebut =="None" and dataTypeNameDeux == "None"):
        createRegression(dataTypeNameUn)
        plt.savefig(f'{filename}.png', transparent=True)
        return

    formatedNameUn = dataTypeNameUn.replace("-", " ") + " " + depNameUn
    formatedNameDeux = dataTypeNameDeux.replace("-", " ") + " " + depNameDeux

    formatedNameMap = dataTypeNameUn.replace("-", " ") + " " + "Nouvelle Aquitaine"

    idDepUn = getCodeDepFromDB(depNameUn)
    

    idDataTypeUn = getDataIdFromDataNameFromSql(dataTypeNameUn)

    fullData = getDonneesFromDate(dateDebut, dateFin, idDataTypeUn, idDepUn) #données pour faire le graphique
    

    fullDates = getDatesFromDate(dateDebut, dateFin, idDataTypeUn, idDepUn)

    fullDatesDeux = getDonneesFromDate(dateDebut, dateFin, idDataTypeUn, idDepUn)
    

    if(depNameDeux != "None" and dataTypeNameDeux != "None"):
        idDepDeux = getCodeDepFromDB(depNameDeux)
        idDataTypeDeux = getDataIdFromDataNameFromSql(dataTypeNameDeux)
        fullDataDeux = getDonneesFromDate(dateDebut, dateFin, idDataTypeDeux, idDepDeux) #données pour faire le graphique
        try:
            plt.plot(fullDates, fullDataDeux, marker='o', linestyle='-', color='red', label=formatedNameDeux)
            plt.legend()
        except:
            print("données inexistantes")
    
    cmap = ListedColormap([
        '#08306b', '#08519c', '#2171b5', '#4292c6',
        '#6baed6', '#9ecae1', '#c6dbef', '#deebf7',
        '#fee0d2', '#fcbba1', '#fc9272', '#fb6a4a',
        '#ef3b2c', '#cb181d', '#a50f15', '#67000d',
    ])

    if(dateDebut == dateFin):
        map = makeDataframeForMap(idDataTypeUn, dateDebut)
        mapMin = makeDataframeForMap("4", dateDebut)

        max = map['donnée'].max()
        min = mapMin['donnée'].min()

        map.plot(column="donnée", vmin=min, vmax=max, cmap=cmap, legend=True, legend_kwds={"label": formatedNameMap, "orientation": "horizontal"})

    else:
        try:
            plt.plot(fullDates, fullData, marker='o', linestyle='-', color='blue', label=formatedNameUn)

            plt.legend()
        except:

            print(len(fullDates))
            print("---------------")
            print(len(fullData))
            print("données inexistantes 218")
    
        if(gridTrueOrFalse == 'True'):
            plt.grid()
    if (dateDebut == "None" and dateFin == "None" and depNameDeux == "None" and dataTypeNameUn == "None" and dataTypeNameDeux == "None"):
        df_pivot = fetchRisques(depNameUn)
    
    ax = plt.gca()
    ax.xaxis.set_major_locator(plt.MaxNLocator(nbins=10))
    plt.setp(ax.get_xticklabels(), rotation=45, ha='right', fontsize=8)
    plt.tight_layout()
    plt.savefig(f'{filename}.png', transparent=True)

if __name__ == "__main__":
    
    gridBool = str(sys.argv[1])

    dateDebutOne = str(sys.argv[2])
    dateFinOne = str(sys.argv[3])

    regionNameOne = str(sys.argv[4])
    regionNameTwo = str(sys.argv[5])
    dataTypeUn = str(sys.argv[6])
    dataTypeDeux = str(sys.argv[7])

    fileName = str(sys.argv[8])

    generateGraph(gridBool, dateDebutOne, dateFinOne, regionNameOne, regionNameTwo, dataTypeUn, dataTypeDeux, fileName)


#//iut.bx/Etudiants/Home/jdurot/Documents/PythonLinkCSharp/venv/Scripts/activate

#cd avant

#python //iut.bx/Etudiants/Home/jdurot/Documents/PythonLinkCSharp/generateImage.py