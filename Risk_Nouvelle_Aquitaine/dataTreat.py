#--------------------------------------------------------------------------------------------------------------------------------------#
                                                          #SORTING OUT DATAS
#--------------------------------------------------------------------------------------------------------------------------------------#

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                          #FIRST IMPORT OF THE DATAS
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Import the datas of weather datas and treat them to get a town dataframe, the temp, the wind and rain datas
import pandas as pd
import os

#Treatment for the weather datas

na_department_codes = ("33", "16", "17", "19", "23", "24", "40", "47","64","79", "86", "87")


col = [
    "NUM_POSTE", "NOM_USUEL", "LAT", "LON", "ALTI", "AAAAMMJJ",
    "RR", "DRR", "TN", "TM", "TX", "HTN", "HTX", "TNTXM",
    "TAMPLI", "DG","FFM",  "FXY", "DXY", "HXY",
    "FXI", "DXI", "HXI", "FXI2", "DXI2", "HXI2",
    "FXI3S", "DXI3S", "HXI3S"
]

# department : numberOfDepartment_nameOfDepartment (essentially the name of the directory where datas are found)
department = {
    "16": "16_Charente",
    "17": "17_Charente-Maritime",
    "19": "19_Correze",
    "23": "23_Creuse",
    "24": "24_Dordogne",
    "33": "33_Gironde",
    "40": "40_Landes",
    "47": "47_Lot-et-Garonne",
    "64": "64_Pyrenees-Atlantiques",
    "79": "79_Deux-Sevres",
    "86": "86_Vienne",
    "87": "87_Haute-Vienne",
}

# Timeframes : key is suffix of the file => value is timeframe
timeframes = {
    "avant-1949_RR-T-Vent":      "avant_1949",
    "previous-1950-2024_RR-T-Vent": "5024",
    "latest-2025-2026_RR-T-Vent":   "2526",
}

base_path = "Jeu_de_donnee_sae/Jeu_de_donnee_sae/Donnees_meteo"

# result : data[num_dept][timeframe] = DataFrame
weather_data = {}
towns = {}

# The goal here is to sort every data per file per departments into dataframes
# and also to build the dataframe with every unique towns in the csv
for num, folder in department.items():
    weather_data[num] = {}
    for suffix, key in timeframes.items():
        filename = f"Q_{num}_{suffix}.csv"
        path = os.path.join(base_path, folder, filename)
        df = pd.read_csv(path, sep=";")
        cols_available = [c for c in col if c in df.columns]
        weather_data[num][key] = df[cols_available]

    ref = weather_data[num]["5024"]
    towns[num] = (
        ref[["NUM_POSTE","NOM_USUEL", "LAT", "LON"]]
        .drop_duplicates(subset="NOM_USUEL")
        .dropna(subset="NOM_USUEL")
    )
    towns[num]["NUM_POSTE"] = towns[num]["NUM_POSTE"].astype(str).str[:-3]
    towns[num] = towns[num].reset_index()
    towns[num] = towns[num].drop_duplicates(subset="NUM_POSTE").dropna(subset="NUM_POSTE")


#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #SORTING OUT DATAS PER DEPARTMENT
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# all datas from all timeframes for each department
weather_data_complete = {}
# We regroup every timeframes for each data types together
for region in weather_data.keys() : 
    weather_data_complete[region] = pd.concat(weather_data[region].values(), ignore_index=True)
    weather_data_complete[region]["NUM_POSTE"] = weather_data_complete[region]["NUM_POSTE"].astype(str).str[:-3]



#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR RAIN PER DEPARTMENT
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# rain data for all departments
rain_datas_dep = {}
for region in weather_data_complete.keys():
    rain_datas_dep[region] = weather_data_complete[region][["NUM_POSTE","NOM_USUEL","AAAAMMJJ","RR","DRR"]]
    rain_datas_dep[region] = rain_datas_dep[region].dropna(subset="RR")
rain_datas_dep["33"]

#---------------------------------------------------------------------------------------------------------------------------------------

# %%
# Sorting to have the mean per month per year for rain
# We also limit the datas starting from 1980 for rain datas because of the amount of data there is
mean_rain_per_month = {}

for dep in na_department_codes:
    df = rain_datas_dep[dep].copy()
    df["AAAAMM"] = df["AAAAMMJJ"].astype(str).str[:6]
    mean_rain_per_month[dep] = (
        df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])[["RR","DRR"]]
        .mean()
        .reset_index()
    )
    mean_rain_per_month[dep] = mean_rain_per_month[dep].merge(towns[dep], right_on ="NUM_POSTE", left_on ="NUM_POSTE", how="inner" ) 
    year = mean_rain_per_month[dep]["AAAAMM"].astype(int)/100
    mean_rain_per_month[dep] = mean_rain_per_month[dep][year>=1980]
    mean_rain_per_month[dep] = mean_rain_per_month[dep].reset_index()
    mean_rain_per_month[dep] = mean_rain_per_month[dep].drop('level_0', axis =1)
mean_rain_per_month["33"]

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR TEMPERATURES PER DEPARTMENT
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# temp data for all departments
temp_datas_dep = {}
for region in weather_data_complete.keys():
    temp_datas_dep[region] = weather_data_complete[region][["NUM_POSTE","NOM_USUEL","AAAAMMJJ","TN","TX","TM"]]
    temp_datas_dep[region] = temp_datas_dep[region].dropna(subset=["TN","TX","TM"], how="all")
temp_datas_dep["33"]

#----------------------------------------------------------------------------------------------------------------------------------------

# %%
# Sorting to have the mean per month per year for rain
  
mean_temp_per_month = {}

for dep in na_department_codes:
    df = temp_datas_dep[dep].copy()
    df["AAAAMM"] = df["AAAAMMJJ"].astype(str).str[:6]
    mean_temp_per_month[dep] = (
        df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])[["TN", "TX", "TM"]]
        .mean()
        .reset_index()
    )
    mean_temp_per_month[dep] = mean_temp_per_month[dep].merge(towns[dep], right_on ="NUM_POSTE", left_on ="NUM_POSTE", how="inner" ) 
mean_temp_per_month["33"]
    
        

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR WIND PER DEPARTMENT
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Pulling wind datas from the csv
wind_datas_dep = {}
for region in weather_data_complete.keys():
    wind_datas_dep[region] = weather_data_complete[region][["NUM_POSTE","NOM_USUEL","AAAAMMJJ","FFM","FXY","FXI","FXI2","FXI3S"]]
    wind_datas_dep[region] = wind_datas_dep[region].dropna(subset=["FFM","FXY","FXI","FXI2","FXI3S"], how="all")
wind_datas_dep["33"]

#---------------------------------------------------------------------------------------------------------------------------------------

#%%
# Sorting to have the mean per month per year for wind

mean_wind_per_month = {}
for dep in na_department_codes:
    df = wind_datas_dep[dep].copy()
    df["AAAAMM"] = df["AAAAMMJJ"].astype(str).str[:6]
    mean_wind_per_month[dep] = (
        df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])[["FFM","FXY","FXI","FXI2","FXI3S"]]
        .mean()
        .reset_index()
    )
    mean_wind_per_month[dep] = mean_wind_per_month[dep].dropna(subset=["FFM","FXY","FXI","FXI2","FXI3S"],how="all")
    mean_wind_per_month[dep] = mean_wind_per_month[dep].merge(towns[dep], right_on ="NUM_POSTE", left_on ="NUM_POSTE", how="inner" ) 

mean_wind_per_month["33"]

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR RISKS PER DEPARTMENT
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Getting datas from gaspard risks csv
risk = pd.read_csv("Jeu_de_donnee_sae/Jeu_de_donnee_sae/Donnees_base_gaspar/catnat_gaspar.csv", sep=";")
risk = risk[risk["cod_commune"].str.len() == 5]
risk = risk[risk["cod_commune"].str.startswith(na_department_codes)]
risk["lib_commune"] = risk["lib_commune"].str.upper()
risk

#--------------------------------------------------------------------------------------------------------------------------------------

# %%
# Risk per deps
risk_dep= {}
for dep in na_department_codes:
    risk_dep[dep] = risk[risk["cod_commune"].str.startswith(dep)]
    risk_dep[dep] = risk_dep[dep].merge(towns[dep], right_on ="NUM_POSTE", left_on ="cod_commune", how="inner" )
    risk_dep[dep] = risk_dep[dep][["cod_commune", "lib_commune", "lib_risque_jo","dat_deb", "dat_fin"]]
risk_dep["33"]

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR AIR QUALITY PER DEPARTMENT
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# pulling Air quality datas
air_quality_datas = pd.read_csv("Jeu_de_donnee_sae/Jeu_de_donnee_sae/Donnees_qualite_de_l'air/ind_nouvelle_aquitaine.csv")
air_quality_datas = air_quality_datas[["date_ech", "code_qual", "lib_qual", "coul_qual","date_dif", "lib_zone","code_zone", "epsg_reg", "geom"]]
air_quality_datas["code_zone"] = air_quality_datas["code_zone"].astype(str)
air_quality_datas["lib_zone"] = air_quality_datas["lib_zone"].str.upper()
air_quality_datas = air_quality_datas[air_quality_datas["code_zone"].str.len() == 5]
air_quality_datas = air_quality_datas[air_quality_datas["code_zone"].str.startswith(na_department_codes)]
air_quality_datas



#---------------------------------------------------------------------------------------------------------------------------------------

# %%
# Air quality per department
air_quality_datas_dep = {}

for dep in na_department_codes:
    air_quality_datas_dep[dep] = air_quality_datas[air_quality_datas["code_zone"].str.startswith(dep)]
    air_quality_datas_dep[dep] = air_quality_datas_dep[dep].merge(towns[dep],right_on ="NUM_POSTE", left_on ="code_zone", how="inner") 
air_quality_datas_dep["33"]

#---------------------------------------------------------------------------------------------------------------------------------------

# %%
# Sorting to have the mean per month per year for air quality

mean_air_quality_per_month = {}

for dep in na_department_codes:
    df = air_quality_datas_dep[dep].copy()
    df["AAAAMM"] = df["date_ech"].astype(str).str[:7].str.replace("-", "")
    mean_air_quality_per_month[dep] = (
        df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])["code_qual"]
        .mean()
        .round()
        .astype(int)
        .reset_index()
    )
    qual_mapping = air_quality_datas[["code_qual", "lib_qual", "coul_qual"]].drop_duplicates()
    mean_air_quality_per_month[dep] = mean_air_quality_per_month[dep].merge(qual_mapping, on="code_qual", how="left")

mean_air_quality_per_month["33"]

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #GETTING DATAS FOR GEOMETRY OF DEPARTMENTS
                                                        #AND SORTING THEM
#--------------------------------------------------------------------------------------------------------------------------------------#

# %%
# Getting geometry data for Nouvelle-Aquitaine department
import geopandas as gpd

geometry_dep = gpd.read_file("Jeu_de_donnee_sae/Jeu_de_donnee_sae/departements.geojson")
geometry_dep = geometry_dep[geometry_dep["code"].isin(na_department_codes)]
geometry_dep = geometry_dep.reset_index()
geometry_dep


#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #INSERTING DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#


#--------------------------------------------------------------------------------------------------------------------------------------#
                                                         #CONNEXION TO THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#

# %%
# Connection to server 
import pymssql

cnxn = pymssql.connect(server="info-mssql-etd", user="ETD", password="ETD", database="EQUIPE4_SAE")

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #INSERTING DEPARTMENTS
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Insert departments in database
cursor = cnxn.cursor()

cursor.execute("DELETE FROM T_Departement")

for dep in na_department_codes:
    name = geometry_dep[geometry_dep["code"] == dep]["nom"].values[0]
    cursor.execute(
        "INSERT INTO T_Departement(num_departement, lib_departement) VALUES (%s, %s)",
        (dep, name)
    )

cnxn.commit()
cursor.close()

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                    #INSERTING GEOMETRIES IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Inserting geometries
from shapely import wkt
cursor = cnxn.cursor()

cursor.execute("DELETE FROM T_Contour_Departement")

for dep in na_department_codes:
    geom = geometry_dep[geometry_dep["code"] == dep]["geometry"].values[0]
    geom_to_string = wkt.dumps(geom)
    cursor.execute("INSERT INTO T_Contour_Departement(num_departement, geometry) " \
    "VALUES ((SELECT num_departement FROM T_Departement WHERE num_departement = %s), %s)", 
    (dep,geom_to_string))
cnxn.commit()
cursor.close()
    

#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #INSERTING TOWNS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Inserting all towns in database
cursor = cnxn.cursor()

cursor.execute("DELETE FROM T_Commune")

for dep in na_department_codes:
    for index, town in towns[dep].iterrows():
        town_name = town["NOM_USUEL"]
        town_code = town["NUM_POSTE"]
        town_lat = town["LAT"]
        town_long = town["LON"]
        cursor.execute(
            "INSERT INTO T_Commune(code_commune, num_departement, lib_commune, latitude, longitude )" \
            "VALUES (%s,(SELECT num_departement FROM T_Departement WHERE num_departement = %s) ,%s, %s, %s)",
            (town_code, dep, town_name, town_lat, town_long)
        )

cnxn.commit()
cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
                                                        #INSERTING DATA TYPES IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
# %%
# Inserting all different data types
cursor = cnxn.cursor()
cursor.execute("DELETE FROM T_Type")

cursor.execute(
    "INSERT INTO T_Type(type) " \
    "VALUES ('risque'), ('cumul de précipitation')," \
    "('durée de précipitation'), ('température minimale')," \
    "('température maximale'), ('température moyenne'), ('Vitesse moyenne vent')," \
    "('rafale max quotidienne'), ('rafale max sur 1 heure'), ('rafale max sur 2 heures')," \
    "('rafale max sur 3 secondes'), ('qualité d''air')"
)

cnxn.commit()
cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
#                                               INSERTING TEMPERATURE DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
#%%
# Inserting temperature datas
cursor = cnxn.cursor()
cursor.execute(
    "DELETE FROM T_Donnees WHERE id_Type IN "
    "(SELECT id_Type FROM T_Type WHERE type IN "
    "('température minimale', 'température maximale', 'température moyenne'))"
)

cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'température minimale'")
id_min = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'température maximale'")
id_max = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'température moyenne'")
id_mean = cursor.fetchone()[0]

rows_min, rows_max, rows_mean = [], [], []

for dep in na_department_codes:
    print(f"département actuel : {dep}")
    for row in mean_temp_per_month[dep].itertuples():
        date = str(row.AAAAMM)
        date = f"{date[4:6]}-{date[0:4]}"
        postal = row.NUM_POSTE

        if not pd.isna(row.TN):
            rows_min.append((row.TN, id_min, date, postal))
        if not pd.isna(row.TX):
            rows_max.append((row.TX, id_max, date, postal))
        if not pd.isna(row.TM):
            rows_mean.append((row.TM, id_mean, date, postal))

sql = (
    "INSERT INTO T_Donnees(donnée, id_Type, date_deb, code_commune) "
    "VALUES (%s, %s, %s, "
    "(SELECT code_commune FROM T_Commune WHERE code_commune = %s))"
)

cursor.executemany(sql, rows_min)
cursor.executemany(sql, rows_max)
cursor.executemany(sql, rows_mean)
cnxn.commit()

cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
#                                               INSERTING WIND DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
#%%
#Inserting wind datas
cursor = cnxn.cursor()
cursor.execute(
    "DELETE FROM T_Donnees WHERE id_Type IN "
    "(SELECT id_Type FROM T_Type WHERE type IN "
    "('Vitesse moyenne vent', 'rafale max quotidienne', 'rafale max sur 1 heure','rafale max sur 2 heures', 'rafale max sur 3 secondes'))"
)

cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'Vitesse moyenne vent'")
id_wind_mean = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'rafale max quotidienne'")
id_max = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'rafale max sur 1 heure'")
id_1h = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'rafale max sur 2 heures'")
id_2h = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'rafale max sur 3 secondes'")
id_3s = cursor.fetchone()[0]

rows_1h, rows_max, rows_wind_mean, rows_2h, rows_3s = [], [], [], [], []

for dep in na_department_codes:
    print(f"département actuel : {dep}")
    for row in mean_wind_per_month[dep].itertuples():
        date = str(row.AAAAMM)
        date = f"{date[4:6]}-{date[0:4]}"
        postal = row.NUM_POSTE

        if not pd.isna(row.FXI3S):
            rows_3s.append((row.FXI3S, id_3s, date, postal))
        if not pd.isna(row.FXY):
            rows_max.append((row.FXY, id_max, date, postal))
        if not pd.isna(row.FFM):
            rows_wind_mean.append((row.FFM, id_wind_mean, date, postal))
        if not pd.isna(row.FXI):
            rows_1h.append((row.FXI, id_1h, date, postal))
        if not pd.isna(row.FXI2):
            rows_2h.append((row.FXI2, id_2h, date, postal))

sql = (
    "INSERT INTO T_Donnees(donnée, id_Type, date_deb, code_commune) "
    "VALUES (%s, %s, %s, "
    "(SELECT code_commune FROM T_Commune WHERE code_commune = %s))"
)

cursor.executemany(sql, rows_3s)
cursor.executemany(sql, rows_max)
cursor.executemany(sql, rows_wind_mean)
cursor.executemany(sql, rows_1h)
cursor.executemany(sql, rows_2h)

cnxn.commit()
cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
#                                               INSERTING AIR DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
#%%
# Inserting air quality datas
cursor = cnxn.cursor()
cursor.execute(
    "DELETE FROM T_Donnees WHERE id_Type IN "
    "(SELECT id_Type FROM T_Type WHERE type IN ('qualité d''air'))"
)

cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'qualité d''air'")
id_qual = cursor.fetchone()[0]

rows_air_qual = []

for dep in na_department_codes:
    print(f"département actuel : {dep}")
    for row in mean_air_quality_per_month[dep].itertuples():
        date = str(row.AAAAMM)
        date = f"{date[4:6]}-{date[0:4]}"
        postal = row.NUM_POSTE
        lib_qual = row.lib_qual
        coul_qual = row.coul_qual
        rows_air_qual.append((f"{lib_qual};{coul_qual}", id_qual, date, postal))

sql = (
    "INSERT INTO T_Donnees(donnée, id_Type, date_deb, code_commune) "
    "VALUES (%s, %s, %s, "
    "(SELECT code_commune FROM T_Commune WHERE code_commune = %s))"
)

cursor.executemany(sql, rows_air_qual)

cnxn.commit()
cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
#                                               INSERTING RAIN DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
#%%
# Inserting rain datas
cursor = cnxn.cursor()
cursor.execute(
    "DELETE FROM T_Donnees WHERE id_Type IN "
    "(SELECT id_Type FROM T_Type WHERE type IN "
    "('cumul de précipitation', 'durée de précipitation'))"
)

cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'cumul de précipitation'")
id_amount = cursor.fetchone()[0]
cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'durée de précipitation'")
id_length = cursor.fetchone()[0]

rows_length, rows_amount = [], []

for dep in na_department_codes:
    print(f"département actuel : {dep}")
    for row in mean_rain_per_month[dep].itertuples():
        date = str(row.AAAAMM)
        date = f"{date[4:6]}-{date[0:4]}"
        postal = row.NUM_POSTE

        if not pd.isna(row.RR):
            rows_amount.append((row.RR, id_amount, date, postal))
        if not pd.isna(row.DRR):
            rows_length.append((row.DRR, id_length, date, postal))

sql = (
    "INSERT INTO T_Donnees(donnée, id_Type, date_deb, code_commune) "
    "VALUES (%s, %s, %s, "
    "(SELECT code_commune FROM T_Commune WHERE code_commune = %s))"
)



cursor.executemany(sql, rows_amount)
cursor.executemany(sql, rows_length)
cnxn.commit()

cursor.close()


#--------------------------------------------------------------------------------------------------------------------------------------#
#                                               INSERTING RISK DATAS IN THE DATABASE
#--------------------------------------------------------------------------------------------------------------------------------------#
#%%
# Inserting risk datas
cursor = cnxn.cursor()
cursor.execute(
    "DELETE FROM T_Donnees WHERE id_Type IN "
    "(SELECT id_Type FROM T_Type WHERE type IN ('risque'))"
)

cursor.execute("SELECT id_Type FROM T_Type WHERE type = 'risque'")
id_risk = cursor.fetchone()[0]

rows_risk = []

for dep in na_department_codes:
    print(f"département actuel : {dep}")
    for row in risk_dep[dep].itertuples():
        date_deb = str(row.dat_deb)
        date_fin = str(row.dat_fin)
        annee_deb, month_deb, day_deb = date_deb.split("-")
        annee_fin, month_fin, day_fin = date_fin.split("-")
        date_deb = f"{day_deb}-{month_deb}-{annee_deb}"
        date_fin = f"{day_fin}-{month_fin}-{annee_fin}"
        postal = row.cod_commune

        if not pd.isna(row.lib_risque_jo):
            rows_risk.append((row.lib_risque_jo, id_risk, date_deb, date_fin, postal))

sql = (
    "INSERT INTO T_Donnees(donnée, id_Type, date_deb, date_fin, code_commune) "
    "VALUES (%s, %s, %s, %s, "
    "(SELECT code_commune FROM T_Commune WHERE code_commune = %s))"
)

cursor.executemany(sql, rows_risk)

cnxn.commit()
cursor.close()

